/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

import java.util.Comparator;

/**
 *
 * @author koossery
 */
public class Reussite implements Comparable<Reussite> {

    private String message;
    private String entete;
    private String credit;
    private String date;
    private String lieu;
    private String nationalite;
    private String ue;
    private String nomPrenom;
    private String matricule;
    private String departement;
    private String section;
    private String session;
    private Float mgp;
    private String mention;
    private String specialite;
    private String annee;
    private String sexe;
    private String grade;
    private String no;
    private Float moyenne;
    private String decision;
    private String niveau;
    private String matiere;
    private String ue1;
    private String ue2;
    private String ue3;
    private String ue4;
    private String ue5;
    private String ue6;

    public Reussite() {
    }

    public String getUe1() {
        return ue1;
    }

    public void setUe1(String ue1) {
        this.ue1 = ue1;
    }

    public String getUe2() {
        return ue2;
    }

    public void setUe2(String ue2) {
        this.ue2 = ue2;
    }

    public String getUe3() {
        return ue3;
    }

    public void setUe3(String ue3) {
        this.ue3 = ue3;
    }

    public String getUe4() {
        return ue4;
    }

    public void setUe4(String ue4) {
        this.ue4 = ue4;
    }

    public String getUe5() {
        return ue5;
    }

    public void setUe5(String ue5) {
        this.ue5 = ue5;
    }

    public String getUe6() {
        return ue6;
    }

    public void setUe6(String ue6) {
        this.ue6 = ue6;
    }

    
    
    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
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

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public Float getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    @Override
    public int compareTo(Reussite reussite) {
        Float moySem1 = this.getMoyenne();
        Float moySem2 = reussite.getMoyenne();
        return moySem1 > moySem2 ? -1
                : moySem1 < moySem2 ? 1
                : 0;
    }
    public static Comparator<Reussite> nomComparator = new Comparator<Reussite>() {
        @Override
        public int compare(Reussite r1, Reussite r2) {
            String nomEt1 = r1.getNomPrenom();
            String nomEt2 = r2.getNomPrenom();
            return nomEt1.compareTo(nomEt2);
        }
    };

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public static Comparator<Reussite> getNomComparator() {
        return nomComparator;
    }

    public static void setNomComparator(Comparator<Reussite> nomComparator) {
        Reussite.nomComparator = nomComparator;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getEntete() {
        return entete;
    }

    public void setEntete(String entete) {
        this.entete = entete;
    }

    public String getUe() {
        return ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
