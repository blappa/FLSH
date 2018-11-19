/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import java.util.List;
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
@ManagedBean(name="inscriptionBean")
@RequestScoped
@ViewScoped
public class InscriptionBean implements Serializable {

    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    @ManagedProperty(value = "#{ISInscription}")
    private ISInscription insc;
    @ManagedProperty(value = "#{ISEtudiant}")
    private ISEtudiant etu;
    private Etudiant etudiant;
    private List<Etudiant> listEtudiant;
    private Inscription inscription;
    private List<Inscription> listInscription;
    private AnneeAcademique anneeAcademique;
    private List<AnneeAcademique> listAnneeAcademique;
    private Inscription selected;
    private List<Inscription> items;
    private Long anneeAcademiqueid;
    private Long etudiantId;
    private Long inscriptionId;
   private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
 
    public InscriptionBean() {

        selected = new Inscription();
        etudiant = new Etudiant();
        inscription = new Inscription();
        anneeAcademique = new AnneeAcademique();
    }

    public void saveNew(ActionEvent event) {
        /* try {
         etudiant = etu.findById(etudiantId);
         inscription = insc.findById(inscriptionId);
         anneeAcademique = anAc.findById(anneeAcademiqueid);
         selected.setEtudiant(etudiant);
         selected.setAnneeAcademique(anneeAcademique);
         selected.setInscription(inscription);
         histInsc.create(selected);
           logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"ajout de l'inscription de l'etudiant "+etudiant+ "Pour l'annee"+anneeAcademique);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
         } catch (ServiceException e) {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement impossible", ""));
         }*/
    }

    public void delete(ActionEvent event) {
        /*try {
         histInsc.delete(selected);
         logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"suppression de l'inscription de l'etudiant "+selected+);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
         } catch (ServiceException e) {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression impossible", ""));
         }*/
    }

    public void update(ActionEvent event) {
        /* try {
         etudiant = etu.findById(etudiantId);
         inscription = insc.findById(inscriptionId);
         anneeAcademique = anAc.findById(anneeAcademiqueid);
         selected.setEtudiant(etudiant);
         selected.setAnneeAcademique(anneeAcademique);
         selected.setInscription(inscription);
         Inscription h = histInsc.findById(selected.getId());
         selected.setId(h.getId());
         histInsc.update(selected);
         logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mis à jour de l'inscription de l'etudiant "+etudiant+ "Pour l'annee"+anneeAcademique);
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
         } catch (ServiceException e) {
         FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour impossible", ""));
         }*/
    }

    public List<Inscription> getItems() throws ServiceException {
        return insc.findAll();
    }

    public void prepare() {
        selected = new Inscription();
    }

    public Inscription getSelected() {
        return selected;
    }

    public void setSelected(Inscription selected) {
        this.selected = selected;
    }

    public Long getAnneeAcademiqueid() {
        return anneeAcademiqueid;
    }

    public void setAnneeAcademiqueid(Long anneeAcademiqueid) {
        this.anneeAcademiqueid = anneeAcademiqueid;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getInscriptionId() {
        return inscriptionId;
    }

    public void setInscriptionId(Long inscriptionId) {
        this.inscriptionId = inscriptionId;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public ISInscription getInsc() {
        return insc;
    }

    public void setInsc(ISInscription insc) {
        this.insc = insc;
    }

    public ISEtudiant getEtu() {
        return etu;
    }

    public void setEtu(ISEtudiant etu) {
        this.etu = etu;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public List<Etudiant> getListEtudiant() throws ServiceException {
        listEtudiant = etu.findAll();
        return listEtudiant;
    }

    public void setListEtudiant(List<Etudiant> listEtudiant) {
        this.listEtudiant = listEtudiant;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public List<Inscription> getListInscription() throws ServiceException {
        listInscription = insc.findAll();
        return listInscription;
    }

    public void setListInscription(List<Inscription> listInscription) {
        this.listInscription = listInscription;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public List<AnneeAcademique> getListAnneeAcademique() throws ServiceException {
        listAnneeAcademique = anAc.findAll();
        return listAnneeAcademique;
    }

    public void setListAnneeAcademique(List<AnneeAcademique> listAnneeAcademique) {
        this.listAnneeAcademique = listAnneeAcademique;
    }

    public String nom(int type) {
        String result = null;
        if (type == 1) {
            result = "premiere tranche";
        }
        if (type == 2) {
            result = "seconde tranche";
        }
        if (type == 3) {
            result = "totalite";
        }
        return result;
    }
}
