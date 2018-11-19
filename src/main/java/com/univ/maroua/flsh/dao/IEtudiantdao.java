/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Specialite;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IEtudiantdao extends IDao<Etudiant, Long> {

    public Etudiant findByMatricule(String matricule) throws DataAccessException;

    public Specialite findSpecialiteEtudiant(Long idEtudiant) throws DataAccessException;

    public List<Etudiant> findEtudiantBySpecialite(Long idSpecialite) throws DataAccessException;

    public List<Etudiant> RameneByMatiere(Long idMatiere, int niveau) throws DataAccessException;

    public List<Etudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnneeAc) throws DataAccessException;

    public List<Etudiant> findBySpecialiteMatiere(Long idSpecialite, Long idMatiere) throws DataAccessException;

    public List<Etudiant> findByNiveauAnnee(Long idNiveau, Long idAnnee) throws DataAccessException;

    public List<Etudiant> findBySpecialiteAnneeNiveau(Long idSpecialite, Long idAnneeAc, int level) throws DataAccessException;

    public List<Etudiant> findBySexe(String sexe) throws DataAccessException;

    public List<Etudiant> findByAnnee(Long idAnnee) throws DataAccessException;

    public List<Etudiant> findByDepartement(Long idDepartement) throws DataAccessException;
    
    public List<Etudiant> findByAnneeDepartementType(Long anneeId,Long departementId,int type) throws DataAccessException;

    public List<Etudiant> findByAnneeSectionType(Long anneeId,Long sectionId,int type) throws DataAccessException;


    public List<Etudiant> findBySection(Long idSection) throws DataAccessException;

    public boolean haveDoneSpecialiteAnnee(Long idEtudiant, Long idSpecialite, Long idAnnee) throws DataAccessException;
    
    
}
