package it.polito.tdp.metrodeparis.model;

import java.util.List;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.db.FermataDAO;
import it.polito.tdp.metrodeparis.db.LineaDAO;

public class MetroDeParisModel {
	
	private FermataDAO fdao;
	private LineaDAO ldao;
	private List<Fermata> fermate;
	private DefaultDirectedWeightedGraph<Fermata, DefaultWeightedEdge> graph;
	
	public MetroDeParisModel(){
		fdao = new FermataDAO();
		fermate = fdao.getFermate();
		ldao = new LineaDAO();
		graph = new DefaultDirectedWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	}
	
	public List<Fermata> getFermate(){
		return fermate;
	}
	
	public void generateGraph(){
		//Ogni stazione rappresenta un vertice
		for(Fermata f : fermate){
			//Se la fermata appartiene a più di una linea, creo un vertice aggiuntivo per ogni linea di appartenenza
			if(f.getLinea().size()>1){
				for(Linea l : f.getLinea()){
					Fermata fn = new Fermata(f.getIdFermata(), f.toString(), f.getCoordX(), f.getCoordY());
					fn.setLineaRif(l);
					graph.addVertex(fn);
				}
			}
			else{
				f.setLineaRif(f.getLinea().get(0));
				graph.addVertex(f);
			}
		}
		System.out.println(graph.vertexSet());
		//Collego le stazioni con lo stesso id (cambio linea)
		for(Fermata f : graph.vertexSet()){
			for(Fermata f1 : graph.vertexSet()){
				if(f.getIdFermata() == f1.getIdFermata()){
					graph.addEdge(f, f1);
					graph.setEdgeWeight(graph.getEdge(f, f1), f1.getLineaRif().getIntervallo());
				}
				//System.out.println(graph.edgeSet());
			}
		}
		for(Fermata f : graph.vertexSet()){
			List<String> near = fdao.getVicine(f);
			for(Fermata f1 : graph.vertexSet()){
				//Collego le stazioni vicine solo se sono sulla stessa linea. I collegamenti tra le linee sono già fatti
				if(near.contains(f1.toString())){
					if(f.getLineaRif().getIdLinea() == f1.getLineaRif().getIdLinea()){
						graph.addEdge(f, f1);
						graph.setEdgeWeight(graph.getEdge(f, f1), calcolaTempo(f, f1));
					}
				}
			}
		}
		System.out.println(String.format("Generato grafo con %d nodi e %d archi", graph.vertexSet().size(), graph.edgeSet().size()));
	}
	
	public double calcolaTempo(Fermata f1, Fermata f2){
		//Calcolo la distanza
		double d = LatLngTool.distance(new LatLng(f1.getCoordX(), f1.getCoordY()), new LatLng(f2.getCoordX(), f2.getCoordY()), LengthUnit.KILOMETER);
		//La velocità è specificata nella tabella linea
		double v = ldao.getVelocity(f1, f2);
		return d/v;
	}

	public List<Fermata> calcolaPercorso(Fermata f1, Fermata f2){
		Fermata start = null; 
		Fermata stop = null;
		for(Fermata f : graph.vertexSet()){
			if(f1.getIdFermata() == f.getIdFermata()){
				start = f;
			}
			if(f2.getIdFermata() == f.getIdFermata()){
				stop = f;
			}
		}
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> dijkstra = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(graph, start, stop);
		GraphPath<Fermata, DefaultWeightedEdge> path = dijkstra.getPath();
		return Graphs.getPathVertexList(path);
	}
	
	public String tempoPercorso(List<Fermata> l){
		//Calcolo tempo percorso in s
		int time = 0;
		for(int i=0; i<l.size()-1; i++){
			time += calcolaTempo(l.get(i), l.get(i+1));
		}
		time = time*3600;
		//Calcolo del tempo delle soste
		int s = 0;
		String resSoste = "";
		for(Fermata f : l){
			s += 30;
		}
		s = s+time;
		if(s < 60){
			resSoste = s+" secondi.";
		}
		else if(s < 3600){
			int min = s/60;
			int sec = s%60;
			resSoste = min+" minuti e "+sec+" secondi.";
		}
		else{
			int h = s/3600;
			int min = (s%3600)/60;
			int sec = (s%3600)%60;
			resSoste = h+" ore, "+min+" minuti e "+sec+" secondi.";
		}
						
		return resSoste;	
	}

}

