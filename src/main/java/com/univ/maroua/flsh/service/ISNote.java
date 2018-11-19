/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.projection.DelStat;
import com.univ.maroua.flsh.projection.Pv;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISNote {
    public List<Note> findEtudiantAnneeSpecialite(String matricule, Long idAnnee, Long idSpecialite) throws ServiceException;

    public Note create(Note s) throws ServiceException;

    public String mention(Float result) throws ServiceException;

    public String decision(Float result) throws ServiceException;

    public void delete(Note s) throws ServiceException;

    public void update(Note s) throws ServiceException;

    public List<Note> findAll() throws ServiceException;

    public List<Note> filters(Long matiereId, Long anneeAcademiqueId) throws ServiceException;

    public Note findById(Long id) throws ServiceException;

    public Note calcul(Note n);

    public Note calculMoyenne(Note n);

    public Note deliberationSpeciale(Note n);

    public Note calculDeliberation(Note n, float noteDel, int session, DelStat del);
    
    public Note ajout(Note n, float noteDel, int session, DelStat del);

    public String verifier(Float result);

    public Note calculMoyGenSem(Note note, Pv pvSemestre);

    public List<Pv> traitementSemestriel(List<Pv> pvSemestres, int token);

    public List<Pv> traitementAnnuel(List<Pv> pvSemestres,int token);

    public Note findNoteByEtudiantMatiere(Long etudiantId, Long matiereId) throws ServiceException;

    public Note findByUEEtudiant(Long idEtudiant, Long idUE) throws ServiceException;

    public Note findAnonymatExMatiere(String anonymatName, Long matiereId) throws ServiceException;

    public Note findAnonymatRatMatiere(String anonymatName, Long matiereId) throws ServiceException;

    public String getSession(Note n);

    public void blanchirEtudiantAnnee(String matricule, Long idAnnee) throws ServiceException;

    public List<Note> sortByAnnymat(List<Note> items, int session) throws ServiceException;

    public List<Note> findByEtudiant(String matricule) throws ServiceException;

    public List<Note> findByNoteForProfil(String matricule) throws ServiceException;

    public Long findMasterUE(Long id) throws DataAccessException;

    public Note calculNormale(Note n) throws ServiceException;

    public void validerNoteEtudiantSemestre(String matricule, Long idAnnee, int semestre) throws ServiceException;

    public void inValiderNoteEtudiant(String matricule, Long idAnnee) throws ServiceException;

    public List<Note> findByAnnee(Long idAnnee) throws ServiceException;

    public List<Note> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws ServiceException;
    
    public List<Note> findByEtudiantSection(String matricule, Long idSection) throws ServiceException;
    
    public void blanchirEtudiantAnnee(String matricule, Long idAnnee,Long idSection) throws ServiceException;
    
    public List<Note> findByEtudiantSectionAnnee(String matricule, Long idSection,Long idAnnee) throws ServiceException;


 }
