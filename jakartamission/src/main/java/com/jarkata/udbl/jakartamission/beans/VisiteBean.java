package com.jarkata.udbl.jakartamission.beans;

import com.jarkata.udbl.jarkatamission.buisness.LieuEntrepriseBean;
import com.jarkata.udbl.jarkatamission.buisness.UtilisateurEntrepriseBean;
import com.jarkata.udbl.jarkatamission.buisness.VisiteEntrepriseBean;
import com.jarkata.udbl.jarkatamission.buisness.sessionManager;
import com.jarkata.udbl.jarkatamission.entities.Lieu;
import com.jarkata.udbl.jarkatamission.entities.Utilisateur;
import com.jarkata.udbl.jarkatamission.entities.Visite;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author joelm
 */
@Named(value = "visiteBean")
@RequestScoped
public class VisiteBean implements Serializable {

    private Integer lieuId;
    private Double tempsPasse;
    private String observation;
    private Double depense;
    private List<Visite> mesVisites;

    @Inject
    private VisiteEntrepriseBean visiteEntrepriseBean;

    @Inject
    private LieuEntrepriseBean lieuEntrepriseBean;

    @Inject
    private UtilisateurEntrepriseBean utilisateurEntrepriseBean;

    @Inject
    private sessionManager sessionManager;

    public Integer getLieuId() {
        return lieuId;
    }

    public void setLieuId(Integer lieuId) {
        this.lieuId = lieuId;
    }

    public Double getTempsPasse() {
        return tempsPasse;
    }

    public void setTempsPasse(Double tempsPasse) {
        this.tempsPasse = tempsPasse;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Double getDepense() {
        return depense;
    }

    public void setDepense(Double depense) {
        this.depense = depense;
    }

    public List<Visite> getMesVisites() {
        if (mesVisites == null) {
            chargerVisites();
        }
        return mesVisites;
    }

    private void chargerVisites() {
        String email = sessionManager.getValueFromSession("user");
        if (email != null) {
            Utilisateur u = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
            if (u != null) {
                mesVisites = visiteEntrepriseBean.listerVisitesParUtilisateur(u.getId());
            }
        }
    }

    /**
     * Affiche le formulaire de visite
     * @return la navigation vers la page de formulaire de visite
     */
    public String afficherFormulaireVisite(Integer id) {
        this.lieuId = id;
        return "formulaire_visite?faces-redirect=true&lieuId=" + id;
    }

    /**
     * Enregistre une visite
     * @return la navigation après l'enregistrement
     */
    public String enregistrerVisite() {
        FacesContext context = FacesContext.getCurrentInstance();
        String email = sessionManager.getValueFromSession("user");
        
        if (email == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Vous devez être connecté pour enregistrer une visite.", null));
            return null;
        }

        if (lieuId == null || lieuId == 0) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Veuillez sélectionner un lieu.", null));
            return null;
        }

        Utilisateur u = utilisateurEntrepriseBean.trouverUtilisateurParEmail(email);
        Lieu l = lieuEntrepriseBean.trouverLieuParId(lieuId);

        if (u != null && l != null) {
            Visite visite = new Visite(u, l);
            visite.setTempsPasse(tempsPasse);
            visite.setObservation(observation);
            visite.setDepense(depense);
            
            visiteEntrepriseBean.enregistrerVisite(visite);
            
            // Reset form
            lieuId = null;
            tempsPasse = null;
            observation = null;
            depense = null;
            
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Visite enregistrée avec succès !", null));
            return "visite?faces-redirect=true";
        } else {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'enregistrement de la visite.", null));
            return null;
        }
    }
}
