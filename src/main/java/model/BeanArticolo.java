package model;

public class BeanArticolo {
	private int IdArticolo;
	private String logo;
	private String nome;
	private String piattaforma;
<<<<<<< HEAD
=======
	private float prezzo;
>>>>>>> origin/cartCreation
	
	public BeanArticolo() {
		// TODO Auto-generated constructor stub
	}
	

	public int getIdArticolo() {
		return IdArticolo;
	}
	public void setIdArticolo(int idArticolo) {
		IdArticolo = idArticolo;
	}
<<<<<<< HEAD
=======
	public float getPrezzo() {
		return prezzo;
	}


	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}


>>>>>>> origin/cartCreation
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getPiattaforma() {
		return piattaforma;
	}
	public void setPiattaforma(String piattaforma) {
		this.piattaforma = piattaforma;
	}
	
	public String debugToString() {
		return "BeanArticolo [IdArticolo=" + IdArticolo + ", logo=" + logo + ", nome=" + nome + ", piattaforma="
				+ piattaforma + "]";
	}
	@Override
	public String toString() {
<<<<<<< HEAD
		return "Articolo: nome= %s, piattaforma = %s, srcLogo = %s "
				.formatted(this.nome,this.piattaforma,this.logo);
=======
		return "Articolo: nome= %s, piattaforma = %s, srcLogo = %s , prezzo = %s €"
				.formatted(this.nome,this.piattaforma,this.logo, this.prezzo);
>>>>>>> origin/cartCreation
	}
}
