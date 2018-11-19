/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Statistique;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IStatistiquedao extends IDao<Statistique, Long> {
   public Statistique findBySpecialiteAnnee(Long idAnnee,Long idSpecialite)throws DataAccessException;
    
    
}
