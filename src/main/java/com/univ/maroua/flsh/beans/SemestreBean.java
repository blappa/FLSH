/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.service.ISSemestre;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author koossery
 */
@ManagedBean(name="semestreBean")
@SessionScoped
@ViewScoped
public class SemestreBean implements Serializable {

    @ManagedProperty(value = "#{ISSemestre}")
    private ISSemestre sem;
    private Semestre selected ;
    private List<Semestre> items;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
  
    public SemestreBean() {
        selected = new Semestre();
    }

     
    public void saveNew(ActionEvent event) {
        try {
            sem.create(selected);
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation du semestre :"+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement impossible", ""));
        }
    }
    
    
   public void delete(ActionEvent event) {
        try {
            sem.delete(selected);
              logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"suppression du semestre :"+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression impossible", ""));
        } catch (java.lang.IllegalArgumentException e) {
            java.util.logging.Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        }catch (org.springframework.dao.DataIntegrityViolationException er){
            JsfUtil.addErrorMessage("supprimez toutes les matieres concernées par ce semestre!");
        }
    }

    public void update(ActionEvent event) {
        try {
            Semestre se=sem.findById(selected.getId());
            selected.setId(se.getId());
            sem.update(selected);
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour  du semestre :"+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour impossible", ""));
        }
    }
    
    public List<Semestre> getItems() throws ServiceException {
        if (items == null) {
            items = sem.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new Semestre();
    }
    
    public Semestre getSelected() {
        return selected;
    }

    public void setSelected(Semestre selected) {
        this.selected = selected;
    }

    public ISSemestre getSem() {
        return sem;
    }

    public void setSem(ISSemestre sem) {
        this.sem = sem;
    }

}
