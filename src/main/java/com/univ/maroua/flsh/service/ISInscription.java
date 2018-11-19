/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISInscription {

    public void create(Inscription s) throws ServiceException;

    public void delete(Inscription s) throws ServiceException;

    public void update(Inscription s) throws ServiceException;

    public List<Inscription> findAll() throws ServiceException;

    public Inscription findById(Long id) throws ServiceException;

    public List<Etudiant> findByTypeAnnee(int type, Long idAnnee) throws ServiceException;

    public boolean aPayeByTypeAnnee(int type, Long idAnnee, String matricule,Long idSection) throws ServiceException;
    
    public boolean estEligible(int semestre, Long idAnnee, String matricule,Long idSection) throws ServiceException;
}
