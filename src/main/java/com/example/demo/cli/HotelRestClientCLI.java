package com.example.demo.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.Agence;
import com.example.demo.models.CarteCredit;
import com.example.demo.models.Chambre;
import com.example.demo.models.Client;
import com.example.demo.models.HotelPartenaireTarif;
import com.example.demo.models.Lit;
import com.example.demo.models.Propose;
import com.example.demo.models.Reservation;

@Component
public class HotelRestClientCLI extends AbstractMain implements CommandLineRunner {

    @Autowired
    private RestTemplate proxy;
    public static StringToCalendar inputStringToCalendar;
    public static StringToDouble inputStringToDouble;
    public static StringToInt inputStringToInt;

    @Override
    public void run(String...args) throws Exception {
        BufferedReader inputReader;
        String userInput = "";
        try {
            inputReader = new BufferedReader(
                new InputStreamReader(System.in));
            setHotelSearchUrl(inputReader);
            setHotelBookUrl(inputReader);
            do {
                menu();
                userInput = inputReader.readLine();
                processUserInput(inputReader, userInput);
                Thread.sleep(1000);

            } while (!userInput.equals(QUIT));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean validHotelSearchUrl() {
        return HOTEL_SEARCH_URL.equals(
            "http://localhost:8080/hotelsearch/api");
    }

    @Override
    protected boolean validHotelBookUrl() {
        return HOTEL_BOOK_URL.equals(
            "http://localhost:8080/hotelbook/api");
    }

    @Override
    protected void menu() {
        StringBuilder builder = new StringBuilder();
        builder.append(QUIT + ". Quit.");
        builder.append("\n1. Agence Login.");
        System.out.println(builder);
    }

    private void processUserInput(BufferedReader reader, String userInput) {
        try {
            switch (userInput) {
            default:
                System.err.println("Désolé, mauvaise saisie. Veuillez réessayer.");
                return;
            case QUIT:
                System.out.println("Au revoir ...");
                System.exit(0);
            case "1":
                Agence[] agenceLogin = this.login(reader);
                while (agenceLogin.length == 0) {
                    System.err.println("Identifiant ou mot de passe n'est pas correct, veuillez réessayer !\n");
                    agenceLogin = this.login(reader);
                }
                String agenceLoginId = agenceLogin[0].getIdentifiant();
                System.out.println(agenceLoginId + " login avec succès !");

                System.out.println("Date arrivée (dd/MM/yyyy) aujourd'hui ou après aujourd'hui : ");
                inputStringToCalendar = new StringToCalendar(reader);
                String dateArrivee = inputStringToCalendar.process();
                Calendar dateArriveeCal = (Calendar) inputStringToCalendar.processToCalendar(dateArrivee);
                System.out.println();

                System.out.println("Date départ (dd/MM/yyyy) après date arrivée : ");
                inputStringToCalendar = new StringToCalendar(reader);
                String dateDepart = inputStringToCalendar.process();
                Calendar dateDepartCal = (Calendar) inputStringToCalendar.processToCalendar(dateDepart);
                while (!dateDepartCal.after(dateArriveeCal)) {
                    System.err.println("Date départ doit être après date arrivée !");
                    System.out.println();
                    System.out.println("Date départ (dd/MM/yyyy): ");
                    inputStringToCalendar = new StringToCalendar(reader);
                    dateDepart = inputStringToCalendar.process();
                    dateDepartCal = (Calendar) inputStringToCalendar.processToCalendar(dateDepart);
                }
                System.out.println();

                System.out.println("Nombre de personnes à héberger: ");
                inputStringToInt = new StringToInt(reader);
                int nombrePerson = (int) inputStringToInt.process();
                System.out.println();

                String uri = HOTEL_SEARCH_URL + "/agence/propose?" +
                    "agenceLoginId=" + agenceLoginId +
                    "&dateArrivee=" + dateArrivee +
                    "&dateDepart=" + dateDepart +
                    "&nombrePerson=" + nombrePerson;
                System.out.println(uri);
                Propose[] allCombinations = proxy.getForObject(uri, Propose[].class);
                if (allCombinations.length == 0) {
                    System.err.println("Désolé, pas d'hôtel correspond. Veuillez réessayer.");
                    break;
                } else {
                    System.out.println("Voici tous les propositions : ");
                    int days = this.daysBetween(dateArriveeCal, dateDepartCal);
                    List < String > listChambreId = this.displayAllCombinations(allCombinations, dateArriveeCal, dateDepartCal, agenceLogin, days);
                    String display = "";
                    do {
                        System.out.println("Display image de chambre ? y/n");
                        display = reader.readLine();
                        System.out.println();
                        this.processDisplayInput(reader, display, listChambreId);
                    } while (!display.equals("n"));

                    // IHotelServiceWeb2 reserver
                    System.out.println("Saisir l‘identifiant de l'offre pour réserver : ");
                    String identifiantOffre = reader.readLine();
                    System.out.println();
                    Propose offreChoisi = this.checkPropose(allCombinations, identifiantOffre);
                    while (offreChoisi == null) {
                        System.err.println("Désolé, pas d'hôtel correspond. Veuillez réessayer.");
                        System.out.println("Saisir l‘identifiant de l'offre pour réserver : ");
                        identifiantOffre = reader.readLine();
                        System.out.println();
                        offreChoisi = this.checkPropose(allCombinations, identifiantOffre);
                    }

                    System.out.println(identifiantOffre + " est choisi.");
                    // Agence login
                    Agence[] agenceLoginRes = this.loginRes(reader);
                    while (agenceLoginRes.length == 0 ||
                        !agenceLoginRes[0].getIdentifiant().equals(agenceLogin[0].getIdentifiant()) ||
                        !agenceLoginRes[0].getMdp().equals(agenceLogin[0].getMdp())) {
                        System.err.println("Identifiant ou mot de passe n'est pas correct, veuillez réessayer !\n");
                        agenceLoginRes = this.loginRes(reader);
                    }
                    String agenceId = agenceLoginRes[0].getIdentifiant();
                    System.out.println(agenceId + " login avec succès !");
                    // Client
                    System.out.println("Nom de client principal : ");
                    String nom = reader.readLine();
                    System.out.println();

                    System.out.println("Prenom de client principal : ");
                    String prenom = reader.readLine();
                    System.out.println();

                    CarteCredit createdCarteCredit = createCarteCredit(reader);

                    uri = HOTEL_BOOK_URL + "/cartecredit";
                    CarteCredit returnedCarteCredit = proxy.postForObject(uri, createdCarteCredit, CarteCredit.class);

                    Client createdClient = new Client(nom, prenom, returnedCarteCredit);
                    uri = HOTEL_BOOK_URL + "/client";
                    Client returnedClient = proxy.postForObject(uri, createdClient, Client.class);

                    HotelPartenaireTarif hotelPartenaireTarif = offreChoisi.getHotelPartenaireTarif();
                    int hotelId = hotelPartenaireTarif.getHotel().getHotelId();
                    List < Chambre > chambreChoisi = offreChoisi.getListChambre();
                    double prixChoisi = agenceLogin[0].prixChoisi(offreChoisi) * days;

                    String reservationId = this.generateResId(agenceLoginRes[0], returnedClient);
                    reservationId = reservationId.replaceAll("\\s+", "");
                    Reservation createdRes = new Reservation(reservationId, chambreChoisi,
                        dateArriveeCal, dateDepartCal, returnedClient, prixChoisi);
                    try {
                        uri = HOTEL_BOOK_URL + "/reservation";
                        Reservation returnedRes = proxy.postForObject(uri, createdRes, Reservation.class);
                        uri = HOTEL_BOOK_URL + "/addreservationagence/" + agenceId;
                        proxy.put(uri, returnedRes);
                        uri = HOTEL_BOOK_URL + "/addreservationhotel/" + hotelId;
                        proxy.put(uri, returnedRes);
                        System.out.println("Réservé avec succès. Votre numéro de réservation est " + reservationId);
                    } catch (Exception e) {
                        System.err.println("Désolé, il y a un problème avec la réservation. Veuillez réessayer.");
                        break;
                    }
                }
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (HttpClientErrorException e) { // format error in cli
            System.err.println(e.getMessage());
        }
    }

    public List < String > displayAllCombinations(Propose[] allCombinations,
        Calendar dateArrivee, Calendar dateDepart, Agence[] agenceLogin, int days) {
        List < String > listChambreId = new ArrayList < > ();
        for (Propose propose: allCombinations) {
            System.out.println(
                "Identifiant de l'offre : " + propose.getOffreId() + "\n" +
                "Nom de l'hôtel : " + propose.getHotelPartenaireTarif().getHotel().getNom() + "\n" +
                "Date de disponibilité : de " + this.calendarToString(dateArrivee) + " à " + this.calendarToString(dateDepart)
            );
            int nombreLits = 0;
            for (Chambre c: propose.getListChambre()) {
                listChambreId.add(c.getChambreId());
                String descLit = "";
                System.out.println(
                    "#Chambre Id : " + c.getChambreId()
                );
                for (Lit lit: c.getLitCollection()) {
                    descLit = descLit + lit.toString() + "\n";
                    nombreLits++;
                }
                System.out.println(
                    descLit
                );
            }
            System.out.println(
                "Nombre de lits totaux proposés : " + nombreLits + "\n" +
                "Prix total à payer : " + this.doubleToString(agenceLogin[0].prixChoisi(propose) * days) + " (avec pourcentage de commission)" + " (Pour " + days + " nuits)" + "\n" +
                "--------------------"
            );
        }
        return listChambreId;
    }

    private String calendarToString(Calendar date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        String dateString = format1.format(date.getTime());
        return dateString;
    }

    private String doubleToString(double prix) {
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(prix);
    }

    private int daysBetween(Calendar dateArrivee, Calendar dateDepart) {
        Date d1 = dateArrivee.getTime();
        Date d2 = dateDepart.getTime();
        return (int)((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    private Agence[] login(BufferedReader reader) {
        String identifiant = null;
        String mdp = null;
        try {
            System.out.println("Veuillez login pour rechercher !");
            System.out.println("Saisir identifiant de l'agence :");

            identifiant = reader.readLine();
            System.out.println();

            System.out.println("Saisir mot de passe de l'agence :");
            mdp = reader.readLine();
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String uri = HOTEL_SEARCH_URL + "/agence/identifiantandmdp?identifiant=" + identifiant + "&mdp=" + mdp;
        Agence[] agenceLogin = proxy.getForObject(uri, Agence[].class);
        return agenceLogin;
    }

    private Agence[] loginRes(BufferedReader reader) {
        String identifiant = null;
        String mdp = null;
        try {
            System.out.println("Veuillez login pour reserver !");
            System.out.println("Saisir identifiant de l'agence (le même identifiant d'agence qui a récupéré les offres) :");

            identifiant = reader.readLine();
            System.out.println();

            System.out.println("Saisir mot de passe de l'agence :");
            mdp = reader.readLine();
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
        String uri = HOTEL_BOOK_URL + "/agence/identifiantandmdp?identifiant=" + identifiant + "&mdp=" + mdp;
        Agence[] agenceLoginRes = proxy.getForObject(uri, Agence[].class);
        return agenceLoginRes;
    }

    private String generateResId(Agence agenceLoginRes, Client client) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String reservationId = agenceLoginRes.getIdentifiant() + client.getNom() + timestamp.getTime();
        return reservationId;
    }

    private Propose checkPropose(Propose[] allCombinationsList, String identifiantOffre) {
        for (Propose propose: allCombinationsList) {
            if (propose.getOffreId().equals(identifiantOffre)) {
                return propose;
            }
        }
        return null;
    }

    public void displayImage(String imageName) throws Exception {
        String uri = HOTEL_SEARCH_URL + "/hotel/image?imageName=" + imageName;
        String myOS = System.getProperty("os.name").toLowerCase();
        System.out.println("(Votre système d'exploitation est: " + myOS + ")\n");

        try {
            // Only For Windows
        	if (myOS.contains("windows 10")) {
                System.out.println("Parfait! Aller avec Windows 10 (Attendez quelques secondes ...)");
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "start", uri);
                pb.start();
            }
        } catch (Exception e) {
            System.out.println("Oops!! Quelque chose ne va pas. " + e.getMessage());
        }
    }

    public void processDisplayInput(BufferedReader reader, String display, List < String > listChambreId) {
        try {
            switch (display) {
            default:
                System.err.println("Désolé, mauvaise saisie. Veuillez réessayer.");
                return;
            case "n":
                return;
            case "y":
                System.out.println("Chambre Id ?");
                String chambreId = reader.readLine();
                if (listChambreId.contains(chambreId)) {
                    this.displayImage(chambreId);
                } else {
                    System.err.println("Chambre Id n'est pas correct.");
                }
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CarteCredit createCarteCredit(BufferedReader reader) {
        // carte credit
        CarteCredit createdCarteCredit = null;
        while (!(createdCarteCredit instanceof CarteCredit)) {
            System.out.println("Numéro de carte crédit (16 chiffres) : ");
            String carteNumero = null;
            try {
                carteNumero = reader.readLine();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println();

            System.out.println("Mois expiré (2 chiffres) : ");
            inputStringToInt = new StringToInt(reader);
            int expireMois = (int) inputStringToInt.process();
            System.out.println();

            System.out.println("Année expirée (4 chiffres) : ");
            inputStringToInt = new StringToInt(reader);
            int expireAnnee = (int) inputStringToInt.process();
            System.out.println();

            System.out.println("CVC code (3 chiffres) : ");
            String cvcCode = null;
            try {
                cvcCode = reader.readLine();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            System.out.println();
            createdCarteCredit = new CarteCredit(carteNumero, cvcCode, expireMois, expireAnnee);
        }
        return createdCarteCredit;
    }

    //	private XMLGregorianCalendar calendarToXMLGregorianCalendar(Calendar cal) {
    //		XMLGregorianCalendar gDateFormatted = null;
    //		try {
    //			gDateFormatted =
    //					DatatypeFactory.newInstance().newXMLGregorianCalendar(
    //						cal.get(Calendar.YEAR),
    //						cal.get(Calendar.MONTH) + 1,
    //						cal.get(Calendar.DAY_OF_MONTH),
    //						cal.get(Calendar.HOUR_OF_DAY),
    //						cal.get(Calendar.MINUTE),
    //						cal.get(Calendar.SECOND), DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);
    //		} catch (DatatypeConfigurationException e) {
    //			e.printStackTrace();
    //		}
    //		return gDateFormatted;
    //	}

}