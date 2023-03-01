package com.example.demo.cli;

import java.io.BufferedReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Predicate;

public class StringToCalendar {
	private BufferedReader inputReader;
	private String message;
	private Predicate<String> isValid;
	
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    
	public StringToCalendar(BufferedReader inputReader) {
		this.inputReader = inputReader;
		setMessage();
		setValidityCriterion();
	}

	public void setMessage() {
		message = "Veuillez saisir date en format (dd/MM/yyyy):";		
	}

	public void setValidityCriterion() {
		isValid = str -> {
		try { 
			Date date = sdf.parse((String) str);
				return date instanceof Date;
		    } catch (Exception e) {
		    	return false;
		    }
		};
	}
	
	public String process() {
		try {
//			System.out.println(message);
			String userInput = inputReader.readLine();

			while (!isValid.test(userInput)) {
				System.err.println("Désolé, mauvaise saisie. Veuillez réessayer.");
				System.out.println();
				System.out.println(message);
				userInput = inputReader.readLine();
			}
			
	        return userInput;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public Calendar processToCalendar(String userInput) {
		try {
			Date date = sdf.parse(userInput);
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(date);
	        return cal;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}