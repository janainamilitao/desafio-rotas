package br.radixeng.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Class for objects of type Graph, where they will be contained, values ​​and methods for it.
 *  @author Janaina MIlitão
 */
@Entity
@Getter @Setter
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="Graph_Edge",
            joinColumns={@JoinColumn(name = "graph_id")},
            inverseJoinColumns={@JoinColumn(name = "edge_id")})
    private List<Edge> edges;

    @Transient
    @JsonIgnore
    private Set<Vertex> settledNodes;

    @Transient
    @JsonIgnore
    private List<List<Vertex>> allPath;

    public Graph(){ }

    public void  inicialize(){
        this.settledNodes = new HashSet<Vertex>();
        this.allPath = new ArrayList<>();
    }

    public void addEdge(Edge edge){
        this.edges.add(edge);
    }

    /** Calculates all possible routes given a source and destination
     * @param source
     * @param target
     */
    public void calculateAllPaths(Vertex source, Vertex target) {
        ArrayList<Vertex> pathList = new ArrayList<>();
        pathList.add(source);
        calculatePathsUtil(source, target, pathList);
    }

    /**
     * Recursive method that calculates route using the Backtracking Algorithm
     * @param source
     * @param target
     * @param localPathList
     */
    private void calculatePathsUtil(Vertex source, Vertex target, List<Vertex> localPathList) {

        if (source.getName().equalsIgnoreCase(target.getName())) {
            allPath.add(new ArrayList<>(localPathList));
            return;

        }
        List<Vertex> adjacentNodes = getNeighbors(source);
        settledNodes.add(source);

        for (Vertex vAdjacent : adjacentNodes) {
            if (!settledNodes.contains(vAdjacent)) {
                localPathList.add(vAdjacent);
                calculatePathsUtil(vAdjacent, target, localPathList);
                localPathList.remove(vAdjacent);
            }
        }

        settledNodes.remove(source);
    }

    /**
     * Returns vertex neighbors
     * @param node
     * @return neighbors
     */
    public List<Vertex> getNeighbors(Vertex node) {
        List<Vertex> neighbors = new ArrayList<>();
        for (Edge edge : edges) {
            if (edge.getSource().getName().equalsIgnoreCase(node.getName())
                    && !isSettled(edge.getTarget())) {
                neighbors.add(edge.getTarget());
            }
        }
        return neighbors;
    }

    /** Checks if the vertex has already been selected
     * @param vertex
     * @return boolean
     */
    private boolean isSettled(Vertex vertex) {
        return settledNodes.contains(vertex);
    }
}
