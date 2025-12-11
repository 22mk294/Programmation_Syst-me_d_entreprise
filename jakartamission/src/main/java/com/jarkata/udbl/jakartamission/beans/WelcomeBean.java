/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jarkata.udbl.jakartamission.beans;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;


/**
 *
 * @author joelm
 */
@RequestScoped
@Named

public class WelcomeBean {

    private String nom;
    private Double montantUSD;
    private Double montantIDR;
    private String message;
    private String messageidr;

    private final double TAUX_IDR = 16000; 
    // 1 USD = 16 000 IDR (modifiable)

    // Getters et Setters
    public String getNom() {
        return nom; 
    }
    public void setNom(String nom) { 
        this.nom = nom; 
    }

    public Double getMontantUSD() {
        return montantUSD; 
    }
    public void setMontantUSD(Double montantUSD) {
        this.montantUSD = montantUSD; 
    }

    public Double getMontantIDR() { 
        return montantIDR; 
    }
    public void setMontantIDR(Double montantIDR) { 
        this.montantIDR = montantIDR; 
    }

    public String getMessage() {
        return message; 
    }

    public String getMessageidr() {
        return messageidr;
    }
    
    
    
    public void afficher(){
        this.message = "Welcome to Indonesia dear " + this.nom;
    }

    public void convertir() {

        if (montantUSD != null && montantUSD > 0) {
            // USD → IDR
            double resultat = montantUSD * TAUX_IDR;
            message = "Montant "
                    + montantUSD + " USD équivaut à "
                    + resultat + " IDR.";
        }
        else {
            message = "Veuillez entrer un montant valide en USD ou en IDR.";
        }
    }
    
    public void convertir_idr(){
        if(montantIDR != null && montantIDR > 0) {
            // IDR → USD
            double resultat = montantIDR / TAUX_IDR;
            messageidr = "Montant " + montantIDR + " IDR équivaut à " + resultat + " USD.";
        }
        else {
            message = "Veuillez entrer un montant valide en USD ou en IDR.";
        }
    }
}