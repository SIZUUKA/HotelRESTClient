package com.example.demo.models;

public class HotelPartenaireTarif {
	private Hotel hotel;
	private double pourcentage;

	public HotelPartenaireTarif() {
		super();
	}

	public HotelPartenaireTarif(Hotel hotel, double pourcentage) {
		super();
		this.hotel = hotel;
		this.pourcentage = pourcentage;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public double getPourcentage() {
		return pourcentage;
	}

	public void setPourcentage(double pourcentage) {
		this.pourcentage = pourcentage;
	}

}