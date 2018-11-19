/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Departement;

/**
 *
 * @author lappa
 */
public interface IDepartementdao extends IDao<Departement, Long> {
    
    public Departement findBySigle(String sigle)throws DataAccessException;
    
}
