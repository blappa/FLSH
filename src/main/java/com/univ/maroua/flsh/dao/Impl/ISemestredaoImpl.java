/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.ISemestredao;
import com.univ.maroua.flsh.entities.Semestre;
import java.util.List;

/**
 *
 * @author ing-lateu
 */
public class ISemestredaoImpl extends GenericDao<Semestre, Long>implements ISemestredao {

    @Override
    public Semestre findByLevel(int level) throws DataAccessException {
        return (Semestre)getManager().createNamedQuery("Semestre.findByLevel")
                .setParameter("level", level).getSingleResult();
    }

    @Override
    public List<Semestre> findBySpecialite(Long idSpecialite) throws DataAccessException {
       return getManager().createNamedQuery("Semestre.findBySpecialite")
                .setParameter("idSpecialite", idSpecialite).getResultList();
    }
    
}
