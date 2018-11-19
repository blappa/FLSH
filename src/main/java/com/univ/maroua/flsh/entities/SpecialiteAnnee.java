/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "specialiteannee")
@NamedQueries({
    @NamedQuery(name = "SpecialiteAnnee.findBySpecialiteAnnee", query = "SELECT spe FROM SpecialiteAnnee spe WHERE spe.specialite.id=:idSpecialite AND spe.anneeAcademique.id=:idAnnee "),})
public class SpecialiteAnnee implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "real default 12") //on met a 12 la valeur par defaut de la moyenne de master
    private float moyenne;
    private Boolean semestriel1;
    private Boolean semestriel1N;
    private Boolean semestriel1R;
    private Boolean semestriel2;
    private Boolean semestriel2N;
    private Boolean semestriel2R;
    private Boolean annuel;

    public SpecialiteAnnee() {
        moyenne = 12f; //on met 12 par defaut
        semestriel1 = false;
        semestriel1N = false;
        semestriel1R = false;
        semestriel2 = false;
        semestriel2N = false;
        semestriel2R = false;
        annuel = false;
    }
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Specialite specialite;
    @JoinColumn(name = "anneeacademique_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AnneeAcademique anneeAcademique;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(float moyenne) {
        this.moyenne = moyenne;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Boolean getSemestriel1() {
        return semestriel1;
    }

    public void setSemestriel1(Boolean semestriel1) {
        this.semestriel1 = semestriel1;
    }

    public Boolean getSemestriel1N() {
        return semestriel1N;
    }

    public void setSemestriel1N(Boolean semestriel1N) {
        this.semestriel1N = semestriel1N;
    }

    public Boolean getSemestriel1R() {
        return semestriel1R;
    }

    public void setSemestriel1R(Boolean semestriel1R) {
        this.semestriel1R = semestriel1R;
    }

    public Boolean getSemestriel2() {
        return semestriel2;
    }

    public void setSemestriel2(Boolean semestriel2) {
        this.semestriel2 = semestriel2;
    }

    public Boolean getSemestriel2N() {
        return semestriel2N;
    }

    public void setSemestriel2N(Boolean semestriel2N) {
        this.semestriel2N = semestriel2N;
    }

    public Boolean getSemestriel2R() {
        return semestriel2R;
    }

    public void setSemestriel2R(Boolean semestriel2R) {
        this.semestriel2R = semestriel2R;
    }

    


    public Boolean getAnnuel() {
        return annuel;
    }

    public void setAnnuel(Boolean annuel) {
        this.annuel = annuel;
    }
    
    @Override
    public Object clone() {
        Object o = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la 
            // méthode super.clone()
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons 
            // l'interface Cloneable
            cnse.printStackTrace(System.err);
        }
        // on renvoie le clone
        return o;
    }

    @Override
    public String toString() {
        return "SpecialiteAnnee{" + "id=" + id + ", moyenne=" + moyenne + ", semestriel1=" + semestriel1 + ", semestriel1N=" + semestriel1N + ", semestriel1R=" + semestriel1R + ", semestriel2=" + semestriel2 + ", semestriel2N=" + semestriel2N + ", semestriel2R=" + semestriel2R + ", annuel=" + annuel + '}';
    }
    
    
}
