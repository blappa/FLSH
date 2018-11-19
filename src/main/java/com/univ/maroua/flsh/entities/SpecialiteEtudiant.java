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
@Table(name = "specialiteetudiant")
@NamedQueries({
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiantSection", query = "SELECT spe FROM  SpecialiteEtudiant spe WHERE spe.etudiant.matricule=:matricule  AND spe.specialite.niveau.section.id=:sectionId ORDER BY spe.anneeAcademique.id DESC"),
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiantId", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.etudiant.id=:idEtudiant ORDER BY spe.anneeAcademique.id DESC"),
    @NamedQuery(name = "SpecialiteEtudiant.findByAnneeId", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.anneeAcademique.id=:idAnnee"),
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiant", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.etudiant.matricule=:matricule"),
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiantSpecialiteAnnee", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.etudiant.id=:idEtudiant AND spe.anneeAcademique.id=:idAnnee AND spe.specialite.id=:idSpecialite"),
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiantNiveau", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.etudiant.id=:idEtudiant AND  spe.specialite.niveau.level=:level ORDER BY spe.anneeAcademique.annee DESC"),
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiantNiveauAnnee", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.etudiant.id=:idEtudiant AND  spe.specialite.niveau.id=:idNiveau AND spe.anneeAcademique.id=:idAnnee ORDER BY spe.specialite.id"),
    @NamedQuery(name = "SpecialiteEtudiant.findBySpecialiteAnnee", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.specialite.id=:idSpecialite AND  spe.anneeAcademique.id=:idAnnee"),
    @NamedQuery(name = "SpecialiteEtudiant.findByDepartementAnnee", query = "SELECT spe FROM SpecialiteEtudiant spe WHERE spe.specialite.niveau.section.departement.id=:idDepartement AND  spe.anneeAcademique.id=:idAnnee ORDER BY spe.specialite.niveau.level ASC"),
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiantSectionNiveau", query = "SELECT spe FROM  SpecialiteEtudiant spe WHERE spe.etudiant.matricule=:matricule AND spe.specialite.niveau.level=:level AND spe.specialite.niveau.section.id=:sectionId ORDER BY spe.anneeAcademique.id DESC"),
    @NamedQuery(name = "SpecialiteEtudiant.findByEtudiantSectionNiveauAnnee", query = "SELECT spe FROM  SpecialiteEtudiant spe WHERE spe.etudiant.matricule=:matricule AND spe.specialite.niveau.level=:level AND spe.specialite.niveau.section.id=:sectionId AND spe.anneeAcademique.id=:anneeId"),
})
public class SpecialiteEtudiant implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String decision;
    @Column(columnDefinition = "integer default 0")
    private int chevauche;
    private String ue;
    private String code1;
    private String code2;
    private String code3;
    private Long credits;
    @Column(name = "codeq")
    private String codeQ;
    @Column(length = 300)
    private String synth1;
    @Column(length = 300)
    private String synth2;
    @Column(length = 500)
    private String ann;
    @Column(columnDefinition = "integer default 0")
    private int sem2; //savoir si le semestre2 est deja calcule
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Etudiant etudiant;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Specialite specialite;
    @JoinColumn(name = "anneeacademique_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AnneeAcademique anneeAcademique;

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
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
        if (!(object instanceof SpecialiteEtudiant)) {
            return false;
        }
        SpecialiteEtudiant other = (SpecialiteEtudiant) object;
        if (!(this.specialite.getNom().equals(other.specialite.getNom())) || (this.specialite.getNiveau().getLevel() != other.specialite.getNiveau().getLevel())) {
            return false;
        }
        return true;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
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

    @Override
    public String toString() {
        return "SpecialiteEtudiant{" + "id=" + id + ", etudiant=" + etudiant.getNom() + ", specialite=" + specialite.getNom() + ", anneeAcademique=" + anneeAcademique + '}';
    }

    public String getUe() {
        return ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public int getChevauche() {
        return chevauche;
    }

    public String getCodeQ() {
        return codeQ;
    }

    public void setCodeQ(String codeQ) {
        this.codeQ = codeQ;
    }

    public void setChevauche(int chevauche) {
        this.chevauche = chevauche;
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

    public String getCode1() {
        return code1;
    }

    public void setCode1(String code1) {
        this.code1 = code1;
    }

    public String getCode2() {
        return code2;
    }

    public void setCode2(String code2) {
        this.code2 = code2;
    }

    public String getCode3() {
        return code3;
    }

    public void setCode3(String code3) {
        this.code3 = code3;
    }

    public int getSem2() {
        return sem2;
    }

    public void setSem2(int sem2) {
        this.sem2 = sem2;
    }

    public Long getCredits() {
        return credits;
    }

    public void setCredits(Long credits) {
        this.credits = credits;
    }

    public String getSynth1() {
        return synth1;
    }

    public void setSynth1(String synth1) {
        this.synth1 = synth1;
    }

    public String getSynth2() {
        return synth2;
    }

    public void setSynth2(String synth2) {
        this.synth2 = synth2;
    }

    public String getAnn() {
        return ann;
    }

    public void setAnn(String ann) {
        this.ann = ann;
    }
    
    
}
