package com.nrgserver.ergovision.monitoring.interfaces.rest;

import com.nrgserver.ergovision.iam.infrastructure.authorization.sfs.model.UserDetailsImpl;
import com.nrgserver.ergovision.monitoring.domain.model.commands.DeleteMonitoringSessionCommand;
import com.nrgserver.ergovision.monitoring.domain.model.queries.GetMonitoringSessionByIdQuery;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.nrgserver.ergovision.monitoring.domain.model.queries.GetAllMonitoringSessionsByUserIdQuery;
import java.util.List;
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

    private Long getUserIdFromContext() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var principal = auth.getPrincipal();
        if (principal instanceof UserDetailsImpl userDetails) {
            return userDetails.getId();
        }
        throw new RuntimeException("Invalid principal type");
    }

    @PostMapping()
    public ResponseEntity<MonitoringSessionResource> create(@RequestBody CreateMonitoringSessionResource resource) {
        Long userId = getUserIdFromContext();
        var createMonitoringSessionCommand = CreateMonitoringSessionCommandFromResourceAssembler.toCommandFromResource(resource, userId);
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
    @Operation(summary = "Get all monitoring sessions by user ID", description = "Retrieves all monitoring sessions for a specific user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No sessions found")
    })
    public ResponseEntity<List<MonitoringSessionResource>> getAllByUserId(@PathVariable Long userId) {
        var getAllMonitoringSessionsByUserIdQuery = new GetAllMonitoringSessionsByUserIdQuery(userId);
        var sessions = this.monitoringSessionQueryService.handle(getAllMonitoringSessionsByUserIdQuery);
        if (sessions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var sessionResources = sessions.stream()
                .map(MonitoringSessionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessionResources);
    }

    @GetMapping("/me")
    @Operation(summary = "Get all monitoring sessions for current user", description = "Retrieves all monitoring sessions for the currently authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sessions retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No sessions found")
    })
    public ResponseEntity<List<MonitoringSessionResource>> getMyMonitoringSessions() {
        Long userId = getUserIdFromContext();
        var getAllMonitoringSessionsByUserIdQuery = new GetAllMonitoringSessionsByUserIdQuery(userId);
        var sessions = this.monitoringSessionQueryService.handle(getAllMonitoringSessionsByUserIdQuery);
        if (sessions.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var sessionResources = sessions.stream()
                .map(MonitoringSessionResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(sessionResources);
    }
}
