/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.dao.IUtilisateurdao;

/**
 *
 * @author lappa
 */
public class IUtilisateurdaoImpl extends GenericDao<Utilisateur, Long> implements IUtilisateurdao {

    @Override
    public Utilisateur findByUsername(String username) throws DataAccessException {
        return (Utilisateur) getManager().createNamedQuery("Utilisateur.findByUsername").
                setParameter("username", username).getSingleResult();
    }
}
