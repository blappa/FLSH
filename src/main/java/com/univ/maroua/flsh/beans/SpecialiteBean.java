/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lappa
 */
@ManagedBean(name="specialiteBean")
@SessionScoped
@ViewScoped
public class SpecialiteBean implements Serializable {
    
    @ManagedProperty(value = "#{ISSpecialte}")
    private ISSpecialite spe;
    @ManagedProperty(value = "#{ISNiveau}")
    private ISNiveau niv;
    @ManagedProperty(value = "#{ISSection}")
    private ISSection sec;
     @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
     @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
    
    private Niveau niveau;
    private List<Niveau> listNiv;
    private Specialite selected;
    private List<Specialite> items;
    private List<Departement> listDepartement;
    private Long niveauId;
    private Long departementId;
    
     private List<Niveau> niveaus;
    private List<Module> modules;
    private List<Specialite> specialites;
    private List<Section> sections;
 
    //pour desactiver les combo boxes au lancement
    private boolean sectionDisable = true;
    private boolean niveauDisable = true;
    private boolean specialiteDisable = true;
    //les valeurs des elements contenus dans la combobox
    private Long sectionId;
    private Long specialiteId;
    private Long niveauid;
    private int level;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
  
    
    public SpecialiteBean() {
        selected = new Specialite();
        niveau = new Niveau();
    }
    
    public void saveNew(ActionEvent event) {
        try {
            niveau = niv.findById(niveauId);
            selected.setNiveau(niveau);
            spe.create(selected);
             logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation de la specialite :"+selected);
           
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }
    
    public void saveNewSpecial(ActionEvent event) {
        /*try {
            Specialite sp=spe.findById(specialiteId);
            List<Module> modules=mod.findByAnneeAcSpecialite(niveauId, specialiteId)
            sp.se
            niveau = niv.findById(niveauId);
            selected.setNiveau(niveau);
            spe.create(selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }*/
    }
    
    public void delete(ActionEvent event) {
        try {
            spe.delete(selected);
             logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"suppression de la specialite :"+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        }catch (org.springframework.dao.DataIntegrityViolationException er){
            JsfUtil.addErrorMessage("supprimez d'abord tous les UE de cette specialité!");
       }
    }
    
    public void update(ActionEvent event) {
        try {
            Specialite s = spe.findById(selected.getId());
           // niveau = niv.findById(niveauId);
            selected.setId(s.getId());
           // selected.setNiveau(niveau);
            spe.update(selected);
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour  de la specialite :"+selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }
    
    public List<Specialite> getItems() throws ServiceException {
        if (items == null) {
            items = spe.findAll();
        }
        return items;
    }
    
    public void prepare() {
        selected = new Specialite();
    }
    
    public Specialite getSelected() {
        return selected;
    }
    
    public void setSelected(Specialite selected) {
        this.selected = selected;
    }
    
    public ISSpecialite getSpe() {
        return spe;
    }
    
    public void setSpe(ISSpecialite spe) {
        this.spe = spe;
    }
    
    public Long getNiveauId() {
        return niveauId;
    }
    
    public void setNiveauId(Long niveauId) {
        this.niveauId = niveauId;
    }
    
    public ISNiveau getNiv() {
        return niv;
    }
    
    public void setNiv(ISNiveau niv) {
        this.niv = niv;
    }
    
    public Niveau getNiveau() {
        return niveau;
    }
    
    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }
    
    public List<Niveau> getListNiv() {
        try {
            listNiv = niv.findAll();
            return listNiv;
        } catch (ServiceException ex) {
            Logger.getLogger(SpecialiteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void setListNiv(List<Niveau> listNiv) {
        this.listNiv = listNiv;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }
    
      public void processDepartement() throws ServiceException {
        sectionDisable = false;
        niveauDisable = true;
        specialiteDisable = true;
        getSections();
    }

    public void processSection() throws ServiceException {
        niveauDisable = false;
        specialiteDisable = true;
        getNiveaus();
    }

    public void processNiveau() throws ServiceException {
        specialiteDisable = false;
        getSpecialites();
    }
    
     public List<Niveau> getNiveaus() throws ServiceException {

        try {

            niveaus = niv.findBySection(sectionId);
        } catch (NullPointerException ex) {

            niveaus = new ArrayList<Niveau>();
        }

        return niveaus;
    }
     
     public List<Specialite> getSpecialites() throws ServiceException {

        try {
            specialites = spe.findByNiveau((long)level);
        } catch (NullPointerException ex) {

            specialites = new ArrayList<Specialite>();
        }
        return specialites;
    }

    public List<Section> getSections() throws ServiceException {

        try {

            sections = sec.findByDepartement(departementId);
            //this.niveauDisable = false;
        } catch (NullPointerException ex) {

            sections = new ArrayList<Section>();
        }

        return sections;
    }
    
     public List<Departement> getListDepartement() throws ServiceException {
        listDepartement = dep.findAll();
        return listDepartement;
    }

    public ISSection getSec() {
        return sec;
    }

    public void setSec(ISSection sec) {
        this.sec = sec;
    }

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    public Long getNiveauid() {
        return niveauid;
    }

    public void setNiveauid(Long niveauid) {
        this.niveauid = niveauid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isSectionDisable() {
        return sectionDisable;
    }

    public void setSectionDisable(boolean sectionDisable) {
        this.sectionDisable = sectionDisable;
    }

    public boolean isNiveauDisable() {
        return niveauDisable;
    }

    public void setNiveauDisable(boolean niveauDisable) {
        this.niveauDisable = niveauDisable;
    }

    public boolean isSpecialiteDisable() {
        return specialiteDisable;
    }

    public void setSpecialiteDisable(boolean specialiteDisable) {
        this.specialiteDisable = specialiteDisable;
    }

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }
     
     

}
