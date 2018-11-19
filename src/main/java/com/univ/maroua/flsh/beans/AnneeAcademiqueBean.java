/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISUtilisateur;
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

/**
 *
 * @author koossery
 */
@ManagedBean(name = "anneeAcademiqueBean")
@SessionScoped
@ViewScoped
public class AnneeAcademiqueBean implements Serializable {

    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    @ManagedProperty(value = "#{ISUtilisateur}")
    private ISUtilisateur uti;
    private AnneeAcademique selected;
    private List<AnneeAcademique> items;
    private Long selectedId;
    private Long anneId;
    private int typeOperation;
    private Logger logger = Logger.getLogger(this.getClass().getName());
   
    public AnneeAcademiqueBean() {
        selected = new AnneeAcademique();
    }

    public void saveNew(ActionEvent event) {
        try {
            selected.setRank(0L);
            selected.setQuitus(0L);
            anAc.create(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation de l'année academique: " + selected);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement impossible", ""));
        }
    }

    public void delete(ActionEvent event) {
        try {
            anAc.delete(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de l'année academique: " + selected);
       FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression impossible", ""));
        } catch (java.lang.IllegalArgumentException e) {
            java.util.logging.Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        }catch (org.springframework.dao.DataIntegrityViolationException er){
            JsfUtil.addErrorMessage("supprimez d'abord toutes les matieres de cette annee!");
        }
    }

    public void update(ActionEvent event) {
        try {
            AnneeAcademique a = anAc.findById(selected.getId());
            selected.setId(a.getId());
            anAc.update(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour de l'année academique: " + selected);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour impossible", ""));
        }
    }

    public List<AnneeAcademique> getItems() throws ServiceException {
        if (items == null) {
            items = anAc.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new AnneeAcademique();
    }

    public AnneeAcademique getSelected() {
        return selected;
    }

    public void setSelected(AnneeAcademique selected) {
        this.selected = selected;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public Long getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(Long selectedId) {
        this.selectedId = selectedId;
    }

    public int getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(int typeOperation) {
        this.typeOperation = typeOperation;
    }

    public void doSelect(ActionEvent actionEven) throws ServiceException {
        Utilisateur utilisateur = uti.findByUsername(UserUtil.getUsername());
        if (typeOperation == 1) {
            utilisateur.setAllyear(Boolean.TRUE);
            uti.update(utilisateur);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "basculement pour toutes les annees");
        } else {
            utilisateur.setAllyear(Boolean.FALSE);
            uti.update(utilisateur);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "basculement a l'annee courante");
        }
        JsfUtil.addSuccessMessage(" Operation faite avec succes ");
    }

    public void doSetTarget(ActionEvent actionEven) throws ServiceException {
        List<AnneeAcademique> anneeAcademiques = getItems();
        for (AnneeAcademique anneeAcademique : anneeAcademiques) {
            if (anneeAcademique.getIstargetyear() == true) {
                anneeAcademique.setIstargetyear(Boolean.FALSE);
                anAc.update(anneeAcademique);
            }
        }
        AnneeAcademique an = anAc.findById(anneId);
        an.setIstargetyear(Boolean.TRUE);
        anAc.update(an);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "changement de l'annee courante. la nouvelle année est "+an);
        JsfUtil.addSuccessMessage(" Operation faite avec succes ");
    }

    public ISUtilisateur getUti() {
        return uti;
    }

    public void setUti(ISUtilisateur uti) {
        this.uti = uti;
    }

    public Long getAnneId() {
        return anneId;
    }

    public void setAnneId(Long anneId) {
        this.anneId = anneId;
    }
}
