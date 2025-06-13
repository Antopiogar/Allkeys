package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
			System.out.println("risultato creazione carta = " + risultatoInserimento);
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
	
	public static synchronized ArrayList<BeanCartaPagamento> loadCartaPagamentoByIdUtente(int idUtente){
		ArrayList<BeanCartaPagamento> carte = new ArrayList<BeanCartaPagamento>();
		Connection con = DBConnection.getConnection();
		try {
			BeanCartaPagamento bc;
			String query="""
					SELECT *
					FROM Carta_Pagamento
					WHERE
						FkUtente = ?
					""";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idUtente);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bc = new BeanCartaPagamento(); 
				bc.setIdCarta(rs.getInt("idCarta"));
				bc.setTitolare(rs.getString("Titolare"));
				bc.setnCarta(rs.getString("numeroCarta"));
				bc.setScadenza(rs.getDate("scadenza").toLocalDate());
				bc.setCodiceCVC(rs.getString("codiceCVC"));
				carte.add(bc);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MORTO IN LOAD CARTE");
		}
		DBConnection.releseConnection(con);
		return carte;
	}
	
	public static synchronized BeanCartaPagamento loadCartaById(int idCarta){
		BeanCartaPagamento bc = null;
		Connection con = DBConnection.getConnection();
		try {
			String query="""
					SELECT *
					FROM Carta_Pagamento
					WHERE
						IdCarta = ?
					""";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setInt(1, idCarta);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				bc = new BeanCartaPagamento(); 
				bc.setIdCarta(rs.getInt("idCarta"));
				bc.setTitolare(rs.getString("Titolare"));
				bc.setnCarta(rs.getString("numeroCarta"));
				bc.setScadenza(rs.getDate("scadenza").toLocalDate());
				bc.setCodiceCVC(rs.getString("codiceCVC"));
				
			}
			ps.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("MORTO IN LOAD CARTE");
		}
		DBConnection.releseConnection(con);
		return bc;
	}
	

	

}