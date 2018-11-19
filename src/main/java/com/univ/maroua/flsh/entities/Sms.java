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
@Table(name = "sms")
@NamedQueries({
    @NamedQuery(name = "Sms.findByAnneeSectionTypeEtudiant", query = "SELECT s FROM Sms s WHERE s.anneeAcademique.id = :anneeId AND s.section.id = :sectionId AND s.type = :type AND s.etudiant.id = :etudiantId"),})
public class Sms implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int type; //par defaut que ce soit le premier ou le second semestre on publie les notes de session normale de ratrapage et les syntheses
    private String portee; // l'envoi doit etre national, inernationnal ou les deux?
    @Column(columnDefinition = "boolean default false")
    private Boolean isS1Send;
    @Column(columnDefinition = "boolean default false")
    private Boolean isS2Send;
    @Column(columnDefinition = "boolean default false")
    private Boolean isAnSend;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Etudiant etudiant;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AnneeAcademique anneeAcademique;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Section section;

    public Sms() {
        isAnSend=false;
        isS1Send=false;
        isS2Send=false;
    }

    
    
    public String getPortee() {
        return portee;
    }

    public void setPortee(String portee) {
        this.portee = portee;
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
        if (!(object instanceof Sms)) {
            return false;
        }
        Sms other = (Sms) object;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Boolean getIsS1Send() {
        return isS1Send;
    }

    public void setIsS1Send(Boolean isS1Send) {
        this.isS1Send = isS1Send;
    }

    public Boolean getIsS2Send() {
        return isS2Send;
    }

    public void setIsS2Send(Boolean isS2Send) {
        this.isS2Send = isS2Send;
    }

    public Boolean getIsAnSend() {
        return isAnSend;
    }

    public void setIsAnSend(Boolean isAnSend) {
        this.isAnSend = isAnSend;
    }
    
    
}
