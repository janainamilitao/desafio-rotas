package br.radixeng.service;

import br.radixeng.exception.GraphNotFoundException;
import br.radixeng.model.*;
import br.radixeng.request.EdgeRequest;
import br.radixeng.repository.EdgeRepository;
import br.radixeng.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GraphService {

    @Autowired
    private GraphRepository graphRepository;

    public Graph  saveGraph(List<EdgeRequest> edgeRequestList){

        Graph graph = createGraph(edgeRequestList);
        return graphRepository.save(graph);
    }

    private Graph createGraph(List<EdgeRequest> edgeRequestList) {

        Graph graph = new Graph();

        graph.setEdges(new ArrayList<>());
        edgeRequestList.stream().forEach( e-> {
            Edge edge = e.convertObject(e);
            graph.getEdges().add(edge);

        });

        return graph;

    }

    public Graph recoverGraph(Long graphId) throws GraphNotFoundException {
       Optional<Graph> graph = graphRepository.findById(graphId);

       if(graph.isPresent()){
           return graph.get();
       }else {
           throw new GraphNotFoundException();
       }
    }



    public List<Route> findRoutes(Long graphId, String town1, String town2, int maxStops) throws GraphNotFoundException {

        Graph graph = recoverGraph(graphId);

        List<Route> routes = new ArrayList<>();

        return routes;
    }


    public RouteMinimalDTO findMinimalRoute(Long graphId, String town1, String town2) throws GraphNotFoundException {

        Graph graph = recoverGraph(graphId);
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        dijkstraAlgorithm.execute(new Vertex(town1));
        LinkedList<Vertex>  path = dijkstraAlgorithm.getPath( new Vertex(town2));
        int distanceTotal = dijkstraAlgorithm.getDistanceTotal(path);

        RouteMinimalDTO route = new RouteMinimalDTO();
        route.setDistance(distanceTotal);
        route.setPath(path);

        return route;
    }

}
