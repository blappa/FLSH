/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.dao.IDepartementdao;

/**
 *
 * @author ing-lateu
 */
public class IDepartementdaoImpl extends GenericDao<Departement, Long>implements IDepartementdao{

    public Departement findBySigle(String sigle) throws DataAccessException {
      return (Departement)getManager().createNamedQuery("Departement.findBySigle")
                .setParameter("sigle", sigle).getSingleResult();
    }
    
}
