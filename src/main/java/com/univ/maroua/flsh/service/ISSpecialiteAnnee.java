/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISSpecialiteAnnee {

    public SpecialiteAnnee create(SpecialiteAnnee s) throws ServiceException;

    public void delete(SpecialiteAnnee s) throws ServiceException;

    public void update(SpecialiteAnnee s) throws ServiceException;

    public List<SpecialiteAnnee> findAll() throws ServiceException;

    public SpecialiteAnnee findById(Long id) throws ServiceException;

    public SpecialiteAnnee findBySpecialiteAnnee( Long idSpecialite,Long idAnnee) throws ServiceException;

  }
