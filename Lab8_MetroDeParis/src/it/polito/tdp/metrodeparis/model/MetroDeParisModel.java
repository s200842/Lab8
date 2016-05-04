package it.polito.tdp.metrodeparis.model;

import java.util.List;

import it.polito.tdp.metrodeparis.db.FermataDAO;

public class MetroDeParisModel {
	
	public List<Fermata> getFermate(){
		FermataDAO dao = new FermataDAO();
		return dao.getFermate();
	}

}
