package br.radixeng.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

import java.util.List;


@Entity
@Getter @Setter
public class Graph {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany(mappedBy = "graph", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<Edge> edges;

    @OneToMany(mappedBy = "graph", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Fetch(org.hibernate.annotations.FetchMode.SUBSELECT)
    private List<Vertex> vertexes;


    public Graph(){

    }

    public Graph(List<Edge> edges){
       this.edges = edges;
       this.vertexes = vertexes;
    }

}
