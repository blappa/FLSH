/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.ISpecialiteAnneedao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ing-lateu
 */
public class ISpecialiteAnneedaoImpl extends GenericDao<SpecialiteAnnee, Long> implements ISpecialiteAnneedao {
  
    @Override
    public SpecialiteAnnee findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws DataAccessException {
   
        try {
            return (SpecialiteAnnee) getManager().createNamedQuery("SpecialiteAnnee.findBySpecialiteAnnee")
                    .setParameter("idAnnee", idAnnee)
                    .setParameter("idSpecialite", idSpecialite)
                    .getSingleResult();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
