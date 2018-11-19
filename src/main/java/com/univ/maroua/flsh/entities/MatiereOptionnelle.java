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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "matiereoptionnelle")
@NamedQueries({
  //  @NamedQuery(name = "MatiereOptionnelleBySpecialite.findListMatiereOptionnelleBySpecialite", query = "SELECT matOp FROM MatiereOptionnelle matOp, Matiere mat, Module mod, Specialite spec JOIN matOp.matiere matOp_mat JOIN mat.module mat_mod JOIN mod.specialite mod_spec WHERE matOp.id=matOp_mat.id AND mat.id=mat_mod.id AND mod.id=mod_spec.id AND spec.code=:code"),
 @NamedQuery(name = "MatiereOptionnelleBySpecialite.findListMatiereOpByEtudiant", query = "SELECT matop FROM Matiere m,MatiereOptionnelle matop,Etudiant e, Specialite sp, SpecialiteEtudiant spe WHERE m.id=matop.matiere.id AND matop.etudiant.id=e.id AND e.id=:idEtudiant AND spe.etudiant.id=e.id AND sp.id=spe.specialite.id AND sp.id=:idSpecialite"),
   
})
public class MatiereOptionnelle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Matiere matiere;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Etudiant etudiant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
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
        if (!(object instanceof MatiereOptionnelle)) {
            return false;
        }
        MatiereOptionnelle other = (MatiereOptionnelle) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.univ.maroua.flsh.entities.MatiereOptionnelle[ id=" + id + " ]";
    }
}
