package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CartaPagamentoDAO {
	private static Connection con;

	public CartaPagamentoDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public static synchronized boolean AddCartaPagamento(BeanUtente user, BeanCartaPagamento cp) {
		con = DBConnection.getConnection();
		String query ="""
				Insert into carta_pagamento (titolare,numeroCarta,scadenza,codiceCVC,FkUtente) 
				value(?,?,?,?,?);
				""";		
		try {
			int risultatoInserimento;
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, cp.getTitolare());
			ps.setString(2, cp.getnCarta());
			ps.setDate(3, Date.valueOf(cp.getScadenza()));
			ps.setString(4, cp.getCodiceCVC());
			ps.setInt(5, user.getIdUtente());
			
			
			risultatoInserimento=ps.executeUpdate();
			
				if(risultatoInserimento == 1) {
					con.commit();
					return true;
					
					
				}
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		DBConnection.releseConnection(con);

		return false;
	}
	
	

	

}