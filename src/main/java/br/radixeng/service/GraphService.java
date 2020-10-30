package br.radixeng.service;

import br.radixeng.exception.GraphNotFoundException;
import br.radixeng.model.Edge;
import br.radixeng.model.Graph;
import br.radixeng.model.Route;
import br.radixeng.request.EdgeRequest;
import br.radixeng.repository.EdgeRepository;
import br.radixeng.repository.GraphRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GraphService {

    @Autowired
    private GraphRepository graphRepository;

    @Autowired
    private EdgeRepository edgeRepository;

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
       List<Edge> edges =   graph.getEdges().stream().filter( e -> e.getSource().getName().equals(town1)).collect(Collectors.toList());
       edges.


        GraphOperations graphOperations = new GraphOperations(graph);
        graphOperations.execute(town1, town2, maxStops);


       // List<List<String>> path = graphOperations.montarPath(town1, town2);
        List<Route> routes = new ArrayList<>();

//        for(Edge e : graph.getEdges()){
//            Route  route = new Route();
//            if(e.getSource().equals(town1)){
//
//
//                route.setStops(path.size() - 1);
//                route.setRoute("");
//                path.stream().forEach( p -> route.setRoute(route.getRoute().concat(p.getName())));
//                routes.add(route);
//            }
//        }




        return routes;
    }

//    private void addRoute(List<Route> routes, Vertex vertex, int stops){
//        Route  route = new Route();
//    }


}
