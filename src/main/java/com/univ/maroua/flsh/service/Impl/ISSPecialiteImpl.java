/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.INiveaudao;
import com.univ.maroua.flsh.dao.ISectiondao;
import com.univ.maroua.flsh.dao.ISpecialitedao;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.service.ISSpecialite;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISSPecialiteImpl implements ISSpecialite {

    private ISpecialitedao specialitedao;
    private INiveaudao niveaudao;
    
    @Override
    public void create(Specialite s) throws ServiceException {
        try {
            specialitedao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Specialite s) throws ServiceException {
        try {
            Specialite spe = specialitedao.findById(s.getId());
            specialitedao.delete(spe);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Specialite s) throws ServiceException {
        try {
            specialitedao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Specialite> findAll() throws ServiceException {
        try {
            return specialitedao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Specialite findById(Long id) throws ServiceException {
        try {
            return specialitedao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ISpecialitedao getSpecialitedao() {
        return specialitedao;
    }

    public void setSpecialitedao(ISpecialitedao specialitedao) {
        this.specialitedao = specialitedao;
    }

    @Override
    public Specialite findByCode(String code) throws ServiceException {
        try {
            return specialitedao.findByCode(code);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Etudiant> findEtudiantBySpecialite(String nomSpecialite) throws ServiceException {
        try {
            return specialitedao.findEtudiantBySpecialite(nomSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Module> findUEBySpecialiteId(Long IdSpecialite) throws ServiceException {
        try {
            return specialitedao.findUEBySpecialiteId(IdSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    @Override
    public List<Specialite> findByNiveau(Long id) throws ServiceException {
    try {
            return specialitedao.findByNiveau(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
   }

    
    public INiveaudao getNiveaudao() {
        return niveaudao;
    }

    public void setNiveaudao(INiveaudao niveaudao) {
        this.niveaudao = niveaudao;
    }

    @Override
    public Specialite nextSpecialite(Long idSpecialite) throws ServiceException {
        try {
            return specialitedao.nextSpecialite(idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        return null;
        }
        
    }

    
    @Override
    public List<Specialite> findByEtudiant(String matricule) throws ServiceException {
     try {
            return specialitedao.findByEtudiant(matricule);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Specialite> findByEtudiantAnnee(Long etudiantId, Long anneeId) throws ServiceException {
    try {
            return specialitedao.findByEtudiantAnnee(etudiantId, anneeId);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
}
