/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.IMatieredao;
import com.univ.maroua.flsh.entities.Matiere;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author ing-lateu
 */
public class IMatieredaoImpl extends GenericDao<Matiere, Long> implements IMatieredao {

    @Override
    public Matiere findbyCode(String code) throws DataAccessException {
        return (Matiere) getManager().createNamedQuery("Matiere.findByCode")
                .setParameter("code", code).getSingleResult();
    }

    @Override
    public List<Matiere> findListMatiereNiveauSpecialite(Long idEtudiant, Long idNiveau, Long idSpecialite) throws DataAccessException {
        return getManager().createNamedQuery("Matiere.findListMatiereNiveauSpecialite")
                .setParameter("idEtudiant", idEtudiant)
                .setParameter("idNiveau", idNiveau)
                .setParameter("idSpecialite", idSpecialite)
                .getResultList();
    }

    @Override
    public List<Matiere> findListMatiereByUE(Long idUE) throws DataAccessException {
        return getManager().createNamedQuery("Matiere.findListMatiereByUE")
                .setParameter("idUE", idUE)
                .getResultList();
    }

    @Override
    public List<Matiere> findBySpecialite(Long id) throws DataAccessException {
        return getManager().createNamedQuery("Matiere.findBySpecalite")
                .setParameter("id", id).getResultList();
    }

    @Override
    public List<Matiere> findListMatiereOpByEtudiantAnneeAc(Long idEtudiant, Long idAnneeAc) throws DataAccessException {
        return getManager().createNamedQuery("Matiere.findListMatiereOpByEtudiantAnneeAc")
                .setParameter("idEtudiant", idEtudiant)
                .setParameter("idAnneeAc", idAnneeAc)
                .getResultList();
    }

    @Override
    public Matiere findBySpecialiteAnneeNom(String nomMatiere, Long idSpecialite, Long idAnnee) throws DataAccessException {
        return (Matiere) getManager().createNamedQuery("Matiere.findByNameSpecialiteAnnee")
                .setParameter("nomMatiere", nomMatiere)
                .setParameter("idSpecialite", idSpecialite)
                .setParameter("idAnnee", idAnnee)
                .getSingleResult();
    }

    @Override
    public List<Matiere> findMasterBySpecialiteAnneeNom(Long idSpecialite, Long idAnnee) throws DataAccessException {
        List<Matiere> matieres = getManager().createNamedQuery("Matiere.findMasterBySpecialiteAnnee")
                .setParameter("idSpecialite", idSpecialite)
                .setParameter("idAnnee", idAnnee)
                .getResultList();
        return matieres;
    }

    @Override
    public List<Matiere> findBySpecialiteAnneeSemestre(Long idSpecialite, Long idAnnee, Long idSemestre) throws DataAccessException {
        List<Matiere> matieres = getManager().createNamedQuery("Matiere.findBySpecialiteAnneeSemestre")
                .setParameter("idSpecialite", idSpecialite)
                .setParameter("idAnnee", idAnnee)
                .setParameter("idSemestre", idSemestre)
                .getResultList();
        return matieres;
    }

    @Override
    public List<Matiere> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws DataAccessException {
     List<Matiere> matieres = getManager().createNamedQuery("Matiere.findBySpecialiteAnnee")
                .setParameter("idSpecialite", idSpecialite)
                .setParameter("idAnnee", idAnnee)
                .getResultList();
        return matieres;
    }
}
