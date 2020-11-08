package br.radixeng.service;

import br.radixeng.exception.GraphNotFoundException;
import br.radixeng.model.*;
import br.radixeng.request.EdgeRequest;
import br.radixeng.repository.GraphRepository;
import br.radixeng.response.RouteMinimalResponse;
import br.radixeng.response.RoutePathResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Janaina Militão
 */
@Service
public class GraphService {

    @Autowired
    private GraphRepository graphRepository;

    /** Receive a set of edges and save to a chart
     * @param edgeRequestList
     * @return graph
     */
    public Graph saveGraph(List<EdgeRequest> edgeRequestList){
        Graph graph = new Graph();

        graph.setEdges(new ArrayList<>());
        edgeRequestList.stream().forEach( e-> {
            Edge edge = e.convertObject(e);
            graph.addEdge(edge);

        });

        return graphRepository.save(graph);
    }

    /**Searching for a chart by id
     * @param graphId
     * @return graph
     * @throws GraphNotFoundException
     */
    public Graph recoverGraph(Long graphId) throws GraphNotFoundException {
       Optional<Graph> graph = graphRepository.findById(graphId);

       if(graph.isPresent()){
           return graph.get();
       }else {
          throw new GraphNotFoundException();
       }
    }

    /** Find all available routes given a source, a destination and a maximum number of stops
     * @param graphId
     * @param town1
     * @param town2
     * @param maxStops
     * @return routes
     * @throws GraphNotFoundException
     */
    public List<RoutePathResponse> findRoutes(Long graphId, String town1, String town2, Integer maxStops) throws GraphNotFoundException {

        Graph graph = recoverGraph(graphId);
        Vertex source = findSourceVertex(town1, graph);
        Vertex target = findTargetVertex(town2, graph);
        List<RoutePathResponse> routes = new ArrayList<>();

        if(source!= null && target!=null){
            graph.inicialize();
            graph.calculateAllPaths(source, target);

            for (List<Vertex> path: graph.getAllPath()){
                RoutePathResponse route = new RoutePathResponse();

                String routeName ="";
                for(Vertex town: path){
                    routeName = routeName.concat(town.getName());
                }
                route.setRoute(routeName);
                route.setStops(route.getRoute().length()-1);
                if(maxStops!=null && route.getStops()<=maxStops){
                    routes.add(route);
                }else{
                    if(maxStops==null){
                        routes.add(route);
                    }
                }
            }
        }

        return routes;
    }


    /** Determines the minimum distance between two cities on a graph
     * @param graphId
     * @param town1
     * @param town2
     * @return routeMinimal
     * @throws GraphNotFoundException
     */
    public RouteMinimalResponse findMinimalRoute(Long graphId, String town1, String town2) throws GraphNotFoundException {
        Graph graph = recoverGraph(graphId);
        Vertex source = findSourceVertex(town1, graph);
        Vertex target = findTargetVertex(town2, graph);
        RouteMinimalResponse route = new RouteMinimalResponse();

        if(town1.equalsIgnoreCase(town2)){
            route.setDistance(0);
            route.setPath(Arrays.asList(town1));
        }else {
            if(source!= null && target!=null){
                DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
                dijkstraAlgorithm.execute(source);
                LinkedList<Vertex> path = dijkstraAlgorithm.calculatePath(target);
                route.setDistance(dijkstraAlgorithm.getDistanceTotal(path));
                route.setPath(new ArrayList<>());
                path.stream().forEach( p ->  route.getPath().add( p.getName()));
            } else{
                route.setDistance(-1);
                route.setPath(Arrays.asList(town1,town2));
            }
        }

        return route;
    }

    /**
     * @param town
     * @param graph
     * @return source
     */
    public Vertex findSourceVertex(String town, Graph graph){
        for(Edge edge: graph.getEdges()){
            if(edge.getSource().getName().equalsIgnoreCase(town)){
                return edge.getSource();
            }
        }

        return null;
    }

    /**
     * @param town
     * @param graph
     * @return target
     */
    public Vertex findTargetVertex(String town, Graph graph){
        for(Edge edge: graph.getEdges()){
            if(edge.getTarget().getName().equalsIgnoreCase(town)){
                return edge.getTarget();
            }
        }

        return null;
    }
}
