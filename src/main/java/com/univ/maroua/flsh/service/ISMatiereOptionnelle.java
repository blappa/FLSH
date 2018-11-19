/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.MatiereOptionnelle;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISMatiereOptionnelle {

    public void create(MatiereOptionnelle m) throws ServiceException;

    public void delete(MatiereOptionnelle m) throws ServiceException;

    public void update(MatiereOptionnelle m) throws ServiceException;

    public List<MatiereOptionnelle> findAll() throws ServiceException;

    public MatiereOptionnelle findById(Long id) throws ServiceException;
        public List<MatiereOptionnelle> findListMatiereOpByEtudiant(Long idEtudiant,Long idSpecialite) throws ServiceException;
}
