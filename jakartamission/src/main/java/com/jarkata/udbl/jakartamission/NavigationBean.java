/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jarkata.udbl.jakartamission;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named("navigationController")
@RequestScoped
public class NavigationBean {

    public void voirApropos() {
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("pages/a_propos.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(NavigationBean.class.getName())
                    .log(Level.SEVERE, "Erreur lors de la navigation", ex);
        }
    }
    
    public void redirection(String destination){
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect(destination);
        } catch (IOException ex) {
            Logger.getLogger(NavigationBean.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
    
    //navigation vers lieu
    public void ajouterLieu(){
        try {
            FacesContext.getCurrentInstance().getExternalContext()
                    .redirect("pages/lieu.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(NavigationBean.class.getName())
                    .log(Level.SEVERE, "Erreur lors de la navigation", ex);
        }
        
    }
    public void voirVisite(){
        this.redirection("pages/visite.xhtml");
    }
}