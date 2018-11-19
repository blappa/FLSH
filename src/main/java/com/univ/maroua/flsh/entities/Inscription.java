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
@Table(name = "inscription")
@NamedQueries({
    @NamedQuery(name = "Inscription.findByTypeAnnee", query = "SELECT i.etudiant FROM Inscription i WHERE i.type =:type AND i.anneeAcademique.id=:idAnnee"),
    @NamedQuery(name = "Inscription.findByTypeAnneeMatricule", query = "SELECT i FROM Inscription i WHERE i.type =:type AND i.anneeAcademique.id=:idAnnee AND i.etudiant.matricule=:matricule"),
    @NamedQuery(name = "Inscription.findByTypeAnneeMatriculeSection", query = "SELECT i FROM Inscription i WHERE i.type =:type AND i.anneeAcademique.id=:idAnnee AND i.etudiant.matricule=:matricule AND i.section.id=:idSection  "),

}) 
public class Inscription implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private int  type;
    @JoinColumn(name ="anneeacademique_id" )
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AnneeAcademique anneeAcademique;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Etudiant etudiant;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Section section;

    public Inscription(AnneeAcademique anneeAcademique, Inscription inscription, Etudiant etudiant) {
        this.anneeAcademique = anneeAcademique;
        this.etudiant = etudiant;
    }

    public Inscription() {
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
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
        if (!(object instanceof Inscription)) {
            return false;
        }
        Inscription other = (Inscription) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.iss.infotel.entities.HistoriqueInscription[ id=" + id + " ]";
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

   
    
    
    
    
}
