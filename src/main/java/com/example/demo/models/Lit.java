package com.example.demo.models;

public class Lit {
	private String type;
	private int capacite;
	
	public Lit() {
		super();
	}

	public Lit(String type, int capacite) {
		super();
		this.type = type;
		this.capacite = capacite;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCapacite() {
		return capacite;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	@Override
	public String toString() {
		return "Lit [type=" + type + ", capacite=" + capacite + "]";
	}
	
	
}