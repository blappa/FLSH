/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author koossery
 */
@ManagedBean(name = "departementBean")
@SessionScoped
@ViewScoped
public class DepartementBean implements Serializable {

    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
    private Departement selected;
    private List<Departement> items;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());

    public DepartementBean() {
        selected = new Departement();
    }

    public void saveNew(ActionEvent event) {
        try {
            dep.create(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation du departement  " + selected);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }

    public void delete(ActionEvent event) {
        try {
            dep.delete(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression  du departement  " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        } catch (java.lang.IllegalArgumentException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        } catch (org.springframework.dao.DataIntegrityViolationException er) {
            JsfUtil.addErrorMessage("supprimez d'abord toutes les sections de ce département!");
        }
    }

    public void update(ActionEvent event) {
        try {
            Departement d = dep.findById(selected.getId());
            selected.setId(d.getId());
            dep.update(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mis à jour   du departement  " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public List<Departement> getItems() throws ServiceException {
        if (items == null) {
            items = dep.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new Departement();
    }

    public Departement getSelected() {
        return selected;
    }

    public void setSelected(Departement selected) {
        this.selected = selected;
    }

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }
}
