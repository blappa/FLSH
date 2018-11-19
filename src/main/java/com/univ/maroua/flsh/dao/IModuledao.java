/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.*;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IModuledao extends IDao<Module, Long> {

    public List<Module> findBySemestreAnneeAcSpecialite(Long idAneeAc, Long idsemestre, Long idSpecialite) throws DataAccessException;

    public List<Module> findByAnneeAcSpecialite(Long idAneeAc, Long idSpecialite) throws DataAccessException;

    public List<Module> findByAnnee(Long idAneeAc) throws DataAccessException;

    public List<Module> findByCodeModule(Long idEtudiant, String codeUE, Long idSpecialite) throws DataAccessException;

    public List<Module> findByCodeModulePaye(Long idEtudiant, String codeUE, Long idSpecialite) throws DataAccessException;

    public Module findbyCode(String code) throws DataAccessException;

    public List<Module> findMaterByCodeAnnee(String code, Long idAnnee, Long niveauId) throws DataAccessException;

    public List<Module> findByCodeSpecialiteAnnee(Long idAnnee, String codeUE, Long idSpecialite) throws DataAccessException;
}
