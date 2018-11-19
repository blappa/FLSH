/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISNiveau {
    
    public void create(Niveau s)throws ServiceException;

    public void delete(Niveau s)throws ServiceException;

    public void update(Niveau s)throws ServiceException;

    public List<Niveau> findAll()throws ServiceException;

    public Niveau findById(Long id)throws ServiceException;
    
    public Niveau findByLevel(int  level)throws ServiceException;
    
    public Niveau findLastLevel(Long  idEtudiant)throws ServiceException;
    
    public List<Niveau> findBySection(Long id) throws ServiceException;

    public String[] formatSemestre(Long level);
    
    public  Niveau nextNiveau(Long idNiveau) throws ServiceException;
}
