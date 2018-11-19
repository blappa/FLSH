/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Semestre;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISemestredao extends IDao<Semestre, Long> {
    
    public Semestre findByLevel(int level)throws DataAccessException;
    
    public List<Semestre> findBySpecialite(Long idSpecialite)throws DataAccessException;
    
    
}
