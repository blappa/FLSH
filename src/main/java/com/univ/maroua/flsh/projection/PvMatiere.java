/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

import java.util.Comparator;

/**
 *
 * @author ZALEWO
 */
public class PvMatiere implements Comparable<PvMatiere> {

    private Long noteId;
    private String specialite;
    private String nomDep;
    private String sem;
    private String anAc;
    private String niveau;
    private String section;
    private String intitule;
    private String ue;
    private Float credit;
    private int numEtu;
    private String matricule;
    private String nomEtudiant;
    private Float tpe;
    private Float td;
    private Float cc;
    private Float ee;
    private Float rat;
    private Float moy;
    private Float coef;
    private String grade;
    private String decision;
    private String anoExam;
    private String anoRat;
    private String date;
    private int nombreEtu;
    private int nombreVa;
    private int nombreCa;
    private int nombreCant;
    private int nombreAj;
    private int nombreEch;
    private float pourcentVa;
    private float pourcentEch;
    private float pourcentCa;
    private float pourcentCant;
    private float pourcentAj;
    private int nombre95;
    private int nombre9;
    private int nombre85;
    private int nombre8;
    private float pourcent95;
    private float pourcent9;
    private float pourcent85;
    private float pourcent8;
    private int nombreCompose;
    private int nombreNoCompose;
    private int nombreExam;

    public PvMatiere() {
    }

    public PvMatiere(String nomDep, String sem, String anAc, String niveau, String section, String intitule, String ue, Float credit, int numEtu, String matricule, String nomEtudiant, Float tpe, Float td, Float cc, Float ee, Float rat, Float moy, Float coef, String grade, String decision) {
        this.nomDep = nomDep;
        this.sem = sem;
        this.anAc = anAc;
        this.niveau = niveau;
        this.section = section;
        this.intitule = intitule;
        this.ue = ue;
        this.credit = credit;
        this.numEtu = numEtu;
        this.matricule = matricule;
        this.nomEtudiant = nomEtudiant;
        this.tpe = tpe;
        this.td = td;
        this.cc = cc;
        this.ee = ee;
        this.rat = rat;
        this.moy = moy;
        this.coef = coef;
        this.grade = grade;
        this.decision = decision;
    }

    public int getNombreCompose() {
        return nombreCompose;
    }

    public void setNombreCompose(int nombreCompose) {
        this.nombreCompose = nombreCompose;
    }

    public int getNombreNoCompose() {
        return nombreNoCompose;
    }

    public void setNombreNoCompose(int nombreNoCompose) {
        this.nombreNoCompose = nombreNoCompose;
    }

    public int getNombreExam() {
        return nombreExam;
    }

    public void setNombreExam(int nombreExam) {
        this.nombreExam = nombreExam;
    }

    
    
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public String getSem() {
        return sem;
    }

    public void setSem(String sem) {
        this.sem = sem;
    }

    public String getAnAc() {
        return anAc;
    }

    public void setAnAc(String anAc) {
        this.anAc = anAc;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getUe() {
        return ue;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public int getNumEtu() {
        return numEtu;
    }

    public void setNumEtu(int numEtu) {
        this.numEtu = numEtu;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public Float getTpe() {
        return tpe;
    }

    public void setTpe(Float tpe) {
        this.tpe = tpe;
    }

    public Float getTd() {
        return td;
    }

    public void setTd(Float td) {
        this.td = td;
    }

    public Float getCc() {
        return cc;
    }

    public void setCc(Float cc) {
        this.cc = cc;
    }

    public Float getEe() {
        return ee;
    }

    public void setEe(Float ee) {
        this.ee = ee;
    }

    public Float getRat() {
        return rat;
    }

    public void setRat(Float rat) {
        this.rat = rat;
    }

    public Float getMoy() {
        return moy;
    }

    public void setMoy(Float moy) {
        this.moy = moy;
    }

    public Float getCoef() {
        return coef;
    }

    public void setCoef(Float coef) {
        this.coef = coef;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public void setAnoExam(String anoExam) {
        this.anoExam = anoExam;
    }

    public void setAnoRat(String anoRat) {
        this.anoRat = anoRat;
    }

    public String getAnoExam() {
        return anoExam;
    }

    public String getAnoRat() {
        return anoRat;
    }

    public int getNombreEtu() {
        return nombreEtu;
    }

    public void setNombreEtu(int nombreEtu) {
        this.nombreEtu = nombreEtu;
    }

    public int getNombreVa() {
        return nombreVa;
    }

    public void setNombreVa(int nombreVa) {
        this.nombreVa = nombreVa;
    }

    public int getNombreEch() {
        return nombreEch;
    }

    public void setNombreEch(int nombreEch) {
        this.nombreEch = nombreEch;
    }

    public float getPourcentVa() {
        return pourcentVa;
    }

    public void setPourcentVa(float pourcentVa) {
        this.pourcentVa = pourcentVa;
    }

    public int getNombre95() {
        return nombre95;
    }

    public void setNombre95(int nombre95) {
        this.nombre95 = nombre95;
    }

    public int getNombre9() {
        return nombre9;
    }

    public void setNombre9(int nombre9) {
        this.nombre9 = nombre9;
    }

    public int getNombre85() {
        return nombre85;
    }

    public void setNombre85(int nombre85) {
        this.nombre85 = nombre85;
    }

    public int getNombre8() {
        return nombre8;
    }

    public void setNombre8(int nombre8) {
        this.nombre8 = nombre8;
    }

    public float getPourcent95() {
        return pourcent95;
    }

    public void setPourcent95(float pourcent95) {
        this.pourcent95 = pourcent95;
    }

    public float getPourcent9() {
        return pourcent9;
    }

    public void setPourcent9(float pourcent9) {
        this.pourcent9 = pourcent9;
    }

    public float getPourcent85() {
        return pourcent85;
    }

    public void setPourcent85(float pourcent85) {
        this.pourcent85 = pourcent85;
    }

    public float getPourcent8() {
        return pourcent8;
    }

    public void setPourcent8(float pourcent8) {
        this.pourcent8 = pourcent8;
    }

    public float getPourcentEch() {
        return pourcentEch;
    }

    public void setPourcentEch(float pourcentEch) {
        this.pourcentEch = pourcentEch;
    }

    @Override
    public int compareTo(PvMatiere pv) {

        String ano = pv.getAnoExam();
        //ascending order
        return this.anoExam.compareTo(ano);
    }

    public Long getNoteId() {
        return noteId;
    }

    public void setNoteId(Long noteId) {
        this.noteId = noteId;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getNombreCa() {
        return nombreCa;
    }

    public void setNombreCa(int nombreCa) {
        this.nombreCa = nombreCa;
    }

    public int getNombreCant() {
        return nombreCant;
    }

    public void setNombreCant(int nombreCant) {
        this.nombreCant = nombreCant;
    }

    public int getNombreAj() {
        return nombreAj;
    }

    public void setNombreAj(int nombreAj) {
        this.nombreAj = nombreAj;
    }

    public float getPourcentCa() {
        return pourcentCa;
    }

    public void setPourcentCa(float pourcentCa) {
        this.pourcentCa = pourcentCa;
    }

    public float getPourcentCant() {
        return pourcentCant;
    }

    public void setPourcentCant(float pourcentCant) {
        this.pourcentCant = pourcentCant;
    }

    public float getPourcentAj() {
        return pourcentAj;
    }

    public void setPourcentAj(float pourcentAj) {
        this.pourcentAj = pourcentAj;
    }
}
