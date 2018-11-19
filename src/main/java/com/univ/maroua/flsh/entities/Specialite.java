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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "specialite")
@NamedQueries({
    @NamedQuery(name = "Specialite.findAll", query = "SELECT s FROM Specialite s"),
    @NamedQuery(name = "Specialite.findById", query = "SELECT s FROM Specialite s WHERE s.id = :id"),
    @NamedQuery(name = "Specialite.findByNom", query = "SELECT s FROM Specialite s WHERE s.nom = :nom"),
    @NamedQuery(name = "Specialite.findByCode", query = "SELECT s FROM Specialite s WHERE s.code = :code"),
    @NamedQuery(name = "Specialite.findEtudiant", query = "SELECT spe.specialite FROM  SpecialiteEtudiant spe WHERE  spe.etudiant.matricule=:matricule"),
    @NamedQuery(name = "Specialite.findEtudiantBySpecialite", query = "SELECT e FROM Specialite sp, SpecialiteEtudiant spe, Etudiant e  WHERE sp.nom=:nomSpecialite AND spe.specialite.id=sp.id AND spe.etudiant.id=e.id"),
    @NamedQuery(name = "Specialite.findUEBySpecialiteId", query = "SELECT ue FROM Module ue,Specialite  sp  WHERE sp.id = :IdSpecialite AND ue.specialite.id=sp.id"),
    @NamedQuery(name = "Specialite.findByNiveau", query = "SELECT s  FROM Specialite s WHERE s.niveau.id = :id"),
    @NamedQuery(name = "Specialite.findByEtudiantAnnee", query = "SELECT DISTINCT n.matiere.module.specialite FROM Note n  WHERE  n.etudiant.id =:etudiantId AND n.matiere.module.anneeAcademique.id=:anneeId"),})
public class Specialite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String code;
    private int professional;
    @Column(columnDefinition = "integer default 0")
    private int type; //pour savoir si c'est en tronc commun ou pas
   
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Niveau niveau;
    @OneToMany(mappedBy = "specialite", cascade = {CascadeType.ALL})
    private List<SpecialiteEtudiant> specialiteEtudiants;
    @OneToMany(mappedBy = "specialite", cascade = {CascadeType.ALL})
    private List<Module> modules;
    @OneToMany(mappedBy = "specialite", cascade = {CascadeType.ALL})
    private List<Statistique> statistiques;
    @OneToMany(mappedBy = "specialite", cascade = {CascadeType.ALL})
    private List<SpecialiteAnnee> specialiteAnnees;

    public Specialite() {
        type=0;
    }

    public Specialite(String nom, String code, Niveau niveau) {
        this.nom = nom;
        this.code = code;
        this.niveau = niveau;
    }

    public List<SpecialiteAnnee> getSpecialiteAnnees() {
        return specialiteAnnees;
    }

    public void setSpecialiteAnnees(List<SpecialiteAnnee> specialiteAnnees) {
        this.specialiteAnnees = specialiteAnnees;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Statistique> getStatistiques() {
        return statistiques;
    }

    public void setStatistiques(List<Statistique> statistiques) {
        this.statistiques = statistiques;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
        if (!(object instanceof Specialite)) {
            return false;
        }
        Specialite other = (Specialite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom + " du niveau " + niveau;
    }

    

    public List<SpecialiteEtudiant> getSpecialiteEtudiants() {
        return specialiteEtudiants;
    }

    public void setSpecialiteEtudiants(List<SpecialiteEtudiant> specialiteEtudiants) {
        this.specialiteEtudiants = specialiteEtudiants;
    }

    public int getProfessional() {
        return professional;
    }

    public void setProfessional(int professional) {
        this.professional = professional;
    }

   
}
