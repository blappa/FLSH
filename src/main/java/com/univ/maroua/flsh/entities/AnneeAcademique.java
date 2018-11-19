/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "anneeacademique")
@NamedQueries({
    @NamedQuery(name = "AnneeAcademique.findAll", query = "SELECT a FROM AnneeAcademique a"),
    @NamedQuery(name = "AnneeAcademique.findById", query = "SELECT a FROM AnneeAcademique a WHERE a.id = :id"),
    @NamedQuery(name = "AnneeAcademique.findByAnnee", query = "SELECT a FROM AnneeAcademique a WHERE a.annee = :annee"),
    @NamedQuery(name = "AnneeAcademique.findByEtudiantSpecialite", query = "SELECT se.anneeAcademique FROM SpecialiteEtudiant se WHERE se.etudiant.id = :idEtudiant AND se.specialite.id=:idSpecialite"),})
public class AnneeAcademique implements Serializable, Comparable<AnneeAcademique> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String annee;
    private Long rank; //rang qui contient le dernier numero genere
    private Long quitus; //le numero de quitus
    @Column(columnDefinition = "boolean default false")
    private Boolean istargetyear;
    
    @OneToMany(mappedBy = "anneeAcademique", cascade = {CascadeType.ALL})
    private List<Inscription> inscriptions;
    @OneToMany(mappedBy = "anneeAcademique", cascade = {CascadeType.ALL})
    private List<Module> modules;
   
    @OneToMany(mappedBy = "anneeAcademique", cascade = {CascadeType.ALL})
    private List<SpecialiteEtudiant> specialiteEtudiants;
    @OneToMany(mappedBy = "anneeAcademique", cascade = {CascadeType.ALL})
    private List<Statistique> statistiques;
    @OneToMany(mappedBy = "anneeAcademique", cascade = {CascadeType.ALL})
    private List<SpecialiteAnnee> specialiteAnnees;
    @OneToMany(mappedBy = "anneeAcademique", cascade = {CascadeType.ALL})
    private List<Sms> smses;
   

    public List<SpecialiteAnnee> getSpecialiteAnnees() {
        return specialiteAnnees;
    }

    public void setSpecialiteAnnees(List<SpecialiteAnnee> specialiteAnnees) {
        this.specialiteAnnees = specialiteAnnees;
    }
    
    
    

    public List<Statistique> getStatistiques() {
        return statistiques;
    }

    public void setStatistiques(List<Statistique> statistiques) {
        this.statistiques = statistiques;
    }

    
    
    public AnneeAcademique() {
        istargetyear=false;
    }

    public AnneeAcademique(String annee) {
        this.annee = annee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnneeAcademique)) {
            return false;
        }
        AnneeAcademique other = (AnneeAcademique) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public List<Inscription> getHistoriqueInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public List<SpecialiteEtudiant> getSpecialiteEtudiants() {
        return specialiteEtudiants;
    }

    public void setSpecialiteEtudiants(List<SpecialiteEtudiant> specialiteEtudiants) {
        this.specialiteEtudiants = specialiteEtudiants;
    }

    @Override
    public int compareTo(AnneeAcademique o) {
        return this.annee.compareTo(o.getAnnee());
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getQuitus() {
        return quitus;
    }

    public void setQuitus(Long quitus) {
        this.quitus = quitus;
    }

    public Boolean getIstargetyear() {
        return istargetyear;
    }

    public void setIstargetyear(Boolean istargetyear) {
        this.istargetyear = istargetyear;
    }

    public List<Sms> getSmses() {
        return smses;
    }

    public void setSmses(List<Sms> smses) {
        this.smses = smses;
    }

    @Override
    public String toString() {
        return annee;
    }

   

   
    
    
    
}
