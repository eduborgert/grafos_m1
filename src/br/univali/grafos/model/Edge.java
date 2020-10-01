package br.univali.grafos.model;

public class Edge {
	
	private String name;
	private Integer value;
	private Vertice output;
	
	
	public Edge(String name, Integer value, Vertice output) {
		this.name = name;
		this.value = value;
		this.output = output;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public Vertice getOutput() {
		return output;
	}
	public void setOutput(Vertice output) {
		this.output = output;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Edge) {
			Edge newObj = (Edge)obj;
			if (newObj.getName().equals(this.name))
				return true;
		}
		return super.equals(obj);
	}
}
