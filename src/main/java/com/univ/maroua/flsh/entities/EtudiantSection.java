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
@Table(name = "etudiantsection")
@NamedQueries({
    @NamedQuery(name = "EtudiantSection.findEtudiantBySection", query = "SELECT spe FROM EtudiantSection spe WHERE spe.etudiant.id=:idEtudiant AND spe.section.id=:idSection"),})
public class EtudiantSection implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //pour sauvegarder les credits pour les differnets niveaux;
    @Column(columnDefinition = "integer default 0")
    private int credit1;
    @Column(columnDefinition = "integer default 0")
    private int credit2;
    @Column(columnDefinition = "integer default 0")
    private int credit3;
    @Column(columnDefinition = "integer default 0")
    private int credit4;
    @Column(columnDefinition = "integer default 0")
    private int credit5;
    @Column(columnDefinition = "integer default 0")
    private int credit6;
    @Column(columnDefinition = "integer default 0")
    private int credit7;
    @Column(columnDefinition = "integer default 0")
    private int credit8;
    @Column(columnDefinition = "integer default 0")
    private int credit9;
    @Column(columnDefinition = "integer default 0")
    private int credit10;
    //pour les mgp des differents niveaux
    @Column(columnDefinition = "real default 0")
    private float mgp1;
    @Column(columnDefinition = "real default 0")
    private float mgp2;
    @Column(columnDefinition = "real default 0")
    private float mgp3;
    @Column(columnDefinition = "real default 0")
    private float mgp4;
    @Column(columnDefinition = "real default 0")
    private float mgp5;
    @Column(columnDefinition = "real default 0")
    private float mgp6;
    @Column(columnDefinition = "real default 0")
    private float mgp7;
    @Column(columnDefinition = "real default 0")
    private float mgp8;
    @Column(columnDefinition = "real default 0")
    private float mgp9;
    @Column(columnDefinition = "real default 0")
    private float mgp10;
    //pour les moyennes des differents niveaux
    @Column(columnDefinition = "real default 0")
    private float moy1;
    @Column(columnDefinition = "real default 0")
    private float moy2;
    @Column(columnDefinition = "real default 0")
    private float moy3;
    @Column(columnDefinition = "real default 0")
    private float moy4;
    @Column(columnDefinition = "real default 0")
    private float moy5;
    @Column(columnDefinition = "real default 0")
    private float moy6;
    @Column(columnDefinition = "real default 0")
    private float moy7;
    @Column(columnDefinition = "real default 0")
    private float moy8;
    @Column(columnDefinition = "real default 0")
    private float moy9;
    @Column(columnDefinition = "real default 0")
    private float moy10;
    //pour savoir si un etudiant est au niveau superieur, mais a repris le niveau inferieur
    private int sup;
    //pour savoir si un etudiant a valide un semestre a la session normale ou au ratrapage
    @Column(columnDefinition = "integer default 0")
    private int session1;
    @Column(columnDefinition = "integer default 0")
    private int session2;
    @Column(columnDefinition = "integer default 0")
    private int session3;
    @Column(columnDefinition = "integer default 0")
    private int session4;
    @Column(columnDefinition = "integer default 0")
    private int session5;
    @Column(columnDefinition = "integer default 0")
    private int session6;
    @Column(columnDefinition = "integer default 0")
    private int session7;
    @Column(columnDefinition = "integer default 0")
    private int session8;
    @Column(columnDefinition = "integer default 0")
    private int session9;
    @Column(columnDefinition = "integer default 0")
    private int session10;
    @Column(columnDefinition = "integer default 0")
    private int shortable;
    @Column(columnDefinition = "integer default 0")
    private int ncR1; //nombre de credits ramenes au semestre 1
    @Column(length = 1000)
    private String ueR1; //ue ramenées au semestre 1
    @Column(columnDefinition = "integer default 0")
    private int ncR2;
    @Column(length = 1000)
    private String ueR2;
    @Column(columnDefinition = "integer default 0")
    private int ncR3;
    @Column(length = 1000)
    private String ueR3;
    @Column(columnDefinition = "integer default 0")
    private int ncR4;
    @Column(length = 1000)
    private String ueR4;
    @Column(columnDefinition = "integer default 0")
    private int ncR5;
    @Column(length = 1000)
    private String ueR5;
    @Column(columnDefinition = "integer default 0")
    private int ncR6;
    @Column(length = 1000)
    private String ueR6;
    @Column(columnDefinition = "integer default 0")
    private int ncR7;
    @Column(length = 1000)
    private String ueR7;
    @Column(columnDefinition = "integer default 0")
    private int ncR8;
    @Column(length = 1000)
    private String ueR8;
    @Column(columnDefinition = "integer default 0")
    private int ncR9;
    @Column(length = 1000)
    private String ueR9;
    @Column(columnDefinition = "integer default 0")
    private int ncR10;
    @Column(length = 1000)
    private String ueR10;
    @Column(columnDefinition = "integer default 0")
    private int admis2; //admis au niveau 2 et venant en externe
    @Column(columnDefinition = "integer default 0")
    private int admis3; //admis au niveau 3 et venant en externe
    @Column(columnDefinition = "integer default 0")
    private int admis5; //admis au niveau 5 et venant en externe
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Etudiant etudiant;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Section section;

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
        if (!(object instanceof EtudiantSection)) {
            return false;
        }
        EtudiantSection other = (EtudiantSection) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
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

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public int getCredit1() {
        return credit1;
    }

    public void setCredit1(int credit1) {
        this.credit1 = credit1;
    }

    public int getCredit2() {
        return credit2;
    }

    public void setCredit2(int credit2) {
        this.credit2 = credit2;
    }

    public int getCredit3() {
        return credit3;
    }

    public void setCredit3(int credit3) {
        this.credit3 = credit3;
    }

    public int getCredit4() {
        return credit4;
    }

    public void setCredit4(int credit4) {
        this.credit4 = credit4;
    }

    public int getCredit5() {
        return credit5;
    }

    public void setCredit5(int credit5) {
        this.credit5 = credit5;
    }

    public int getCredit6() {
        return credit6;
    }

    public void setCredit6(int credit6) {
        this.credit6 = credit6;
    }

    public int getCredit7() {
        return credit7;
    }

    public void setCredit7(int credit7) {
        this.credit7 = credit7;
    }

    public int getCredit8() {
        return credit8;
    }

    public void setCredit8(int credit8) {
        this.credit8 = credit8;
    }

    public int getCredit9() {
        return credit9;
    }

    public void setCredit9(int credit9) {
        this.credit9 = credit9;
    }

    public int getCredit10() {
        return credit10;
    }

    public void setCredit10(int credit10) {
        this.credit10 = credit10;
    }

    public float getMgp1() {
        return mgp1;
    }

    public void setMgp1(float mgp1) {
        this.mgp1 = mgp1;
    }

    public float getMgp2() {
        return mgp2;
    }

    public void setMgp2(float mgp2) {
        this.mgp2 = mgp2;
    }

    public float getMgp3() {
        return mgp3;
    }

    public void setMgp3(float mgp3) {
        this.mgp3 = mgp3;
    }

    public float getMgp4() {
        return mgp4;
    }

    public void setMgp4(float mgp4) {
        this.mgp4 = mgp4;
    }

    public float getMgp5() {
        return mgp5;
    }

    public void setMgp5(float mgp5) {
        this.mgp5 = mgp5;
    }

    public float getMgp6() {
        return mgp6;
    }

    public void setMgp6(float mgp6) {
        this.mgp6 = mgp6;
    }

    public float getMgp7() {
        return mgp7;
    }

    public void setMgp7(float mgp7) {
        this.mgp7 = mgp7;
    }

    public float getMgp8() {
        return mgp8;
    }

    public void setMgp8(float mgp8) {
        this.mgp8 = mgp8;
    }

    public float getMgp9() {
        return mgp9;
    }

    public void setMgp9(float mgp9) {
        this.mgp9 = mgp9;
    }

    public float getMgp10() {
        return mgp10;
    }

    public void setMgp10(float mgp10) {
        this.mgp10 = mgp10;
    }

    public float getMoy1() {
        return moy1;
    }

    public void setMoy1(float moy1) {
        this.moy1 = moy1;
    }

    public float getMoy2() {
        return moy2;
    }

    public void setMoy2(float moy2) {
        this.moy2 = moy2;
    }

    public float getMoy3() {
        return moy3;
    }

    public void setMoy3(float moy3) {
        this.moy3 = moy3;
    }

    public float getMoy4() {
        return moy4;
    }

    public void setMoy4(float moy4) {
        this.moy4 = moy4;
    }

    public float getMoy5() {
        return moy5;
    }

    public void setMoy5(float moy5) {
        this.moy5 = moy5;
    }

    public float getMoy6() {
        return moy6;
    }

    public void setMoy6(float moy6) {
        this.moy6 = moy6;
    }

    public float getMoy7() {
        return moy7;
    }

    public void setMoy7(float moy7) {
        this.moy7 = moy7;
    }

    public float getMoy8() {
        return moy8;
    }

    public void setMoy8(float moy8) {
        this.moy8 = moy8;
    }

    public float getMoy9() {
        return moy9;
    }

    public void setMoy9(float moy9) {
        this.moy9 = moy9;
    }

    public float getMoy10() {
        return moy10;
    }

    public void setMoy10(float moy10) {
        this.moy10 = moy10;
    }

    public int getSup() {
        return sup;
    }

    public void setSup(int sup) {
        this.sup = sup;
    }

    public int getSession1() {
        return session1;
    }

    public void setSession1(int session1) {
        this.session1 = session1;
    }

    public int getSession2() {
        return session2;
    }

    public void setSession2(int session2) {
        this.session2 = session2;
    }

    public int getSession3() {
        return session3;
    }

    public void setSession3(int session3) {
        this.session3 = session3;
    }

    public int getSession4() {
        return session4;
    }

    public void setSession4(int session4) {
        this.session4 = session4;
    }

    public int getSession5() {
        return session5;
    }

    public void setSession5(int session5) {
        this.session5 = session5;
    }

    public int getSession6() {
        return session6;
    }

    public void setSession6(int session6) {
        this.session6 = session6;
    }

    public int getSession7() {
        return session7;
    }

    public void setSession7(int session7) {
        this.session7 = session7;
    }

    public int getSession8() {
        return session8;
    }

    public void setSession8(int session8) {
        this.session8 = session8;
    }

    public int getSession9() {
        return session9;
    }

    public void setSession9(int session9) {
        this.session9 = session9;
    }

    public int getSession10() {
        return session10;
    }

    public void setSession10(int session10) {
        this.session10 = session10;
    }

    public int getShortable() {
        return shortable;
    }

    public void setShortable(int shortable) {
        this.shortable = shortable;
    }

    public int getNcR1() {
        return ncR1;
    }

    public void setNcR1(int ncR1) {
        this.ncR1 = ncR1;
    }

    public String getUeR1() {
        return ueR1;
    }

    public void setUeR1(String ueR1) {
        this.ueR1 = ueR1;
    }

    public int getNcR2() {
        return ncR2;
    }

    public void setNcR2(int ncR2) {
        this.ncR2 = ncR2;
    }

    public String getUeR2() {
        return ueR2;
    }

    public void setUeR2(String ueR2) {
        this.ueR2 = ueR2;
    }

    public int getNcR3() {
        return ncR3;
    }

    public void setNcR3(int ncR3) {
        this.ncR3 = ncR3;
    }

    public String getUeR3() {
        return ueR3;
    }

    public void setUeR3(String ueR3) {
        this.ueR3 = ueR3;
    }

    public int getNcR4() {
        return ncR4;
    }

    public void setNcR4(int ncR4) {
        this.ncR4 = ncR4;
    }

    public String getUeR4() {
        return ueR4;
    }

    public void setUeR4(String ueR4) {
        this.ueR4 = ueR4;
    }

    public int getNcR5() {
        return ncR5;
    }

    public void setNcR5(int ncR5) {
        this.ncR5 = ncR5;
    }

    public String getUeR5() {
        return ueR5;
    }

    public void setUeR5(String ueR5) {
        this.ueR5 = ueR5;
    }

    public int getNcR6() {
        return ncR6;
    }

    public void setNcR6(int ncR6) {
        this.ncR6 = ncR6;
    }

    public String getUeR6() {
        return ueR6;
    }

    public void setUeR6(String ueR6) {
        this.ueR6 = ueR6;
    }

    public int getNcR7() {
        return ncR7;
    }

    public void setNcR7(int ncR7) {
        this.ncR7 = ncR7;
    }

    public String getUeR7() {
        return ueR7;
    }

    public void setUeR7(String ueR7) {
        this.ueR7 = ueR7;
    }

    public int getNcR8() {
        return ncR8;
    }

    public void setNcR8(int ncR8) {
        this.ncR8 = ncR8;
    }

    public String getUeR8() {
        return ueR8;
    }

    public void setUeR8(String ueR8) {
        this.ueR8 = ueR8;
    }

    public int getNcR9() {
        return ncR9;
    }

    public void setNcR9(int ncR9) {
        this.ncR9 = ncR9;
    }

    public String getUeR9() {
        return ueR9;
    }

    public void setUeR9(String ueR9) {
        this.ueR9 = ueR9;
    }

    public int getNcR10() {
        return ncR10;
    }

    public void setNcR10(int ncR10) {
        this.ncR10 = ncR10;
    }

    public String getUeR10() {
        return ueR10;
    }

    public void setUeR10(String ueR10) {
        this.ueR10 = ueR10;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public int getAdmis2() {
        return admis2;
    }

    public void setAdmis2(int admis2) {
        this.admis2 = admis2;
    }

    public int getAdmis3() {
        return admis3;
    }

    public void setAdmis3(int admis3) {
        this.admis3 = admis3;
    }

    public int getAdmis5() {
        return admis5;
    }

    public void setAdmis5(int admis5) {
        this.admis5 = admis5;
    }
    
    
}
