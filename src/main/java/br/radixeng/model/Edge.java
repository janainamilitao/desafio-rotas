package br.radixeng.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter @Setter
public class Edge {

    @Id
    @SequenceGenerator(name = "edge_seq", sequenceName = "edge_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edge_seq")
    private Long id;

    @NotNull(message = "source: required field")

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Vertex source;

    @NotNull(message = "target: required field")

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Vertex target;

    @NotNull(message = "distance: required field")
    private Integer distance;

    @ManyToOne
    private Graph graph;

    public  Edge(){}

    public Edge(Vertex source, Vertex target){
        this.source = source;
        this.target = target;
    }


    public Edge(Vertex source, Vertex target, Integer distance){
        this.source = source;
        this.target = target;
        this.distance = distance;
    }

}
