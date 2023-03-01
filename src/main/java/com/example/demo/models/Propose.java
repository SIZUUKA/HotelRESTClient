package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Propose {
	private String offreId;
	private HotelPartenaireTarif hotelPartenaireTarif;
	private List<Chambre> listChambre = new ArrayList<>();
	
	public Propose() {
		super();
	}

	public Propose(String offreId, HotelPartenaireTarif hotelPartenaireTarif, List<Chambre> unGroupPropose) {
		super();
		this.offreId = offreId;
		this.hotelPartenaireTarif = hotelPartenaireTarif;
		this.listChambre = unGroupPropose;
	}

	public String getOffreId() {
		return offreId;
	}

	public void setOffreId(String offreId) {
		this.offreId = offreId;
	}

	public HotelPartenaireTarif getHotelPartenaireTarif() {
		return hotelPartenaireTarif;
	}

	public void setHotelPartenaireTarif(HotelPartenaireTarif hotelPartenaireTarif) {
		this.hotelPartenaireTarif = hotelPartenaireTarif;
	}

	public List<Chambre> getListChambre() {
		return listChambre;
	}

	public void setListChambre(List<Chambre> listChambre) {
		this.listChambre = listChambre;
	}
	
}