package com.nrgserver.ergovision.statistics.interfaces.rest;

import com.nrgserver.ergovision.statistics.domain.model.commands.DeleteStatisticsCommand;
import com.nrgserver.ergovision.statistics.domain.model.queries.GetStatisticsByIdQuery;
import com.nrgserver.ergovision.statistics.domain.services.StatisticsCommandService;
import com.nrgserver.ergovision.statistics.domain.services.StatisticsQueryService;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.CreateStatisticsResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.resources.StatisticsResource;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.CreateStatisticsCommandFromResourceAssembler;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.StatisticsResourceFromEntityAssembler;
import com.nrgserver.ergovision.statistics.interfaces.rest.transform.UpdateStatisticsCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "**",methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/statistics",produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Statistics",description = "Statistics Management Endpoints")
public class StatisticsController {
    private final StatisticsCommandService statisticsCommandService;
    private final StatisticsQueryService statisticsQueryService;

    public StatisticsController(StatisticsCommandService statisticsCommandService, StatisticsQueryService statisticsQueryService) {
        this.statisticsCommandService = statisticsCommandService;
        this.statisticsQueryService = statisticsQueryService;
    }

    @PostMapping
    public ResponseEntity<StatisticsResource> create(@RequestBody CreateStatisticsResource resource) {
        var createStatisticsCommand = CreateStatisticsCommandFromResourceAssembler.toCommandFromResource(resource);
        var statisticsId = this.statisticsCommandService.handle(createStatisticsCommand);
        if (statisticsId.equals(0L)) {
            return ResponseEntity.badRequest().build();

        }
        var getStatisticsByIdQuery = new GetStatisticsByIdQuery(statisticsId);
        var optionalStatistics = this.statisticsQueryService.handle(getStatisticsByIdQuery);
        var statisticsResource = StatisticsResourceFromEntityAssembler.toResourceFromEntity(optionalStatistics.get());
        return new ResponseEntity<>(statisticsResource, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<StatisticsResource> update(@PathVariable Long id,@RequestBody StatisticsResource resource) {
        var updateStatisticsCommand = UpdateStatisticsCommandFromResourceAssembler.toCommandFromResource(id, resource);
        var optionalStatistics = this.statisticsCommandService.handle(updateStatisticsCommand);
        if (optionalStatistics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var statisticsResource = StatisticsResourceFromEntityAssembler.toResourceFromEntity(optionalStatistics.get());
        return ResponseEntity.ok(statisticsResource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        var deleteStatisticsCommand = new DeleteStatisticsCommand(id);
        this.statisticsCommandService.handle(deleteStatisticsCommand);
        return ResponseEntity.noContent().build();
    }

    //###################################QUERIES ##########################################

    @GetMapping("/{id}")
    public ResponseEntity<StatisticsResource> getById(@PathVariable Long id) {
        var getStatisticsByIdQuery = new GetStatisticsByIdQuery(id);
        var optionalStatistics = this.statisticsQueryService.handle(getStatisticsByIdQuery);
        if (optionalStatistics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var statisticsResource = StatisticsResourceFromEntityAssembler.toResourceFromEntity(optionalStatistics.get());
        return ResponseEntity.ok(statisticsResource);
    }
}
