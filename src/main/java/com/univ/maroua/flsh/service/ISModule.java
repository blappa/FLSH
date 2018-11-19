/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISModule {

    public Module create(Module s) throws ServiceException;

    public void delete(Module s) throws ServiceException;

    public void update(Module s) throws ServiceException;

    public List<Module> findAll() throws ServiceException;

    public Module findById(Long id) throws ServiceException;

    public List<Module> findBySemestreAnneeAcSpecialite(Long idAneeAc, Long idsemestre, Long idSpecialite) throws ServiceException;

    public List<Module> findByAnneeAcSpecialite(Long idAneeAc, Long idSpecialite) throws ServiceException;

    public List<Module> findByAnnee(Long idAneeAc) throws ServiceException;

    public Module findLastUE(Long idEtudiant, Long idUE, int reglementaires) throws ServiceException;

    public Module findbyCode(String code) throws ServiceException;

    public List<Module> findbyMasterUE(List<Module> modules) throws ServiceException;

    public String getIntitules(Long moduleId) throws ServiceException;

    public String getCodes(Long moduleId) throws ServiceException;

    public List<Module> findByCodeSpecialiteAnnee(Long idAnnee, String codeUE, Long idSpecialite) throws ServiceException;
}
