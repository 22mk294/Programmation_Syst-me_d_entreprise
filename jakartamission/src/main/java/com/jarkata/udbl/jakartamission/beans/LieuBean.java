/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jarkata.udbl.jakartamission.beans;

import com.jarkata.udbl.jarkatamission.buisness.LieuEntrepriseBean;
import com.jarkata.udbl.jarkatamission.entities.Lieu;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author joelm
 */
@Named(value = "lieuBean")
@RequestScoped
public class LieuBean implements Serializable{

    private int id;
    private String nom;
    private String description;
    private double longitude;
    private double latitude;
    private final List<Lieu> lieux = new ArrayList<>();

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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
}
