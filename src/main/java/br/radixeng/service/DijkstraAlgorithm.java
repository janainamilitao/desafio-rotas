package br.radixeng.service;

import br.radixeng.model.Edge;
import br.radixeng.model.Graph;
import br.radixeng.model.Vertex;

import java.util.*;

/** Class with methods for calculating minimum distance between two vertices
 * @author Janaina Militão
 */
public class DijkstraAlgorithm {

    private final List<Edge> edges;
    private Set<Vertex> settledNodes;
    private Set<Vertex> unSettledNodes;
    private Map<Vertex, Vertex> predecessors;
    private Map<Vertex, Integer> distance;
    private Graph graph;

    public DijkstraAlgorithm(Graph graph) {
        this.edges = new ArrayList<Edge>(graph.getEdges());
        this.graph = graph;
    }

    /** Execution of the algorithm while there is an unselected vertex
     * @param source
     */
    public void execute(Vertex source) {
        settledNodes = new HashSet<Vertex>();
        unSettledNodes = new HashSet<Vertex>();
        distance = new HashMap<Vertex, Integer>();
        predecessors = new HashMap<Vertex, Vertex>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
           Vertex node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    /** Calculate the source's predecessors and distance between them
     * @param node
     */
    private void findMinimalDistances(Vertex node) {
        List<Vertex> adjacentNodes = getNeighbors(node);
        for (Vertex target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    /** Calculates distance between two edges
     * @param node
     * @param target
     * @return distance
     */
    private int getDistance(Vertex node, Vertex target) {
        for (Edge edge : edges) {
            if (edge.getSource().getName().equalsIgnoreCase(node.getName())
                    && edge.getTarget().getName().equalsIgnoreCase(target.getName())) {
                return edge.getDistance();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    /** Calculates the total distance given a path (a Vertex list)
     * @param path
     * @return distanceTotal
     */
    public int getDistanceTotal(LinkedList<Vertex> path) {
        int total = 0;
        for(int i = 0; i< path.size() -1 ;i++){
            total += getDistance(path.get(i), path.get(i+1));
        }
        return total;
    }

    /** Returns vertex neighbors
     * @param node
     * @return neighbors
     */
    private List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().getName().equalsIgnoreCase(node.getName())
                    && !isSettled(edge.getTarget())) {
                neighbors.add(edge.getTarget());
            }
        }
        return neighbors;
    }

    /**
     * Returns the vertex with the shortest distance
     * @param vertexes
     * @return vertex
     */
    private Vertex getMinimum(Set<Vertex> vertexes) {
        Vertex minimum = null;
        for (Vertex vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }

        return  minimum;
    }

    /** Checks if the vertex has already been selected
     * @param vertex
     * @return boolean
     */
    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }

    /** Returns the shortest distance given a destination
     * @param destination
     * @return distance
     */
    private int getShortestDistance(Vertex destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /** Returns the shortest path
     * @param target
     * @return path
     */
    public LinkedList<Vertex> calculatePath(Vertex target) {
        LinkedList<Vertex> path = new LinkedList<>();

        if (predecessors.get(target) == null) {
            return null;
        }
        path.add(target);
        while (predecessors.get(target) != null) {
            target = predecessors.get(target);
            path.add(target);
        }

        Collections.reverse(path);
        return path;
    }
}