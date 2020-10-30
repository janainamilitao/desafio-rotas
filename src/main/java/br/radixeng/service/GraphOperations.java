package br.radixeng.service;

import br.radixeng.model.Edge;
import br.radixeng.model.Graph;

import java.util.*;

public class GraphOperations {

	private final List<Edge> edges;

	private List<Edge> edgesList;

	public GraphOperations(Graph graph) {
		this.edges = new ArrayList<Edge>(graph.getEdges());
	}

	public void execute(String source, String target, int maxStops) {

	}

//	public void execute(String source, String target) {
//		edgesList = new ArrayList<>();
//
//		String targetAnterior, targeAtual = "";
//
//		for (Edge edge : edges) {
//			targetAnterior = targeAtual;
//			if (source.equals(edge.getSource()) || target.equals(edge.getTarget())){
//				edgesList.add( new Edge(edge.getSource(), edge.getTarget()));
//				targeAtual = edge.getTarget();
//
//			}else{
//				if(targetAnterior.equals(edge.getSource())){
//
//					edgesList.add( new Edge(edge.getSource(), edge.getTarget()));
//				}else{
//					if(!edge.getSource().equals(target) &&  !edge.getTarget().equals(source)){
//						for (Edge e : edges) {
//							if(e.getSource().equals(edge.getTarget())){
//								edgesList.add( new Edge(edge.getSource(), edge.getTarget()));
//								break;
//							}
//						}
//					}
//
//				}
//			}
//		}
//
//
//
//		System.out.println(edgesList);
//	}


//	public List<List<String>> montarPath(String source, String target) {
//		List<List<String>> pathList = new ArrayList<>();
//
//		String targetAnterior, targeAtual = "";
//
//		for (Edge edge : edgesList) {
//			//if(edge.getSource().equals(source)){
//				LinkedList<String> path = new LinkedList<String>();
//				for (Edge e : edgesList) {
//					targetAnterior = targeAtual;
//
//					if (source.equals(edge.getSource()) && e.getSource().equals(edge.getSource())) {
//						if (!path.contains(edge.getSource())) {
//							path.add(e.getSource());
//							path.add(e.getTarget());
//						}
//					}
//					targeAtual = e.getTarget();
//
//					if (targetAnterior.equals(e.getSource())) {
//						path.add(e.getTarget());
//						if (e.getTarget().equals(target)) {
//							break;
//						} else {
//							path.add(e.getTarget());
//						}
//					}
//
//				}
//				pathList.add(path);
//			//}
//
//		}
//
//		return pathList;
//	}

}