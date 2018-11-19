/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lappa
 */
@ManagedBean(name = "matiereBean")
@SessionScoped
@ViewScoped
public class MatiereBean implements Serializable {

    @ManagedProperty(value = "#{ISMatiere}")
    private ISMatiere mat;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
    private Matiere selected;
    private List<Matiere> items;
    private Module module;
    private List<Module> listSpe;
    private Long moduleId;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());

    public MatiereBean() {
        selected = new Matiere();
    }

    public void saveNew(ActionEvent event) {
        try {

            module = mod.findById(moduleId);
            selected.setModule(module);
            mat.create(selected);
            String intitule = module.getIntitule();
            intitule = intitule + "/" + selected.getIntitule();
            intitule = intitule.substring(1);
            module.setIntitule(intitule);
            mod.update(module);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation de la  matiere" + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }

    public void delete(ActionEvent event) {//lors de la supression il faut suprimer l'intitule dans le module
        try {
            Matiere m = mat.findById(selected.getId());
            mat.delete(m);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de la matiere" + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException | IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible contacter l'administrateur ", ""));
        } catch (org.springframework.dao.DataIntegrityViolationException er){
           JsfUtil.addErrorMessage("supprimez d'abord toutes les notes de cette matiere!");
       }
    }

    public void update(ActionEvent event) {
        try {
            //module = mod.findById(moduleId);
            //selected.setModule(module);
            Matiere m = mat.findById(selected.getId());
            //selected.setId(m.getId());
            //mat.update(selected);
            m.setIntitule(selected.getIntitule());
            m.setCode(selected.getCode());
            mat.update(m);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour de la   matiere" + m);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public List<Matiere> getItems() throws ServiceException {
        if (items == null) {
            items = mat.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new Matiere();
    }

    public Matiere getSelected() {
        return selected;
    }

    public void setSelected(Matiere selected) {
        this.selected = selected;
    }

    public ISMatiere getMat() {
        return mat;
    }

    public void setMat(ISMatiere mat) {
        this.mat = mat;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public ISModule getSpe() {
        return mod;
    }

    public void setSpe(ISModule mod) {
        this.mod = mod;
    }

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }

    public List<Module> getListSpe() {
        try {
            listSpe = mod.findAll();
            return listSpe;
        } catch (ServiceException ex) {
            Logger.getLogger(MatiereBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setListSpe(List<Module> listSpe) {
        this.listSpe = listSpe;
    }
}
