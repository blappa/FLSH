/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.dao.IAnneeAcademiquedao;
import com.univ.maroua.flsh.dao.IStatistiquedao;
import com.univ.maroua.flsh.entities.Statistique;
import java.util.List;

/**
 *
 * @author ing-lateu
 */
public class IStatistiquedaoImpl extends GenericDao<Statistique, Long> implements IStatistiquedao {

    @Override
    public Statistique findBySpecialiteAnnee(Long idAnnee, Long idSpecialite) throws DataAccessException {
   return (Statistique)getManager().createNamedQuery("Statistique.findBySpecialiteAnnee")
                .setParameter("idAnnee", idAnnee)
                .setParameter("idSpecialite", idSpecialite)
                .getSingleResult();
    }
    
}
