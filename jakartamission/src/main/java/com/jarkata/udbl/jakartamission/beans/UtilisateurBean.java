/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jarkata.udbl.jakartamission.beans;

import com.jarkata.udbl.jarkatamission.buisness.UtilisateurEntrepriseBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


/**
 *
 * @author joelm
 */
@Named
@RequestScoped
public class UtilisateurBean {
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    @Size(min = 3, max = 50, message = "Le nom d'utilisateur doit avoir entre 3 et"
            + " 50 caractères")
    private String username;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;
    
    @NotBlank(message = "Veuillez confirmer votre mot de passe")
    private String confirmPassword;
    
    
     
    private String description;
    
    
     @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
   
    public void ajouterUtilisateur(){
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Vérifier si les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les mots de passe ne correspondent pas", null));
            return;
        }
        
        // Vérifier si l'utilisateur existe déjà
        if (utilisateurEntrepriseBean.utilisateurExiste(username, email)) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                "Ce nom d'utilisateur et cette adresse existent déjà.", null));
            return;
        }
        
        // Ajouter l'utilisateur avec un mot de passe haché
        utilisateurEntrepriseBean.ajouterUtilisateurEntreprise(username, email, password, description);
        
        // Ajout du message de succès si le mot de passe respecte les critères
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Utilisateur ajouté avec succès", null));
        
        System.out.println("Utilisateur ajouté : " + username + " - " + email);
        
        // Réinitialisation des champs
        username = "";
        email = "";
        password = "";
        confirmPassword = "";
        description = "";
    }
}
