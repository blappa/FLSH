/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Specialite;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISpecialitedao extends IDao<Specialite, Long> {

    public Specialite findByCode(String code) throws DataAccessException;
    
   public List <Etudiant> findEtudiantBySpecialite(String nomSpecialite)throws DataAccessException;
   
   public List <Module> findUEBySpecialiteId(Long IdSpecialite)throws DataAccessException;
 
     public List<Specialite> findByNiveau(Long id)throws DataAccessException;
     
     public  Specialite nextSpecialite(Long idSpecialite) throws DataAccessException;
     
      public List<Specialite> findByEtudiant(String matricule)throws DataAccessException;

      public List<Specialite> findByEtudiantAnnee(Long etudiantId,Long anneeId)throws DataAccessException;

}
