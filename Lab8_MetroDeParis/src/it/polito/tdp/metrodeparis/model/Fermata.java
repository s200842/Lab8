package it.polito.tdp.metrodeparis.model;

import java.util.ArrayList;
import java.util.List;

public class Fermata {
	
	private int idFermata;
	private String nome;
	private double coordX;
	private double coordY;
	private List<FermataSuLinea> fermateSuLinea = new ArrayList<FermataSuLinea>();
	
	public Fermata(int idFermata, String nome, double coordX, double coordY) {
		this.idFermata = idFermata;
		this.nome = nome;
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	public Fermata(int idFermata) {
		this.idFermata = idFermata;
	}

	public int getIdFermata() {
		return idFermata;
	}
	
	public List<FermataSuLinea> getFermateSuLinea() {
		return this.fermateSuLinea;
	}

	public void addFermataSuLinea(FermataSuLinea fermataSuLinea) {
		this.fermateSuLinea.add(fermataSuLinea);
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
		return true;
	}

	public String getNome() {
		return this.nome;
	}
	
}
