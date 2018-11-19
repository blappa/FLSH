/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISSection;
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
 * @author lappa
 */
@ManagedBean(name = "sectionBean")
@SessionScoped
@ViewScoped
public class SectionBean implements Serializable {

    @ManagedProperty(value = "#{ISSection}")
    private ISSection sec;
    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
    private Section selected;
    private List<Section> items;
    private List<Departement> listDep;
    private Long departementId;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());

    public SectionBean() {
        selected = new Section();
    }

    public void saveNew(ActionEvent event) {
        try {
            Departement d = dep.findById(departementId);
            selected.setDepartement(d);
            sec.create(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation de la section :" + selected);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }

    public void delete(ActionEvent event) {
        try {
            sec.delete(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de la section :" + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            Logger.getLogger(SectionBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        } catch (java.lang.IllegalArgumentException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        } catch (org.springframework.dao.DataIntegrityViolationException er) {
            JsfUtil.addErrorMessage("supprimez d'abord tous les niveaux de cette section!");
        }
    }

    public void update(ActionEvent event) {
        try {
            Section s = sec.findById(selected.getId());
            //Departement d=dep.findById(departementId);
            //selected.setDepartement(d);
            selected.setId(s.getId());
            sec.update(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour  de la section :" + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            Logger.getLogger(SectionBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public List<Section> getItems() throws ServiceException {
        if (items == null) {
            items = sec.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new Section();
    }

    public Section getSelected() {
        return selected;
    }

    public void setSelected(Section selected) {
        this.selected = selected;
    }

    public ISSection getSec() {
        return sec;
    }

    public void setSec(ISSection sec) {
        this.sec = sec;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }

    public List<Departement> getListDep() throws ServiceException {
        listDep = dep.findAll();
        return listDep;
    }

    public void setListDep(List<Departement> listDep) {
        this.listDep = listDep;
    }
}
