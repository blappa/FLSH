/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.MatiereOptionnelle;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISMatiereOptionnelle;
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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lappa
 */
@ManagedBean(name="matiereOptionnelleBean")
@RequestScoped
@ViewScoped
public class MatiereOptionnelleBean implements Serializable {

    @ManagedProperty(value = "#{ISMatiere}")
    private ISMatiere mat;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
    @ManagedProperty(value = "#{ISEtudiant}")
    private ISEtudiant etu;
    @ManagedProperty(value = "#{ISMatiereOptionnelle}")
    private ISMatiereOptionnelle modOp;
    private Etudiant etudiant;
    private Matiere matiere;
     private Module module;
    private List<Etudiant> listEtu;
    private List<Matiere> listMat;
    private MatiereOptionnelle selected;
    private List<MatiereOptionnelle> items;
    private Long etudiantId;
    private Long matiereId;
    private String matricule;
    private String mat1;
    private String mat2;
    private String mat3;
    private String mat4;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    public MatiereOptionnelleBean() {
        selected = new MatiereOptionnelle();
        etudiant = new Etudiant();
        matiere = new Matiere();
    }

    public void saveNew(ActionEvent event) {
//        System.out.println(""+matiereId+" et "+etudiantId);
        try {
            try {
//            etudiant = etu.findById(etudiantId);
//            matiere = mat.findById(matiereId);
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat1));
                modOp.create(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
            }
            try {
                selected = new MatiereOptionnelle();
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat2));
                modOp.create(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
            }
            try {
                selected = new MatiereOptionnelle();
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat3));
                modOp.create(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
            }
            try {
                selected = new MatiereOptionnelle();
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat4));
                modOp.create(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
             //   FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (javax.persistence.NonUniqueResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les Modules Possedent les meme codes! contactez administrateur", ""));
        }
    }

    public void delete(ActionEvent event) {
        try {
            modOp.delete(selected);
             logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"suppression d'une matiere optionnelle: "+selected);
        } catch (ServiceException ex) {
            Logger.getLogger(MatiereOptionnelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
    }

    public void update(ActionEvent event) {
         try {
            try {
//            etudiant = etu.findById(etudiantId);
//            matiere = mat.findById(matiereId);
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat1));
                modOp.update(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
            }
            try {
                selected = new MatiereOptionnelle();
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat2));
                modOp.update(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
            }
            try {
                selected = new MatiereOptionnelle();
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat3));
                modOp.update(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
            }
            try {
                selected = new MatiereOptionnelle();
                selected.setEtudiant(etu.findByMatricule(matricule));
                selected.setMatiere(mat.findByCode(mat4));
                modOp.update(selected);
                 logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour d'une matiere optionnelle: "+selected);
            } catch (ServiceException e) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
            } catch (NullPointerException e) {
            } catch (NoResultException e) {
              //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (javax.persistence.NonUniqueResultException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les Modules Possedent les meme codes! contactez administrateur", ""));
        }
    }

    public List<MatiereOptionnelle> getItems() throws ServiceException {
        if (items == null) {
            items = modOp.findAll();
        }
        return items;
    }

    public void prepare() {
        selected = new MatiereOptionnelle();
    }

    public MatiereOptionnelle getSelected() {
        return selected;
    }

    public void setSelected(MatiereOptionnelle selected) {
        this.selected = selected;
    }

    public ISMatiere getMat() {
        return mat;
    }

    public void setMat(ISMatiere mat) {
        this.mat = mat;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getMatiereOptionnelleId() {
        return matiereId;
    }

    public void setMatiereOptionnelleId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public ISEtudiant getSem() {
        return etu;
    }

    public void setSem(ISEtudiant etu) {
        this.etu = etu;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public ISEtudiant getEtu() {
        return etu;
    }

    public void setEtu(ISEtudiant etu) {
        this.etu = etu;
    }

    public ISMatiereOptionnelle getModOp() {
        return modOp;
    }

    public void setModOp(ISMatiereOptionnelle modOp) {
        this.modOp = modOp;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public List<Etudiant> getListEtu() {
        try {
            listEtu = etu.findAll();
        } catch (ServiceException ex) {
            Logger.getLogger(MatiereOptionnelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listEtu;
    }

    public void setListEtu(List<Etudiant> listEtu) {
        this.listEtu = listEtu;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public List<Matiere> getListMat() {
        try {
            listMat = mat.findAll();
        } catch (ServiceException ex) {
            Logger.getLogger(MatiereOptionnelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMat;
    }

    public void setListMat(List<Matiere> listMat) {
        this.listMat = listMat;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getMat1() {
        return mat1;
    }

    public void setMat1(String mat1) {
        this.mat1 = mat1;
    }

    public String getMat2() {
        return mat2;
    }

    public void setMat2(String mat2) {
        this.mat2 = mat2;
    }

    public String getMat3() {
        return mat3;
    }

    public void setMat3(String mat3) {
        this.mat3 = mat3;
    }

    public String getMat4() {
        return mat4;
    }

    public void setMat4(String mat4) {
        this.mat4 = mat4;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }
    
}
