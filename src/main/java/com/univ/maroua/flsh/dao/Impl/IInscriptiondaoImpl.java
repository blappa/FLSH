/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.dao.IInscriptiondao;
import com.univ.maroua.flsh.entities.Etudiant;
import java.util.List;

/**
 *
 * @author ing-lateu
 */
public class IInscriptiondaoImpl extends GenericDao<Inscription, Long> implements IInscriptiondao {

    @Override
    public List<Etudiant> findByTypeAnnee(int type, Long idAnnee) throws DataAccessException {
        return getManager().createNamedQuery("Inscription.findByTypeAnnee")
                .setParameter("type", type)
                .setParameter("idAnnee", idAnnee)
                .getResultList();
    }

    @Override
    public boolean aPayeByTypeAnnee(int type, Long idAnnee, String matricule) throws DataAccessException {
        List<Inscription> inscriptions = getManager().createNamedQuery("Inscription.findByTypeAnneeMatricule")
                .setParameter("type", type)
                .setParameter("idAnnee", idAnnee)
                .setParameter("matricule", matricule)
                .getResultList();
         return inscriptions.isEmpty() ? false : true;
    }

    @Override
    public boolean aPayeByTypeAnneeSection(int type, Long idAnnee, String matricule,Long idSection) throws DataAccessException {
        List<Inscription> inscriptions = getManager().createNamedQuery("Inscription.findByTypeAnneeMatriculeSection")
                .setParameter("type", type)
                .setParameter("idAnnee", idAnnee)
                .setParameter("matricule", matricule)
                .setParameter("idSection", idSection)
                .getResultList();
         return inscriptions.isEmpty() ? false : true;
    }



}
