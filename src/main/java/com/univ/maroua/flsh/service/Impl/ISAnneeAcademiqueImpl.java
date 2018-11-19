/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IAnneeAcademiquedao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISAnneeAcademiqueImpl implements ISAnneeAcademique {

    private IAnneeAcademiquedao anneeAcademiquedao;

    @Override
    public AnneeAcademique create(AnneeAcademique s) throws ServiceException {
        AnneeAcademique anneeAcademique = new AnneeAcademique();
        try {
            anneeAcademique = anneeAcademiquedao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return anneeAcademique;
    }

    @Override
    public void delete(AnneeAcademique s) throws ServiceException {
        try {
            AnneeAcademique a = anneeAcademiquedao.findById(s.getId());
            anneeAcademiquedao.delete(a);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(AnneeAcademique s) throws ServiceException {
        try {
            anneeAcademiquedao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<AnneeAcademique> findAll() throws ServiceException {
        try {
            List<AnneeAcademique> annees= anneeAcademiquedao.findAll();
            Collections.sort(annees);
            return annees;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public AnneeAcademique findById(Long id) throws ServiceException {
        try {
            return anneeAcademiquedao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public AnneeAcademique findByAnnee(String annee) throws ServiceException {
        try {
            return anneeAcademiquedao.findByAnnee(annee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IAnneeAcademiquedao getAnneeAcademiquedao() {
        return anneeAcademiquedao;
    }

    public void setAnneeAcademiquedao(IAnneeAcademiquedao anneeAcademiquedao) {
        this.anneeAcademiquedao = anneeAcademiquedao;
    }

    @Override
    public List<AnneeAcademique> findByEtudiantSpecialite(Long idEtudiant, Long idSpecialite) throws ServiceException {
        try {
            return anneeAcademiquedao.findByEtudiantSpecialite(idEtudiant, idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public AnneeAcademique previous(String annee) throws ServiceException {
        AnneeAcademique result = new AnneeAcademique();
        try {
            AnneeAcademique an = anneeAcademiquedao.findByAnnee(annee);
            List<AnneeAcademique> anneeAcademiques = anneeAcademiquedao.findAll();
            Collections.sort(anneeAcademiques);
            if (anneeAcademiques.size() == 1) {
                result = an;
            } else if (anneeAcademiques.get(0).getAnnee().equals(annee)) {
                result = an;
            } else {
                for (int i = 0; i < anneeAcademiques.size(); i++) {
                    AnneeAcademique anneeAcademique = anneeAcademiques.get(i);
                    if (anneeAcademique.getAnnee().equals(annee)) {
                        result = anneeAcademiques.get(i - 1);
                        break;
                    }
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public AnneeAcademique next(String annee) throws ServiceException {
        AnneeAcademique result = new AnneeAcademique();
        try {
            AnneeAcademique an = anneeAcademiquedao.findByAnnee(annee);
            List<AnneeAcademique> anneeAcademiques = anneeAcademiquedao.findAll();
            Collections.sort(anneeAcademiques);
            //on repere la position de l'annee courante
            for (int i = 0; i < anneeAcademiques.size(); i++) {
                AnneeAcademique anneeAcademique = anneeAcademiques.get(i);
                if (anneeAcademique.getAnnee().equals(annee)) {
                    result = anneeAcademiques.get(i + 1);
                    break;
                }
            }

        } catch (DataAccessException ex) {
            Logger.getLogger(ISAnneeAcademiqueImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }
    
   
    
    
}
