/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.INotedao;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ing-lateu
 */
public class INotedaoImpl extends GenericDao<Note, Long> implements INotedao {

    @Override
    public List<Note> filters(Long matiereId, Long anneeAcademiqueId) throws DataAccessException {
        return getManager().createQuery("SELECT n FROM  Note n WHERE n.matiere.id=:idMatiere AND n.matiere.module.anneeAcademique.id=:idAnneeAcademique ORDER BY n.etudiant.nom ")
                .setParameter("idMatiere", matiereId)
                .setParameter("idAnneeAcademique", anneeAcademiqueId)
                .getResultList();
    }

    @Override
    public Note findNoteByEtudiantMatiere(Long etudiantId, Long matiereId) throws DataAccessException {
        return (Note) getManager().createNamedQuery("Note.findByMatiereEtudiant")
                .setParameter("idEtudiant", etudiantId)
                .setParameter("idMatiere", matiereId)
                .getSingleResult();
    }

    @Override
    public Note findAnonymatExMatiere(String anonymatName, Long matiereId) throws DataAccessException {
        return (Note) getManager().createNamedQuery("Note.findByMatiereAnonymatEx")
                .setParameter("anonymatName", anonymatName)
                .setParameter("idMatiere", matiereId)
                .getSingleResult();
    }

    @Override
    public Note findAnonymatRatMatiere(String anonymatName, Long matiereId) throws DataAccessException {
        return (Note) getManager().createNamedQuery("Note.findByMatiereAnonymatRat")
                .setParameter("anonymatName", anonymatName)
                .setParameter("idMatiere", matiereId)
                .getSingleResult();
    }

    @Override
    public List<Note> findEtudiantAnnee(String matricule, Long idAnnee) throws DataAccessException {
        return getManager().createNamedQuery("Note.findEtudiantAnnee")
                .setParameter("matricule", matricule)
                .setParameter("idAnnee", idAnnee)
                .getResultList();
    }

    @Override
    public List<Note> findByNoteForProfil(String matricule) throws DataAccessException {
        return getManager().createNamedQuery("Note.findByNoteForProfil")
                .setParameter("matricule", matricule)
                .getResultList();
    }

    @Override
    public List<Note> findByEtudiant(String matricule) throws DataAccessException {
        return getManager().createNamedQuery("Note.findByEtudiant")
                .setParameter("matricule", matricule)
                .getResultList();
    }

    @Override
    public List<Note> findByAnnee(Long idAnnee) throws DataAccessException {
        return getManager().createNamedQuery("Note.findByAnnee")
                .setParameter("idAnnee", idAnnee)
                .getResultList();
    }

    @Override
    public List<Note> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws DataAccessException {
        try {
            return getManager().createNamedQuery("Note.findByEtudiantSectionNiveau")
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
    public List<Note> findByEtudiantSection(String matricule, Long idSection) throws DataAccessException {
        try {
            return getManager().createNamedQuery("Note.findByEtudiantSection")
                    .setParameter("matricule", matricule)
                   .setParameter("sectionId", idSection)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    
    
    @Override
    public List<Note> findEtudiantAnneeSection(String matricule, Long idAnnee, Long idSection) throws DataAccessException {
        return getManager().createNamedQuery("Note.findEtudiantAnneeSection")
                .setParameter("matricule", matricule)
                .setParameter("idAnnee", idAnnee)
                .setParameter("idSection", idSection)
                .getResultList();
    }

    @Override
    public List<Note> findEtudiantAnneeSpecialite(String matricule, Long idAnnee, Long idSpecialite) throws DataAccessException {
    return getManager().createNamedQuery("Note.findEtudiantAnneeSpecialite")
                .setParameter("matricule", matricule)
                .setParameter("idAnnee", idAnnee)
                .setParameter("idSpecialite", idSpecialite)
                .getResultList();
    }

    
}
