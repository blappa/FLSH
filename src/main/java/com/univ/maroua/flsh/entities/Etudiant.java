/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import java.io.Serializable;
import java.util.List;
//import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "etudiant")
@NamedQueries({
    @NamedQuery(name = "Etudiant.findAll", query = "SELECT e FROM Etudiant e"),
    @NamedQuery(name = "Etudiant.findById", query = "SELECT e FROM Etudiant e WHERE e.id = :id"),
    @NamedQuery(name = "Etudiant.findByNom", query = "SELECT e FROM Etudiant e WHERE e.nom = :nom"),
    @NamedQuery(name = "Etudiant.findByMatricule", query = "SELECT e FROM Etudiant e WHERE e.matricule = :matricule"),
    @NamedQuery(name = "Etudiant.findSpecialiteEtudiant", query = "SELECT sp FROM Specialite sp, Etudiant e,SpecialiteEtudiant spe JOIN spe.etudiant spe_e JOIN spe.specialite spe_sp WHERE e.id=:idEtudiant AND spe_e.id=e.id AND spe_sp.id=sp.id"),
    @NamedQuery(name = "Etudiant.findEtudiantBySpecialite", query = "SELECT spe.etudiant FROM SpecialiteEtudiant spe  WHERE  spe.specialite.id =:idSpecialite"),
    @NamedQuery(name = "Etudiant.RameneByMatiere", query = "SELECT e FROM Etudiant e,Matiere mat, Module modul, Specialite sp,SpecialiteEtudiant spe,Note n, Niveau niv  WHERE mat.id=:idMatiere AND mat.module.id=modul.id AND modul.specialite.id=sp.id AND spe.specialite.id=sp.id AND spe.etudiant.id=e.id AND n.matiere.id=:idMatiere AND n.etudiant.id=e.id AND niv.level=:level"),
    @NamedQuery(name = "Etudiant.findBySpecialiteAnnee", query = "SELECT sp.etudiant FROM SpecialiteEtudiant sp  WHERE sp.anneeAcademique.id=:idAnneeAc AND sp.specialite.id=:idSpecialite "),
    @NamedQuery(name = "Etudiant.findBySpecialiteMatiere", query = "SELECT e FROM SpecialiteEtudiant spe, Note n,Etudiant e WHERE spe.etudiant.id=e.id AND e.id=n.etudiant.id AND spe.specialite.id=:idSpecialite  AND n.matiere.id=:idMatiere"),
    @NamedQuery(name = "Etudiant.findBySpecialiteAnneeNiveau", query = "SELECT sp.etudiant FROM SpecialiteEtudiant sp  WHERE sp.anneeAcademique.id=:idAnneeAc AND sp.specialite.id=:idSpecialite AND sp.specialite.niveau.level=:level"),
    @NamedQuery(name = "Etudiant.findByDepartement", query = "SELECT spe.etudiant FROM SpecialiteEtudiant spe WHERE spe.specialite.niveau.section.departement.id=:idDepartement"),
    @NamedQuery(name = "Etudiant.findBySection", query = "SELECT spe.etudiant FROM SpecialiteEtudiant spe WHERE spe.specialite.niveau.section.id=:idSection"),
    @NamedQuery(name = "Etudiant.findByNiveauAnnee", query = "SELECT spe.etudiant FROM SpecialiteEtudiant spe WHERE spe.specialite.niveau.id=:idNiveau  AND spe.anneeAcademique.id=:idAnnee"),
    @NamedQuery(name = "Etudiant.findByAnnee", query = "SELECT spe.etudiant FROM SpecialiteEtudiant spe WHERE spe.anneeAcademique.id=:idAnnee"),
    @NamedQuery(name = "Etudiant.findBySexe", query = "SELECT e FROM Etudiant e where e.sexe=:sexe"),
    @NamedQuery(name = "Etudiant.findByAnneeDepartementType", query = "SELECT s.etudiant FROM Sms s WHERE s.anneeAcademique.id = :anneeId AND s.section.departement.id = :departementId AND s.type = :type"),
    @NamedQuery(name = "Etudiant.findByAnneeSectionType", query = "SELECT s.etudiant FROM Sms s WHERE s.anneeAcademique.id = :anneeId AND s.section.id = :sectionId AND s.type = :type"),

})
public class Etudiant implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "datenais")
    private String dateNais;
    @Column(name = "lieunais")
    private String lieuNais;
    @Column(name = "diplomentree")
    private String diplomEntree;
    private String nom;
    private String nationalite;
    private String sexe;
    private String region;
    private String departement;
    @Column(name = "anneeobtdiplo")
    private String anneeObtDiplo;
    private String orange;
    private String mtn;
    private String nextel;
    private String internationnal;
    //pour savoir si un etudiant est au niveau superieur, mais a repris le niveau inferieur
    private int sup;
    //pour savoir si un etudiant a valide un semestre a la session normale ou au ratrapage
    private int shortable;
    @Column(unique = true, nullable = false)
    private String matricule;
    @OneToMany(mappedBy = "etudiant", cascade = {CascadeType.ALL})
    private List<Note> notes;
    @OneToMany(mappedBy = "etudiant", cascade = {CascadeType.ALL})
    private List<Inscription> inscriptions;
    @OneToMany(mappedBy = "etudiant", cascade = {CascadeType.ALL})
    private List<SpecialiteEtudiant> specialiteEtudiants;
    @OneToMany(mappedBy = "etudiant", cascade = {CascadeType.ALL})
    private List<MatiereOptionnelle> matiereOptionnelles;
    @OneToMany(mappedBy = "etudiant", cascade = {CascadeType.ALL})
    private List<EtudiantSection> etudiantSections;
    @OneToMany(mappedBy = "etudiant", cascade = {CascadeType.ALL})
    private List<Sms> smses;

    public Etudiant() {
    }

    public Etudiant(Long id, String dateNais, String lieuNais, String diplomEntree, String nom) {
        this.id = id;
        this.dateNais = dateNais;
        this.lieuNais = lieuNais;
        this.diplomEntree = diplomEntree;
        this.nom = nom;
    }

    public int getSup() {
        return sup;
    }

    public int getShortable() {
        return shortable;
    }

    public void setShortable(int shortable) {
        this.shortable = shortable;
    }

    public void setSup(int sup) {
        this.sup = sup;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateNais() {
        return dateNais;
    }

    public void setDateNais(String dateNais) {
        this.dateNais = dateNais;
    }

    public String getLieuNais() {
        return lieuNais;
    }

    public void setLieuNais(String lieuNais) {
        this.lieuNais = lieuNais;
    }

    public String getDiplomEntree() {
        return diplomEntree;
    }

    public void setDiplomEntree(String diplomEntree) {
        this.diplomEntree = diplomEntree;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
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

    /*@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Etudiant other = (Etudiant) obj;
        if (!Objects.equals(this.matricule, other.matricule)) {
            return false;
        }
        return true;
    }*/

    @Override
    public String toString() {
        return  nom + " de  matricule " + matricule;
    }

    

    public List<SpecialiteEtudiant> getSpecialiteEtudiants() {
        return specialiteEtudiants;
    }

    public void setSpecialiteEtudiants(List<SpecialiteEtudiant> specialiteEtudiants) {
        this.specialiteEtudiants = specialiteEtudiants;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getAnneeObtDiplo() {
        return anneeObtDiplo;
    }

    public void setAnneeObtDiplo(String anneeObtDiplo) {
        this.anneeObtDiplo = anneeObtDiplo;
    }

    public List<EtudiantSection> getEtudiantSections() {
        return etudiantSections;
    }

    public void setEtudiantSections(List<EtudiantSection> etudiantSections) {
        this.etudiantSections = etudiantSections;
    }

    public String getOrange() {
        return orange;
    }

    public void setOrange(String orange) {
        this.orange = orange;
    }

    public String getMtn() {
        return mtn;
    }

    public void setMtn(String mtn) {
        this.mtn = mtn;
    }

    public String getNextel() {
        return nextel;
    }

    public void setNextel(String nextel) {
        this.nextel = nextel;
    }

    public String getInternationnal() {
        return internationnal;
    }

    public void setInternationnal(String internationnal) {
        this.internationnal = internationnal;
    }

    public List<Sms> getSmses() {
        return smses;
    }

    public void setSmses(List<Sms> smses) {
        this.smses = smses;
    }
}
