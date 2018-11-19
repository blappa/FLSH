/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.ISemestredao;
import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.service.ISSemestre;
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
public class ISSemestreImpl implements ISSemestre {

    private ISemestredao semestredao;

    @Override
    public void create(Semestre s) throws ServiceException {
        try {
            semestredao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSemestreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Semestre s) throws ServiceException {
        try {
            Semestre sem = semestredao.findById(s.getId());
            semestredao.delete(sem);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSemestreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Semestre s) throws ServiceException {
        try {
            semestredao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSemestreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Semestre> findAll() throws ServiceException {
        try {
            List<Semestre> semestres = semestredao.findAll();
            // Collections.sort(semestres,Semestre.moyenneComparator);
            return semestres;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSemestreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Semestre findById(Long id) throws ServiceException {
        try {
            return semestredao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSemestreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ISemestredao getSemestredao() {
        return semestredao;
    }

    public void setSemestredao(ISemestredao semestredao) {
        this.semestredao = semestredao;
    }

    public Semestre findByLevel(int level) throws ServiceException {
        try {
            return semestredao.findByLevel(level);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSemestreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Semestre> findBySpecialite(Long idSpecilaite) throws ServiceException {
        try {
            List<Semestre> semestres = semestredao.findBySpecialite(idSpecilaite);
            Collections.sort(semestres);
            return semestres;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSemestreImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
