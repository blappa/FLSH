/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Niveau;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface INiveaudao extends IDao<Niveau, Long> {

    public Niveau findByLevel(int level) throws DataAccessException;
    
     public List<Niveau> findBySection(Long id)throws DataAccessException;
}
