/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

/**
 *
 * @author richard@lateu
 */
public class LoggerModel {
    private String jourop;
    private String heureop;
    private String utilisateur;
    private String tache;

    public LoggerModel() {
    }

    public LoggerModel(String dateop, String utilisateur, String tache) {
        this.utilisateur = utilisateur;
        this.tache = tache;
    }

 

    public String getTache() {
        return tache;
    }

    public void setTache(String tache) {
        this.tache = tache;
    }

    public String getJourop() {
        return jourop;
    }

    public void setJourop(String jourop) {
        this.jourop = jourop;
    }

    public String getHeureop() {
        return heureop;
    }

    public void setHeureop(String heureop) {
        this.heureop = heureop;
    }

  
  

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    
    
    
    
   
    
    
    
    
}
