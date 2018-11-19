/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IAutorisationdao;
import com.univ.maroua.flsh.dao.IUtilisateurdao;
import com.univ.maroua.flsh.entities.Autorisation;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.service.ISUtilisateur;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lateu
 */
@Transactional
public class ISUtilisateurImpl implements ISUtilisateur {

    private IUtilisateurdao userdao;
    private IAutorisationdao autdao;

    @Override
    public List<Utilisateur> findAll() throws ServiceException {
        try {
            return userdao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISUtilisateurImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Utilisateur findById(Long id) throws ServiceException {
        try {
            return userdao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISUtilisateurImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IUtilisateurdao getUserdao() {
        return userdao;
    }

    public void setUserdao(IUtilisateurdao userdao) {
        this.userdao = userdao;
    }

    public IAutorisationdao getAutdao() {
        return autdao;
    }

    public void setAutdao(IAutorisationdao autdao) {
        this.autdao = autdao;
    }

    @Override
    public void create(Utilisateur u) throws ServiceException {
        try {
            userdao.create(u);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISUtilisateurImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void delete(Utilisateur u) throws ServiceException {
        try {
            Utilisateur user=userdao.findById(u.getId());
            userdao.delete(user);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISUtilisateurImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void update(Utilisateur u) throws ServiceException {
        try {
            userdao.update(u);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISUtilisateurImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public Utilisateur findByUsername(String username) throws ServiceException {
        if (username == null) {
            return null;
        }
        try {
            return userdao.findByUsername(username);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISUtilisateurImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
