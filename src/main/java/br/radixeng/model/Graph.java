package br.radixeng.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="Graph_Vertex",
            joinColumns={@JoinColumn(name = "graph_id")},
            inverseJoinColumns={@JoinColumn(name = "vertex_id")})
    private List<Vertex> vertexes;


    public Graph(){

    }

    public Graph(List<Edge> edges){
       this.edges = edges;
       //this.vertexes = vertexes;
    }

}
