/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISpecialiteEtudiantdao extends IDao<SpecialiteEtudiant, Long> {

    public List<SpecialiteEtudiant> findByMatriculeEtudiant(String matricule) throws DataAccessException;

    public List<SpecialiteEtudiant> findByIdEtudiant(Long idEtudiant) throws DataAccessException;

    public SpecialiteEtudiant findByEtudiantSpecialiteAnnee(Long idEtudiant, Long idAnnee, Long idSpecialite) throws DataAccessException;

    public List<SpecialiteEtudiant> findByEtudiantLevel(Long idEtudiant, int level) throws DataAccessException;

    public List<SpecialiteEtudiant> findByEtudiantNiveauAnnee(Long idEtudiant, Long idNiveau, Long idAnnee) throws DataAccessException;

    public List<SpecialiteEtudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws DataAccessException;

    public List<SpecialiteEtudiant> findByAnneeId(Long idAnnee) throws DataAccessException;

    public List<SpecialiteEtudiant> findByDepartementAnnee(Long idDepartement, Long idAnnee) throws DataAccessException;

    public List<SpecialiteEtudiant> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws DataAccessException;

     public List<SpecialiteEtudiant> findByEtudiantSection(String matricule,Long idSection) throws DataAccessException;

  public List<SpecialiteEtudiant> findByEtudiantSectionNiveauAnnee(String matricule, int level, Long idSection, Long anneeId) throws DataAccessException;

}