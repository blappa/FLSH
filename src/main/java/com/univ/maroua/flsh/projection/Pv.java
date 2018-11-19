/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

import java.util.Comparator;
//import java.util.Objects;

/**
 *
 * @author lappa
 */
public class Pv implements Comparable<Pv> {

    private Long sectionId;
    private Float  moy2;
    private String nomDep;
    private String niveau;
    private Float coef;
    private String intitule;
    private String ue;
    private String nomEtudiant;
    private String matEtudiant;
    private int id;
    private String anneeAc;
    private String numSemS;
    private String numSemF;
    private String intitule1;
    private String intitule2;
    private String intitule3;
    private String intitule4;
    private String intitule5;
    private String intitule6;
    private String intitule7;
    private String intitule8;
    private String intitule9;
    private String intitule10;
    private String intitule11;
    private String intitule12;
    private String ue1;
    private String ue2;
    private String ue3;
    private String ue4;
    private String ue5;
    private String ue6;
    private String ue7;
    private String ue8;
    private String ue9;
    private String ue10;
    private String ue11;
    private String ue12;
    private Float coef1;
    private Float coef2;
    private Float coef3;
    private Float coef4;
    private Float coef5;
    private Float coef6;
    private Float coef7;
    private Float coef8;
    private Float coef9;
    private Float coef10;
    private Float coef11;
    private Float coef12;
    private String grade1;
    private Float cote1;
    private String grade2;
    private Float cote2;
    private String grade3;
    private Float cote3;
    private String grade4;
    private Float cote4;
    private String grade5;
    private Float cote5;
    private String grade6;
    private Float cote6;
    private String grade7;
    private Float cote7;
    private String grade8;
    private Float cote8;
    private String grade9;
    private Float cote9;
    private String grade10;
    private Float cote10;
    private String grade11;
    private Float cote11;
    private String grade12;
    private Float cote12;
    private Float cap;
    private Float moy;
    private int rang;
    private int fin;
    private int semestre;
    private String decision;
    private Float note1;
    private Float note2;
    private Float note3;
    private Float note4;
    private Float note5;
    private Float note6;
    private Float note7;
    private Float note8;
    private Float note9;
    private Float note10;
    private Float note11;
    private Float note12;
    private int nmEtuVal1;
    private String percentVal1;
    private int nmEtuVal2;
    private String percentVal2;
    private int nmEtuVal3;
    private String percentVal3;
    private int nmEtuVal4;
    private String percentVal4;
    private int nmEtuVal5;
    private String percentVal5;
    private int nmEtuVal6;
    private String percentVal6;
    private String percent;
    private Long specialite;
    private String section;
    private String niv;
    private String spec;
    private String date;
    private int normale;
    private int shortable;
    private int reglementaire;
    private Long idAnnnee;
    private Long idSpecialite;
    private String uer;
    private int nuer;
    
    private String ue1m;
    private String ue2m;
    private String ue3m;
    private String ue4m;
    private String ue5m;
    private String ue6m;
    private String ue7m;
    private String ue8m;
    private String ue9m;
    private String ue10m;
    private String ue11m;
    private String ue12m;

    public Pv() {
        this.fin=0;
        this.shortable=1;
        this.reglementaire=1;
    }

    /*@Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.matEtudiant);
        return hash;
    }*/

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
    
    

    public Long getIdAnnnee() {
        return idAnnnee;
    }

    public void setIdAnnnee(Long idAnnnee) {
        this.idAnnnee = idAnnnee;
    }

    public Long getIdSpecialite() {
        return idSpecialite;
    }

    public void setIdSpecialite(Long idSpecialite) {
        this.idSpecialite = idSpecialite;
    }

    public String getUe1m() {
        return ue1m;
    }

    public void setUe1m(String ue1m) {
        this.ue1m = ue1m;
    }

    public String getUe2m() {
        return ue2m;
    }

    public void setUe2m(String ue2m) {
        this.ue2m = ue2m;
    }

    public String getUe3m() {
        return ue3m;
    }

    public void setUe3m(String ue3m) {
        this.ue3m = ue3m;
    }

    public String getUe4m() {
        return ue4m;
    }

    public void setUe4m(String ue4m) {
        this.ue4m = ue4m;
    }

    public String getUe5m() {
        return ue5m;
    }

    public void setUe5m(String ue5m) {
        this.ue5m = ue5m;
    }

    public String getUe6m() {
        return ue6m;
    }

    public void setUe6m(String ue6m) {
        this.ue6m = ue6m;
    }

    public String getUe7m() {
        return ue7m;
    }

    public void setUe7m(String ue7m) {
        this.ue7m = ue7m;
    }

    public String getUe8m() {
        return ue8m;
    }

    public void setUe8m(String ue8m) {
        this.ue8m = ue8m;
    }

    public String getUe9m() {
        return ue9m;
    }

    public void setUe9m(String ue9m) {
        this.ue9m = ue9m;
    }

    public String getUe10m() {
        return ue10m;
    }

    public void setUe10m(String ue10m) {
        this.ue10m = ue10m;
    }

    public String getUe11m() {
        return ue11m;
    }

    public void setUe11m(String ue11m) {
        this.ue11m = ue11m;
    }

    public String getUe12m() {
        return ue12m;
    }

    public void setUe12m(String ue12m) {
        this.ue12m = ue12m;
    }
    
    

    /*@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pv other = (Pv) obj;
        if (!Objects.equals(this.matEtudiant, other.matEtudiant)) {
            return false;
        }
        return true;
    }*/

    
    
    
    public int getShortable() {
        return shortable;
    }

    public void setShortable(int shortable) {
        this.shortable = shortable;
    }
    
    

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNormale() {
        return normale;
    }

    public void setNormale(int normale) {
        this.normale = normale;
    }

    
    
    
    
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getNiv() {
        return niv;
    }

    public void setNiv(String niv) {
        this.niv = niv;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public Float getMoy2() {
        return moy2;
    }

    public void setMoy2(Float moy2) {
        this.moy2 = moy2;
    }

    
    
    
    public String getNomDep() {
        return nomDep;
    }

    public void setNomDep(String nomDep) {
        this.nomDep = nomDep;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public Float getCoef() {
        return coef;
    }

    public void setCoef(Float coef) {
        this.coef = coef;
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

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getMatEtudiant() {
        return matEtudiant;
    }

    public void setMatEtudiant(String matEtudiant) {
        this.matEtudiant = matEtudiant;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnneeAc() {
        return anneeAc;
    }

    public void setAnneeAc(String anneeAc) {
        this.anneeAc = anneeAc;
    }

    public String getNumSemS() {
        return numSemS;
    }

    public void setNumSemS(String numSemS) {
        this.numSemS = numSemS;
    }

    public String getNumSemF() {
        return numSemF;
    }

    public void setNumSemF(String numSemF) {
        this.numSemF = numSemF;
    }

    public String getIntitule1() {
        return intitule1;
    }

    public void setIntitule1(String intitule1) {
        this.intitule1 = intitule1;
    }

    public String getIntitule2() {
        return intitule2;
    }

    public void setIntitule2(String intitule2) {
        this.intitule2 = intitule2;
    }

    public String getIntitule3() {
        return intitule3;
    }

    public void setIntitule3(String intitule3) {
        this.intitule3 = intitule3;
    }

    public String getIntitule4() {
        return intitule4;
    }

    public void setIntitule4(String intitule4) {
        this.intitule4 = intitule4;
    }

    public String getIntitule5() {
        return intitule5;
    }

    public void setIntitule5(String intitule5) {
        this.intitule5 = intitule5;
    }

    public String getIntitule6() {
        return intitule6;
    }

    public void setIntitule6(String intitule6) {
        this.intitule6 = intitule6;
    }

    public String getIntitule7() {
        return intitule7;
    }

    public void setIntitule7(String intitule7) {
        this.intitule7 = intitule7;
    }

    public String getIntitule8() {
        return intitule8;
    }

    public void setIntitule8(String intitule8) {
        this.intitule8 = intitule8;
    }

    public String getIntitule9() {
        return intitule9;
    }

    public void setIntitule9(String intitule9) {
        this.intitule9 = intitule9;
    }

    public String getIntitule10() {
        return intitule10;
    }

    public void setIntitule10(String intitule10) {
        this.intitule10 = intitule10;
    }

    public String getIntitule11() {
        return intitule11;
    }

    public void setIntitule11(String intitule11) {
        this.intitule11 = intitule11;
    }

    public String getIntitule12() {
        return intitule12;
    }

    public void setIntitule12(String intitule12) {
        this.intitule12 = intitule12;
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

    public String getUe7() {
        return ue7;
    }

    public void setUe7(String ue7) {
        this.ue7 = ue7;
    }

    public String getUe8() {
        return ue8;
    }

    public void setUe8(String ue8) {
        this.ue8 = ue8;
    }

    public String getUe9() {
        return ue9;
    }

    public void setUe9(String ue9) {
        this.ue9 = ue9;
    }

    public String getUe10() {
        return ue10;
    }

    public void setUe10(String ue10) {
        this.ue10 = ue10;
    }

    public String getUe11() {
        return ue11;
    }

    public void setUe11(String ue11) {
        this.ue11 = ue11;
    }

    public String getUe12() {
        return ue12;
    }

    public void setUe12(String ue12) {
        this.ue12 = ue12;
    }

    public Float getCoef1() {
        return coef1;
    }

    public void setCoef1(Float coef1) {
        this.coef1 = coef1;
    }

    public Float getCoef2() {
        return coef2;
    }

    public void setCoef2(Float coef2) {
        this.coef2 = coef2;
    }

    public Float getCoef3() {
        return coef3;
    }

    public void setCoef3(Float coef3) {
        this.coef3 = coef3;
    }

    public Float getCoef4() {
        return coef4;
    }

    public void setCoef4(Float coef4) {
        this.coef4 = coef4;
    }

    public Float getCoef5() {
        return coef5;
    }

    public void setCoef5(Float coef5) {
        this.coef5 = coef5;
    }

    public Float getCoef6() {
        return coef6;
    }

    public void setCoef6(Float coef6) {
        this.coef6 = coef6;
    }

    public Float getCoef7() {
        return coef7;
    }

    public void setCoef7(Float coef7) {
        this.coef7 = coef7;
    }

    public Float getCoef8() {
        return coef8;
    }

    public void setCoef8(Float coef8) {
        this.coef8 = coef8;
    }

    public Float getCoef9() {
        return coef9;
    }

    public void setCoef9(Float coef9) {
        this.coef9 = coef9;
    }

    public Float getCoef10() {
        return coef10;
    }

    public void setCoef10(Float coef10) {
        this.coef10 = coef10;
    }

    public Float getCoef11() {
        return coef11;
    }

    public void setCoef11(Float coef11) {
        this.coef11 = coef11;
    }

    public Float getCoef12() {
        return coef12;
    }

    public void setCoef12(Float coef12) {
        this.coef12 = coef12;
    }

    public String getGrade1() {
        return grade1;
    }

    public void setGrade1(String grade1) {
        this.grade1 = grade1;
    }

    public Float getCote1() {
        return cote1;
    }

    public void setCote1(Float cote1) {
        this.cote1 = cote1;
    }

    public String getGrade2() {
        return grade2;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }

    public Float getCote2() {
        return cote2;
    }

    public void setCote2(Float cote2) {
        this.cote2 = cote2;
    }

    public String getGrade3() {
        return grade3;
    }

    public void setGrade3(String grade3) {
        this.grade3 = grade3;
    }

    public Float getCote3() {
        return cote3;
    }

    public void setCote3(Float cote3) {
        this.cote3 = cote3;
    }

    public String getGrade4() {
        return grade4;
    }

    public void setGrade4(String grade4) {
        this.grade4 = grade4;
    }

    public Float getCote4() {
        return cote4;
    }

    public void setCote4(Float cote4) {
        this.cote4 = cote4;
    }

    public String getGrade5() {
        return grade5;
    }

    public void setGrade5(String grade5) {
        this.grade5 = grade5;
    }

    public Float getCote5() {
        return cote5;
    }

    public void setCote5(Float cote5) {
        this.cote5 = cote5;
    }

    public String getGrade6() {
        return grade6;
    }

    public void setGrade6(String grade6) {
        this.grade6 = grade6;
    }

    public Float getCote6() {
        return cote6;
    }

    public void setCote6(Float cote6) {
        this.cote6 = cote6;
    }

    public String getGrade7() {
        return grade7;
    }

    public void setGrade7(String grade7) {
        this.grade7 = grade7;
    }

    public Float getCote7() {
        return cote7;
    }

    public void setCote7(Float cote7) {
        this.cote7 = cote7;
    }

    public String getGrade8() {
        return grade8;
    }

    public void setGrade8(String grade8) {
        this.grade8 = grade8;
    }

    public Float getCote8() {
        return cote8;
    }

    public void setCote8(Float cote8) {
        this.cote8 = cote8;
    }

    public String getGrade9() {
        return grade9;
    }

    public void setGrade9(String grade9) {
        this.grade9 = grade9;
    }

    public Float getCote9() {
        return cote9;
    }

    public void setCote9(Float cote9) {
        this.cote9 = cote9;
    }

    public String getGrade10() {
        return grade10;
    }

    public void setGrade10(String grade10) {
        this.grade10 = grade10;
    }

    public Float getCote10() {
        return cote10;
    }

    public void setCote10(Float cote10) {
        this.cote10 = cote10;
    }

    public String getGrade11() {
        return grade11;
    }

    public void setGrade11(String grade11) {
        this.grade11 = grade11;
    }

    public Float getCote11() {
        return cote11;
    }

    public void setCote11(Float cote11) {
        this.cote11 = cote11;
    }

    public String getGrade12() {
        return grade12;
    }

    public void setGrade12(String grade12) {
        this.grade12 = grade12;
    }

    public Float getCote12() {
        return cote12;
    }

    public void setCote12(Float cote12) {
        this.cote12 = cote12;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public Float getNote1() {
        return note1;
    }

    public void setNote1(Float note1) {
        this.note1 = note1;
    }

    public Float getNote2() {
        return note2;
    }

    public void setNote2(Float note2) {
        this.note2 = note2;
    }

    public Float getNote3() {
        return note3;
    }

    public void setNote3(Float note3) {
        this.note3 = note3;
    }

    public Float getNote4() {
        return note4;
    }

    public void setNote4(Float note4) {
        this.note4 = note4;
    }

    public Float getNote5() {
        return note5;
    }

    public void setNote5(Float note5) {
        this.note5 = note5;
    }

    public Float getNote6() {
        return note6;
    }

    public void setNote6(Float note6) {
        this.note6 = note6;
    }

    public Float getCap() {
        return cap;
    }

    public void setCap(Float cap) {
        this.cap = cap;
    }

    public Float getMoy() {
        return moy;
    }

    public void setMoy(Float moy) {
        this.moy = moy;
    }

    public Float getNote7() {
        return note7;
    }

    public void setNote7(Float note7) {
        this.note7 = note7;
    }

    public Float getNote8() {
        return note8;
    }

    public void setNote8(Float note8) {
        this.note8 = note8;
    }

    public Float getNote9() {
        return note9;
    }

    public void setNote9(Float note9) {
        this.note9 = note9;
    }

    public Float getNote10() {
        return note10;
    }

    public void setNote10(Float note10) {
        this.note10 = note10;
    }

    public Float getNote11() {
        return note11;
    }

    public void setNote11(Float note11) {
        this.note11 = note11;
    }

    public Float getNote12() {
        return note12;
    }

    public void setNote12(Float note12) {
        this.note12 = note12;
    }

    public int getNmEtuVal1() {
        return nmEtuVal1;
    }

    public void setNmEtuVal1(int nmEtuVal1) {
        this.nmEtuVal1 = nmEtuVal1;
    }

    public String getPercentVal1() {
        return percentVal1;
    }

    public void setPercentVal1(String percentVal1) {
        this.percentVal1 = percentVal1;
    }

    public int getNmEtuVal2() {
        return nmEtuVal2;
    }

    public void setNmEtuVal2(int nmEtuVal2) {
        this.nmEtuVal2 = nmEtuVal2;
    }

    public String getPercentVal2() {
        return percentVal2;
    }

    public void setPercentVal2(String percentVal2) {
        this.percentVal2 = percentVal2;
    }

    public int getNmEtuVal3() {
        return nmEtuVal3;
    }

    public void setNmEtuVal3(int nmEtuVal3) {
        this.nmEtuVal3 = nmEtuVal3;
    }

    public String getPercentVal3() {
        return percentVal3;
    }

    public void setPercentVal3(String percentVal3) {
        this.percentVal3 = percentVal3;
    }

    public int getNmEtuVal4() {
        return nmEtuVal4;
    }

    public void setNmEtuVal4(int nmEtuVal4) {
        this.nmEtuVal4 = nmEtuVal4;
    }

    public String getPercentVal4() {
        return percentVal4;
    }

    public void setPercentVal4(String percentVal4) {
        this.percentVal4 = percentVal4;
    }

    public int getNmEtuVal5() {
        return nmEtuVal5;
    }

    public void setNmEtuVal5(int nmEtuVal5) {
        this.nmEtuVal5 = nmEtuVal5;
    }

    public String getPercentVal5() {
        return percentVal5;
    }

    public void setPercentVal5(String percentVal5) {
        this.percentVal5 = percentVal5;
    }

    public int getNmEtuVal6() {
        return nmEtuVal6;
    }

    public void setNmEtuVal6(int nmEtuVal6) {
        this.nmEtuVal6 = nmEtuVal6;
    }

    public String getPercentVal6() {
        return percentVal6;
    }

    public void setPercentVal6(String percentVal6) {
        this.percentVal6 = percentVal6;
    }

    public Long getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Long specialite) {
        this.specialite = specialite;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public static Comparator<Pv> getMoyenneComparator() {
        return moyenneComparator;
    }

    public static void setMoyenneComparator(Comparator<Pv> moyenneComparator) {
        Pv.moyenneComparator = moyenneComparator;
    }

    public static Comparator<Pv> getNomComparator() {
        return nomComparator;
    }

    public static void setNomComparator(Comparator<Pv> nomComparator) {
        Pv.nomComparator = nomComparator;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    @Override
    public int compareTo(Pv comparePvSemestre) {

        Float compareMoySem = ((Pv) comparePvSemestre).getMoy();
        //ascending order
        if ((this.moy.equals(compareMoySem))) {
            return 0;
        } else {
            return -1;
        }

    }
    public static Comparator<Pv> moyenneComparator = new Comparator<Pv>() {
        @Override
        public int compare(Pv pvSemestre1, Pv pvSemestre2) {

            Float moySem1 = pvSemestre1.getMoy2();
            Float moySem2 = pvSemestre2.getMoy2();
            return moySem1 > moySem2 ? -1
                    : moySem1 < moySem2 ? 1
                    : 0;
        }
    };
    public static Comparator<Pv> nomComparator = new Comparator<Pv>() {
        @Override
        public int compare(Pv pvSemestre1, Pv pvSemestre2) {
            String nomEt1 = pvSemestre1.getNomEtudiant();
            String nomEt2 = pvSemestre2.getNomEtudiant();
            return nomEt1.compareTo(nomEt2);
        }
    };

    @Override
    public String toString() {
        return "Pv{" + "niveau=" + niveau + ", coef=" + coef + ", intitule=" + intitule + ", matEtudiant=" + matEtudiant + ", numSemS=" + numSemS + ", intitule10=" + intitule10 + ", intitule11=" + intitule11 + ", ue4=" + ue4 + ", note2=" + note2 + ", note3=" + note3 + ", note4=" + note4 + ", note5=" + note5 + ", note6=" + note6 + ", specialite=" + specialite + '}';
    }

    public int getFin() {
        return fin;
    }

    public void setFin(int fin) {
        this.fin = fin;
    }

    public int getReglementaire() {
        return reglementaire;
    }

    public void setReglementaire(int reglementaire) {
        this.reglementaire = reglementaire;
    }

    public String getUer() {
        return uer;
    }

    public void setUer(String uer) {
        this.uer = uer;
    }

    public int getNuer() {
        return nuer;
    }

    public void setNuer(int nuer) {
        this.nuer = nuer;
    }
    
    
    
    
}
