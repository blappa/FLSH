/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISDepartement {

    public void create(Departement s) throws ServiceException;

    public void delete(Departement s)throws ServiceException;

    public void update(Departement s)throws ServiceException;

    public List<Departement> findAll()throws ServiceException;

    public Departement findById(Long id)throws ServiceException;
    
    public Departement findBySigle(String sigle)throws ServiceException;
}
