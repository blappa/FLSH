/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.projection;

/**
 *
 * @author koossery
 */
public class Releve implements Cloneable, Comparable<Releve> {

    private String num;
    private String nomEtu;
    private String matricule;
    private String datNais;
    private String lieuNais;
    private String nationalite;
    private String parcours;
    private String specialite;
    private String cycle;
    private String niveau;
    private String codeUe1;
    private String codeUe2;
    private String codeUe3;
    private String codeUe4;
    private String codeUe5;
    private String codeUe6;
    private String codeUe7;
    private String codeUe8;
    private String codeUe9;
    private String codeUe10;
    private String codeUe11;
    private String codeUe12;
    private String intituleUe1;
    private String intituleUe2;
    private String intituleUe3;
    private String intituleUe4;
    private String intituleUe5;
    private String intituleUe6;
    private String intituleUe7;
    private String intituleUe8;
    private String intituleUe9;
    private String intituleUe10;
    private String intituleUe11;
    private String intituleUe12;
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
    private String grade1;
    private String grade2;
    private String grade3;
    private String grade4;
    private String grade5;
    private String grade6;
    private String grade7;
    private String grade8;
    private String grade9;
    private String grade10;
    private String grade11;
    private String grade12;
    private Float cote1;
    private Float cote2;
    private Float cote3;
    private Float cote4;
    private Float cote5;
    private Float cote6;
    private Float cote7;
    private Float cote8;
    private Float cote9;
    private Float cote10;
    private Float cote11;
    private Float cote12;
    private Float credit1;
    private Float credit2;
    private Float credit3;
    private Float credit4;
    private Float credit5;
    private Float credit6;
    private Float credit7;
    private Float credit8;
    private Float credit9;
    private Float credit10;
    private Float credit11;
    private Float credit12;
    private String session1;
    private String session2;
    private String session3;
    private String session4;
    private String session5;
    private String session6;
    private String session7;
    private String session8;
    private String session9;
    private String session10;
    private String session11;
    private String session12;
    private String annee1;
    private String annee2;
    private String annee3;
    private String annee4;
    private String annee5;
    private String annee6;
    private String annee7;
    private String annee8;
    private String annee9;
    private String annee10;
    private String annee11;
    private String annee12;
    private String mgp;
    private Float noteT;
    private Float coteT;
    private Float totalCreditCap;
    private String chefDep;
    private String doyen;
    private String decision;
    private String module;
    private String intitule;
    private String tpe;
    private String td;
    private String cc;
    private String examen;
    private String rattrapage;
    private String moyenne;
    private String coef;
    private String gradeT;
    private String sem1;
    private String sem2;

    public Releve() {
    }

    public String getNomEtu() {
        return nomEtu;
    }

    public void setNomEtu(String nomEtu) {
        this.nomEtu = nomEtu;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getTpe() {
        return tpe;
    }

    public Float getCoteT() {
        return coteT;
    }

    public void setCoteT(Float coteT) {
        this.coteT = coteT;
    }

    public void setTpe(String tpe) {
        this.tpe = tpe;
    }

    public String getTd() {
        return td;
    }

    public void setTd(String td) {
        this.td = td;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getExamen() {
        return examen;
    }

    public void setExamen(String examen) {
        this.examen = examen;
    }

    public String getRattrapage() {
        return rattrapage;
    }

    public void setRattrapage(String rattrapage) {
        this.rattrapage = rattrapage;
    }

    public String getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(String moyenne) {
        this.moyenne = moyenne;
    }

    public String getCoef() {
        return coef;
    }

    public void setCoef(String coef) {
        this.coef = coef;
    }

    public Float getNoteT() {
        return noteT;
    }

    public void setNoteT(Float noteT) {
        this.noteT = noteT;
    }

    public String getGradeT() {
        return gradeT;
    }

    public void setGradeT(String gradeT) {
        this.gradeT = gradeT;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getDatNais() {
        return datNais;
    }

    public void setDatNais(String datNais) {
        this.datNais = datNais;
    }

    public String getLieuNais() {
        return lieuNais;
    }

    public void setLieuNais(String lieuNais) {
        this.lieuNais = lieuNais;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public String getParcours() {
        return parcours;
    }

    public void setParcours(String parcours) {
        this.parcours = parcours;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getCodeUe1() {
        return codeUe1;
    }

    public void setCodeUe1(String codeUe1) {
        this.codeUe1 = codeUe1;
    }

    public String getCodeUe2() {
        return codeUe2;
    }

    public void setCodeUe2(String codeUe2) {
        this.codeUe2 = codeUe2;
    }

    public String getCodeUe3() {
        return codeUe3;
    }

    public void setCodeUe3(String codeUe3) {
        this.codeUe3 = codeUe3;
    }

    public String getCodeUe4() {
        return codeUe4;
    }

    public void setCodeUe4(String codeUe4) {
        this.codeUe4 = codeUe4;
    }

    public String getCodeUe5() {
        return codeUe5;
    }

    public void setCodeUe5(String codeUe5) {
        this.codeUe5 = codeUe5;
    }

    public String getCodeUe6() {
        return codeUe6;
    }

    public void setCodeUe6(String codeUe6) {
        this.codeUe6 = codeUe6;
    }

    public String getCodeUe7() {
        return codeUe7;
    }

    public void setCodeUe7(String codeUe7) {
        this.codeUe7 = codeUe7;
    }

    public String getCodeUe8() {
        return codeUe8;
    }

    public void setCodeUe8(String codeUe8) {
        this.codeUe8 = codeUe8;
    }

    public String getCodeUe9() {
        return codeUe9;
    }

    public void setCodeUe9(String codeUe9) {
        this.codeUe9 = codeUe9;
    }

    public String getCodeUe10() {
        return codeUe10;
    }

    public void setCodeUe10(String codeUe10) {
        this.codeUe10 = codeUe10;
    }

    public String getCodeUe11() {
        return codeUe11;
    }

    public void setCodeUe11(String codeUe11) {
        this.codeUe11 = codeUe11;
    }

    public String getCodeUe12() {
        return codeUe12;
    }

    public void setCodeUe12(String codeUe12) {
        this.codeUe12 = codeUe12;
    }

    public String getIntituleUe1() {
        return intituleUe1;
    }

    public void setIntituleUe1(String intituleUe1) {
        this.intituleUe1 = intituleUe1;
    }

    public String getIntituleUe2() {
        return intituleUe2;
    }

    public void setIntituleUe2(String intituleUe2) {
        this.intituleUe2 = intituleUe2;
    }

    public String getIntituleUe3() {
        return intituleUe3;
    }

    public void setIntituleUe3(String intituleUe3) {
        this.intituleUe3 = intituleUe3;
    }

    public String getIntituleUe4() {
        return intituleUe4;
    }

    public void setIntituleUe4(String intituleUe4) {
        this.intituleUe4 = intituleUe4;
    }

    public String getIntituleUe5() {
        return intituleUe5;
    }

    public void setIntituleUe5(String intituleUe5) {
        this.intituleUe5 = intituleUe5;
    }

    public String getIntituleUe6() {
        return intituleUe6;
    }

    public void setIntituleUe6(String intituleUe6) {
        this.intituleUe6 = intituleUe6;
    }

    public String getIntituleUe7() {
        return intituleUe7;
    }

    public void setIntituleUe7(String intituleUe7) {
        this.intituleUe7 = intituleUe7;
    }

    public String getIntituleUe8() {
        return intituleUe8;
    }

    public void setIntituleUe8(String intituleUe8) {
        this.intituleUe8 = intituleUe8;
    }

    public String getIntituleUe9() {
        return intituleUe9;
    }

    public void setIntituleUe9(String intituleUe9) {
        this.intituleUe9 = intituleUe9;
    }

    public String getIntituleUe10() {
        return intituleUe10;
    }

    public void setIntituleUe10(String intituleUe10) {
        this.intituleUe10 = intituleUe10;
    }

    public String getIntituleUe11() {
        return intituleUe11;
    }

    public void setIntituleUe11(String intituleUe11) {
        this.intituleUe11 = intituleUe11;
    }

    public String getIntituleUe12() {
        return intituleUe12;
    }

    public void setIntituleUe12(String intituleUe12) {
        this.intituleUe12 = intituleUe12;
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

    public Float getCote1() {
        return cote1;
    }

    public void setCote1(Float cote1) {
        this.cote1 = cote1;
    }

    public Float getCote2() {
        return cote2;
    }

    public void setCote2(Float cote2) {
        this.cote2 = cote2;
    }

    public Float getCote3() {
        return cote3;
    }

    public void setCote3(Float cote3) {
        this.cote3 = cote3;
    }

    public Float getCote4() {
        return cote4;
    }

    public void setCote4(Float cote4) {
        this.cote4 = cote4;
    }

    public Float getCote5() {
        return cote5;
    }

    public void setCote5(Float cote5) {
        this.cote5 = cote5;
    }

    public Float getCote6() {
        return cote6;
    }

    public void setCote6(Float cote6) {
        this.cote6 = cote6;
    }

    public Float getCote7() {
        return cote7;
    }

    public void setCote7(Float cote7) {
        this.cote7 = cote7;
    }

    public Float getCote8() {
        return cote8;
    }

    public void setCote8(Float cote8) {
        this.cote8 = cote8;
    }

    public Float getCote9() {
        return cote9;
    }

    public void setCote9(Float cote9) {
        this.cote9 = cote9;
    }

    public Float getCote10() {
        return cote10;
    }

    public void setCote10(Float cote10) {
        this.cote10 = cote10;
    }

    public Float getCote11() {
        return cote11;
    }

    public void setCote11(Float cote11) {
        this.cote11 = cote11;
    }

    public Float getCote12() {
        return cote12;
    }

    public void setCote12(Float cote12) {
        this.cote12 = cote12;
    }

    

    public String getGrade1() {
        return grade1;
    }

    public void setGrade1(String grade1) {
        this.grade1 = grade1;
    }

    public String getGrade2() {
        return grade2;
    }

    public void setGrade2(String grade2) {
        this.grade2 = grade2;
    }

    public String getGrade3() {
        return grade3;
    }

    public void setGrade3(String grade3) {
        this.grade3 = grade3;
    }

    public String getGrade4() {
        return grade4;
    }

    public void setGrade4(String grade4) {
        this.grade4 = grade4;
    }

    public String getGrade5() {
        return grade5;
    }

    public void setGrade5(String grade5) {
        this.grade5 = grade5;
    }

    public String getGrade6() {
        return grade6;
    }

    public void setGrade6(String grade6) {
        this.grade6 = grade6;
    }

    public String getGrade7() {
        return grade7;
    }

    public void setGrade7(String grade7) {
        this.grade7 = grade7;
    }

    public String getGrade8() {
        return grade8;
    }

    public void setGrade8(String grade8) {
        this.grade8 = grade8;
    }

    public String getGrade9() {
        return grade9;
    }

    public void setGrade9(String grade9) {
        this.grade9 = grade9;
    }

    public String getGrade10() {
        return grade10;
    }

    public void setGrade10(String grade10) {
        this.grade10 = grade10;
    }

    public String getGrade11() {
        return grade11;
    }

    public void setGrade11(String grade11) {
        this.grade11 = grade11;
    }

    public String getGrade12() {
        return grade12;
    }

    public void setGrade12(String grade12) {
        this.grade12 = grade12;
    }

    

    public Float getCredit1() {
        return credit1;
    }

    public void setCredit1(Float credit1) {
        this.credit1 = credit1;
    }

    public Float getCredit2() {
        return credit2;
    }

    public void setCredit2(Float credit2) {
        this.credit2 = credit2;
    }

    public Float getCredit3() {
        return credit3;
    }

    public void setCredit3(Float credit3) {
        this.credit3 = credit3;
    }

    public Float getCredit4() {
        return credit4;
    }

    public void setCredit4(Float credit4) {
        this.credit4 = credit4;
    }

    public Float getCredit5() {
        return credit5;
    }

    public void setCredit5(Float credit5) {
        this.credit5 = credit5;
    }

    public Float getCredit6() {
        return credit6;
    }

    public void setCredit6(Float credit6) {
        this.credit6 = credit6;
    }

    public Float getCredit7() {
        return credit7;
    }

    public void setCredit7(Float credit7) {
        this.credit7 = credit7;
    }

    public Float getCredit8() {
        return credit8;
    }

    public void setCredit8(Float credit8) {
        this.credit8 = credit8;
    }

    public Float getCredit9() {
        return credit9;
    }

    public void setCredit9(Float credit9) {
        this.credit9 = credit9;
    }

    public Float getCredit10() {
        return credit10;
    }

    public void setCredit10(Float credit10) {
        this.credit10 = credit10;
    }

    public Float getCredit11() {
        return credit11;
    }

    public void setCredit11(Float credit11) {
        this.credit11 = credit11;
    }

    public Float getCredit12() {
        return credit12;
    }

    public void setCredit12(Float credit12) {
        this.credit12 = credit12;
    }

    public String getSession1() {
        return session1;
    }

    public void setSession1(String session1) {
        this.session1 = session1;
    }

    public String getSession2() {
        return session2;
    }

    public void setSession2(String session2) {
        this.session2 = session2;
    }

    public String getSession3() {
        return session3;
    }

    public void setSession3(String session3) {
        this.session3 = session3;
    }

    public String getSession4() {
        return session4;
    }

    public void setSession4(String session4) {
        this.session4 = session4;
    }

    public String getSession5() {
        return session5;
    }

    public void setSession5(String session5) {
        this.session5 = session5;
    }

    public String getSession6() {
        return session6;
    }

    public void setSession6(String session6) {
        this.session6 = session6;
    }

    public String getSession7() {
        return session7;
    }

    public void setSession7(String session7) {
        this.session7 = session7;
    }

    public String getSession8() {
        return session8;
    }

    public void setSession8(String session8) {
        this.session8 = session8;
    }

    public String getSession9() {
        return session9;
    }

    public void setSession9(String session9) {
        this.session9 = session9;
    }

    public String getSession10() {
        return session10;
    }

    public void setSession10(String session10) {
        this.session10 = session10;
    }

    public String getSession11() {
        return session11;
    }

    public void setSession11(String session11) {
        this.session11 = session11;
    }

    public String getSession12() {
        return session12;
    }

    public void setSession12(String session12) {
        this.session12 = session12;
    }

    public String getAnnee1() {
        return annee1;
    }

    public void setAnnee1(String annee1) {
        this.annee1 = annee1;
    }

    public String getAnnee2() {
        return annee2;
    }

    public void setAnnee2(String annee2) {
        this.annee2 = annee2;
    }

    public String getAnnee3() {
        return annee3;
    }

    public void setAnnee3(String annee3) {
        this.annee3 = annee3;
    }

    public String getAnnee4() {
        return annee4;
    }

    public void setAnnee4(String annee4) {
        this.annee4 = annee4;
    }

    public String getAnnee5() {
        return annee5;
    }

    public void setAnnee5(String annee5) {
        this.annee5 = annee5;
    }

    public String getAnnee6() {
        return annee6;
    }

    public void setAnnee6(String annee6) {
        this.annee6 = annee6;
    }

    public String getAnnee7() {
        return annee7;
    }

    public void setAnnee7(String annee7) {
        this.annee7 = annee7;
    }

    public String getAnnee8() {
        return annee8;
    }

    public void setAnnee8(String annee8) {
        this.annee8 = annee8;
    }

    public String getAnnee9() {
        return annee9;
    }

    public void setAnnee9(String annee9) {
        this.annee9 = annee9;
    }

    public String getAnnee10() {
        return annee10;
    }

    public void setAnnee10(String annee10) {
        this.annee10 = annee10;
    }

    public String getAnnee11() {
        return annee11;
    }

    public void setAnnee11(String annee11) {
        this.annee11 = annee11;
    }

    public String getAnnee12() {
        return annee12;
    }

    public void setAnnee12(String annee12) {
        this.annee12 = annee12;
    }

    public String getMgp() {
        return mgp;
    }

    public void setMgp(String mgp) {
        this.mgp = mgp;
    }

    public Float getTotalCreditCap() {
        return totalCreditCap;
    }

    public void setTotalCreditCap(Float totalCreditCap) {
        this.totalCreditCap = totalCreditCap;
    }

    public String getChefDep() {
        return chefDep;
    }

    public void setChefDep(String chefDep) {
        this.chefDep = chefDep;
    }

    public String getDoyen() {
        return doyen;
    }

    public void setDoyen(String doyen) {
        this.doyen = doyen;
    }

    public String getSem1() {
        return sem1;
    }

    public void setSem1(String sem1) {
        this.sem1 = sem1;
    }

    public String getSem2() {
        return sem2;
    }

    public void setSem2(String sem2) {
        this.sem2 = sem2;
    }

    @Override
    public Object clone() {
        Object o = null;
        try {
            o = super.clone();
        } catch (CloneNotSupportedException cnse) {
            cnse.printStackTrace(System.err);
        }
        return o;
    }
    
    @Override
    public int compareTo(Releve rel) {
        return this.nomEtu.compareTo(rel.nomEtu);
    }
    
}
