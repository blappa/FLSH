/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Statistique;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISStatistique {

    public Statistique create(Statistique s)throws ServiceException;

    public void delete(Statistique s)throws ServiceException;

    public void update(Statistique s)throws ServiceException;

    public List<Statistique> findAll()throws ServiceException;

    public Statistique findById(Long id)throws ServiceException;
    
    public Statistique findBySpecialiteAnnee(Long idSpecialite, Long idAnnee)throws ServiceException;
    
   
   
}
