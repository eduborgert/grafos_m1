package br.univali.grafos.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.univali.grafos.model.Edge;
import br.univali.grafos.model.Graph;
import br.univali.grafos.model.Vertice;

public class GraphService {
	
	private Graph graph;
	
	public GraphService() {
		this.graph = new Graph();
	}
	
	public void createVertice(String name) {
		Vertice vertice = new Vertice(name);
		this.graph.addVertice(vertice);
	}
	
	public void createEdge(String name, Integer value, String inputName, String outputName) {
		Optional<Vertice> oInput = findVerticeByName(inputName);
		Vertice input = null;
		Vertice output = null;
		if (oInput.isPresent())
			input = oInput.get();
		Optional<Vertice> oOutput = findVerticeByName(outputName);
		if (oOutput.isPresent())
			output = oOutput.get();
		Edge edge = new Edge(name, value, output);
		input.addEdge(edge);
	}
	
	public void removeVertice(String name) {
		Optional<Vertice> oInput = findVerticeByName(name);
		Vertice v = null;
		if (oInput.isPresent()) {
			v = oInput.get();
			this.graph.getVertices().remove(v);
			List<Edge> es = Optional.ofNullable(getEdges()).orElse(new ArrayList<>());
			for (Edge edge : es) {
				if (edge.getOutput() != null && edge.getOutput().equals(v))
					edge.setOutput(null);
			}
		}
	}
	
	public void removeEdge(String name) {
		if (this.graph.getVertices() != null) {
			for (Vertice v : this.graph.getVertices()) {
				List<Edge> es = Optional.ofNullable(v.getEdges()).orElse(new ArrayList<>());
				for (Edge edge : es) {
					if (edge.getName().equals(name)) {
						v.getEdges().remove(edge);
						break;
					}
				}
			}
		}
	}
	
	private Optional<Vertice> findVerticeByName(String name) {
		return this.graph.getVertices().stream().filter(v -> v.getName().equals(name)).findFirst();
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public List<Edge> getEdges(){
		if (this.graph.getVertices() == null)
			return null;		
		List<Edge> edges = new ArrayList<>();
		for (Vertice v : this.graph.getVertices()) {
			List<Edge> es = Optional.ofNullable(v.getEdges()).orElse(new ArrayList<>());
			for (Edge edge : es) {
				if (!edges.stream().anyMatch(e -> e.equals(edge))) {
					edges.add(edge);
				}
			}
		}
		return edges;
	}
	
	public Vertice findInputVertice(Edge edge) {
		for (Vertice v : this.graph.getVertices()) {
			List<Edge> es = Optional.of(v.getEdges()).orElse(new ArrayList<>());
			if (es.stream().anyMatch(e -> e.equals(edge)))
				return v;
		}
		return null;
	}
	
	public Map<Vertice, List<Edge>> prim() {
		Map<Vertice, List<Edge>> tree = new LinkedHashMap<>();
		if (this.graph.getVertices() == null)
			return tree;
		List<Vertice> vt = new ArrayList<>();
		Vertice actualVertice = this.graph.getVertices().stream().findFirst().get();
		vt.add(actualVertice);
		while (vt.size() < this.graph.getVertices().size()) {
			Edge lowerValue = null;
			for (Vertice vertice : vt) {
				List<Edge> edges = Optional.ofNullable(vertice.getEdges()).orElse(new ArrayList<>());
				for (Edge edge : edges) {
					if (lowerValue == null) {
						if (edge.getOutput() != null && !vt.stream().anyMatch(v -> v.equals(edge.getOutput()))) {
							lowerValue = edge;
							actualVertice = vertice;
						}
					} else if (lowerValue.getValue() > edge.getValue()) {
						if (edge.getOutput() != null && !vt.stream().anyMatch(v -> v.equals(edge.getOutput()))) {
							lowerValue = edge;
							actualVertice = vertice;
						}
					}
				}
			}
			if (lowerValue != null) {
				vt.add(lowerValue.getOutput());
				if (tree.get(actualVertice) == null)
					tree.put(actualVertice, new ArrayList<>());
				tree.get(actualVertice).add(lowerValue);
			} else {
				break;
			}
		}
		return tree;
	}
	
	public List<String> bfs(String name) {
		List<String> result = new ArrayList<>();
		Optional<Vertice> oStart = findVerticeByName(name);
		Vertice vStart = null;
		if (oStart.isPresent())
			vStart = oStart.get();
		else
			return result;
		List<Vertice> viewedVertices = new ArrayList<>();
		LinkedList<Vertice> queue = new LinkedList<>();
		viewedVertices.add(vStart);
		queue.add(vStart);
		result.add("Marcados: "+viewedVertices+"; Fila(Q): "+queue);
		while (!queue.isEmpty()) {
			Vertice v = queue.getFirst();
			queue.removeFirst();
			result.add("Marcados: "+viewedVertices+"; Fila(Q): "+queue);
			List<Edge> edges = Optional.ofNullable(v.getEdges()).orElse(new ArrayList<>());
			for (Edge edge : edges) {
				if (edge.getOutput() != null && !viewedVertices.stream().anyMatch(ve -> ve.equals(edge.getOutput()))) {
					viewedVertices.add(edge.getOutput());
					queue.add(edge.getOutput());
					result.add("Marcados: "+viewedVertices+"; Fila(Q): "+queue);
				}
			}
		}
		return result;
	}
	
	public List<String> dfs(String name) {
		List<String> result = new ArrayList<>();
		Optional<Vertice> oStart = findVerticeByName(name);
		Vertice vStart = null;
		if (oStart.isPresent())
			vStart = oStart.get();
		else
			return result;
		List<Vertice> viewedVertices = new ArrayList<>();
		LinkedList<Vertice> stack = new LinkedList<>();
		result.add("Marcados: "+viewedVertices+"; BP("+vStart.getName()+")");
		viewedVertices.add(vStart);
		stack.add(vStart);
		while (!stack.isEmpty()) {
			Vertice v = stack.getLast();
//			result.add("Marcados: "+viewedVertices+"; Fila(Q): "+queue);
			List<Edge> edges = Optional.ofNullable(v.getEdges()).orElse(new ArrayList<>());
			boolean marked = false;
			for (Edge edge : edges) {
				if (edge.getOutput() != null && !viewedVertices.stream().anyMatch(ve -> ve.equals(edge.getOutput()))) {
					result.add("Marcados: "+viewedVertices+"; w="+edge.getOutput().getName()+" BP("+edge.getOutput().getName()+")");
					viewedVertices.add(edge.getOutput());
					stack.add(edge.getOutput());
					marked = true;
					break;
				}
			}
			if (!marked) {
				result.add("Encerra BP("+v.getName()+")");
				stack.removeLast();
			}
		}
		return result;
	}
	
}
