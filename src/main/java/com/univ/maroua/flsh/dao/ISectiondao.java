/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Section;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISectiondao extends IDao<Section, Long> {
    
    public Section findByCode(String sigle)throws DataAccessException;
    
     public List<Section> findByDepartement(Long id)throws DataAccessException;
     
     public List<Section> findByEtudiantAnnee(Long idEtudaint,Long idAnnee)throws DataAccessException;
     
      public List<Section> findByEtudiantIdNiveau(String matricule,int niveauLevel)throws DataAccessException;
 
     public List<Section> findByEtudiant(String matricule)throws DataAccessException;
 
}
