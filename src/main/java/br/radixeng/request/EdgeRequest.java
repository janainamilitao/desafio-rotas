package br.radixeng.request;

import br.radixeng.model.Edge;
import br.radixeng.model.Vertex;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * @author Janaina Militão
 */
@Getter @Setter
public class EdgeRequest {

    @NotNull(message = "source: required field")
    private String source;

    @NotNull(message = "target: required field")
    private String target;

    @NotNull(message = "distance: required field")
    private Integer distance;

    public  EdgeRequest (){

    }
    public  EdgeRequest (String source, String target, Integer distance){
        this.source = source;
        this.target = target;
        this.distance = distance;
    }


    public Edge convertObject(EdgeRequest edgeRequest) {
        return new Edge(new Vertex(edgeRequest.getSource()), new Vertex(edgeRequest.getTarget()), edgeRequest.getDistance());
    }
}
