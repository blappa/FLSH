/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.dao.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.*;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.entities.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lappa
 */
public class IModuledaoImpl extends GenericDao<Module, Long> implements IModuledao {

     @Override
    public Module findbyCode(String code) throws DataAccessException {
        return (Module) getManager().createNamedQuery("Module.findByCode")
                .setParameter("code", code).getSingleResult();
    }
     
    @Override
    public List<Module> findBySemestreAnneeAcSpecialite(Long idAnee, Long idsemestre, Long idSpecialite)  throws DataAccessException {
        try {
            return getManager().createNamedQuery("Module.findBySemestreAnneeAcSpecialite")
                   .setParameter("idAneeAc", idAnee)
                    .setParameter("idsemestre", idsemestre)
                    .setParameter("idSpecialite", idSpecialite)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }

     @Override
    public List<Module> findByAnneeAcSpecialite(Long idAneeAc, Long idSpecialite)  throws DataAccessException {
        try {
            return getManager().createNamedQuery("Module.findByAnneeAcSpecialite")
                   .setParameter("idAneeAc", idAneeAc)
                    .setParameter("idSpecialite", idSpecialite)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }

    @Override
    public List<Module> findByAnnee(Long idAneeAc) throws DataAccessException {
    try {
            return getManager().createNamedQuery("Module.findByAnnee")
                   .setParameter("idAneeAc", idAneeAc)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null; 
    }

    @Override
    public List<Module> findByCodeModule(Long idEtudiant, String codeUE,Long idSpecialite) throws DataAccessException {
     try {
            return getManager().createNamedQuery("Module.findByCodeModuleEtudiant")
                   .setParameter("idEtudiant", idEtudiant)
                    .setParameter("codeUE", codeUE)
                    .setParameter("idSpecialite", idSpecialite)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }
    
     @Override
    public List<Module> findByCodeModulePaye(Long idEtudiant, String codeUE,Long idSpecialite) throws DataAccessException {
     try {
            return getManager().createNamedQuery("Module.findByCodeModuleEtudiantPaye")
                   .setParameter("idEtudiant", idEtudiant)
                    .setParameter("codeUE", '%'+codeUE+'%')
                    .setParameter("idSpecialite", idSpecialite)
                    .setParameter("token", 1)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }

    @Override
    public List<Module> findMaterByCodeAnnee(String code, Long idAnnee,Long niveauId) throws DataAccessException {
         return  getManager().createNamedQuery("Module.findByMasterByCodeAnne")
                .setParameter("code", code)
                 .setParameter("idAnnee", idAnnee)
                 .setParameter("niveauId",niveauId)
                 .getResultList();
        
    }

    @Override
    public List<Module> findByCodeSpecialiteAnnee(Long idAnnee, String codeUE, Long idSpecialite) throws DataAccessException {
   try {
            return getManager().createNamedQuery("Module.findByCodeSpecialiteAnnee")
                   .setParameter("idAnnee", idAnnee)
                    .setParameter("codeUE", codeUE)
                    .setParameter("idSpecialite", idSpecialite)
                    .getResultList();
        } catch (DataAccessException ex) {
            Logger.getLogger(IModuledaoImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }
}
