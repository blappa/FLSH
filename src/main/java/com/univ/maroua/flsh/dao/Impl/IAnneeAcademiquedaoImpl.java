/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.dao.IAnneeAcademiquedao;
import java.util.List;

/**
 *
 * @author ing-lateu
 */
public class IAnneeAcademiquedaoImpl extends GenericDao<AnneeAcademique, Long>implements IAnneeAcademiquedao {

    @Override
    public AnneeAcademique findByAnnee(String annee) throws DataAccessException {
        return (AnneeAcademique)getManager().createNamedQuery("AnneeAcademique.findByAnnee")
                .setParameter("annee", annee).getSingleResult();
    }

    @Override
    public List<AnneeAcademique> findByEtudiantSpecialite(Long idEtudiant, Long idSpecialite) throws DataAccessException {
    return getManager().createNamedQuery("AnneeAcademique.findByEtudiantSpecialite")
                .setParameter("idEtudiant", idEtudiant)
                .setParameter("idSpecialite", idSpecialite)
                .getResultList();
    
    }
    
}
