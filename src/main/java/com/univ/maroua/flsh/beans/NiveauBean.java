/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISNiveau;
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
@ManagedBean(name="niveauBean")
@SessionScoped
@ViewScoped
public class NiveauBean implements Serializable {

    @ManagedProperty(value = "#{ISNiveau}")
    private ISNiveau niv;
    @ManagedProperty(value = "#{ISSection}")
    private ISSection sec;
    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
    private Departement selectedDepartement;
    private List<Departement> listeDepartement;
    private Long depatementID;
    private Section section;
    private List<Section> listSec;
    private List<Section> DepartSections;
    private Niveau selected;
    private List<Niveau> items;
    private Long sectionId;
    private boolean sectionDisable = true;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
   
    public NiveauBean() {
        selected = new Niveau();
        section = new Section();
    }

    public void saveNew(ActionEvent event) {
        try {
            section = sec.findById(sectionId);
            selected.setSection(section);
            niv.create(selected);
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation du niveau : "+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }

    public void delete(ActionEvent event) {
        try {
            niv.delete(selected);
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"suppression du niveau : "+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        }catch (org.springframework.dao.DataIntegrityViolationException er){
           JsfUtil.addErrorMessage("supprimez d'abord tous les semestres de ce niveau!");
       }
    }

    public void update(ActionEvent event) {
        try {
            Niveau n = niv.findById(selected.getId());
            section = sec.findById(sectionId);
            selected.setId(n.getId());
            selected.setSection(section);
            niv.update(selected);
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour du niveau : "+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public List<Niveau> getItems() throws ServiceException {
        if (items == null) {
            items = niv.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new Niveau();
    }

    public Niveau getSelected() {
        return selected;
    }

    public void setSelected(Niveau selected) {
        this.selected = selected;
    }

    public ISNiveau getNiv() {
        return niv;
    }

    public void setNiv(ISNiveau niv) {
        this.niv = niv;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public ISSection getSec() {
        return sec;
    }

    public void setSec(ISSection sec) {
        this.sec = sec;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Section> getListSec() {
        try {
            listSec = sec.findAll();
            return listSec;
        } catch (ServiceException ex) {
            Logger.getLogger(NiveauBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void setListSec(List<Section> listSec) {
        this.listSec = listSec;
    }

    public Departement getSelectedDepartement() {
        return selectedDepartement;
    }

    public void setSelectedDepartement(Departement selectedDepartement) {
        this.selectedDepartement = selectedDepartement;
    }

    public List<Departement> getListeDepartement() throws ServiceException {
        return listeDepartement = dep.findAll();
    }

    public void setListeDepartement(List<Departement> listeDepartement) {
        this.listeDepartement = listeDepartement;
    }

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }

    public Long getDepatementID() {
        return depatementID;
    }

    public void setDepatementID(Long depatementID) {
        this.depatementID = depatementID;
    }

    public boolean isSectionDisable() {
        return sectionDisable;
    }

    public void setSectionDisable(boolean sectionDisable) {
        this.sectionDisable = sectionDisable;
    }

    public void processSection() throws ServiceException {
          sectionDisable = false;
        //  System.out.println("=== je suis de passage dans le filtre======="+depatementID);
          
           System.out.println("=========="+getDepartSections().toString());
         getDepartSections();
         
    }

    public List<Section> getDepartSections() throws ServiceException {
       
        return sec.findByDepartement(depatementID);
    }

    public void setDepartSections(List<Section> DepartSection) {
        this.DepartSections = DepartSection;
    }
}
