/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Autorisation;
import com.univ.maroua.flsh.entities.Utilisateur;

/**
 *
 * @author lappa
 */
public interface IUtilisateurdao extends IDao<Utilisateur, Long> {
    
   public Utilisateur findByUsername(String username) throws DataAccessException;
    
}
