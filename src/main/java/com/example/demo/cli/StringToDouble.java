package com.example.demo.cli;

import java.io.BufferedReader;
import java.util.function.Predicate;

public class StringToDouble {
	private BufferedReader inputReader;
	private String message;
	private Predicate<String> isValid;
	
	public StringToDouble(BufferedReader inputReader) {
		this.inputReader = inputReader;
		setMessage();
		setValidityCriterion();
	}

	public void setMessage() {
		message = "Veuillez saisir prix en chiffre:";		
	}

	public void setValidityCriterion() {
		isValid = str -> {
		try { 
			Double value = Double.parseDouble((String) str);
				return value instanceof Double;
		    } catch (Exception e) {
		    	return false;
		    }
		};
	}
	
	public double process() {
		try {
			String userInput = inputReader.readLine();

			while (!isValid.test(userInput)) {
				System.err.println("Désolé, mauvaise saisie. Veuillez réessayer.");
				System.out.println();
				System.out.println(message);
				userInput = inputReader.readLine();
			}
			
			double value = Double.parseDouble((String) userInput);
	        return value;
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}