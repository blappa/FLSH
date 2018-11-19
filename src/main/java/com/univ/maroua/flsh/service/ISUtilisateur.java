/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;


import com.univ.maroua.flsh.entities.Autorisation;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISUtilisateur {

    public void create(Utilisateur u) throws ServiceException;

    public void delete(Utilisateur u) throws ServiceException;

    public void update(Utilisateur u) throws ServiceException;

    public List<Utilisateur> findAll() throws ServiceException;

    public Utilisateur findById(Long id) throws ServiceException;
    
    public Utilisateur findByUsername(String username) throws ServiceException;

   
}
