package com.nrgserver.ergovision.orchestrator.interfaces.rest;

import com.nrgserver.ergovision.orchestrator.domain.model.commands.DeleteCalibrationDetailsCommand;
import com.nrgserver.ergovision.orchestrator.domain.model.queries.GetCalibrationDetailsByIdQuery;
import com.nrgserver.ergovision.orchestrator.domain.services.CalibrationDetailsCommandService;
import com.nrgserver.ergovision.orchestrator.domain.services.CalibrationDetailsQueryService;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CalibrationDetailsResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.CreateCalibrationDetailsResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.resources.UpdateCalibrationDetailsResource;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.CalibrationDetailsResourceFromEntityAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.CreateCalibrationDetailsCommandFromResourceAssembler;
import com.nrgserver.ergovision.orchestrator.interfaces.rest.transform.UpdateCalibrationDetailsCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orchestrator/calibration-details")
@Tag(name = "Calibration Details", description = "Calibration Details Management Endpoints")
public class CalibrationDetailsController {
    private final CalibrationDetailsCommandService calibrationDetailsCommandService;
    private final CalibrationDetailsQueryService calibrationDetailsQueryService;

    public CalibrationDetailsController(CalibrationDetailsCommandService calibrationDetailsCommandService, CalibrationDetailsQueryService calibrationDetailsQueryService) {
        this.calibrationDetailsCommandService = calibrationDetailsCommandService;
        this.calibrationDetailsQueryService = calibrationDetailsQueryService;
    }

    @Operation(summary = "Create calibration details", description = "Create new calibration details for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Calibration details created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<CalibrationDetailsResource> createCalibrationDetails(@Valid @RequestBody CreateCalibrationDetailsResource resource) {
        var command = CreateCalibrationDetailsCommandFromResourceAssembler.toCommandFromResource(resource);
        var calibrationId = calibrationDetailsCommandService.handle(command);
        var calibrationDetails = calibrationDetailsQueryService.handle(new GetCalibrationDetailsByIdQuery(calibrationId));

        return calibrationDetails.map(details ->
                new ResponseEntity<>(CalibrationDetailsResourceFromEntityAssembler.toResourceFromEntity(details), HttpStatus.CREATED))
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @Operation(summary = "Get calibration details by user ID", description = "Retrieve calibration details for a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calibration details found"),
            @ApiResponse(responseCode = "404", description = "Calibration details not found")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<CalibrationDetailsResource> getCalibrationDetailsByUserId(@PathVariable Long userId) {
        var query = new GetCalibrationDetailsByIdQuery(userId);
        var calibrationDetails = calibrationDetailsQueryService.handle(query);

        return calibrationDetails.map(details ->
                ResponseEntity.ok(CalibrationDetailsResourceFromEntityAssembler.toResourceFromEntity(details)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get calibration details by ID", description = "Retrieve calibration details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calibration details found"),
            @ApiResponse(responseCode = "404", description = "Calibration details not found")
    })
    @GetMapping("/{calibrationId}")
    public ResponseEntity<CalibrationDetailsResource> getCalibrationDetailsById(@PathVariable Long calibrationId) {
        var query = new GetCalibrationDetailsByIdQuery(calibrationId);
        var calibrationDetails = calibrationDetailsQueryService.handle(query);

        return calibrationDetails.map(details ->
                        ResponseEntity.ok(CalibrationDetailsResourceFromEntityAssembler.toResourceFromEntity(details)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update calibration details", description = "Update existing calibration details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calibration details updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Calibration details not found")
    })
    @PutMapping("/{calibrationId}")
    public ResponseEntity<CalibrationDetailsResource> updateCalibrationDetails(@PathVariable Long calibrationId, @Valid @RequestBody UpdateCalibrationDetailsResource resource) {
        var command = UpdateCalibrationDetailsCommandFromResourceAssembler.toCommandFromResource(calibrationId, resource);
        var updatedCalibrationId = calibrationDetailsCommandService.handle(command);
        var calibrationDetails = calibrationDetailsQueryService.handle(new GetCalibrationDetailsByIdQuery(updatedCalibrationId));

        return calibrationDetails.map(details ->
                        ResponseEntity.ok(CalibrationDetailsResourceFromEntityAssembler.toResourceFromEntity(details)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete calibration details", description = "Delete calibration details by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Calibration details deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Calibration details not found")
    })
    @DeleteMapping("/{calibrationId}")
    public ResponseEntity<Void> deleteCalibrationDetails(@PathVariable Long calibrationId) {
        var command = new DeleteCalibrationDetailsCommand(calibrationId);
        calibrationDetailsCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}
