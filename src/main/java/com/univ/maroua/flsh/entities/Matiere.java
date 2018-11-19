/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
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
import javax.persistence.Temporal;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "matiere")
@NamedQueries({
    @NamedQuery(name = "Matiere.findAll", query = "SELECT m FROM Matiere m"),
    @NamedQuery(name = "Matiere.findById", query = "SELECT m FROM Matiere m WHERE m.id = :id"),
    @NamedQuery(name = "Matiere.findByCode", query = "SELECT m FROM Matiere m WHERE m.module.code=:code"),
    @NamedQuery(name = "Matiere.findByIntitule", query = "SELECT m FROM Matiere m WHERE m.intitule = :intitule"),
    @NamedQuery(name = "Matiere.findListMatiereByUE", query = "SELECT m FROM Matiere m WHERE m.module.id =:idUE"),
    @NamedQuery(name = "Matiere.findListMatiereNiveauSpecialite", query = "SELECT m FROM Matiere m, MatiereOptionnelle matop, SpecialiteEtudiant spEt WHERE (matop.matiere.id=m.id AND m.module.specialite.niveau.id=:idNiveau AND matop.etudiant.id=:idEtudiant AND spEt.etudiant.id=:idEtudiant AND spEt.specialite.id=:idSpecialite AND spEt.specialite.niveau.id=:idNiveau)"),
    @NamedQuery(name = "Matiere.findListMatiereOpByEtudiantAnneeAc", query = "SELECT m FROM Matiere m,MatiereOptionnelle matop,Etudiant e WHERE m.id=matop.matiere.id AND matop.etudiant.id=e.id AND e.id=:idEtudiant AND m.module.anneeAcademique.id =:idAnneeAc"),
    @NamedQuery(name = "Matiere.findBySpecalite", query = "SELECT m  FROM Matiere m WHERE m.module.specialite.id = :id"),
    @NamedQuery(name = "Matiere.findByNameSpecialiteAnnee", query = "SELECT m  FROM Matiere m WHERE m.module.specialite.id=:idSpecialite AND m.module.anneeAcademique.id=:idAnnee AND m.intitule=:nomMatiere"),
    @NamedQuery(name = "Matiere.findMasterBySpecialiteAnnee", query = "SELECT m  FROM Matiere m WHERE m.module.specialite.id=:idSpecialite AND m.module.anneeAcademique.id=:idAnnee ORDER BY m.module.id ASC"),
    @NamedQuery(name = "Matiere.findBySpecialiteAnnee", query = "SELECT m  FROM Matiere m WHERE m.module.specialite.id=:idSpecialite AND m.module.anneeAcademique.id=:idAnnee ORDER BY m.module.id ASC"),
    @NamedQuery(name = "Matiere.findBySpecialiteAnneeSemestre", query = "SELECT m  FROM Matiere m WHERE m.module.specialite.id=:idSpecialite AND m.module.anneeAcademique.id=:idAnnee AND m.module.semestre.id =:idSemestre ORDER BY m.module.id ASC"),})
public class Matiere implements Serializable, Comparable<Matiere> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String intitule;
    private Float noteDelS; //deliberation en session normale
    private Float noteDelR; //deliberation en session de ratrapage
    private Float noteBonusS; //bonus en session normale
    private Float noteBonusR; //bonus en session de ratrapage
    private String formule;
    private String code;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date date; //la date d'importation de la matiere
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Module module;
    @OneToMany(mappedBy = "matiere", cascade = {CascadeType.ALL})
    private List<MatiereOptionnelle> matiereOptionnelles;

    public Matiere() {
    }

    public String getCode() {
        try {
            if (code.trim().isEmpty()) {
                code = null;
            }
        } catch (NullPointerException e) {
        }
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<MatiereOptionnelle> getMatiereOptionnelles() {
        return matiereOptionnelles;
    }

    public void setMatiereOptionnelles(List<MatiereOptionnelle> matiereOptionnelles) {
        this.matiereOptionnelles = matiereOptionnelles;
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
        if (!(object instanceof Matiere)) {
            return false;
        }
        Matiere other = (Matiere) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return intitule + ", code= " + code + " de l'UE " + module;
    }

   

    @Override
    public int compareTo(Matiere matiere) {
        int s1 = Integer.parseInt(this.module.getCode());
        int s2 = Integer.parseInt(matiere.getModule().getCode());
        return s1 < s2 ? -1
                : s1 > s2 ? 1
                : 0;

    }

    public String getFormule() {
        return formule;
    }

    public void setFormule(String formule) {
        this.formule = formule;
    }

    public Float getNoteDelS() {
        return noteDelS;
    }

    public void setNoteDelS(Float noteDelS) {
        this.noteDelS = noteDelS;
    }

    public Float getNoteDelR() {
        return noteDelR;
    }

    public void setNoteDelR(Float noteDelR) {
        this.noteDelR = noteDelR;
    }

    public Float getNoteBonusS() {
        return noteBonusS;
    }

    public void setNoteBonusS(Float noteBonusS) {
        this.noteBonusS = noteBonusS;
    }

    public Float getNoteBonusR() {
        return noteBonusR;
    }

    public void setNoteBonusR(Float noteBonusR) {
        this.noteBonusR = noteBonusR;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
