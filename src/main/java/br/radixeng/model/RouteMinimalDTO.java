package br.radixeng.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;

@Getter @Setter
public class RouteMinimalDTO {

    private int distance;

    private LinkedList<Vertex> path;
}
