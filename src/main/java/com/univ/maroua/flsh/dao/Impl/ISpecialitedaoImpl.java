/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.dao.ISpecialitedao;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.service.Impl.ISSPecialiteImpl;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ing-lateu
 */
public class ISpecialitedaoImpl extends GenericDao<Specialite, Long> implements ISpecialitedao {

    @Override
    public Specialite findByCode(String code) throws DataAccessException {
        return (Specialite) getManager().createNamedQuery("Specialite.findByCode")
                .setParameter("code", code).getSingleResult();
    }

    @Override
    public List<Etudiant> findEtudiantBySpecialite(String nomSpecialite) throws DataAccessException {
        return getManager().createNamedQuery("Specialite.findEtudiantBySpecialite")
                .setParameter("nomSpecialite", nomSpecialite).getResultList();

    }

    @Override
    public List<Module> findUEBySpecialiteId(Long IdSpecialite) throws DataAccessException {
        return getManager().createNamedQuery("Specialite.findUEBySpecialiteId")
                .setParameter("IdSpecialite", IdSpecialite).getResultList();
    }

    @Override
    public List<Specialite> findByNiveau(Long id) throws DataAccessException {
        return getManager().createNamedQuery("Specialite.findByNiveau")
                .setParameter("id", id).getResultList();

    }

    private List<Niveau> findBySection(Long id) throws DataAccessException {
        return getManager().createNamedQuery("Niveau.findBySection")
                .setParameter("id", id).getResultList();

    }

    @Override
    public Specialite nextSpecialite(Long idSpecialite) throws DataAccessException {
        List<Specialite> specialites = null;
        int type = 0;
        try {
            Specialite specialite = findById(idSpecialite);
            type = specialite.getType();
            Niveau niveau = specialite.getNiveau();
            Section section = niveau.getSection();
            List<Niveau> niveaus = findBySection(section.getId());
            if (niveau.getLevel() == 3 || niveau.getLevel() == 5) { //on retrouve la premiere specialite du meme niveau
                for (int i = 0; i < niveaus.size(); i++) {
                    Niveau niveau1 = niveaus.get(i);
                    if (niveau1.getLevel() == niveau.getLevel()) {
                        niveau = niveaus.get(i);
                        break;
                    }
                }
            } else {

                for (int i = 0; i < niveaus.size(); i++) {
                    Niveau niveau1 = niveaus.get(i);
                    if (niveau1.getLevel() == niveau.getLevel()) {
                        niveau = niveaus.get(i + 1);
                        break;
                    }
                }
            }
            specialites = findByNiveau(niveau.getId());
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        if (type == 0) {
            return specialites.get(0);
        } else {
            for (Specialite specialite : specialites) {
                if (specialite.getType() == type) {
                    return specialite;
                }
            }
            return specialites.get(0);
        }
    }

    @Override
    public List<Specialite> findByEtudiant(String matricule) throws DataAccessException {
        return getManager().createNamedQuery("Specialite.findEtudiant")
                .setParameter("matricule", matricule).getResultList();
    }

    @Override
    public List<Specialite> findByEtudiantAnnee(Long etudiantId, Long anneeId) throws DataAccessException {
        return getManager().createNamedQuery("Specialite.findByEtudiantAnnee")
                .setParameter("etudiantId", etudiantId)
                .setParameter("anneeId", anneeId)
                .getResultList();
    }
}
