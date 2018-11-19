/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Inscription;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IInscriptiondao extends IDao<Inscription, Long> {
    
     public List<Etudiant> findByTypeAnnee(int type,Long idAnnee)  throws DataAccessException;
     public boolean aPayeByTypeAnnee(int type,Long idAnnee, String matricule)  throws DataAccessException;
     public boolean aPayeByTypeAnneeSection(int type, Long idAnnee, String matricule,Long idSection) throws DataAccessException;
    
     
}
