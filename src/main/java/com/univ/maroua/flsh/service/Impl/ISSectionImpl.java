/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.ISectiondao;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.service.ISSection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISSectionImpl implements ISSection {

    private ISectiondao sectiondao;

    @Override
    public void create(Section s) throws ServiceException {
        try {
            sectiondao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Section s) throws ServiceException {
        try {
            Section d = sectiondao.findById(s.getId());
            sectiondao.delete(d);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Section s) throws ServiceException {
        try {
            sectiondao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Section> findAll() throws ServiceException {
        try {
            return sectiondao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Section findById(Long id) throws ServiceException {
        try {
            return sectiondao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Section findByCode(String code) throws ServiceException {
        try {
            return sectiondao.findByCode(code);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ISectiondao getSectiondao() {
        return sectiondao;
    }

    public void setSectiondao(ISectiondao sectiondao) {
        this.sectiondao = sectiondao;
    }

    @Override
    public List<Section> findByDepartement(Long id) throws ServiceException {
        try {
            return sectiondao.findByDepartement(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Section> findByEtudiantAnnee(Long idEtudiant, Long idAnnee) throws ServiceException {
        try {
            return sectiondao.findByEtudiantAnnee(idEtudiant, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Section> findByEtudiantIdNiveau(String matricule, int niveauLevel) throws ServiceException {
        try {
            return sectiondao.findByEtudiantIdNiveau(matricule, niveauLevel);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Section> findByEtudiant(String matricule) throws ServiceException {
    try {
            return sectiondao.findByEtudiant(matricule);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
