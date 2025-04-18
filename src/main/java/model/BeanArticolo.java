package model;

public class BeanArticolo {
	private int IdArticolo;
	private String logo;
	private String nome;
	private String piattaforma;

	private float prezzo;
	
	public BeanArticolo() {
		// TODO Auto-generated constructor stub
	}
	

	public int getIdArticolo() {
		return IdArticolo;
	}
	public void setIdArticolo(int idArticolo) {
		IdArticolo = idArticolo;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

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

		return "Articolo: nome= %s, piattaforma = %s, srcLogo = %s , prezzo = %s €"
				.formatted(this.nome,this.piattaforma,this.logo, this.prezzo);
	}
}
