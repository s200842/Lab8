package it.polito.tdp.metrodeparis.model;

import java.util.List;

import it.polito.tdp.metrodeparis.db.LineaDAO;

public class Fermata {
	
	private int idFermata;
	private String nome;
	private double coordX;
	private double coordY;
	private Linea lineaRif;
	
	public Fermata(int idFermata, String nome, double coordX, double coordY) {
		this.idFermata = idFermata;
		this.nome = nome;
		this.coordX = coordX;
		this.coordY = coordY;
	}

	public int getIdFermata() {
		return idFermata;
	}

	public void setIdFermata(int idFermata) {
		this.idFermata = idFermata;
	}

	public String toString() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getCoordX() {
		return coordX;
	}

	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}

	public double getCoordY() {
		return coordY;
	}

	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}


	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idFermata;
		result = prime * result + ((lineaRif == null) ? 0 : lineaRif.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Fermata other = (Fermata) obj;
		if (idFermata != other.idFermata)
			return false;
		if (lineaRif == null) {
			if (other.lineaRif != null)
				return false;
		} else if (!lineaRif.equals(other.lineaRif))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	public List<Linea> getLinea(){
		LineaDAO dao = new LineaDAO();
		return dao.getLinee(this);
	}
	
	public void setLineaRif(Linea l){
		lineaRif = l;
	}
	
	public Linea getLineaRif(){
		return lineaRif;
	}
	
	
	

}
