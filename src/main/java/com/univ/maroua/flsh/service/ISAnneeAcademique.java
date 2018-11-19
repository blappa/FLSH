/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISAnneeAcademique {

    public AnneeAcademique create(AnneeAcademique s)throws ServiceException;

    public void delete(AnneeAcademique s)throws ServiceException;

    public void update(AnneeAcademique s)throws ServiceException;

    public List<AnneeAcademique> findAll()throws ServiceException;

    public AnneeAcademique findById(Long id)throws ServiceException;
    
    public AnneeAcademique findByAnnee(String annee)throws ServiceException;
    
    public List<AnneeAcademique> findByEtudiantSpecialite(Long idEtudiant, Long idSpecialite) throws ServiceException;
   
    public AnneeAcademique previous(String annee) throws ServiceException;
    
    public AnneeAcademique next(String annee) throws ServiceException;
    
   
}
