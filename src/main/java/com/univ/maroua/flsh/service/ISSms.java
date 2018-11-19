/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Sms;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISSms {

    public void create(Sms s) throws ServiceException;

    public void delete(Sms s) throws ServiceException;

    public void update(Sms s) throws ServiceException;

    public List<Sms> findAll() throws ServiceException;

    public Sms findById(Long id) throws ServiceException;

   public Sms findByAnneeSectionTypeEtudiant(Long anneeId,Long sectionId,int type,Long etudiantId) throws ServiceException;
}
