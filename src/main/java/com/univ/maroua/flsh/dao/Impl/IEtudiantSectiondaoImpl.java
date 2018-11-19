/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.IEtudiantSectiondao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.EtudiantSection;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ing-lateu
 */
public class IEtudiantSectiondaoImpl extends GenericDao<EtudiantSection, Long> implements IEtudiantSectiondao {

    @Override
    public EtudiantSection findByEtudiantSection(Long idEtudiant, Long idSection) throws DataAccessException {
        try {
            return (EtudiantSection) getManager().createNamedQuery("EtudiantSection.findEtudiantBySection")
                    .setParameter("idEtudiant", idEtudiant)
                    .setParameter("idSection", idSection)
                    .getSingleResult();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
