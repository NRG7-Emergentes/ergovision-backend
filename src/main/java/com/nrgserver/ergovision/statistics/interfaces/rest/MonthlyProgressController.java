package com.nrgserver.ergovision.statistics.interfaces.rest;

import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteMonthlyProgressCommand;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetDailyProgressByIdQuery;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetMonthlyProgressByIdQuery;
import com.nrgserver.ergovision.statistics.domain.services.MonthlyProgressCommandService;
import com.nrgserver.ergovision.statistics.domain.services.MonthlyProgressQueryService;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateMonthlyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.DailyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.MonthlyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.OnlyMonthlyProgressResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.CreateMonthlyProgressCommandFromResourceAssembler;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.DailyProgressResourceFromEntityAssembler;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.MonthlyProgressResourceFromEntityAssembler;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.UpdateMonthlyProgressCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "**",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/monthlyProgress",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "MonthlyProgress",description = "Monthly Progress Management Endpoints")
public class MonthlyProgressController {
    private final MonthlyProgressCommandService monthlyProgressCommandService;
    private final MonthlyProgressQueryService monthlyProgressQueryService;

    public MonthlyProgressController(MonthlyProgressCommandService monthlyProgressCommandService, MonthlyProgressQueryService monthlyProgressQueryService) {
        this.monthlyProgressCommandService = monthlyProgressCommandService;
        this.monthlyProgressQueryService = monthlyProgressQueryService;
    }
    @PostMapping()
    public ResponseEntity<MonthlyProgressResource> create(@RequestBody CreateMonthlyProgressResource resource){
        var createMonthlyProgressCommand = CreateMonthlyProgressCommandFromResourceAssembler.toCommandFromResource(resource);
        var monthlyProgressId = this.monthlyProgressCommandService.handle(createMonthlyProgressCommand);
        if (monthlyProgressId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }
        var getMonthlyProgressByIdQuery = new GetMonthlyProgressByIdQuery(monthlyProgressId);
        var optionalMonthlyProgress = this.monthlyProgressQueryService.handle(getMonthlyProgressByIdQuery);
        var monthlyProgressResource = MonthlyProgressResourceFromEntityAssembler.toResourceFromEntity(optionalMonthlyProgress.get());
        return new ResponseEntity<>(monthlyProgressResource,HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MonthlyProgressResource> update(@PathVariable Long id, @RequestBody OnlyMonthlyProgressResource resource){
        var updatedMonthlyProgressCommand = UpdateMonthlyProgressCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalMonthlyProgress = this.monthlyProgressCommandService.handle(updatedMonthlyProgressCommand);
        if (optionalMonthlyProgress.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var monthlyProgressResource = MonthlyProgressResourceFromEntityAssembler.toResourceFromEntity(optionalMonthlyProgress.get());
        return ResponseEntity.ok(monthlyProgressResource);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        var deleteMonthlyProgressCommand = new DeleteMonthlyProgressCommand(id);
        this.monthlyProgressCommandService.handle(deleteMonthlyProgressCommand);
        return ResponseEntity.noContent().build();
    }
    //###################################QUERIES ##########################################
    @GetMapping("/{id}")
    public ResponseEntity<MonthlyProgressResource> getById(@PathVariable Long id){
        var getMonthlyProgressByIdQuery = new GetMonthlyProgressByIdQuery(id);
        var optionalMonthlyProgress = this.monthlyProgressQueryService.handle(getMonthlyProgressByIdQuery);
        if (optionalMonthlyProgress.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var monthlyProgressResource = MonthlyProgressResourceFromEntityAssembler.toResourceFromEntity(optionalMonthlyProgress.get());
        return ResponseEntity.ok(monthlyProgressResource);
    }
}
