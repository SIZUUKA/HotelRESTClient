package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Chambre {
	private String chambreId;
	private double prix; // euro
	private double taille; // m2
	private List<Lit> litCollection = new ArrayList<>();
	
	public Chambre() {
		super();
	}

	public Chambre(String chambreId, double prix, double taille, 
			List<Lit> litCollection) {
		super();
		this.chambreId = chambreId;
		this.prix = prix;
		this.taille = taille;
		this.litCollection = litCollection;
	}

	public String getChambreId() {
		return chambreId;
	}

	public void setChambreId(String chambreId) {
		this.chambreId = chambreId;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public List<Lit> getLitCollection() {
		return litCollection;
	}

	public void setLitCollection(List<Lit> litCollection) {
		this.litCollection = litCollection;
	}

	@Override
	public String toString() {
		return "Chambre [chambreId=" + chambreId + ", prix=" + prix + ", taille="
				+ taille + ", litCollection=" + litCollection + "]";
	}
	
	public int getChambreCapacite() {
		int chambreCapacite = 0;
		for (Lit lit : litCollection) {
			chambreCapacite += lit.getCapacite();
		}
		return chambreCapacite;
	}

}