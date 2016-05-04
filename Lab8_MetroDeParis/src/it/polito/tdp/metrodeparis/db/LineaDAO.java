package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.Linea;

public class LineaDAO {
	
	public List<Linea> getLinee(Fermata f){
		List<Linea> list = new ArrayList<Linea>();
		Connection c = DBConnect.getConnection();
		String sql = "SELECT * FROM linea WHERE id_linea IN (SELECT id_linea FROM connessione WHERE id_stazP = ?)";
		try {
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, f.getIdFermata());
			ResultSet res = st.executeQuery();
			while(res.next()){
				int id = res.getInt("id_linea");
				String nome = res.getString("nome");
				double velocita = res.getDouble("velocita");
				double intervallo = res.getDouble("intervallo");
				String colore = res.getString("colore");
				Linea l = new Linea(id, nome, velocita, intervallo, colore);
				list.add(l);
			}
			res.close();
			c.close();
			return list;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public double getVelocity(Fermata f1, Fermata f2){
		Connection c = DBConnect.getConnection();
		String sql = "SELECT velocita FROM linea WHERE id_linea IN (SELECT id_linea FROM connessione WHERE id_stazP = ? AND id_stazA = ?)";
		try {
			PreparedStatement st = c.prepareStatement(sql);
			st.setInt(1, f1.getIdFermata());
			st.setInt(2, f2.getIdFermata());
			ResultSet res = st.executeQuery();
			if(!res.next()){
				return 0;
			}
			else{
				double v = res.getDouble("velocita");
				res.close();
				c.close();
				return v;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
		
	}

}
