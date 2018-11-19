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
@Table(name = "section")
@NamedQueries({
    @NamedQuery(name = "Section.findAll", query = "SELECT s FROM Section s"),
    @NamedQuery(name = "Section.findById", query = "SELECT s FROM Section s WHERE s.id = :id"),
    @NamedQuery(name = "Section.findByNom", query = "SELECT s FROM Section s WHERE s.nom = :nom"),
    @NamedQuery(name = "Section.findBySigle", query = "SELECT s FROM Section s WHERE s.sigle = :sigle"),
    @NamedQuery(name = "Section.findByDepartement", query = "SELECT s  FROM Section s WHERE s.departement.id= :id"),
    @NamedQuery(name = "Section.findByEtudiantAnnee", query = "SELECT s.specialite.niveau.section  FROM  SpecialiteEtudiant s WHERE s.etudiant.id =:idEtudiant AND s.anneeAcademique.id=:idAnnee "),
    @NamedQuery(name = "Section.findByEtudiantNiveau", query = "SELECT DISTINCT s.specialite.niveau.section  FROM  SpecialiteEtudiant s WHERE s.etudiant.matricule =:matricule AND s.specialite.niveau.level=:level "),
    @NamedQuery(name = "Section.findByEtudiant", query = "SELECT DISTINCT s.specialite.niveau.section  FROM  SpecialiteEtudiant s WHERE s.etudiant.matricule =:matricule"),

})
public class Section implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nom;
    private String sigle;
    @ManyToOne(fetch = FetchType.EAGER)
    private Departement departement;
    @OneToMany(mappedBy = "section", cascade = {CascadeType.ALL})
    private List<Niveau> niveaus;
    @OneToMany(mappedBy = "section", cascade = {CascadeType.ALL})
    private List<Inscription> inscriptions;
    @OneToMany(mappedBy = "section", cascade = {CascadeType.ALL})
    private List<EtudiantSection> etudiantSections;
    @OneToMany(mappedBy = "section", cascade = {CascadeType.ALL})
    private List<Sms> smses;

    
    public Section() {
    }

    public Long getId() {
        return id;
    }

    public Section(String nom, String sigle, Departement departement) {
        this.nom = nom;
        this.sigle = sigle;
        this.departement = departement;
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

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public String getSigle() {
        return sigle;
    }

    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    public List<Niveau> getNiveaus() {
        return niveaus;
    }

    public void setNiveaus(List<Niveau> niveaus) {
        this.niveaus = niveaus;
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
        if (!(object instanceof Section)) {
            return false;
        }
        Section other = (Section) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nom+" du departement "+departement;
    }

   

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public List<EtudiantSection> getEtudiantSections() {
        return etudiantSections;
    }

    public void setEtudiantSections(List<EtudiantSection> etudiantSections) {
        this.etudiantSections = etudiantSections;
    }

    public List<Sms> getSmses() {
        return smses;
    }

    public void setSmses(List<Sms> smses) {
        this.smses = smses;
    }
    
    
}
