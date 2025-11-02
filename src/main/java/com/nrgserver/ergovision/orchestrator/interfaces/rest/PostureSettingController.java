package com.nrgserver.ergovision.orchestrator.interfaces.rest;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeletePostureSettingCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetPostureSettingByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetUserPostureSettingQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.PostureSettingCommandService;
import com.nrgserver.ergovision.orchestrator.domain.services.PostureSettingQueryService;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreatePostureSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.PostureSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdatePostureSettingResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.CreatePostureSettingCommandFromResourceAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.PostureSettingResourceFromEntityAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.UpdatePostureSettingCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller: PostureSettingController
 * REST controller for managing posture settings.
 */
@RestController
@RequestMapping("/api/v1/orchestrator/posture-settings")
@Tag(name = "Posture Settings", description = "Posture Settings Management Endpoints")
public class PostureSettingController {
    
    private final PostureSettingCommandService postureSettingCommandService;
    private final PostureSettingQueryService postureSettingQueryService;
    
    public PostureSettingController(PostureSettingCommandService postureSettingCommandService,
                                   PostureSettingQueryService postureSettingQueryService) {
        this.postureSettingCommandService = postureSettingCommandService;
        this.postureSettingQueryService = postureSettingQueryService;
    }
    
    @Operation(summary = "Create posture settings", description = "Create new posture settings for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Posture settings created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<PostureSettingResource> createPostureSetting(
            @Valid @RequestBody CreatePostureSettingResource resource) {
        
        var command = CreatePostureSettingCommandFromResourceAssembler.toCommandFromResource(resource);
        var settingId = postureSettingCommandService.handle(command);
        var postureSetting = postureSettingQueryService.handle(new GetPostureSettingByIdQuery(settingId));
        
        return postureSetting.map(setting -> 
                new ResponseEntity<>(PostureSettingResourceFromEntityAssembler.toResourceFromEntity(setting), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    
    @Operation(summary = "Get posture settings by user ID", description = "Retrieve posture settings for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posture settings found"),
            @ApiResponse(responseCode = "404", description = "Posture settings not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<PostureSettingResource> getPostureSetting(@PathVariable Long userId) {
        var query = new GetUserPostureSettingQuery(userId);
        var postureSetting = postureSettingQueryService.handle(query);
        
        return postureSetting.map(setting -> 
                ResponseEntity.ok(PostureSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get posture settings by ID", description = "Retrieve posture settings by setting ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posture settings found"),
            @ApiResponse(responseCode = "404", description = "Posture settings not found")
    })
    @GetMapping("/{settingId}")
    public ResponseEntity<PostureSettingResource> getPostureSettingById(@PathVariable Long settingId) {
        var query = new GetPostureSettingByIdQuery(settingId);
        var postureSetting = postureSettingQueryService.handle(query);
        
        return postureSetting.map(setting -> 
                ResponseEntity.ok(PostureSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Update posture settings", description = "Update existing posture settings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Posture settings updated successfully"),
            @ApiResponse(responseCode = "404", description = "Posture settings not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{settingId}")
    public ResponseEntity<PostureSettingResource> updatePostureSetting(
            @PathVariable Long settingId,
            @Valid @RequestBody UpdatePostureSettingResource resource) {
        
        var command = UpdatePostureSettingCommandFromResourceAssembler.toCommandFromResource(settingId, resource);
        var updatedSettingId = postureSettingCommandService.handle(command);
        var postureSetting = postureSettingQueryService.handle(new GetPostureSettingByIdQuery(updatedSettingId));
        
        return postureSetting.map(setting -> 
                ResponseEntity.ok(PostureSettingResourceFromEntityAssembler.toResourceFromEntity(setting)))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
    
    @Operation(summary = "Delete posture settings", description = "Delete posture settings by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Posture settings deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Posture settings not found")
    })
    @DeleteMapping("/{settingId}")
    public ResponseEntity<Void> deletePostureSetting(@PathVariable Long settingId) {
        var command = new DeletePostureSettingCommand(settingId);
        postureSettingCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}
