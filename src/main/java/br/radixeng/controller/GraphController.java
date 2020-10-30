package br.radixeng.controller;

import br.radixeng.exception.GraphNotFoundException;
import br.radixeng.model.Graph;
import br.radixeng.model.Route;
import br.radixeng.request.EdgeRequest;
import br.radixeng.service.GraphService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@Api(tags = "Graph", description = "Operations related to Graph")
public class GraphController {

    @Autowired
    private GraphService graphService;


    private static final String APPLICATION_JSON  = "application/json";

    @ApiOperation(value = "Save Graph")
    @RequestMapping(value = "/graph", method= RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public Graph createGraph(@RequestBody List<EdgeRequest> edgeRequest) {
        return graphService.saveGraph(edgeRequest);
    }

    @ApiOperation(value = "Recover Graph", response = Graph.class)
    @GetMapping(value = "/graph/{graphId}")
    public Graph recoverGraph(@PathVariable("graphId") Long graphId) throws GraphNotFoundException {
        return graphService.recoverGraph(graphId);
    }

    @ApiOperation(value = "Find Routes")
    @GetMapping(value = "/routes/{graphId}/from/{town1}/to/{town2}")
    public List<Route> findRoutes(@PathVariable("graphId") Long graphId,
                              @PathVariable("town1") String town1,
                              @PathVariable("town2") String town2,
                              @RequestParam(value = "maxStops") int maxStops) throws GraphNotFoundException {
        return graphService.findRoutes(graphId, town1, town2, maxStops);
    }

}
