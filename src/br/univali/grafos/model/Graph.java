package br.univali.grafos.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Graph {
	
	private List<Vertice> vertices;

	public List<Vertice> getVertices() {
		return vertices;
	}

	public void addVertice(Vertice vertice) {
		if (this.vertices == null)
			this.vertices = new ArrayList<Vertice>();
		this.vertices.add(vertice);
	}
	
}
