package br.radixeng.exception;

public class GraphNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public GraphNotFoundException() {
        super("Graph not found  ");
    }
}
