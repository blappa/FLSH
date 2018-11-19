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

/*
 *
 * @author lappa
 */
@Entity
@Table(name = "utilisateur")
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findById", query = "select u from Utilisateur u where u.id=:id"),
    @NamedQuery(name = "Utilisateur.findByUsername", query = "SELECT a FROM Utilisateur a WHERE a.username = :username"),})
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    @Column(length = 600)
    private String password;
    @Column(length = 300)
    private String name;
    private Boolean enabled;
    @Column(columnDefinition = "boolean default false")
    private Boolean allyear;
    @Column(columnDefinition = "boolean default false")
    private Boolean master;
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Autorisation autorisation;
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Departement departement;

    public Utilisateur() {
        enabled=false;
        allyear=false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Autorisation getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(Autorisation autorisation) {
        this.autorisation = autorisation;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Boolean getAllyear() {
        return allyear;
    }

    public void setAllyear(Boolean allyear) {
        this.allyear = allyear;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    @Override
    public String toString() {
        String roleName="";
        switch (autorisation.getAuthority()) {
                case "ROLE_DOYEN":
                    roleName = "administrateur";
                    break;
                case "ROLE_INFOR":
                    roleName = "informaticien";
                    break;
                case "ROLE_SCOLAR":
                    roleName = "scolarité";
                    break;
                case "ROLE_DEP":
                    roleName = "departement";
                    break;
            }
        return " username= " + username + ", nom= " + name +" de droit d'acces "+roleName;
    }
    
    
    
    
}
