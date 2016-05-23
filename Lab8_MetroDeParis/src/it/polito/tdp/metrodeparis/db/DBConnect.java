package it.polito.tdp.metrodeparis.db;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.*;

class DBConnect {
	
	private static String jdbcURL = "jdbc:mysql://localhost/metroparis?user=root";

	private static ComboPooledDataSource dataSource = null ;
	
	public static Connection getConnection() {
		
		try {
			
			if(dataSource==null) {
				// creare ed attivare il Connection Pool
				dataSource = new ComboPooledDataSource() ;
				dataSource.setJdbcUrl(jdbcURL);
			}
			
			return dataSource.getConnection();
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Errore nella connessione", e) ;
		}
	}
}
