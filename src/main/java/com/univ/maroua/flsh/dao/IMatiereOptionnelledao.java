/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.MatiereOptionnelle;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface IMatiereOptionnelledao extends IDao<MatiereOptionnelle, Long> {

    public List<Matiere> findListMatiereNiveauSpecialite(String code) throws DataAccessException;
    public List<MatiereOptionnelle> findListMatiereOpByEtudiant(Long idEtudiant,Long idSpecialite) throws DataAccessException;
}
