/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.EtudiantSection;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IEtudiantSectiondao extends IDao<EtudiantSection, Long> {  
    public EtudiantSection findByEtudiantSection(Long idEtudiant,Long idSection) throws DataAccessException;

}