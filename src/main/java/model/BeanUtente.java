package model;

import java.time.LocalDate;

public class BeanUtente {
	private int idUtente;
	private String nome;
	private String Cognome;
	private LocalDate dataNascita;
	private String email;
	private String cf;
	private String pass;
	
	public BeanUtente() {
		
	}

	public int getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(int idUtente) {
		this.idUtente = idUtente;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return Cognome;
	}

	public void setCognome(String cognome) {
		Cognome = cognome;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "BeanUtente [idUtente=" + idUtente + ", nome=" + nome + ", Cognome=" + Cognome + ", dataNascita="
				+ dataNascita + ", email=" + email + ", cf=" + cf + ", pass=" + pass + "]";
	}
	
	
}
