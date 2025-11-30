package com.nrgserver.ergovision.statistics.interfaces.rest;

import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteDailyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetDailyProgressByIdQuery;
import com.nrgserver.ergovision.statistics.domain.services.DailyProgressCommandService;
import com.nrgserver.ergovision.statistics.domain.services.DailyProgressQueryService;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateDailyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.DailyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.OnlyDailyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.CreateDailyProgressCommandFromResourceAssembler;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.DailyProgressResourceFromEntityAssembler;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.UpdateDailyProgressCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "**",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/dailyProgress",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "DailyProgress",description = "Daily Progress Management Endpoints")
public class DailyProgressController {
    private final DailyProgressQueryService dailyProgressQueryService;
    private final DailyProgressCommandService dailyProgressCommandService;

    public DailyProgressController(DailyProgressQueryService dailyProgressQueryService, DailyProgressCommandService dailyProgressCommandService) {
        this.dailyProgressQueryService = dailyProgressQueryService;
        this.dailyProgressCommandService = dailyProgressCommandService;
    }
    @PostMapping()
    public ResponseEntity<DailyProgressResource> create(@RequestBody CreateDailyProgressResource resource){
        var createDailyProgressCommand = CreateDailyProgressCommandFromResourceAssembler.toCommandFromResource(resource);
        var dailyProgressId = this.dailyProgressCommandService.handle(createDailyProgressCommand);
        if (dailyProgressId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }
        var getDailyProgressByIdQuery = new GetDailyProgressByIdQuery(dailyProgressId);
        var optionalDailyProgress = this.dailyProgressQueryService.handle(getDailyProgressByIdQuery);
        var dailyProgressResource = DailyProgressResourceFromEntityAssembler.toResourceFromEntity(optionalDailyProgress.get());
        return new ResponseEntity<>(dailyProgressResource,HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DailyProgressResource> update(@PathVariable Long id, @RequestBody OnlyDailyProgressResource resource){
        var updatedDailyProgressCommand = UpdateDailyProgressCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalDailyProgress = this.dailyProgressCommandService.handle(updatedDailyProgressCommand);
        if (optionalDailyProgress.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var dailyProgressResource = DailyProgressResourceFromEntityAssembler.toResourceFromEntity(optionalDailyProgress.get());
        return ResponseEntity.ok(dailyProgressResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        var deleteDailyProgressCommand = new DeleteDailyProgressCommand(id);
        this.dailyProgressCommandService.handle(deleteDailyProgressCommand);
        return ResponseEntity.noContent().build();
    }
    //###################################QUERIES ##########################################
    @GetMapping("/{id}")
    public ResponseEntity<DailyProgressResource> getById(@PathVariable Long id){
        var getDailyProgressByIdQuery = new GetDailyProgressByIdQuery(id);
        var optionalDailyProgress = this.dailyProgressQueryService.handle(getDailyProgressByIdQuery);
        if (optionalDailyProgress.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var dailyProgressResource = DailyProgressResourceFromEntityAssembler.toResourceFromEntity(optionalDailyProgress.get());
        return ResponseEntity.ok(dailyProgressResource);
    }
}
