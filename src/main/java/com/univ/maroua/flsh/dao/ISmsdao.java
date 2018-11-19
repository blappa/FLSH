/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.IDao;
import com.univ.maroua.flsh.entities.Sms;

/**
 *
 * @author lappa
 */
public interface ISmsdao extends IDao<Sms, Long> {
    public Sms findByAnneeSectionTypeEtudiant(Long anneeId,Long sectionId,int type,Long etudiantId) throws DataAccessException; 
}
