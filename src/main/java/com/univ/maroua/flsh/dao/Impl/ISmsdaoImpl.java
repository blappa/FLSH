/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.ISmsdao;
import com.univ.maroua.flsh.entities.Sms;

/**
 *
 * @author ing-lateu
 */
public class ISmsdaoImpl extends GenericDao<Sms, Long> implements ISmsdao {

    @Override
    public Sms findByAnneeSectionTypeEtudiant(Long anneeId, Long sectionId, int type, Long etudiantId) throws DataAccessException {
        return (Sms) getManager().createNamedQuery("Sms.findByAnneeSectionTypeEtudiant")
                .setParameter("anneeId", anneeId)
                .setParameter("sectionId", sectionId)
                .setParameter("type", type)
                .setParameter("etudiantId", etudiantId)
                .getSingleResult();
    }
}
