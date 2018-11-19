/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Autorisation;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISAutorisation {

    public Autorisation findById(Long id) throws ServiceException;

    public void create(Autorisation u) throws ServiceException;

    public void delete(Autorisation u) throws ServiceException;

    public void update(Autorisation s) throws ServiceException;

    public List<Autorisation> findByAll() throws ServiceException;
}
