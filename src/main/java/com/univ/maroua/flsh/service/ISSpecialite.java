/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISSpecialite {

    public void create(Specialite s) throws ServiceException;

    public void delete(Specialite s) throws ServiceException;

    public void update(Specialite s) throws ServiceException;

    public List<Specialite> findAll() throws ServiceException;

    public Specialite findById(Long id) throws ServiceException;

    public Specialite findByCode(String code) throws ServiceException;

    public List<Etudiant> findEtudiantBySpecialite(String nomSpecialite) throws ServiceException;

    public List<Module> findUEBySpecialiteId(Long IdSpecialite) throws ServiceException;

    public List<Specialite> findByNiveau(Long id) throws ServiceException;

    public List<Specialite> findByEtudiant(String matricule) throws ServiceException;

    public Specialite nextSpecialite(Long idSpecialite) throws ServiceException;

    public List<Specialite> findByEtudiantAnnee(Long etudiantId, Long anneeId) throws ServiceException;
}
