/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISEtudiant {

    public Etudiant create(Etudiant s) throws ServiceException;

    public void delete(Etudiant s) throws ServiceException;

    public void update(Etudiant s) throws ServiceException;

    public List<Etudiant> findAll() throws ServiceException;

    public Etudiant findById(Long id) throws ServiceException;

    public Etudiant findByMatricule(String matricule) throws ServiceException;

    public List<Matiere> findListMatiereByEtudiant(Long idEtudiant, Long idAnnée) throws ServiceException;

    public Specialite findSpecialiteEtudiant(Long idEtudiant) throws ServiceException;

    public List<Etudiant> findEtudiantBySpecialite(Long idSpecialite) throws ServiceException;

    public List<Etudiant> RameneByMatiere(Long idMatiere, int niveau) throws ServiceException;

    public List<Etudiant> findByMatiere(Long idMatiere) throws ServiceException;

    public List<Etudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnneeAc) throws ServiceException;

    public boolean esNouveau(Long idEtudiant, Long idSpecialite, Long idAnnee) throws ServiceException;

 
    public List<Etudiant> findBySexe(String sexe) throws ServiceException;

    public List<Etudiant> findBySpecialiteAnneeForSyntheses(Long idSpecialite, Long idAnneeAc) throws ServiceException;

    public List<Etudiant> findByDepartement(Long idDepartement) throws ServiceException;

    public List<Etudiant> findBySection(Long idSection) throws ServiceException;

    public List<Etudiant> findByAnnee(Long idAnnee) throws ServiceException;

    public void deleteStudentForLevel(Long idSpec, Long idAn, int level) throws ServiceException;
    
    public List<Etudiant> findByAnneeDepartementType(Long anneeId,Long departementId,int type) throws ServiceException;
    
    public List<Etudiant> findByAnneeSectionType(Long anneeId, Long sectionId, int type) throws ServiceException;

}
