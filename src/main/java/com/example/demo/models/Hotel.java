package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hotel {
	// attribut
	private int hotelId;
	private String nom;
	private int categorie;
//	private Adresse adresse;
//	private List<Chambre> chambreCollection = new ArrayList<>();
//	private List<Reservation> reservCollection = new ArrayList<>();
//	private List<List<Chambre>> groupPropose = new ArrayList<List<Chambre>>();
	
	public Hotel() {
	}

//	public Hotel(String nom, int categorie, 
//			Adresse adresse, List<Chambre> chambreCollection) {
//		this.nom = nom;
//		this.categorie = categorie;
//		this.adresse = adresse;
//		this.chambreCollection = chambreCollection;
//	}
	
	@Override
	public String toString() {
		return "Hotel [hotelId=" + hotelId + ", nom=" + nom + ", categorie=" + categorie + "]";
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getCategorie() {
		return categorie;
	}

	public void setCategorie(int categorie) {
		this.categorie = categorie;
	}

	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
//	public List<Chambre> getChambreCollection() {
//		return chambreCollection;
//	}
//
//	public void setChambreCollection(List<Chambre> chambreCollection) {
//		this.chambreCollection = chambreCollection;
//	}
//
//	// propose all the combinations
//	public List<List<Chambre>> chambresAllPropose(Calendar dateArrivee, Calendar dateDepart, int nombrePerson) {
//		this.groupPropose.clear();
//		List<Chambre> chambresDispo = this.chambresDispoDansPeriode(dateArrivee, dateDepart);
//		int hotelCapacite = this.capaciteDeChambresDispo(chambresDispo);
//		if (hotelCapacite >= nombrePerson) {
//			this.sum_up(chambresDispo, nombrePerson);
//			if (this.groupPropose.size() == 0) {
//				this.sum_up(chambresDispo, nombrePerson+1);
//			}
//		}
//		return this.groupPropose;
//	}
//	
//	private void sum_up(List<Chambre> numbers, int target) {
//        sum_up_recursive(numbers,target,new ArrayList<Chambre>());
//    }
//	
//	private void sum_up_recursive(List<Chambre> numbers, int target, List<Chambre> partial) {
//       int s = 0;
//       for (Chambre x: partial) {
//    	   s += x.getChambreCapacite();
//       }
//       if (s == target) {
//    	   System.out.println("proposition ("+Arrays.toString(partial.toArray())+")="+target);
//    	   this.groupPropose.add(partial);
//       }
//       if (s >= target) {
//           return;
//       }
//       for(int i=0;i<numbers.size();i++) {
//			List<Chambre> remaining = new ArrayList<Chambre>();
//			int n = numbers.get(i).getChambreCapacite();//p
//			for (int j=i+1; j<numbers.size();j++) remaining.add(numbers.get(j));
//			List<Chambre> partial_rec = new ArrayList<Chambre>(partial);
//			partial_rec.add(numbers.get(i));
//			sum_up_recursive(remaining,target,partial_rec);
//       }
//    }
//		
//	public List<Chambre> chambresDispoDansPeriode(Calendar dateArrivee, Calendar dateDepart) {
//		List<Chambre> chambresDispo = new ArrayList<>();
//		chambresDispo.addAll(this.chambreCollection);
//		for (Reservation reservation : this.reservCollection) {
//			if (!(!reservation.getDateArrivee().before(dateDepart) || !reservation.getDateDepart().after(dateArrivee))) {
//				for (Chambre cr : reservation.getChambreReserveCollection()) {
//					if (chambresDispo.contains(cr)) {
//						chambresDispo.remove(cr);
//					}
//				}
//			}
//		}
//		return chambresDispo;
//	}
//	
//	public int capaciteDeChambresDispo(List<Chambre> chambresDispo) {
//		int hotelCapacite = 0;
//		for (Chambre c : chambresDispo) {
//			hotelCapacite += c.getChambreCapacite();
//		}
//		return hotelCapacite;
//	}
//	
//	public void reserve(HotelPartenaireTarif hotelPartenaireTarif, Reservation res, Agence agence) {
//		this.reservCollection.add(res);
//		agence.addReservation(res);
//	}
}