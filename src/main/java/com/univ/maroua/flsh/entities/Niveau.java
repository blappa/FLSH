/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "niveau")
@NamedQueries({
    @NamedQuery(name = "Niveau.findAll", query = "SELECT n FROM Niveau n"),
    @NamedQuery(name = "Niveau.findById", query = "SELECT n FROM Niveau n WHERE n.id = :id"),
    @NamedQuery(name = "Niveau.findBySection", query = "SELECT n  FROM Niveau n WHERE n.section.id = :id ORDER BY n.level ASC"),
    @NamedQuery(name = "Niveau.findByLevel", query = "SELECT n  FROM Niveau n WHERE n.level= :level"),
    @NamedQuery(name = "Niveau.findByEtudiantAnnee", query = "SELECT n  FROM Niveau n WHERE n.level= :level"),})
public class Niveau implements Serializable, Comparable<Niveau> {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private int level;
    @ManyToOne(fetch = FetchType.EAGER)
    private Section section;
    @OneToMany(mappedBy = "niveau", cascade = {CascadeType.ALL})
    private List<Specialite> specialites;
    
    public Niveau() {
    }
    
    public Niveau(String nom, Section section) {
        this.section = section;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Section getSection() {
        return section;
    }
    
    public void setSection(Section section) {
        this.section = section;
    }
    
    public List<Specialite> getSpecialites() {
        return specialites;
    }
    
    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
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
        if (!(object instanceof Niveau)) {
            return false;
        }
        Niveau other = (Niveau) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return level + " de la section " + section;
    }
    
    
    
    public int getLevel() {
        return level;
    }
    
    public void setLevel(int level) {
        this.level = level;
    }
    
    public static Comparator<Niveau> niveauComparator = new Comparator<Niveau>() {
        @Override
        public int compare(Niveau n1, Niveau n2) {
            int s1 = n1.level;
            int s2 = n2.level;
            return s1 <s2 ? -1
                    : s1 > s2 ? 1
                    : 0;
        }
    };

    @Override
    public int compareTo(Niveau o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
