/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISSpecialiteEtudiant {

    public void create(SpecialiteEtudiant s) throws ServiceException;

    public void delete(SpecialiteEtudiant s) throws ServiceException;

    public void update(SpecialiteEtudiant s) throws ServiceException;

    public List<SpecialiteEtudiant> findAll() throws ServiceException;

    public SpecialiteEtudiant findById(Long id) throws ServiceException;

    public List<SpecialiteEtudiant> findByMatriculeEtudiant(String matricule) throws ServiceException;

    public List<SpecialiteEtudiant> findByEtudiantLevel(Long idEtudiant, int level) throws ServiceException;

    public SpecialiteEtudiant findByEtudiantSpecialiteAnnee(Long idEtudiant, Long idAnnee, Long idSpecialite) throws ServiceException;

    public List<SpecialiteEtudiant> findByEtudiantNiveauAnnee(Long idEtudiant, Long idNiveau, Long idAnnee) throws ServiceException;

    public List<SpecialiteEtudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException;

    public List<SpecialiteEtudiant> findByDepartementAnnee(Long idDepartement, Long idAnnee) throws ServiceException;

    public List<SpecialiteEtudiant> findByAnneeId(Long idAnnee) throws ServiceException;

    public List<SpecialiteEtudiant> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws ServiceException;

    public List<SpecialiteEtudiant> findByEtudiantSection(String matricule, Long idSection) throws ServiceException;

     public List<SpecialiteEtudiant> findByEtudiantSectionNiveauAnnee(String matricule, int level, Long idSection, Long anneeId) throws ServiceException;

    
}
