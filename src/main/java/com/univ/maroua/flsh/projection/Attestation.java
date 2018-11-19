/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

/**
 *
 * @author koossery
 */
public class Attestation implements Comparable<Attestation> {

    public Attestation(String delDate, String nomPrenom, String matricule, String dateNais, String lieuNais, String diplomeEntree, String specialite, String session, String mgp, String mention) {
        this.delDate = delDate;
        this.nomPrenom = nomPrenom;
        this.matricule = matricule;
        this.dateNais = dateNais;
        this.lieuNais = lieuNais;
        this.diplomeEntree = diplomeEntree;
        this.specialite = specialite;
        this.session = session;
        this.mention = mention;

    }
    private String delDate;
    private String nomPrenom;
    private String matricule;
    private String dateNais;
    private String lieuNais;
    private String diplomeEntree;
    private String specialite;
    private String session;
    private Float mgp;
    private String mention;
    private String filiere;
    private String nationalite;
    private String annee;
    private String niveau;
    private String sexe;
    private String no;
    private String redoublant;
    private String tranche;

    public Attestation() {
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    public String getNomPrenom() {
        return nomPrenom;
    }

    public void setNomPrenom(String nomPrenom) {
        this.nomPrenom = nomPrenom;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
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

    public String getDiplomeEntree() {
        return diplomeEntree;
    }

    public void setDiplomeEntree(String diplomeEntree) {
        this.diplomeEntree = diplomeEntree;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public Float getMgp() {
        return mgp;
    }

    public void setMgp(Float mgp) {
        this.mgp = mgp;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    @Override
    public int compareTo(Attestation att) {
        return this.nomPrenom.compareTo(att.nomPrenom);
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getRedoublant() {
        return redoublant;
    }

    public void setRedoublant(String redoublant) {
        this.redoublant = redoublant;
    }

    public String getTranche() {
        return tranche;
    }

    public void setTranche(String tranche) {
        this.tranche = tranche;
    }
}
