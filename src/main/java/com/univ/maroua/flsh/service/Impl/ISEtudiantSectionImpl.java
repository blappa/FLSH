/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IEtudiantSectiondao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.EtudiantSection;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISEtudiantSection;
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
public class ISEtudiantSectionImpl implements ISEtudiantSection {

    private IEtudiantSectiondao etudiantSectiondao;

    @Override
    public void create(EtudiantSection s) throws ServiceException{
        try {
            etudiantSectiondao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(EtudiantSection s) throws ServiceException{
        try {
            EtudiantSection speEtu=etudiantSectiondao.findById(s.getId());
            etudiantSectiondao.delete(speEtu);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(EtudiantSection s) throws ServiceException{
        try {
            etudiantSectiondao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<EtudiantSection> findAll() throws ServiceException{
        try {
            return etudiantSectiondao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public EtudiantSection findById(Long id)throws ServiceException {
        try {
            return etudiantSectiondao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

  

    @Override
    public EtudiantSection findByEtudiantSection(Long idEtudiant, Long idSection) throws ServiceException {
    try {
            return etudiantSectiondao.findByEtudiantSection(idEtudiant, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IEtudiantSectiondao getEtudiantSectiondao() {
        return etudiantSectiondao;
    }

    public void setEtudiantSectiondao(IEtudiantSectiondao etudiantSectiondao) {
        this.etudiantSectiondao = etudiantSectiondao;
    }
    
    

   
}
