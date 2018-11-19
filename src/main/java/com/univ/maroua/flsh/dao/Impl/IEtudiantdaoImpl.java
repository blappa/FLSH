/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.dao.IEtudiantdao;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;
import javax.persistence.NoResultException;

/**
 *
 * @author ing-lateu
 */
public class IEtudiantdaoImpl extends GenericDao<Etudiant, Long> implements IEtudiantdao {

    @Override
    public Etudiant findByMatricule(String matricule) throws DataAccessException {
        return (Etudiant) getManager().createNamedQuery("Etudiant.findByMatricule")
                .setParameter("matricule", matricule).getSingleResult();
    }

    @Override
    public Specialite findSpecialiteEtudiant(Long idEtudiant) throws DataAccessException {

        return (Specialite) getManager().createNamedQuery("Etudiant.findSpecialiteEtudiant")
                .setParameter("idEtudiant", idEtudiant).getSingleResult();

    }

    @Override
    public List<Etudiant> findEtudiantBySpecialite(Long idSpecialite) throws DataAccessException {

        return getManager().createNamedQuery("Etudiant.findEtudiantBySpecialite")
                .setParameter("idSpecialite", idSpecialite).getResultList();

    }

    @Override
    public List<Etudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnneeAc) throws DataAccessException {

        return getManager().createNamedQuery("Etudiant.findBySpecialiteAnnee")
                .setParameter("idSpecialite", idSpecialite)
                .setParameter("idAnneeAc", idAnneeAc)
                .getResultList();


    }

    @Override
    public List<Etudiant> RameneByMatiere(Long idMatiere, int niveau) throws DataAccessException {

        return getManager().createNamedQuery("Etudiant.findByMatiere")
                .setParameter("idMatiere", idMatiere)
                .setParameter("level", niveau)
                .getResultList();
    }

    @Override
    public List<Etudiant> findBySpecialiteMatiere(Long idSpecialite, Long idMatiere) throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findBySpecialiteMatiere")
                .setParameter("idMatiere", idMatiere)
                .setParameter("idSpecialite", idSpecialite)
                .getResultList();
    }

    @Override
    public List<Etudiant> findBySpecialiteAnneeNiveau(Long idSpecialite, Long idAnneeAc, int level) throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findBySpecialiteAnneeNiveau")
                .setParameter("idSpecialite", idSpecialite)
                .setParameter("idAnneeAc", idAnneeAc)
                .setParameter("level", level)
                .getResultList();
    }

    @Override
    public List<Etudiant> findBySexe(String sexe) throws DataAccessException {

        return getManager().createNamedQuery("Etudiant.findBySexe")
                .setParameter("sexe", sexe)
                .getResultList();
    }

    @Override
    public boolean haveDoneSpecialiteAnnee(Long idEtudiant, Long idSpecialite, Long idAnnee) throws DataAccessException {
        List<SpecialiteEtudiant> sps = getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantSpecialiteAnnee")
                .setParameter("idEtudiant", idEtudiant)
                .setParameter("idSpecialite", idSpecialite)
                .setParameter("idAnnee", idAnnee)
                .getResultList();
        return sps.isEmpty() ? false : true;
    }

    @Override
    public List<Etudiant> findByDepartement(Long idDepartement) throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findByDepartement")
                .setParameter("idDepartement", idDepartement)
                .getResultList();
    }

    @Override
    public List<Etudiant> findBySection(Long idSection) throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findBySection")
                .setParameter("idSection", idSection)
                .getResultList();
    }

    @Override
    public List<Etudiant> findByAnnee(Long idAnnee) throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findByAnnee")
                .setParameter("idAnnee", idAnnee).getResultList();

    }

    @Override
    public List<Etudiant> findByNiveauAnnee(Long idNiveau, Long idAnnee) throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findByNiveauAnnee")
                .setParameter("idNiveau", idNiveau)
                .setParameter("idAnnee", idAnnee)
                .getResultList();
    }

    @Override
    public List<Etudiant> findByAnneeDepartementType(Long anneeId, Long departementId, int type) throws DataAccessException {
        return getManager().createNamedQuery("Etudiant.findByAnneeDepartementType")
                .setParameter("anneeId", anneeId)
                .setParameter("departementId", departementId)
                .setParameter("type", type)
                .getResultList();
    }

    @Override
    public List<Etudiant> findByAnneeSectionType(Long anneeId, Long sectionId, int type) throws DataAccessException {
    return getManager().createNamedQuery("Etudiant.findByAnneeSectionType")
                .setParameter("anneeId", anneeId)
                .setParameter("sectionId", sectionId)
                .setParameter("type", type)
                .getResultList(); }
}
