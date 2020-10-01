package br.univali.grafos.model;

import java.util.ArrayList;
import java.util.List;

public class Vertice {
	
		private String name;
		private List<Edge> edges;
		
		
		public Vertice(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public List<Edge> getEdges() {
			return edges;
		}
		public void addEdge(Edge edge) {
			if (this.edges == null)
				this.edges = new ArrayList<Edge>();
			this.edges.add(edge);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Vertice) {
				Vertice newObj = (Vertice)obj;
				if (newObj.getName().equals(this.name))
					return true;
			}
			return super.equals(obj);
		}
		
		@Override
		public String toString() {
			return this.name;
		}
	
}
