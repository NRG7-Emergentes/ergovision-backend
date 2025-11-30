package com.nrgserver.ergovision.monitoring.interfaces.rest;

import com.nrgserver.ergovision.monitoring.domain.model.commands.DeleteMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetMonitoringSessionByUserIdQuery;
import com.nrgserver.ergovision.monitoring.domain.services.MonitoringSessionCommandService;
import com.nrgserver.ergovision.monitoring.domain.services.MonitoringSessionQueryService;
import com.nrgserver.ergovision.monitoring.interfaces.rest.resources.CreateMonitoringSessionResource;
import com.nrgserver.ergovision.monitoring.interfaces.rest.resources.MonitoringSessionResource;
import com.nrgserver.ergovision.monitoring.interfaces.rest.transform.CreateMonitoringSessionCommandFromResourceAssembler;
import com.nrgserver.ergovision.monitoring.interfaces.rest.transform.MonitoringSessionResourceFromEntityAssembler;
import com.nrgserver.ergovision.monitoring.interfaces.rest.transform.UpdateMonitoringSessionCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "**",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.PATCH,RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/monitoringSession",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "MonitoringSession",description = "Monitoring Session Management Endpoints")
public class MonitoringSessionController {
    private final MonitoringSessionQueryService monitoringSessionQueryService;
    private final MonitoringSessionCommandService monitoringSessionCommandService;

    public MonitoringSessionController(MonitoringSessionQueryService monitoringSessionQueryService, MonitoringSessionCommandService monitoringSessionCommandService) {
        this.monitoringSessionQueryService = monitoringSessionQueryService;
        this.monitoringSessionCommandService = monitoringSessionCommandService;
    }

    @PostMapping()
    public ResponseEntity<MonitoringSessionResource> create(@RequestBody CreateMonitoringSessionResource resource) {
        var createMonitoringSessionCommand = CreateMonitoringSessionCommandFromResourceAssembler.toCommandFromResource(resource);
        var monitoringSessionId = this.monitoringSessionCommandService.handle(createMonitoringSessionCommand);
        if (monitoringSessionId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }
        var getMonitoringSessionByIdQuery = new GetMonitoringSessionByIdQuery(monitoringSessionId);
        var optionalMonitoringSession = this.monitoringSessionQueryService.handle(getMonitoringSessionByIdQuery);
        var monitoringSessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(optionalMonitoringSession.get());
        return new ResponseEntity<>(monitoringSessionResource, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonitoringSessionResource> update(@PathVariable Long id, @RequestBody MonitoringSessionResource resource) {
        var updateMonitoringSessionCommand = UpdateMonitoringSessionCommandFromResourceAssembler.toCommandFromResource(id,resource);
        var optionalMonitoringSession = this.monitoringSessionCommandService.handle(updateMonitoringSessionCommand);
        if (optionalMonitoringSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var monitoringSessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(optionalMonitoringSession.get());
        return ResponseEntity.ok(monitoringSessionResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var deleteMonitoringSessionCommand = new DeleteMonitoringSessionCommand(id);
        this.monitoringSessionCommandService.handle(deleteMonitoringSessionCommand);
        return ResponseEntity.noContent().build();
    }

    /// ################################# QUERIES #################################
    @GetMapping("/{id}")
    public ResponseEntity<MonitoringSessionResource> getById(@PathVariable Long id) {
        var getMonitoringSessionByIdQuery = new GetMonitoringSessionByIdQuery(id);
        var optionalMonitoringSession = this.monitoringSessionQueryService.handle(getMonitoringSessionByIdQuery);
        if (optionalMonitoringSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var monitoringSessionResource = MonitoringSessionResourceFromEntityAssembler.toResourceFromEntity(optionalMonitoringSession.get());
        return ResponseEntity.ok(monitoringSessionResource);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MonitoringSessionResource>> getByUserId(@PathVariable Long userId) {
        var getMonitoringSessionByUserIdQuery = new GetMonitoringSessionByUserIdQuery(userId);
        var monitoringSessions = this.monitoringSessionQueryService.handle(getMonitoringSessionByUserIdQuery);
        if (monitoringSessions.isEmpty()) {
            return ResponseEntity.notFound().build();

        }
        var monitoringSessionResources = monitoringSessions.stream()
                .map(MonitoringSessionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(monitoringSessionResources);
    }
}
