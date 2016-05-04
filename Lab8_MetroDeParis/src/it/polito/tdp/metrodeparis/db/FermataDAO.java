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

}
