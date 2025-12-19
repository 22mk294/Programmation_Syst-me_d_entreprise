/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jarkata.udbl.jakartamission.beans;

import com.jarkata.udbl.jarkatamission.buisness.UtilisateurEntrepriseBean;
import com.jarkata.udbl.jarkatamission.buisness.sessionManager;
import com.jarkata.udbl.jarkatamission.entities.Utilisateur;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;


/**
 *
 * @author joelm
 */
@RequestScoped
@Named

public class WelcomeBean {
    @Inject
    private UtilisateurEntrepriseBean utilisateurBean;
    
    @Inject
    private sessionManager sessionManager;
    
    private String email;
    private String password;
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String sAuthentifier(){
        Utilisateur utilisateur = utilisateurBean.authentifier(email, password);
        FacesContext context = FacesContext.getCurrentInstance();
        if (utilisateur != null){
                sessionManager.createSession("user", email);
                sessionManager.createSession("username", utilisateur.getUsername());
                return "home?faces-redirect=true";
                
            }else {
            this.message="Email ou mot de passe incorrect.";
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
                return null;
        }
    }
    
}