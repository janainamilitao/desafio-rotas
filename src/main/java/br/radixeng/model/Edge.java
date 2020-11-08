package br.radixeng.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/** Class for objects of type Edge, where they will be contained, values ​​and methods for it.
 *  @author Janaina MIlitão
 */
@Entity
@Getter @Setter
public class Edge {

    @Id
    @SequenceGenerator(name = "edge_seq", sequenceName = "edge_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edge_seq")
    @JsonIgnore
    private Long id;

    @NotNull(message = "source: required field")

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Vertex source;

    @NotNull(message = "target: required field")

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Vertex target;

    @NotNull(message = "distance: required field")
    private Integer distance;


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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (id == null) {
            if (other.getId() != null)
                return false;
        } else if (!id.equals(other.getId()))
            return false;
        return true;
    }


}
