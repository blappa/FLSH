/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISSection {

    public void create(Section s) throws ServiceException;

    public void delete(Section s) throws ServiceException;

    public void update(Section s) throws ServiceException;

    public List<Section> findAll() throws ServiceException;

    public Section findById(Long id) throws ServiceException;

    public Section findByCode(String code) throws ServiceException;

    public List<Section> findByDepartement(Long id) throws ServiceException;

    public List<Section> findByEtudiantAnnee(Long idEtudiant, Long idAnnee) throws ServiceException;

    public List<Section> findByEtudiantIdNiveau(String matricule, int niveauLevel) throws ServiceException;

    public List<Section> findByEtudiant(String matricule) throws ServiceException;
}
