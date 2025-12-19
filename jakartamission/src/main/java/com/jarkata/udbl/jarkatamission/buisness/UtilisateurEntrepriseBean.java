/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB40/StatelessEjbClass.java to edit this template
 */
package com.jarkata.udbl.jarkatamission.buisness;

import com.jarkata.udbl.jarkatamission.entities.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.ejb.LocalBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;
import javax.print.attribute.HashAttributeSet;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author joelm
 */
@Stateless
@LocalBean
public class UtilisateurEntrepriseBean {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void ajouterUtilisateurEntreprise(String username, String email, String password, String description) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Utilisateur utilisateur = new Utilisateur(username, email, hashedPassword, description);
        em.persist(utilisateur);
    }
    
    // Méthode pour vérifier un mot de passe public boolean verifierMotDePasse(String password, String hashedPassword) { return BCrypt.checkpw(password, hashedPassword); }  

    public List<Utilisateur> listerTousLesUtilisateurs() {
        return em.createQuery("SELECT u FROM Utilisateur u", Utilisateur.class).getResultList();
    }

    @Transactional
    public void supprimerUtilisateur(Long id) {
        Utilisateur utilisateur = em.find(Utilisateur.class, id);
        if (utilisateur != null) {
            em.remove(utilisateur);
        }
    }

    public Utilisateur trouverUtilisateurParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    public Utilisateur trouverUtilisateurParEmail(String email) {
        try {
            return em.createQuery("SELECT u FROM Utilisateur u WHERE u.email = :email", Utilisateur.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Vérifie si un utilisateur existe déjà avec le même username ou email
     * @param username Nom d'utilisateur à vérifier
     * @param email Email à vérifier
     * @return true si un utilisateur existe déjà, false sinon
     */
    public boolean utilisateurExiste(String username, String email) {
        try {
            Long count = em.createQuery(
                "SELECT COUNT(u) FROM Utilisateur u WHERE u.username = :username OR u.email = :email", 
                Long.class)
                .setParameter("username", username)
                .setParameter("email", email)
                .getSingleResult();
            return count > 0;
        } catch (Exception e) {
            return false;
        }
    }
    public boolean verifierMotDePasse(String password, String hashedPassword) { 
        return BCrypt.checkpw(password, hashedPassword); 
    }
    
    public Utilisateur authentifier(String email, String password){
        Utilisateur utilisateur = this.trouverUtilisateurParEmail(email);
        if (utilisateur != null && this.verifierMotDePasse(password, utilisateur.getPassword())){
            return utilisateur;
            }
        return null;
        
    }

    @Transactional
    public void modifierUtilisateur(String email, String password, String description) {
        Utilisateur utilisateur = trouverUtilisateurParEmail(email);
        if (utilisateur != null) {
            if (password != null && !password.isEmpty()) {
                String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
                utilisateur.setPassword(hashedPassword);
            }
            utilisateur.setDescription(description);
            em.merge(utilisateur);
        }
    }
}