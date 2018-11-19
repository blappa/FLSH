/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IAnneeAcademiquedao;
import com.univ.maroua.flsh.dao.IEtudiantdao;
import com.univ.maroua.flsh.dao.IInscriptiondao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.service.ISInscription;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISIncriptionImpl implements ISInscription {

    private IInscriptiondao inscriptiondao;
    private IEtudiantdao etudiantdao;
    private IAnneeAcademiquedao anneeAcademiquedao;

    @Override
    public void create(Inscription s) throws ServiceException {
        try {
            inscriptiondao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Inscription s) throws ServiceException {
        try {
            Inscription h = inscriptiondao.findById(s.getId());
            inscriptiondao.delete(h);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Inscription s) throws ServiceException {
        try {
            inscriptiondao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Inscription> findAll() throws ServiceException {
        try {
            return inscriptiondao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Inscription findById(Long id) throws ServiceException {
        try {
            return inscriptiondao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IInscriptiondao getInscriptiondao() {
        return inscriptiondao;
    }

    public void setInscriptiondao(IInscriptiondao inscriptiondao) {
        this.inscriptiondao = inscriptiondao;
    }

    public IEtudiantdao getEtudiantdao() {
        return etudiantdao;
    }

    public void setEtudiantdao(IEtudiantdao etudiantdao) {
        this.etudiantdao = etudiantdao;
    }

    @Override
    public List<Etudiant> findByTypeAnnee(int type, Long idAnnee) throws ServiceException {
        try {
            return inscriptiondao.findByTypeAnnee(type, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean aPayeByTypeAnnee(int type, Long idAnnee, String matricule, Long idSection) throws ServiceException {
        try {
            if ((idAnnee == 2L)||(idSection==null)) { //ie 2014/2015
                return inscriptiondao.aPayeByTypeAnnee(type, idAnnee, matricule);
            }
            return inscriptiondao.aPayeByTypeAnneeSection(type, idAnnee, matricule, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * permet de savoir si un etudiant a le droit de composser une matiere a un
     * semestre donne
     *
     * @param semestre
     * @param idAnnee
     * @param matricule
     * @return vrai s'il l'est et faux sinon
     * @throws ServiceException
     */
    @Override
    public boolean estEligible(int semestre, Long idAnnee, String matricule,Long idSection) throws ServiceException {
        try {
            AnneeAcademique an = anneeAcademiquedao.findById(idAnnee);
            if (an.getAnnee().equals("2013/2014")) {
                return true;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (semestre % 2 == 0) { //si nous sommes au second semestre
            if (aPayeByTypeAnnee(3, idAnnee, matricule,idSection)) //s'il a paye les deux tranches
            {
                return true;
            } else if (aPayeByTypeAnnee(1, idAnnee, matricule,idSection) && aPayeByTypeAnnee(2, idAnnee, matricule,idSection)) //s'il a pye separement
            {
                return true;
            }
        } else { //nous sommes forcement au premier semestre
            if (aPayeByTypeAnnee(1, idAnnee, matricule,idSection)) //s'il a paye la premiere tranche
            {
                return true;
            } else if (aPayeByTypeAnnee(3, idAnnee, matricule,idSection)) //ou s'il a paye la totalite
            {
                return true;
            }
        }
        return false;
    }

    public IAnneeAcademiquedao getAnneeAcademiquedao() {
        return anneeAcademiquedao;
    }

    public void setAnneeAcademiquedao(IAnneeAcademiquedao anneeAcademiquedao) {
        this.anneeAcademiquedao = anneeAcademiquedao;
    }
}
