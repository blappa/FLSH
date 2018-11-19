/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.*;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.MatiereOptionnelle;
import java.util.List;

/**
 *
 * @author lappa
 */
public class IMatiereOptionnelledaoImpl extends GenericDao<MatiereOptionnelle, Long> implements IMatiereOptionnelledao {

    @Override
    public List<Matiere> findListMatiereNiveauSpecialite(String code) throws DataAccessException {
        return getManager().createNamedQuery("MatiereOptionnelleBySpecialite.findListMatiereOptionnelleBySpecialite")
                .setParameter("code", code)
                .getResultList();
    }
    
    
      @Override
    public List<MatiereOptionnelle> findListMatiereOpByEtudiant(Long idEtudiant,Long idSpecialite) throws DataAccessException {
        return getManager().createNamedQuery("MatiereOptionnelleBySpecialite.findListMatiereOpByEtudiant")
                .setParameter("idEtudiant", idEtudiant)
                .setParameter("idSpecialite", idSpecialite)
                .getResultList();

    }
}
