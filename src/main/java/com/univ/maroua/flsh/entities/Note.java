/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import com.sun.org.apache.xpath.internal.operations.Bool;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "note")
@NamedQueries({
    @NamedQuery(name = "Note.findEtudiantAnneeSpecialite", query = "SELECT n FROM Note n  WHERE  n.etudiant.matricule =:matricule AND n.matiere.module.anneeAcademique.id =:idAnnee AND n.matiere.module.specialite.id =:idSpecialite"),
    @NamedQuery(name = "Note.findAll", query = "SELECT n FROM Note n"),
    @NamedQuery(name = "Note.findById", query = "SELECT n FROM Note n WHERE n.id = :id"),
    @NamedQuery(name = "Note.findByCc", query = "SELECT n FROM Note n WHERE n.cc = :cc"),
    @NamedQuery(name = "Note.findByExamen", query = "SELECT n FROM Note n WHERE n.examen = :examen"),
    @NamedQuery(name = "Note.findByRattrapage", query = "SELECT n FROM Note n WHERE n.rattrapage = :rattrapage"),
    @NamedQuery(name = "Note.findByTpe", query = "SELECT n FROM Note n WHERE n.tpe = :tpe"),
    @NamedQuery(name = "Note.findByMatiereEtudiant", query = "SELECT n FROM Note n WHERE n.etudiant.id = :idEtudiant AND n.matiere.id = :idMatiere"),
    @NamedQuery(name = "Note.findByMatiereAnonymatEx", query = "SELECT n FROM Note n WHERE n.anonymatEx= :anonymatName AND n.matiere.id = :idMatiere"),
    @NamedQuery(name = "Note.findByMatiereAnonymatRat", query = "SELECT n FROM Note n WHERE n.anonymatRat= :anonymatName AND n.matiere.id = :idMatiere"),
    @NamedQuery(name = "Note.findEtudiantAnnee", query = "SELECT n FROM Note n  WHERE  n.etudiant.matricule =:matricule AND n.matiere.module.anneeAcademique.id =:idAnnee"),
    @NamedQuery(name = "Note.findEtudiantAnneeSection", query = "SELECT n FROM Note n  WHERE  n.etudiant.matricule =:matricule AND n.matiere.module.anneeAcademique.id =:idAnnee AND n.matiere.module.specialite.niveau.section.id =:idSection"),
    @NamedQuery(name = "Note.findByEtudiant", query = "SELECT n FROM Note n  WHERE  n.etudiant.matricule =:matricule ORDER BY n.matiere.module.anneeAcademique.annee"),
    @NamedQuery(name = "Note.findByNoteForProfil", query = "SELECT n FROM Note n  WHERE  n.etudiant.matricule =:matricule ORDER BY n.matiere.module.specialite.niveau.section.nom,n.matiere.module.specialite.niveau.level,n.matiere.module.specialite.nom,n.matiere.module.anneeAcademique.annee,n.matiere.module.code"),
    @NamedQuery(name = "Note.findByAnnee", query = "SELECT n FROM Note n  WHERE  n.matiere.module.anneeAcademique.id =:idAnnee "),
    @NamedQuery(name = "Note.findByEtudiantSectionNiveau", query = "SELECT spe FROM  Note spe WHERE spe.etudiant.matricule =:matricule AND spe.matiere.module.specialite.niveau.level  =:level AND spe.matiere.module.specialite.niveau.section.id =:sectionId ORDER BY spe.matiere.module.anneeAcademique.id DESC "),
    @NamedQuery(name = "Note.findByEtudiantSection", query = "SELECT spe FROM  Note spe WHERE spe.etudiant.matricule =:matricule  AND spe.matiere.module.specialite.niveau.section.id =:sectionId")
})
public class Note implements Serializable, Comparable<Note>, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float cc;
    private Float td;
    private Float tpe;
    @Column(name = "anonymatex")
    private String anonymatEx;
    @Column(name = "anonymatrat")
    private String anonymatRat;
    private Float examen;
    private Float rattrapage;
    private Float moy;
    private Float cote;
    private String grade;
    private String decision;
    private String filter;
    @Column(columnDefinition = "boolean default false")
    private Boolean isSmsSend;
    @Column(name = "codedeliberation")
    private String codeDeliberation;
    private int paye;
    @Column(columnDefinition = "boolean default false")
    private Boolean requette;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date daterequette;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Matiere matiere;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Etudiant etudiant;

    public Note() {
        isSmsSend=false;
        requette=false;
    }

    public Note(Float cc, Float tpe, Float examen, Float rattrapage, Matiere matiere, Etudiant etudiant, AnneeAcademique anneeAcademique) {
        this.cc = cc;
        this.tpe = tpe;
        this.examen = examen;
        this.rattrapage = rattrapage;
        this.matiere = matiere;
        this.etudiant = etudiant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPaye() {
        return paye;
    }

    public void setPaye(int paye) {
        this.paye = paye;
    }

    public Float getCc() {
        return cc;
    }

    public void setCc(Float cc) {
        this.cc = cc;
    }

    public Float getTpe() {
        return tpe;
    }

    public void setTpe(Float tpe) {
        this.tpe = tpe;
    }

    public Float getExamen() {
        return examen;
    }

    public void setExamen(Float examen) {
        this.examen = examen;
    }

    public Float getRattrapage() {
        return rattrapage;
    }

    public void setRattrapage(Float rattrapage) {
        this.rattrapage = rattrapage;
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

    public Float getTd() {
        return td;
    }

    public void setTd(Float td) {
        this.td = td;
    }

    public Float getMoy() {
        return moy;
    }

    public void setMoy(Float moy) {
        this.moy = moy;
    }

    public Float getCote() {
        return cote;
    }

    public void setCote(Float cote) {
        this.cote = cote;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
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
        if (!(object instanceof Note)) {
            return false;
        }
        Note other = (Note) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return " cc= " + cc + ", td= " + td + ", tpe= " + tpe + ", anonymatEx= " + anonymatEx + ", anonymatRat= " + anonymatRat + ", examen= " + examen  +", rattrapage= " + rattrapage + ", moyenne= " + moy+ " de la matiere= " + matiere + " pour etudiant= " + etudiant;
    }

    

    public String getAnonymatEx() {
        try {
            if (anonymatEx.trim().isEmpty()) {
                return null;
            }
        } catch (NullPointerException e) {
        }

        return anonymatEx;
    }

    public void setAnonymatEx(String anonymatEx) {
        this.anonymatEx = anonymatEx;
    }

    public String getAnonymatRat() {
         try {
            if (anonymatRat.trim().isEmpty()) {
                return null;
            }
        } catch (NullPointerException e) {
        }
        return anonymatRat;
    }

    public void setAnonymatRat(String anonymatRat) {
        this.anonymatRat = anonymatRat;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getCodeDeliberation() {
        return codeDeliberation;
    }

    public void setCodeDeliberation(String codeDeliberation) {
        this.codeDeliberation = codeDeliberation;
    }

    @Override
    public int compareTo(Note note) {
        return this.etudiant.getNom().compareTo(note.etudiant.getNom());
    }
    public static Comparator<Note> anonymatRatComparator = new Comparator<Note>() {
        @Override
        public int compare(Note n1, Note n2) {
            int s1 = Integer.parseInt(n1.anonymatRat);
            int s2 = Integer.parseInt(n2.anonymatRat);
            return s1 < s2 ? -1
                    : s1 > s2 ? 1
                    : 0;
        }
    };
    public static Comparator<Note> anonymatExComparator = new Comparator<Note>() {
        @Override
        public int compare(Note n1, Note n2) {
            int s1 = Integer.parseInt(n1.anonymatEx);
            int s2 = Integer.parseInt(n2.anonymatEx);
            return s1 < s2 ? -1
                    : s1 > s2 ? 1
                    : 0;
        }
    };

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

    public Boolean getIsSmsSend() {
        return isSmsSend;
    }

    public void setIsSmsSend(Boolean isSmsSend) {
        this.isSmsSend = isSmsSend;
    }

    public Boolean getRequette() {
        return requette;
    }

    public void setRequette(Boolean requette) {
        this.requette = requette;
    }

    public Date getDaterequette() {
        return daterequette;
    }

    public void setDaterequette(Date daterequette) {
        this.daterequette = daterequette;
    }
}
