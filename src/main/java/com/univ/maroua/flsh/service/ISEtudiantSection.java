/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service;

import com.univ.maroua.flsh.entities.EtudiantSection;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISEtudiantSection {
    
    public void create(EtudiantSection s)throws ServiceException;

    public void delete(EtudiantSection s)throws ServiceException;

    public void update(EtudiantSection s)throws ServiceException;

    public List<EtudiantSection> findAll()throws ServiceException;

    public EtudiantSection findById(Long id)throws ServiceException;
        
    public EtudiantSection findByEtudiantSection(Long idEtudiant,Long idSection) throws ServiceException;
    
}
