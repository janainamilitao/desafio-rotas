package br.radixeng.controller;

import br.radixeng.exception.GraphNotFoundException;
import br.radixeng.model.Graph;
import br.radixeng.response.RoutePathResponse;
import br.radixeng.response.RouteMinimalResponse;
import br.radixeng.request.EdgeRequest;
import br.radixeng.service.GraphService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Janaina Militão
 */
@RestController
@Api(tags = "Graph", description = "Operations related to Graph")
public class GraphController {

    @Autowired
    private GraphService graphService;


    private static final String APPLICATION_JSON  = "application/json";

    @ApiOperation(value = "Save Graph")
    @RequestMapping(value = "/graph", method= RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<Graph> createGraph(@RequestBody List<EdgeRequest> edgeRequest) {
        return new ResponseEntity<>(graphService.saveGraph(edgeRequest), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Recover Graph", response = Graph.class)
    @GetMapping(value = "/graph/{graphId}")
    public ResponseEntity<Graph> recoverGraph(@PathVariable("graphId") Long graphId) throws GraphNotFoundException {
       try {
            return new ResponseEntity<>( graphService.recoverGraph(graphId), HttpStatus.OK);
        } catch (GraphNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Find Routes")
    @RequestMapping(value = "/routes/{graphId}/from/{town1}/to/{town2}", method= RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<List<RoutePathResponse>> findRoutes(@PathVariable("graphId") Long graphId,
                                                              @PathVariable("town1") String town1,
                                                              @PathVariable("town2") String town2,
                                                              @RequestParam(value = "maxStops", required=false) Integer maxStops)  {
        try {
            return new ResponseEntity<>(graphService.findRoutes(graphId, town1, town2, maxStops), HttpStatus.OK);
        } catch (GraphNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Find Route Minimal")
    @GetMapping(value = "/distance/{graphId}/from/{town1}/to/{town2}")
    public ResponseEntity<RouteMinimalResponse> findRouteMinimal(@PathVariable("graphId") Long graphId,
                                                                 @PathVariable("town1") String town1,
                                                                 @PathVariable("town2") String town2) {
        try {
            return new ResponseEntity<>( graphService.findMinimalRoute(graphId, town1, town2), HttpStatus.OK);
        } catch (GraphNotFoundException e) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
