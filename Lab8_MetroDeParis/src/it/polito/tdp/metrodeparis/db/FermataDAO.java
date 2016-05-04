package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import it.polito.tdp.metrodeparis.model.Fermata;

public class FermataDAO {
	
	public List<Fermata> getFermate(){
		List<Fermata> listaFermate = new ArrayList<Fermata>();
		Connection c = DBConnect.getConnection();
		String sql = "SELECT * FROM fermata";
		try {
			PreparedStatement st = c.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while(res.next()){
				int id = res.getInt("id_fermata");
				String nome = res.getString("nome");
				double coordX = res.getDouble("coordX");
				double coordY = res.getDouble("coordY");
				Fermata f = new Fermata(id, nome, coordX, coordY);
				listaFermate.add(f);
			}
			res.close();
			c.close();
			return listaFermate;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Fermata> getVicine(Fermata f1){
		List<Fermata> listaFermateVicine = new ArrayList<Fermata>();
		Connection c = DBConnect.getConnection();
		String sql = "SELECT * FROM fermata WHERE id_fermata IN (SELECT id_stazA FROM connessione WHERE id_stazP = ?)";
		try {
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, f1.getIdFermata());
			ResultSet res = st.executeQuery();
			while(res.next()){
				int id = res.getInt("id_fermata");
				String nome = res.getString("nome");
				double coordX = res.getDouble("coordX");
				double coordY = res.getDouble("coordY");
				Fermata f = new Fermata(id, nome, coordX, coordY);
				listaFermateVicine.add(f);
			}
			res.close();
			c.close();
			return listaFermateVicine;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
