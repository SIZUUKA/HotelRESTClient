package com.example.demo.models;

public class Adresse {
	private String pays;
	private String ville;
	private int numero;
	private String codePostale;
	private String nom;
	private AdresseType type;
	private GPS gps;
	
	public Adresse() {
		super();
	}

	public Adresse(String pays, String ville, int numero, String codePostale, String nom, 
			AdresseType type, GPS gps) {
		super();
		this.pays = pays;
		this.ville = ville;
		this.numero = numero;
		this.codePostale = codePostale;
		this.nom = nom;
		this.type = type;
		this.gps = gps;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getCodePostale() {
		return codePostale;
	}

	public void setCodePostale(String codePostale) {
		this.codePostale = codePostale;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public AdresseType getType() {
		return type;
	}

	public void setType(AdresseType type) {
		this.type = type;
	}

	public GPS getGps() {
		return gps;
	}

	public void setGps(GPS gps) {
		this.gps = gps;
	}

	@Override
	public String toString() {
		return "Adresse [pays=" + pays + ", ville=" + ville + ", numero=" + numero + ", codePostale=" + codePostale
				+ ", nom=" + nom + ", type=" + type + ", gps=" + gps + "]";
	}

}