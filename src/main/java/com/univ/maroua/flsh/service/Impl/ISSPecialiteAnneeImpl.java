/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.ISpecialiteAnneedao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISSpecialiteAnnee;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISSPecialiteAnneeImpl implements ISSpecialiteAnnee {

    private ISpecialiteAnneedao specialiteAnneedao;

    @Override
    public SpecialiteAnnee create(SpecialiteAnnee s) throws ServiceException {
        try {
            return specialiteAnneedao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteAnneeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void delete(SpecialiteAnnee s) throws ServiceException {
        try {
            SpecialiteAnnee speEtu = specialiteAnneedao.findById(s.getId());
            specialiteAnneedao.delete(speEtu);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteAnneeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(SpecialiteAnnee s) throws ServiceException {
        try {
            specialiteAnneedao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteAnneeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<SpecialiteAnnee> findAll() throws ServiceException {
        try {
            return specialiteAnneedao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteAnneeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SpecialiteAnnee findById(Long id) throws ServiceException {
        try {
            return specialiteAnneedao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteAnneeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SpecialiteAnnee findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException {
        try {
            return specialiteAnneedao.findBySpecialiteAnnee(idSpecialite, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteAnneeImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ISpecialiteAnneedao getSpecialiteAnneedao() {
        return specialiteAnneedao;
    }

    public void setSpecialiteAnneedao(ISpecialiteAnneedao specialiteAnneedao) {
        this.specialiteAnneedao = specialiteAnneedao;
    }
    
    
    
}
