/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IMatiereOptionnelledao;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.MatiereOptionnelle;
import com.univ.maroua.flsh.service.ISMatiereOptionnelle;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISMatiereOptionnelleImpl implements ISMatiereOptionnelle {

    private IMatiereOptionnelledao matiereOptionnelledao;

    @Override
    public void create(MatiereOptionnelle m) throws ServiceException {
        try {
            matiereOptionnelledao.create(m);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereOptionnelleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(MatiereOptionnelle m) throws ServiceException {
        try {
            MatiereOptionnelle matOp = matiereOptionnelledao.findById(m.getId());
            matiereOptionnelledao.delete(matOp);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereOptionnelleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(MatiereOptionnelle m) throws ServiceException {
        try {
            matiereOptionnelledao.update(m);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereOptionnelleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<MatiereOptionnelle> findAll() throws ServiceException {
        try {
            return matiereOptionnelledao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereOptionnelleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public MatiereOptionnelle findById(Long id) throws ServiceException {
        try {
            return matiereOptionnelledao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereOptionnelleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IMatiereOptionnelledao getMatiereOptionnelledao() {
        return matiereOptionnelledao;
    }

    public void setMatiereOptionnelledao(IMatiereOptionnelledao matiereOptionnelledao) {
        this.matiereOptionnelledao = matiereOptionnelledao;
    }

    @Override
    public List<MatiereOptionnelle> findListMatiereOpByEtudiant(Long idEtudiant, Long idSpecialite) throws ServiceException {
        try {
            return matiereOptionnelledao.findListMatiereOpByEtudiant(idEtudiant, idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
