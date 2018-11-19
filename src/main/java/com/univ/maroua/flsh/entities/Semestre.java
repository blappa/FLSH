/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.entities;

import com.univ.maroua.flsh.projection.Pv;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lappa
 */
@Entity
@Table(name = "semestre")
@NamedQueries({
    @NamedQuery(name = "Semestre.findAll", query = "SELECT s FROM Semestre s"),
    @NamedQuery(name = "Semestre.findById", query = "SELECT s FROM Semestre s WHERE s.id = :id"),
    @NamedQuery(name = "Semestre.findByLevel", query = "SELECT s FROM Semestre s WHERE s.level = :level"),
    @NamedQuery(name = "Semestre.findBySpecialite", query = "SELECT DISTINCT m.semestre FROM Module m WHERE m.specialite.id = :idSpecialite")
})
public class Semestre implements Serializable, Comparable<Semestre> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int level;
    @OneToMany(mappedBy = "semestre", cascade = {CascadeType.ALL})
    private List<Module> modules;

    public Semestre() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
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
        if (!(object instanceof Semestre)) {
            return false;
        }
        Semestre other = (Semestre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        switch (level) {
            case 7:
                level = 1;
                break;
            case 8:
                level = 2;
                break;
            case 9:
                level = 3;
                break;
            case 10:
                level = 4;
                break;
        }
        return level+"";
    }

    @Override
    public int compareTo(Semestre o) {
        return this.level - o.getLevel();
    }
    public static Comparator<Semestre> moyenneComparator = new Comparator<Semestre>() {
        @Override
        public int compare(Semestre semestre1, Semestre semestre2) {
            return semestre1.getLevel() > semestre2.getLevel() ? -1
                    : semestre1.getLevel() < semestre2.getLevel() ? 1
                    : 0;
        }
    };
}
