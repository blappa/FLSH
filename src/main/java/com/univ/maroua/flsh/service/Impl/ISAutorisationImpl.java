/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.Autorisation;
import com.univ.maroua.flsh.dao.IAutorisationdao;
import com.univ.maroua.flsh.service.ISAutorisation;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISAutorisationImpl implements ISAutorisation {

    private IAutorisationdao autorisationDao;

    @Override
    public Autorisation findById(Long id) throws ServiceException {
        try {
            return autorisationDao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAutorisationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IAutorisationdao getAutorisationDao() {
        return autorisationDao;
    }

    public void setAutorisationDao(IAutorisationdao autorisationDao) {
        this.autorisationDao = autorisationDao;
    }

   
    @Override
    public List<Autorisation> findByAll() throws ServiceException {
        try {
            return autorisationDao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAutorisationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void update(Autorisation s) throws ServiceException {
        try {
            autorisationDao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAutorisationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
    @Override
    public void create(Autorisation u) throws ServiceException {
    try {
            autorisationDao.create(u);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAutorisationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Autorisation u) throws ServiceException {
    try {
            autorisationDao.delete(u);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAutorisationImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
