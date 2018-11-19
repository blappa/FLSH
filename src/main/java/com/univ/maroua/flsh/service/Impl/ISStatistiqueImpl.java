/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IStatistiquedao;
import com.univ.maroua.flsh.dao.IStatistiquedao;
import com.univ.maroua.flsh.entities.Statistique;
import com.univ.maroua.flsh.service.ISStatistique;
import com.univ.maroua.flsh.service.ISStatistique;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISStatistiqueImpl implements ISStatistique {

    private IStatistiquedao statistiquedao;

    @Override
    public Statistique create(Statistique s) throws ServiceException {
        Statistique statistique = new Statistique();
        try {
            statistique = statistiquedao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISStatistiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statistique;
    }

    @Override
    public void delete(Statistique s) throws ServiceException {
        try {
            Statistique a = statistiquedao.findById(s.getId());
            statistiquedao.delete(a);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISStatistiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Statistique s) throws ServiceException {
        try {
            statistiquedao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISStatistiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Statistique> findAll() throws ServiceException {
        try {
            return statistiquedao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISStatistiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Statistique findById(Long id) throws ServiceException {
        try {
            return statistiquedao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISStatistiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Statistique findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException {
        try {
            return statistiquedao.findBySpecialiteAnnee(idAnnee, idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISStatistiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IStatistiquedao getStatistiquedao() {
        return statistiquedao;
    }

    public void setStatistiquedao(IStatistiquedao statistiquedao) {
        this.statistiquedao = statistiquedao;
    }
    
    
    

   
}
