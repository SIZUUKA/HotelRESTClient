package com.example.demo.cli;

import java.io.BufferedReader;
import java.io.IOException;

public abstract class AbstractMain {
	public static String HOTEL_SEARCH_URL;
	public static String HOTEL_BOOK_URL;
	public static final String QUIT = "0";
	
	protected void setHotelSearchUrl(BufferedReader inputReader) 
			throws IOException {
		
		System.out.println("Veuillez fournir l'URL de recherche d'hôtel au service Web à utiliser :");
		HOTEL_SEARCH_URL = inputReader.readLine();
		
		while(!validHotelSearchUrl()) {
			System.err.println("Error: "+HOTEL_SEARCH_URL+
					" n'est pas une URL de service Web de recherche d'hôtel valide. "
					+ "Veuillez réessayer: ");
			HOTEL_SEARCH_URL = inputReader.readLine();
		}
	}
	
	protected void setHotelBookUrl(BufferedReader inputReader) 
			throws IOException {
		
		System.out.println("Veuillez fournir l'URL de réservation d'hôtel au service Web pour consommer: ");
		HOTEL_BOOK_URL = inputReader.readLine();
		
		while(!validHotelBookUrl()) {
			System.err.println("Error: "+HOTEL_BOOK_URL+
					" n'est pas une URL de service Web de réservation d'hôtel valide. "
					+ "Veuillez réessayer: ");
			HOTEL_BOOK_URL = inputReader.readLine();
		}
	}
	
	protected abstract boolean validHotelSearchUrl();
	
	protected abstract boolean validHotelBookUrl();
	
	protected abstract void menu();
}