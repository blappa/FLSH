/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ing-lateu
 */
public class ISpecialiteEtudiantdaoImpl extends GenericDao<SpecialiteEtudiant, Long> implements ISpecialiteEtudiantdao {

    @Override
    public List<SpecialiteEtudiant> findByMatriculeEtudiant(String matricule) throws DataAccessException {
        try {
            return getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiant")
                    .setParameter("matricule", matricule)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SpecialiteEtudiant findByEtudiantSpecialiteAnnee(Long idEtudiant, Long idAnnee, Long idSpecialite) throws DataAccessException {
        try {
            return (SpecialiteEtudiant) getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantSpecialiteAnnee")
                    .setParameter("idEtudiant", idEtudiant)
                    .setParameter("idAnnee", idAnnee)
                    .setParameter("idSpecialite", idSpecialite)
                    .getSingleResult();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public List<SpecialiteEtudiant> findByEtudiantLevel(Long idEtudiant, int level) throws DataAccessException {
        try {
            return getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantNiveau")
                    .setParameter("idEtudiant", idEtudiant)
                    .setParameter("level", level)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByEtudiantNiveauAnnee(Long idEtudiant, Long idNiveau, Long idAnnee) throws DataAccessException {
        try {
            return getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantNiveauAnnee")
                    .setParameter("idEtudiant", idEtudiant)
                    .setParameter("idAnnee", idAnnee)
                    .setParameter("idNiveau", idNiveau)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws DataAccessException {
        try {
            return getManager().createNamedQuery("SpecialiteEtudiant.findBySpecialiteAnnee")
                    .setParameter("idSpecialite", idSpecialite)
                    .setParameter("idAnnee", idAnnee)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByIdEtudiant(Long idEtudiant) throws DataAccessException {
        try {
            return getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantId")
                    .setParameter("idEtudiant", idEtudiant)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByAnneeId(Long idAnnee) throws DataAccessException {
        try {
            return getManager().createNamedQuery("SpecialiteEtudiant.findByAnneeId")
                    .setParameter("idAnnee", idAnnee)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByDepartementAnnee(Long idDepartement, Long idAnnee) throws DataAccessException {

        try {
            return getManager().createNamedQuery("SpecialiteEtudiant.findByDepartementAnnee")
                    .setParameter("idDepartement", idDepartement)
                    .setParameter("idAnnee", idAnnee)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws DataAccessException {
        try {
            return  getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantSectionNiveau")
                    .setParameter("matricule", matricule)
                    .setParameter("level", level)
                    .setParameter("sectionId", idSection)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     @Override
    public List<SpecialiteEtudiant> findByEtudiantSectionNiveauAnnee(String matricule, int level, Long idSection, Long anneeId) throws DataAccessException {
        try {
            return  getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantSectionNiveauAnnee")
                    .setParameter("matricule", matricule)
                    .setParameter("level", level)
                    .setParameter("sectionId", idSection)
                    .setParameter("anneeId", anneeId)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    @Override
    public List<SpecialiteEtudiant> findByEtudiantSection(String matricule, Long idSection) throws DataAccessException {
        try {
            return  getManager().createNamedQuery("SpecialiteEtudiant.findByEtudiantSection")
                    .setParameter("matricule", matricule)
                    .setParameter("sectionId", idSection)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
