package com.example.demo.cli;

import java.io.BufferedReader;
import java.util.function.Predicate;

public class StringToInt {
	private BufferedReader inputReader;
	private String message;
	private Predicate<String> isValid;
	
	public StringToInt(BufferedReader inputReader) {
		this.inputReader = inputReader;
		setMessage();
		setValidityCriterion();
	}

	public void setMessage() {
		message = "Veuillez saisir en chiffre Integer:";		
	}

	public void setValidityCriterion() {
		isValid = str -> {
		try { 
			Integer value = Integer.parseInt((String) str);
				return value instanceof Integer;
		    } catch (Exception e) {
		    	return false;
		    }
		};
	}
	
	public int process() {
		try {
			String userInput = inputReader.readLine();

			while (!isValid.test(userInput)) {
				System.err.println("Désolé, mauvaise saisie. Veuillez réessayer.");
				System.out.println();
				System.out.println(message);
				userInput = inputReader.readLine();
			}
			
			int value = Integer.parseInt((String) userInput);
	        return value;
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}