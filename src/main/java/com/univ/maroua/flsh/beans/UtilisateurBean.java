/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Autorisation;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.service.ISAutorisation;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lappa
 */
@ManagedBean(name = "utilisateurBean")
@SessionScoped
@ViewScoped
public class UtilisateurBean implements Serializable {

    @ManagedProperty(value = "#{ISUtilisateur}")
    private ISUtilisateur uti;
    @ManagedProperty(value = "#{ISAutorisation}")
    private ISAutorisation aut;
    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
    private Departement departement;
    private Utilisateur selected;
    private Autorisation auto;
    private List<Autorisation> autos;
    private List<Utilisateur> items;
    private Long departementId;
    private String etat;
    private String pwdmd5;
    private Long idAutority;
    private List<Departement> departements;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private String password;
    private Map<String, Long> roles;

    public UtilisateurBean() {
        selected = new Utilisateur();
        auto = new Autorisation();
        departement = new Departement();
    }

    public void saveNew(ActionEvent event) {
        if (!selected.getPassword().equals(password)) {
            JsfUtil.addErrorMessage("Les mots de passe sont différents");
            return;
        }
        SygePasswordEncoder encoder = new SygePasswordEncoder();
        selected.setPassword(encoder.encode(selected.getPassword()));

        try {
            try {
                auto = aut.findById(idAutority);
                if (auto.getAuthority().equals("ROLE_DEP")) {
                    if (departementId == 0) {
                        JsfUtil.addErrorMessage("Vous devez selectionner un département");
                        return;
                    }
                    departement = dep.findById(departementId);
                    selected.setDepartement(departement);
                }
                selected.setAutorisation(auto);
                if (etat.equals("0")) {
                    selected.setEnabled(false);
                } else {
                    selected.setEnabled(true);
                }
            } catch (NoResultException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autorisation introuvable", ""));
            }
            try {
                uti.create(selected);
            } catch (EntityExistsException ee) {
                JsfUtil.addErrorMessage("Le UserUtilname est deja pris! changez le!");
                return;
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation de l'utilisateur :" + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Duplicate entry 'admin' for key 'PASSWORD'", ""));
//        } catch (NoSuchAlgorithmException ex) {
//           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors du cryptage de votre mot de passe", ""));
//        }
        }
    }

    public void delete(ActionEvent event) throws ServiceException {
        try {
            Utilisateur UserUtil = uti.findById(selected.getId());
            uti.delete(UserUtil);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de l'utilisateur :" + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (java.lang.NullPointerException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de suppression! 'matricule' inconnu", ""));
        } catch (java.lang.IllegalArgumentException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        }
    }

    public void togleState(ActionEvent event) throws ServiceException {
        try {
            Utilisateur UserUtil = uti.findById(selected.getId());
            if (UserUtil.getEnabled() == true) {
                     logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "activation de l'utilisateur :" + selected);
        
                UserUtil.setEnabled(false);
                JsfUtil.addSuccessMessage("cet utilisateur a été désactivé");
                uti.update(UserUtil);
            } else {
                     logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "desactivation de l'utilisateur :" + selected);
        
                UserUtil.setEnabled(true);
                uti.update(UserUtil);
                JsfUtil.addSuccessMessage("cet utilisateur a été activé");

            }
       } catch (java.lang.NullPointerException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de suppression! 'matricule' inconnu", ""));
        } catch (java.lang.IllegalArgumentException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        }
    }

    public void resetPassword(ActionEvent event) throws ServiceException {
        try {
            Utilisateur UserUtil = uti.findById(selected.getId());
            String newPassword = "sygeuser";
            SygePasswordEncoder spe = new SygePasswordEncoder();
            newPassword = spe.encode(newPassword);
            UserUtil.setPassword(newPassword);
            uti.update(UserUtil);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "reinitialisation du mot de passe de l'utilisateur :" + selected);
            JsfUtil.addSuccessMessage("le mot de passe de cet utilisateur a été réinitialisé");

        } catch (java.lang.NullPointerException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur de suppression! 'matricule' inconnu", ""));
        } catch (java.lang.IllegalArgumentException e) {
            Logger.getLogger(DepartementBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (contacter le super UserUtil)", ""));
        }
    }

    public void update(ActionEvent event) {
        try {
            try {
                auto = aut.findById(idAutority);
                if (auto.getAuthority().equals("ROLE_DEP")) {
                    if (departementId == 0) {
                        JsfUtil.addErrorMessage("Vous devez selectionner un département");
                        return;
                    }
                }
                departement = dep.findById(departementId);
                selected.setDepartement(departement);
                selected.setAutorisation(auto);
                if (etat.equals("0")) {
                    selected.setEnabled(false);
                } else {
                    selected.setEnabled(true);
                }
            } catch (NoResultException e) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Autorisation introuvable", ""));
            }

            // System.out.println("==== "+selected+"===== "+selected);
            // selected.setUtilisateur(u);

            SygePasswordEncoder encoder = new SygePasswordEncoder();
            selected.setPassword(encoder.encode(selected.getPassword()));
            uti.update(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour des informations de l'utilisateur :" + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            Logger.getLogger(UtilisateurBean.class.getName()).log(Level.SEVERE, null, e);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public List<Utilisateur> getItems() throws ServiceException {
        List<Utilisateur> data = new LinkedList<>();
        items = uti.findAll();
        if (items == null) {
            items = new LinkedList<>();
        }
        Utilisateur u = uti.findByUsername(UserUtil.getUsername());
        for (Utilisateur utilisateur : items) {
            if (utilisateur.getUsername().equals(u.getUsername())) {
                data.add(utilisateur); //on ne s'affiche pas dans le datatable
            }
            if (u.getAutorisation().getAuthority().equals("ROLE_INFOR")) {
                if (utilisateur.getAutorisation().getAuthority().equals("ROLE_INFOR") || utilisateur.getAutorisation().getAuthority().equals("ROLE_DOYEN")) {
                    data.add(utilisateur);
                }
            }

            if (u.getAutorisation().getAuthority().equals("ROLE_DOYEN")) {
                if (u.getMaster() == null) {
                    u.setMaster(false);
                }
                if (u.getMaster() == false) {
                    if (utilisateur.getAutorisation().getAuthority().equals("ROLE_DOYEN")) {
                        data.add(utilisateur);
                    }
                }
            }
        }

        for (Utilisateur utilisateur : data) {
            items.remove(utilisateur);
        }
        return items;
    }

    public void prepare() {
        selected = new Utilisateur();
    }

    public String compte(boolean b) {
        if (b == true) {
            return "Actif";
        }
        return "Inactif";
    }

    public Utilisateur getSelected() {
        return selected;
    }

    public void setSelected(Utilisateur selected) {
        this.selected = selected;
    }

    public ISUtilisateur getUti() {
        return uti;
    }

    public void setUti(ISUtilisateur uti) {
        this.uti = uti;
    }

    public ISAutorisation getAut() {
        return aut;
    }

    public void setAut(ISAutorisation aut) {
        this.aut = aut;
    }

    public Autorisation getAuto() {
        return auto;
    }

    public void setAuto(Autorisation auto) {
        this.auto = auto;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public List<Autorisation> getAutos() throws ServiceException {
        autos = aut.findByAll();
        return autos;
    }

    public void setAutos(List<Autorisation> autos) {
        this.autos = autos;
    }

    public Long getIdAutority() {
        return idAutority;
    }

    public void setIdAutority(Long idAutority) {
        this.idAutority = idAutority;
    }

    public static String getEncodedMD5(String key) throws NoSuchAlgorithmException {
        byte[] uniqueKey = key.getBytes();
        byte[] hash = null;
        hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }

    public String getPwdmd5() {
        return pwdmd5;
    }

    public void setPwdmd5(String pwdmd5) {
        this.pwdmd5 = pwdmd5;
    }

   

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public List<Departement> getDepartements() throws ServiceException {
        departements = dep.findAll();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public void setItems(List<Utilisateur> items) {
        this.items = items;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, Long> getRoles() throws ServiceException {
        roles = new HashMap<>();
        Utilisateur u = uti.findByUsername(UserUtil.getUsername());
        String roleName = "";
        for (Autorisation au : getAutos()) {
            switch (au.getAuthority()) {
                case "ROLE_DOYEN":
                    roleName = "administrateur";
                    break;
                case "ROLE_INFOR":
                    roleName = "informaticien";
                    break;
                case "ROLE_SCOLAR":
                    roleName = "scolarité";
                    break;
                case "ROLE_DEP":
                    roleName = "departement";
                    break;
            }
            if (!u.getAutorisation().getAuthority().equals("ROLE_DOYEN")) {
                if (au.getAuthority().equals("ROLE_DOYEN") || au.getAuthority().equals("ROLE_INFOR")) {
                    continue;
                }
            }
            roles.put(roleName, au.getId());
        }

        return roles;
    }

    public String convertRole(String role) throws ServiceException {
        String result = "";
        switch (role) {
            case "ROLE_DOYEN":
                result = "administrateur";
                break;
            case "ROLE_INFOR":
                result = "informaticien";
                break;
            case "ROLE_SCOLAR":
                result = "scolarité";
                break;
            case "ROLE_DEP":
                result = "departement";
                break;
        }
        return result;
    }
}
