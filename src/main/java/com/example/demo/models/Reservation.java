package com.example.demo.models;

import java.util.Calendar;
import java.util.List;

public class Reservation {
	private String reservationId;
	private List<Chambre> chambreReserveCollection;
	private Calendar dateArrivee;
	private Calendar dateDepart;
	private Client client;
	private double prix;
	
	public Reservation() {
	}

	public Reservation(String reservationId, List<Chambre> chambreChoisi, Calendar dateArrivee, Calendar dateDepart,
			Client client, double prix) {
		this.reservationId = reservationId;
		this.chambreReserveCollection = chambreChoisi;
		this.dateArrivee = dateArrivee;
		this.dateDepart = dateDepart;
		this.client = client;
		this.prix = prix;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}

	public List<Chambre> getChambreReserveCollection() {
		return chambreReserveCollection;
	}

	public void setChambreReserveCollection(List<Chambre> chambreReserveCollection) {
		this.chambreReserveCollection = chambreReserveCollection;
	}

	public Calendar getDateArrivee() {
		return dateArrivee;
	}

	public void setDateArrivee(Calendar dateArrivee) {
		this.dateArrivee = dateArrivee;
	}

	public Calendar getDateDepart() {
		return dateDepart;
	}

	public void setDateDepart(Calendar dateDepart) {
		this.dateDepart = dateDepart;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
	

}