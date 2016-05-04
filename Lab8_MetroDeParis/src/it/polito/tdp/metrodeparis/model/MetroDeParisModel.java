package it.polito.tdp.metrodeparis.model;

import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.db.FermataDAO;
import it.polito.tdp.metrodeparis.db.LineaDAO;

public class MetroDeParisModel {
	
	private FermataDAO fdao;
	private LineaDAO ldao;
	private List<Fermata> fermate;
	private SimpleWeightedGraph<Fermata, DefaultWeightedEdge> graph;
	
	public MetroDeParisModel(){
		fdao = new FermataDAO();
		fermate = fdao.getFermate();
		ldao = new LineaDAO();
		graph = new SimpleWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	public List<Fermata> getFermate(){
		return fermate;
	}
	
	public void generateGraph(){
		//Ogni stazione rappresenta un vertice
		for(Fermata f : fermate){
			graph.addVertex(f);
		}
		//Controllo le fermate vicine e creo un arco
		for(Fermata f : graph.vertexSet()){
			List<Fermata> near = fdao.getVicine(f);
			for(Fermata f1 : near){
				graph.addEdge(f, f1);
				graph.setEdgeWeight(graph.getEdge(f, f1), calcolaTempo(f, f1));
			}
		}
		//System.out.println(String.format("Generato grafo con %d nodi e %d archi", graph.vertexSet().size(), graph.edgeSet().size()));
	}
	
	public double calcolaTempo(Fermata f1, Fermata f2){
		//Calcolo la distanza
		double d = LatLngTool.distance(new LatLng(f1.getCoordX(), f1.getCoordY()), new LatLng(f2.getCoordX(), f2.getCoordY()), LengthUnit.KILOMETER);
		//La velocità è specificata nella tabella linea
		double v = ldao.getVelocity(f1, f2);
		return d/v;
	}

	public List<Fermata> calcolaPercorso(Fermata f1, Fermata f2){
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(graph, f1, f2);
		GraphPath<Fermata, DefaultWeightedEdge> path = dijkstra.getPath();
		return Graphs.getPathVertexList(path);
	}
	
	public String tempoPercorso(List<Fermata> l){
		int s = 0;
		String res = "";
		for(Fermata f : l){
			s += 30;
		}
		if(s < 60){
			res = s+" secondi.";
		}
		else if(s < 3600){
			int min = s/60;
			int sec = s%60;
			res = min+" minuti e "+sec+" secondi.";
		}
		else{
			int h = s/3600;
			int min = (s%3600)/60;
			int sec = (s%3600)%60;
			res = h+" ore, "+min+" minuti e "+sec+" secondi.";
		}
		return res;	
	}

}

