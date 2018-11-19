/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import java.io.Serializable;
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
 * @author kejemto
 */
@Entity
@Table(name = "statistiques")
@NamedQueries({
    @NamedQuery(name = "Statistique.findBySpecialiteAnnee", query = "SELECT s  FROM Statistique s WHERE s.anneeAcademique.id = :idAnnee    AND s.specialite.id = :idSpecialite"),})
public class Statistique implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int admis;
    private int inscrits;
    private int reglementaires;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Specialite specialite;
    @JoinColumn(name = "anneeacademique_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AnneeAcademique anneeAcademique;

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public int getAdmis() {
        return admis;
    }

    public void setAdmis(int admis) {
        this.admis = admis;
    }

    public int getInscrits() {
        return inscrits;
    }

    public void setInscrits(int inscrits) {
        this.inscrits = inscrits;
    }

    public int getReglementaires() {
        return reglementaires;
    }

    public void setReglementaires(int reglementaires) {
        this.reglementaires = reglementaires;
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
        if (!(object instanceof Statistique)) {
            return false;
        }
        Statistique other = (Statistique) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.univ.maroua.flsh.entities.Statistiques[ id=" + id + " ]";
    }
}
