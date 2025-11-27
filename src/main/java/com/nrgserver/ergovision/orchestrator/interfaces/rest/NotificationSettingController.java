package com.nrgserver.ergovision.orchestrator.interfaces.rest;

import com.nrgserver.ergovision.orchestrator.domain.model.aggregates.NotificationSetting;
import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteNotificationSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetNotificationSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserNotificationSettingQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.NotificationSettingCommandService;
import com.nrgserver.ergovision.orchestrator.domain.services.NotificationSettingQueryService;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreateNotificationSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.NotificationSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdateNotificationSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.CreateNotificationSettingCommandFromResourceAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.NotificationSettingResourceFromEntityAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.UpdateNotificationSettingCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orchestrator/notification-settings")
@Tag(name = "Notification Settings", description = "Notification Settings Management Endpoints")
public class NotificationSettingController {
    private final NotificationSettingCommandService notificationSettingCommandService;
    private final NotificationSettingQueryService notificationSettingQueryService;

    public NotificationSettingController(NotificationSettingCommandService notificationSettingCommandService, NotificationSettingQueryService notificationSettingQueryService) {
        this.notificationSettingCommandService = notificationSettingCommandService;
        this.notificationSettingQueryService = notificationSettingQueryService;
    }

    @Operation(summary = "Create notification settings", description = "Create new notification settings for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Notification settings created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<NotificationSettingResource> createNotificationSetting( @Valid @RequestBody CreateNotificationSettingResource resource)
    {
        var command = CreateNotificationSettingCommandFromResourceAssembler.toCommandFromResource(resource);
        var notificationSettingsId = notificationSettingCommandService.handle(command);
        var notificationSetting = notificationSettingQueryService.handle(new GetNotificationSettingByIdQuery(notificationSettingsId));

        return notificationSetting.map( setting ->
                new ResponseEntity<>(NotificationSettingResourceFromEntityAssembler.toResourceFromEntity(setting), HttpStatus.CREATED))
        .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get notification settings by user ID", description = "Retrieve notification settings for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification settings found"),
            @ApiResponse(responseCode = "404", description = "Notification settings not found")
    })
    @GetMapping("user/{userId}")
    public ResponseEntity<NotificationSettingResource> getNotificationSettingByUserId(@PathVariable Long userId) {
        var notificationSetting = notificationSettingQueryService.handle(new GetUserNotificationSettingQuery(userId));
        return notificationSetting.map(setting ->
                        ResponseEntity.ok(NotificationSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get notification settings by ID", description = "Retrieve notification settings by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification settings found"),
            @ApiResponse(responseCode = "404", description = "Notification settings not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<NotificationSettingResource> getNotificationSettingById(@PathVariable Long id) {
        var notificationSetting = notificationSettingQueryService.handle(new GetNotificationSettingByIdQuery(id));
        return notificationSetting.map(setting ->
                        ResponseEntity.ok(NotificationSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update notification settings", description = "Update existing notification settings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notification settings updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Notification settings not found")
    })
    @PutMapping("/{id}")
    public ResponseEntity<NotificationSettingResource> updateNotificationSetting(@PathVariable Long id, @Valid @RequestBody UpdateNotificationSettingResource resource) {
        var command = UpdateNotificationSettingCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var updatedNotificationSettingId = notificationSettingCommandService.handle(command);
        var notificationSetting = notificationSettingQueryService.handle(new GetNotificationSettingByIdQuery(updatedNotificationSettingId));

        return notificationSetting.map(setting ->
                        ResponseEntity.ok(NotificationSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete notification settings", description = "Delete notification settings by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Notification settings deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Notification settings not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificationSetting(@PathVariable Long id) {
        var command = new DeleteNotificationSettingCommand(id);
        notificationSettingCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}
