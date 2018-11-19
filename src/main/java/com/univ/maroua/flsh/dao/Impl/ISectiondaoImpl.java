/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.dao.ISectiondao;
import java.util.List;

/**
 *
 * @author ing-lateu
 */
public class ISectiondaoImpl extends GenericDao<Section, Long> implements ISectiondao {

    /**
     *
     * @param code
     * @return
     * @throws DataAccessException
     */
    @Override
    public Section findByCode(String sigle) throws DataAccessException {
        return (Section) getManager().createNamedQuery("Section.findBySigle")
                .setParameter("sigle", sigle).getSingleResult();
    }

    @Override
    public List<Section> findByDepartement(Long id) throws DataAccessException {
        return getManager().createNamedQuery("Section.findByDepartement")
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Section> findByEtudiantAnnee(Long idEtudiant, Long idAnnee) throws DataAccessException {
        return  getManager().createNamedQuery("Section.findByEtudiantAnnee")
                .setParameter("idEtudiant", idEtudiant)
                .setParameter("idAnnee", idAnnee)
                .getResultList();
    }

    @Override
    public List<Section> findByEtudiantIdNiveau(String matricule, int niveauLevel) throws DataAccessException {
    return  getManager().createNamedQuery("Section.findByEtudiantNiveau")
                .setParameter("matricule", matricule)
                .setParameter("level", niveauLevel)
                .getResultList();
    }

    @Override
    public List<Section> findByEtudiant(String matricule) throws DataAccessException {
   return  getManager().createNamedQuery("Section.findByEtudiant")
                .setParameter("matricule", matricule)
                .getResultList();
        
    }
    
     
    
    
}
