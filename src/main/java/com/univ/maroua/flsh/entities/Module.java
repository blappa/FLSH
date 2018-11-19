/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "module")
@NamedQueries({
    @NamedQuery(name = "Module.findBySemestreAnneeAcSpecialite", query = "SELECT mod FROM Module mod WHERE mod.anneeAcademique.id =:idAneeAc AND mod.semestre.id =:idsemestre AND mod.specialite.id =:idSpecialite "),
    @NamedQuery(name = "Module.findByCode", query = "SELECT m FROM Module m WHERE m.code =:code"),
    @NamedQuery(name = "Module.findByAnneeAcSpecialite", query = "SELECT mod FROM Module mod WHERE mod.anneeAcademique.id =:idAneeAc  AND mod.specialite.id =:idSpecialite "),
    @NamedQuery(name = "Module.findByAnnee", query = "SELECT mod FROM Module mod WHERE mod.anneeAcademique.id =:idAneeAc "),
    @NamedQuery(name = "Module.findByCodeModuleEtudiant", query = "SELECT n.matiere.module FROM Note n WHERE n.etudiant.id =:idEtudiant AND n.matiere.module.code =:codeUE AND n.matiere.module.specialite.id=:idSpecialite  ORDER BY n.matiere.module.anneeAcademique.annee DESC"),
    @NamedQuery(name = "Module.findByCodeSpecialiteAnnee", query = "SELECT n.matiere.module FROM Note n WHERE n.matiere.module.anneeAcademique.id =:idAnnee AND n.matiere.module.code =:codeUE AND n.matiere.module.specialite.id=:idSpecialite  ORDER BY n.matiere.module.anneeAcademique.annee DESC"),
    @NamedQuery(name = "Module.findByCodeModuleEtudiantPaye", query = "SELECT n.matiere.module FROM Note n WHERE n.etudiant.id =:idEtudiant AND n.matiere.module.code =:codeUE AND n.matiere.module.specialite.id=:idSpecialite AND n.paye=:token  ORDER BY n.matiere.module.anneeAcademique.annee DESC"),
    @NamedQuery(name = "Module.findByMasterByCodeAnne", query = "SELECT m FROM Module m WHERE m.code =:code AND m.anneeAcademique.id=:idAnnee AND m.specialite.niveau.id=:niveauId")
})
public class Module implements Serializable, Comparable<Module> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; // le code pour la compatibile entre les modules au cas ou il change
    @Column(name = "targetcode")
    private String targetCode; // le code meme du module ex 310
    private Float credit;
    private String intitule;
   @ManyToOne(fetch = FetchType.EAGER)
    private Specialite specialite;
    @OneToMany(mappedBy = "module", cascade = {CascadeType.ALL})
    private List<Matiere> matieres;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Semestre semestre;
    @JoinColumn(name ="anneeacademique_id" )
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private AnneeAcademique anneeAcademique;

    public Module() {
        
    }

   
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
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
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return targetCode + " de la  specialite " + specialite + " pour l'année " + anneeAcademique;
    }

   

    public String getTargetCode() {
        return targetCode;
    }

    public void setTargetCode(String targetCode) {
        this.targetCode = targetCode;
    }
    
    @Override
    public int compareTo(Module module) {
   int s1 = Integer.parseInt(this.code);
            int s2 = Integer.parseInt(module.getCode());
            return s1 < s2 ? -1
                    : s1 > s2 ? 1
                    : 0;
        
    }
    
    
}
