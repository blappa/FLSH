/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import java.util.List;

/**
 *
 * @author lappa
 */
public interface ISpecialiteAnneedao extends IDao<SpecialiteAnnee, Long> {

    public SpecialiteAnnee findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws DataAccessException;
}