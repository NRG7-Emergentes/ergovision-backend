package com.nrgserver.ergovision.orchestrator.interfaces.rest;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteAlertsSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetAlertSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserAlertSettingQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.AlertsSettingCommandService;
import com.nrgserver.ergovision.orchestrator.domain.services.AlertsSettingQueryService;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.AlertSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreateAlertSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdateAlertSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.AlertSettingResourceFromEntityAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.CreateAlertsSettingCommandFromResourceAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.UpdateAlertsSettingCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orchestrator/alerts-settings")
@Tag(name = "Alert Settings", description = "Alert Settings Management Endpoints")
public class AlertsSettingController {
    
    private final AlertsSettingCommandService alertsSettingCommandService;
    private final AlertsSettingQueryService alertsSettingQueryService;
    
    public AlertsSettingController(AlertsSettingCommandService alertsSettingCommandService,
                                  AlertsSettingQueryService alertsSettingQueryService) {
        this.alertsSettingCommandService = alertsSettingCommandService;
        this.alertsSettingQueryService = alertsSettingQueryService;
    }
    
    @Operation(summary = "Create alert settings", description = "Create new alert settings for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alert settings created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<AlertSettingResource> createAlertsSetting(
            @Valid @RequestBody CreateAlertSettingResource resource) {
        
        var command = CreateAlertsSettingCommandFromResourceAssembler.toCommandFromResource(resource);
        var settingId = alertsSettingCommandService.handle(command);
        var alertSetting = alertsSettingQueryService.handle(new GetAlertSettingByIdQuery(settingId));
        
        return alertSetting.map(setting -> 
                new ResponseEntity<>(AlertSettingResourceFromEntityAssembler.toResourceFromEntity(setting), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    
    @Operation(summary = "Get alert settings by user ID", description = "Retrieve alert settings for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert settings found"),
            @ApiResponse(responseCode = "404", description = "Alert settings not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<AlertSettingResource> getAlertsSetting(@PathVariable Long userId) {
        var query = new GetUserAlertSettingQuery(userId);
        var alertSetting = alertsSettingQueryService.handle(query);
        
        return alertSetting.map(setting -> 
                ResponseEntity.ok(AlertSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get alert settings by ID", description = "Retrieve alert settings by setting ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert settings found"),
            @ApiResponse(responseCode = "404", description = "Alert settings not found")
    })
    @GetMapping("/{settingId}")
    public ResponseEntity<AlertSettingResource> getAlertsSettingById(@PathVariable Long settingId) {
        var query = new GetAlertSettingByIdQuery(settingId);
        var alertSetting = alertsSettingQueryService.handle(query);
        
        return alertSetting.map(setting -> 
                ResponseEntity.ok(AlertSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Update alert settings", description = "Update existing alert settings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert settings updated successfully"),
            @ApiResponse(responseCode = "404", description = "Alert settings not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{settingId}")
    public ResponseEntity<AlertSettingResource> updateAlertsSetting(
            @PathVariable Long settingId,
            @Valid @RequestBody UpdateAlertSettingResource resource) {
        
        var command = UpdateAlertsSettingCommandFromResourceAssembler.toCommandFromResource(settingId, resource);
        var updatedSettingId = alertsSettingCommandService.handle(command);
        var alertSetting = alertsSettingQueryService.handle(new GetAlertSettingByIdQuery(updatedSettingId));
        
        return alertSetting.map(setting -> 
                ResponseEntity.ok(AlertSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    
    @Operation(summary = "Delete alert settings", description = "Delete alert settings by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alert settings deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Alert settings not found")
    })
    @DeleteMapping("/{settingId}")
    public ResponseEntity<Void> deleteAlertsSetting(@PathVariable Long settingId) {
        var command = new DeleteAlertsSettingCommand(settingId);
        alertsSettingCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}
