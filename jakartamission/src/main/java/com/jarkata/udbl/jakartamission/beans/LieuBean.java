/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jarkata.udbl.jakartamission.beans;

import com.jarkata.udbl.jarkatamission.buisness.LieuEntrepriseBean;
import com.jarkata.udbl.jarkatamission.entities.Lieu;
import jakarta.faces.view.ViewScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joelm
 */
@Named(value = "lieuBean")
@ViewScoped
public class LieuBean implements Serializable{

    private int id;
    private String nom;
    private String description;
    private double longitude;
    private double latitude;
    private final List<Lieu> lieux = new ArrayList<>();
    private Integer selectedLieu;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getSelectedLieu() {
        return selectedLieu;
    }

    public void setSelectedLieu(Integer selectedLieu) {
        this.selectedLieu = selectedLieu;
    }
    
    

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }

    public List<Lieu> getLieux() { return lieuEntrepriseBean.listerTousLesLieux(); }

    public void ajouterLieux() {
        if (nom != null && !nom.isEmpty() && description != null && !description.isEmpty()) {
            lieuEntrepriseBean.ajouterLieuEntreprise(nom, description, latitude, longitude);
            resetForm();
        }
    }

    public void modifier() {
        if (id > 0 && nom != null && !nom.isEmpty()) {
            Lieu lieu = new Lieu(nom, description, longitude, latitude);
            lieu.setId(id);
            lieuEntrepriseBean.modifierLieu(lieu);
            resetForm();
        }
    }

    public void supprimer() {
        if (id > 0) {
            lieuEntrepriseBean.supprimerLieu(id);
            resetForm();
        }
    }

    private void resetForm() {
        id = 0;
        nom = null;
        description = null;
        latitude = 0.0;
        longitude = 0.0;
    }
    
    private String weatherMessage = "";
    
    public void fetchWeatherMessage(Lieu l) {
        if (l == null) {
            this.weatherMessage = "Veuillez sélectionner un lieu valide.";
            return;
        }
        
        try {
            // Appel au service web pour obtenir les données météorologiques
            //String serviceURL = "http://localhost:8080/j-weather/webapi/JakartaWeather?latitude="
              String serviceURL = "http://localhost:8080/j-weather/resources/JakartaWeather?latitude="
                    + l.getLatitude() + "&longitude=" + l.getLongitude();
            
            System.out.println("Appel du service météo: " + serviceURL);
            
            Client client = ClientBuilder.newClient();
            String response = client.target(serviceURL)
                    .request(MediaType.TEXT_PLAIN)
                    .get(String.class);
            
            // Enregistrement du message météo dans la variable weatherMessage
            this.weatherMessage = response;
            System.out.println("Réponse météo: " + response);
            
        } catch (Exception e) {
            this.weatherMessage = "Erreur lors de la récupération de la météo: " + e.getMessage() + ". Vérifiez que le service j-weather est démarré sur http://localhost:8080/j-weather/";
            System.err.println("Erreur météo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void updateWeatherMessage(AjaxBehaviorEvent event) {
        if (selectedLieu == null || selectedLieu == 0) {
            this.weatherMessage = "Veuillez sélectionner un lieu.";
            return;
        }
        
        try {
            Lieu lieu = lieuEntrepriseBean.trouverLieuParId(selectedLieu);
            if (lieu != null) {
                this.fetchWeatherMessage(lieu);
            } else {
                this.weatherMessage = "Lieu introuvable avec l'ID: " + selectedLieu;
            }
        } catch (Exception e) {
            this.weatherMessage = "Erreur lors de la recherche du lieu: " + e.getMessage();
            System.err.println("Erreur lors de updateWeatherMessage: " + e.getMessage());
            e.printStackTrace();
        }
    }
 public String getWeatherMessage() {
        return weatherMessage;
    }
    public void setWeatherMessage(String weatherMessage) {
        this.weatherMessage = weatherMessage;
    }
}
