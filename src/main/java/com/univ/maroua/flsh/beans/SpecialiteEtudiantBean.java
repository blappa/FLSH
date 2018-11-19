/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lappa
 */
@ManagedBean(name="specialiteEtudiantBean")
@RequestScoped
@ViewScoped
public class SpecialiteEtudiantBean implements Serializable {
    
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant speEt;
    @ManagedProperty(value = "#{ISEtudiant}")
    private ISEtudiant etu;
    @ManagedProperty(value = "#{ISSpecialte}")
    private ISSpecialite spe;
    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    private AnneeAcademique annee;
    private List<AnneeAcademique> listAnnees;
    private Etudiant etudiant;
    private List<Etudiant> listEtudiant;
    private Specialite specialite;
    private List<Specialite> listSpecialite;
    private SpecialiteEtudiant selected;
    private List<SpecialiteEtudiant> items;
    private Long specialiteId;
    private Long etudiantId;
    private Long anneeId;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
  
    public SpecialiteEtudiantBean() {
        selected = new SpecialiteEtudiant();
        etudiant = new Etudiant();
        annee = new AnneeAcademique();
        specialite = new Specialite();
    }
    
    public void saveNew(ActionEvent event) {
        try {
            annee = anAc.findById(anneeId);
            etudiant = etu.findById(etudiantId);
            specialite = spe.findById(specialiteId);
            selected.setEtudiant(etudiant);
            selected.setSpecialite(specialite);
            selected.setAnneeAcademique(annee);
            speEt.create(selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement impossible", ""));
        }
    }
    
    public void delete(ActionEvent event) {
        try {
            speEt.delete(selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression impossible", ""));
        }
    }
    
    public void update(ActionEvent event) {
        try {
            annee = anAc.findById(anneeId);
           
           etudiant = etu.findById(etudiantId);
           specialite = spe.findById(specialiteId);
             selected.setEtudiant(etudiant);
            selected.setSpecialite(specialite);
            selected.setAnneeAcademique(annee);
            Specialite s = spe.findById(selected.getSpecialite().getId());
            selected.setId(s.getId());
             //System.out.println("=======specialite========="+selected);
            speEt.update(selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour impossible", ""));
        }
    }
    
    public List<SpecialiteEtudiant> getItems() throws ServiceException {
        int i = 0;
        if (items == null) {
            items = speEt.findAll();
        }
//        if (items.isEmpty()) {
//            for (i = 0; i < 15; i++) {
//                items.add(selected);
//            }
//        }if (items.size()<15) {
//            for (i = 0; i < 15-items.size(); i++) {
//                items.add(selected);
//            }
//        }
        return items;
    }
    
    public void prepare() {
        selected = new SpecialiteEtudiant();
    }
    
    public SpecialiteEtudiant getSelected() {
        return selected;
    }
    
    public void setSelected(SpecialiteEtudiant selected) {
        this.selected = selected;
    }
    
    public ISSpecialiteEtudiant getSpeEt() {
        return speEt;
    }
    
    public void setSpeEt(ISSpecialiteEtudiant speEt) {
        this.speEt = speEt;
    }
    
    public Long getSpecialiteId() {
        return specialiteId;
    }
    
    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }
    
    public Long getEtudiantId() {
        return etudiantId;
    }
    
    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }
    
    public Long getAnneeId() {
        return anneeId;
    }
    
    public void setAnneeId(Long anneeId) {
        this.anneeId = anneeId;
    }
    
    public ISEtudiant getEtu() {
        return etu;
    }
    
    public void setEtu(ISEtudiant etu) {
        this.etu = etu;
    }
    
    public ISSpecialite getSpe() {
        return spe;
    }
    
    public void setSpe(ISSpecialite spe) {
        this.spe = spe;
    }
    
    public Etudiant getEtudiant() {
        return etudiant;
    }
    
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }
    
    public List<Etudiant> getListEtudiant() {
        try {
            listEtudiant = etu.findAll();
            return listEtudiant;
        } catch (ServiceException ex) {
            Logger.getLogger(SpecialiteEtudiantBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setListEtudiant(List<Etudiant> listEtudiant) {
        this.listEtudiant = listEtudiant;
    }
    
    public Specialite getSpecialite() {
        return specialite;
    }
    
    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }
    
    public List<Specialite> getListSpecialite() {
        try {
            listSpecialite = spe.findAll();
            return listSpecialite;
        } catch (ServiceException ex) {
            Logger.getLogger(SpecialiteEtudiantBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setListSpecialite(List<Specialite> listSpecialite) {
        this.listSpecialite = listSpecialite;
    }
    
    public ISAnneeAcademique getAnAc() {
        return anAc;
    }
    
    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }
    
    public AnneeAcademique getAnnee() {
        return annee;
    }
    
    public void setAnnee(AnneeAcademique annee) {
        this.annee = annee;
    }
    
    public List<AnneeAcademique> getListAnnees() throws ServiceException {
        listAnnees = anAc.findAll();
        return listAnnees;
    }
    
    public void setListAnnees(List<AnneeAcademique> listAnnees) {
        this.listAnnees = listAnnees;
    }
}
