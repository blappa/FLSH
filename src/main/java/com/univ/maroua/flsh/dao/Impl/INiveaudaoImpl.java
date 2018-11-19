/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.dao.INiveaudao;
import java.util.List;

/**
 *
 * @author ing-lateu
 */
public class INiveaudaoImpl extends GenericDao<Niveau, Long> implements INiveaudao{

    @Override
    public Niveau findByLevel(int level) throws DataAccessException {
               return (Niveau)getManager().createNamedQuery("Niveau.findByLevel")
                .setParameter("level", level).getSingleResult();
    }

    @Override
    public List<Niveau> findBySection(Long id) throws DataAccessException {
            return getManager().createNamedQuery("Niveau.findBySection")
                .setParameter("id", id).getResultList();
    
    }
    
}
