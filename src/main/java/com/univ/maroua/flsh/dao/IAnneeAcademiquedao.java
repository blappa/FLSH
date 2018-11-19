/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IAnneeAcademiquedao extends IDao<AnneeAcademique, Long> {
    
    public AnneeAcademique findByAnnee(String annee)throws DataAccessException;
    
    public List<AnneeAcademique> findByEtudiantSpecialite(Long idEtudiant,Long idSpecialite)throws DataAccessException;
    
    
}
