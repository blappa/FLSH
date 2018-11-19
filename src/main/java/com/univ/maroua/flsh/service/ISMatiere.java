/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISMatiere {

    public void create(Matiere s) throws ServiceException;

    public void delete(Matiere s) throws ServiceException;

    public void update(Matiere s) throws ServiceException;

    public List<Matiere> findAll() throws ServiceException;

    public Matiere findById(Long id) throws ServiceException;

    public Matiere findByCode(String code) throws ServiceException;

    public List<Matiere> findListMatiereNiveauSpecialite(Long idEtudiant, Long idNiveau, Long idSpecialite) throws ServiceException;

    public List<Matiere> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException;

    public List<Matiere> findByUE(Long idUE) throws ServiceException;

    public List<Matiere> findListMatiereOpByEtudiantAnneeAc(Long idEtudiant, Long idAnneeAc) throws ServiceException;

    public List<Matiere> findBySpecialite(Long id) throws ServiceException;

    public Matiere findBySpecialiteAnneeNom(String nomMatiere, Long idSpecialite, Long idAnnee) throws ServiceException;

     public List<Matiere> findMasterBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException;

    public List<Matiere> findBySpecialiteAnneeSemestre(Long idSpecialite, Long idAnnee, Long idSemestre) throws ServiceException;

}
