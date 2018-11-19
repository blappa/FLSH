/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISSemestre {

    public void create(Semestre s) throws ServiceException;

    public void delete(Semestre s)throws ServiceException;

    public void update(Semestre s)throws ServiceException;

    public List<Semestre> findAll()throws ServiceException;

    public Semestre findById(Long id)throws ServiceException;
    
    public Semestre findByLevel(int level)throws ServiceException;
    
    public List<Semestre> findBySpecialite(Long idSpecilaite)throws ServiceException;
}
