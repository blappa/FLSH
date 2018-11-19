/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISSemestre;
import com.univ.maroua.flsh.service.ISSpecialite;
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
@ManagedBean(name = "moduleBean")
@SessionScoped
@ViewScoped
public class ModuleBean implements Serializable {

    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    @ManagedProperty(value = "#{ISSemestre}")
    private ISSemestre sem;
    @ManagedProperty(value = "#{ISSpecialte}")
    private ISSpecialite spec;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
    private Specialite specialite;
    private List<Specialite> listSpecialite;
    private List<AnneeAcademique> annees;
    private Semestre semestre;
    private List<Semestre> listSem;
    private Module selected;
    private List<Module> items;
    private Long specialiteId;
    private Long semestreId;
    private Long anneeId;
    private String type;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());

    public ModuleBean() {
        selected = new Module();
        specialite = new Specialite();
    }

    public void saveNew(ActionEvent event) {
        try {
            semestre = sem.findById(semestreId);
            specialite = spec.findById(specialiteId);
            List<Semestre> sems = sem.findBySpecialite(specialiteId);
            boolean found = false;
            for (Semestre semestre1 : sems) {
                if (semestre1.getId().equals(semestre.getId())) {
                    found = true;
                    return;
                }
            }
            if (!found) {
                JsfUtil.addErrorMessage("le semestre choisit n'appartient pas a cette specialite!");
                return;
            }
            selected.setAnneeAcademique(anAc.findById(anneeId));
            selected.setSemestre(semestre);
            selected.setSpecialite(specialite);
            mod.create(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation du module : " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }

    public void delete(ActionEvent event) {
        try {
            mod.delete(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression du module : " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        } catch (java.lang.IllegalArgumentException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        }catch (org.springframework.dao.DataIntegrityViolationException er){
            JsfUtil.addErrorMessage("supprimez d'abord toutes les matieres de cette UE!");
        }
    }

    public void update(ActionEvent event) {
        try {
            Module m = mod.findById(selected.getId());
            semestre = sem.findById(m.getSemestre().getId());
            specialite = spec.findById(m.getSpecialite().getId());
            m.setAnneeAcademique(anAc.findById(m.getAnneeAcademique().getId()));
            m.setSemestre(semestre);
            m.setSpecialite(specialite);
            m.setId(selected.getId());
//            mat.update(selected);
            m.setIntitule(selected.getIntitule());
            m.setTargetCode(selected.getTargetCode());
            m.setCredit(selected.getCredit());
            m.setCode(selected.getCode());
            mod.update(m);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à  jour du module : " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "l'unité d'enseignement a été mis a jour", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public List<Module> getItems() throws ServiceException {
        if (items == null) {
            items = mod.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new Module();
    }

    public ISSpecialite getSpec() {
        return spec;
    }

    public void setSpec(ISSpecialite spec) {
        this.spec = spec;
    }

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public List<Specialite> getListSpecialite() {
        try {
            listSpecialite = spec.findAll();
        } catch (ServiceException ex) {
            Logger.getLogger(ModuleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSpecialite;
    }

    public void setListSpecialite(List<Specialite> listSpecialite) {
        this.listSpecialite = listSpecialite;
    }

    public Module getSelected() {
        return selected;
    }

    public void setSelected(Module selected) {
        this.selected = selected;
    }

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public ISSemestre getSem() {
        return sem;
    }

    public void setSem(ISSemestre sem) {
        this.sem = sem;
    }

    public List<AnneeAcademique> getAnnees() throws ServiceException {
        annees = anAc.findAll();
        return annees;
    }

    public void setAnnees(List<AnneeAcademique> annees) {
        this.annees = annees;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public List<Semestre> getListSem() throws ServiceException {
        listSem = sem.findAll();
        return listSem;
    }

    public void setListSem(List<Semestre> listSem) {
        this.listSem = listSem;
    }

    public Long getSemestreId() {
        return semestreId;
    }

    public void setSemestreId(Long semestreId) {
        this.semestreId = semestreId;
    }

    public Long getAnneeId() {
        return anneeId;
    }

    public void setAnneeId(Long anneeId) {
        this.anneeId = anneeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
