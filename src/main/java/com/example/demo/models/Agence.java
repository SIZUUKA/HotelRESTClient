package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

public class Agence {
	private String identifiant;
	private String mdp;
	private List<HotelPartenaireTarif> partenaire = new ArrayList<>();
	private List<Reservation> listReservation = new ArrayList<>();

	public Agence() {
		super();
	}

	// Le "identifiant" et le "mdp" de l'agence sont les mêmes pour accéder à tous
	// ces hôtels partenaires
	public Agence(String identifiant, String mdp) {
		super();
		this.identifiant = identifiant;
		this.mdp = mdp;
	}

	public List<Reservation> getListReservation() {
		return listReservation;
	}

	public void setListReservation(List<Reservation> listReservation) {
		this.listReservation = listReservation;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setIdentifiant(String identifiant) {
		this.identifiant = identifiant;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public List<HotelPartenaireTarif> getHotelPartenaireTarif() {
		return partenaire;
	}

	public void setHotelPartenaireTarif(List<HotelPartenaireTarif> partenaire) {
		this.partenaire = partenaire;
	}

	// pourcentage d'agence est varie pour chaque hôtel
	public void addHotelPartenaireTarif(Hotel hotel, double pourcentage) {
		HotelPartenaireTarif newPartenaire = new HotelPartenaireTarif(hotel, pourcentage);
		this.partenaire.add(newPartenaire);
	}

	// according to hotel's dispo date and nombrePerson
//	public List<Propose> allProposes(Calendar dateArrivee, Calendar dateDepart, int nombrePerson) {
//		List<Propose> allProposes = new ArrayList<>();
//		int i = 0;
//		for (HotelPartenaireTarif hotelPartenaireTarif : this.partenaire) {
//			List<List<Chambre>> groupPropose = hotelPartenaireTarif.getHotel().chambresAllPropose(dateArrivee, dateDepart, nombrePerson);
//			for (List<Chambre> unGroupPropose : groupPropose) {
//				i++;
//				Propose unPropose = new Propose("offre"+i, hotelPartenaireTarif, unGroupPropose);
//				allProposes.add(unPropose);
//			}
//		}
//		return allProposes;
//	}

	// test
//	public List<Propose> allProposesTest(Calendar dateArrivee, Calendar dateDepart, int nombrePerson) {
//		List<Propose> allProposes = new ArrayList<>();
//		Propose unPropose = new Propose("offre1", this.getHotelPartenaireTarif().get(0), this.getHotelPartenaireTarif().get(0).getHotel().getChambreCollection());
//		allProposes.add(unPropose);
//		return allProposes;
//	}

	// prix total par nuit, de toutes les chambres, calcule avec commission
	public double prixChoisi(Propose propose) {
		double prix = 0;
		List<Chambre> listChambre = propose.getListChambre();
		for (Chambre c : listChambre) {
			prix += c.getPrix();
		}
		prix = prix * propose.getHotelPartenaireTarif().getPourcentage();
		return prix;
	}

	public void addReservation(Reservation res) {
		this.listReservation.add(res);
	}

}