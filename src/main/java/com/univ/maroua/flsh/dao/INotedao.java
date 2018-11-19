/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface INotedao extends IDao<Note, Long> {

    public List<Note> filters(Long matiereId, Long anneeAcademiqueId) throws DataAccessException;

    public Note findNoteByEtudiantMatiere(Long etudiantId, Long matiereId) throws DataAccessException;

    public Note findAnonymatExMatiere(String anonymatName, Long matiereId) throws DataAccessException;

    public Note findAnonymatRatMatiere(String anonymatName, Long matiereId) throws DataAccessException;

    public List<Note> findEtudiantAnnee(String matricule, Long idAnnee) throws DataAccessException;

    public List<Note> findEtudiantAnneeSection(String matricule, Long idAnnee, Long idSection) throws DataAccessException;

    public List<Note> findByEtudiant(String matricule) throws DataAccessException;

    public List<Note> findByNoteForProfil(String matricule) throws DataAccessException;

    public List<Note> findByAnnee(Long idAnnee) throws DataAccessException;

    public List<Note> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws DataAccessException;

   public List<Note> findByEtudiantSection(String matricule,Long idSection) throws DataAccessException;
   
  public List<Note> findEtudiantAnneeSpecialite(String matricule, Long idAnnee, Long idSpecialite) throws DataAccessException;

  

}
