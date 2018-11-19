/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.Autorisation;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.service.ISAutorisation;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lappa
 */
@ManagedBean(name = "authentification")
@ViewScoped
public class Authentification implements Serializable {

    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
    private ISAutorisation aut = (ISAutorisation) ctx.getBean("ISAutorisation");
    private ISUtilisateur uti = (ISUtilisateur) ctx.getBean("ISUtilisateur");
    private String pwd;
    private String UserUtilname;
    private String UserUtilname_n;
    private Autorisation autorisation;
    private Utilisateur utilisateur;
    private Logger logger = Logger.getLogger(this.getClass().getName());
   
    public Authentification() {
        utilisateur = new Utilisateur();
        autorisation = new Autorisation();
    }

    public void changeLogin(ActionEvent actionEven) {
        try {
           UserUtilname = UserUtil.getUsername();
            Utilisateur u = uti.findByUsername(UserUtilname);
            SygePasswordEncoder encoder = new SygePasswordEncoder();
            if (encoder.matches(UserUtilname_n, u.getPassword())) {
                pwd = encoder.encode(pwd);
                u.setPassword(pwd);
                uti.update(u);
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action" + "changement du mot de passe");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, u.getName() + " votre login à étè modifié avec succés", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, u.getUsername() + " vous n'etez pas autorisé! ancien mot de passe incorrect", ""));
            }
        } catch (ServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Erreur modification de login", ""));
        } catch (javax.persistence.NoResultException a) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "votre login est inconnu par le systeme! veillez entrer le login exact", ""));
        }
    }

    public ApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public ISAutorisation getAut() {
        return aut;
    }

    public void setAut(ISAutorisation aut) {
        this.aut = aut;
    }

    public ISUtilisateur getUti() {
        return uti;
    }

    public void setUti(ISUtilisateur uti) {
        this.uti = uti;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getUsername() {
        return UserUtilname;
    }

    public void setUsername(String UserUtilname) {
        this.UserUtilname = UserUtilname;
    }

    public String getUsername_n() {
        return UserUtilname_n;
    }

    public void setUsername_n(String UserUtilname_n) {
        this.UserUtilname_n = UserUtilname_n;
    }

    public Autorisation getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(Autorisation autorisation) {
        this.autorisation = autorisation;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
