package br.radixeng.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Vertex2 {

    private String name;

    private List<Edge> edges;
}
