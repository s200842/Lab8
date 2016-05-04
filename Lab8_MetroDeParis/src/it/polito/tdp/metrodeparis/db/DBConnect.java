package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBConnect {
	
	private static String url = "jdbc:mysql://localhost/metroparis?user=root";

	/**
	 * Restituisce una nuova connessione, con i parametri a lui noti
	 * @return la nuova java.sql.Connection, oppure null se si verificano
	 * errori di connessione
	 */
	public static Connection getConnection() {
		Connection conn;
		try {
			conn = DriverManager.getConnection(url);
			return conn ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null ;
	}

}
