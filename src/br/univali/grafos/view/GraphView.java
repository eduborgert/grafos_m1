package br.univali.grafos.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import br.univali.grafos.model.Edge;
import br.univali.grafos.model.Vertice;
import br.univali.grafos.service.GraphService;

public class GraphView {
	
	private GraphService graphService;
	private Scanner in = new Scanner(System.in);
	
	public void run() {
		this.graphService = new GraphService();
		int option = 0;
		
		while (option != 9) {
			System.out.println("=============== Grafos ===============");
			System.out.println("1 - Criar vértice");
			System.out.println("2 - Criar aresta");
			System.out.println("3 - Mostrar grafo");
			System.out.println("4 - Remover vértice");
			System.out.println("5 - Remover aresta");
			System.out.println("6 - Algoritmo PRIM");
			System.out.println("7 - BFS");
			System.out.println("8 - BFS");
			System.out.println("9 - Fechar");
			option = in.nextInt();
			switch (option) {
			case 1:
				createVertice();
				break;
			case 2:
				createEdge();
				break;
			case 3:
				showGraph();
				break;
			case 4:
				removeVertice();
				break;
			case 5:
				removeEdge();
				break;
			case 6:
				prim();
				break;
			case 7:
				bfs();
				break;
			case 8:
				dfs();
				break;
			default:
				break;
			}
		}
		
	}
	
	private void createVertice() {
		System.out.print("Informe o nome do vértice: ");
		String name = in.next();
		this.graphService.createVertice(name);
	}
	
	private void removeVertice() {
		System.out.print("Informe o nome do vértice: ");
		String name = in.next();
		this.graphService.removeVertice(name);
	}
	
	private void removeEdge() {
		System.out.print("Informe o nome da aresta: ");
		String name = in.next();
		this.graphService.removeEdge(name);
	}
	
	private void createEdge() {
		System.out.print("Informe o nome da aresta: ");
		String name = in.next();
		System.out.println();
		System.out.print("Informe o valor da aresta: ");
		Integer value = in.nextInt();
		System.out.println();
		System.out.print("Informe o nome do vértice de origem: ");
		String input = in.next();
		System.out.println();
		System.out.print("Informe o nome do vértice de destino: ");
		String output = in.next();
		System.out.println();
		this.graphService.createEdge(name, value, input, output);
	}
	
	private void showGraph() {
		System.out.println("======================== GRAFO ========================");
		System.out.print("Vértices: ");
		List<Vertice> vertices = Optional.ofNullable(this.graphService.getGraph().getVertices()).orElse(new ArrayList<>());
		for (Vertice vertice : vertices) {
			System.out.print("( "+vertice.getName()+" ) ");
		}
		System.out.println();
		System.out.println("Arestas: ");
		List<Edge> edges = Optional.ofNullable(this.graphService.getEdges()).orElse(new ArrayList<>());
		for (Edge edge : edges) {
			System.out.println("Name: "+edge.getName()+" Valor: "+edge.getValue());
			Vertice v = this.graphService.findInputVertice(edge);
			System.out.println("Origem: ( "+(v != null ? v.getName() : "Sem origem")+" ) Destino: ( "+
								(edge.getOutput() != null ? edge.getOutput().getName() : "Sem destino")+" )");
		}
		try {
	        System.in.read();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void prim() {
		Map<Vertice, List<Edge>> tree = this.graphService.prim();
		System.out.println("======================== ÁRVORE GERADORA MÍNIMA ========================");
		if (tree.size() == 0)
			System.out.println("Não foi possível calcular a árvore");
		Integer total = 0;
		for (Map.Entry<Vertice, List<Edge>> entry : tree.entrySet()) {
			for (Edge edge : entry.getValue()) {
				System.out.println("Origem: "+entry.getKey().getName()+" Destino: "+edge.getOutput().getName()
						+" Aresta: "+edge.getName()+" Valor: "+edge.getValue());
				total += edge.getValue();
			}
		}
		if (total > 0) {
			System.out.println("Total: "+total);
		}
		System.out.println("========================================================================");
		try {
	        System.in.read();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void bfs() {
		System.out.println("======================== BFS ========================");
		System.out.print("Informe o nome do vértice para iniciar: ");
		String input = in.next();
		System.out.println();
		List<String> result = this.graphService.bfs(input);
		for (String string : result) {
			System.out.println(string);
		}
		try {
	        System.in.read();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	private void dfs() {
		System.out.println("======================== DFS ========================");
		System.out.print("Informe o nome do vértice para iniciar: ");
		String input = in.next();
		System.out.println();
		List<String> result = this.graphService.dfs(input);
		for (String string : result) {
			System.out.println(string);
		}
		try {
	        System.in.read();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
}
