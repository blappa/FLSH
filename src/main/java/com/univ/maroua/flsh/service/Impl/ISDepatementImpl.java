/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.dao.IDepartementdao;
import com.univ.maroua.flsh.dao.IUtilisateurdao;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.service.ISDepartement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISDepatementImpl implements ISDepartement {

    private IUtilisateurdao utilisateurdao;
    private IDepartementdao departementdao;

    @Override
    public void create(Departement s) throws ServiceException {
        try {
            departementdao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISDepatementImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Departement s) throws ServiceException {
        try {
            Departement d = departementdao.findById(s.getId());
            departementdao.delete(d);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISDepatementImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Departement s) throws ServiceException {
        try {
            departementdao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISDepatementImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Departement> findAll() throws ServiceException {
        try {
            try {
                Utilisateur utilisateur = utilisateurdao.findByUsername(UserUtil.getUsername());
                if (utilisateur.getAutorisation().getAuthority().equals("ROLE_DEP")) {
                    List<Departement> departements = new ArrayList<>();
                    Departement departement = departementdao.findById(utilisateur.getDepartement().getId());
                    departements.add(departement);
                    return departements;
                }
            } catch (NoResultException ex) {
                return departementdao.findAll();
            }
            return departementdao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISDepatementImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    @Override
    public Departement findById(Long id) throws ServiceException {
        try {
            return departementdao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISDepatementImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Departement findBySigle(String sigle) throws ServiceException {
        try {
            return departementdao.findBySigle(sigle);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISDepatementImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IDepartementdao getDepartementdao() {
        return departementdao;
    }

    public void setDepartementdao(IDepartementdao departementdao) {
        this.departementdao = departementdao;
    }

    public IUtilisateurdao getUtilisateurdao() {
        return utilisateurdao;
    }

    public void setUtilisateurdao(IUtilisateurdao utilisateurdao) {
        this.utilisateurdao = utilisateurdao;
    }
}
