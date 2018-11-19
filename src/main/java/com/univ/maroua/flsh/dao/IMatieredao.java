/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Matiere;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IMatieredao extends IDao<Matiere, Long> {

    public Matiere findbyCode(String code) throws DataAccessException;

    public List<Matiere> findListMatiereNiveauSpecialite(Long idEtudiant, Long niveau, Long specialite) throws DataAccessException;

    public List<Matiere> findListMatiereByUE(Long idUE) throws DataAccessException;

    public List<Matiere> findListMatiereOpByEtudiantAnneeAc(Long idEtudiant, Long idAnneeAc) throws DataAccessException;

    public List<Matiere> findBySpecialite(Long id) throws DataAccessException;

    public Matiere findBySpecialiteAnneeNom(String nomMatiere, Long idSpecialite, Long idAnnee) throws DataAccessException;

    public List<Matiere> findMasterBySpecialiteAnneeNom(Long idSpecialite, Long idAnnee) throws DataAccessException;

    public List<Matiere> findBySpecialiteAnneeSemestre(Long idSpecialite, Long idAnnee, Long idSemestre) throws DataAccessException;

    public List<Matiere> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws DataAccessException;


    
}
