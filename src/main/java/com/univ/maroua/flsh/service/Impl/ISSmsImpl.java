/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IMatieredao;
import com.univ.maroua.flsh.dao.ISmsdao;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Sms;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISSms;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISSmsImpl implements ISSms {

    private ISmsdao smsdao;

    @Override
    public void create(Sms s) throws ServiceException {
        try {
            smsdao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSmsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Sms s) throws ServiceException {
        try {
            Sms m = smsdao.findById(s.getId());
            smsdao.delete(m);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSmsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Sms s) throws ServiceException {
        try {
            smsdao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSmsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Sms> findAll() throws ServiceException {
        try {
            return smsdao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSmsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Sms findById(Long id) throws ServiceException {
        try {
            return smsdao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSmsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ISmsdao getSmsdao() {
        return smsdao;
    }

    public void setSmsdao(ISmsdao smsdao) {
        this.smsdao = smsdao;
    }

    @Override
    public Sms findByAnneeSectionTypeEtudiant(Long anneeId, Long sectionId, int type, Long etudiantId) throws ServiceException {
    try {
            return smsdao.findByAnneeSectionTypeEtudiant(anneeId, sectionId, type, etudiantId);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSmsImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

   
}
