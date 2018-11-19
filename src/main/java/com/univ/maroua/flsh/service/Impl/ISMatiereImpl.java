/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IMatieredao;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.service.ISMatiere;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISMatiereImpl implements ISMatiere {

    private IMatieredao matieredao;

    @Override
    public void create(Matiere s) throws ServiceException {
        try {
            matieredao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Matiere s) throws ServiceException {
        try {
            Matiere m = matieredao.findById(s.getId());
            matieredao.delete(m);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Matiere s) throws ServiceException {
        try {
            matieredao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Matiere> findAll() throws ServiceException {
        try {
            return matieredao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Matiere findById(Long id) throws ServiceException {
        try {
            return matieredao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Matiere findByCode(String code) throws ServiceException {
        try {
            return matieredao.findbyCode(code);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IMatieredao getMatieredao() {
        return matieredao;
    }

    public void setMatieredao(IMatieredao matieredao) {
        this.matieredao = matieredao;
    }

    @Override
    public List<Matiere> findListMatiereNiveauSpecialite(Long idEtudiant, Long idNiveau, Long idSpecialite) throws ServiceException {
        try {
            return matieredao.findListMatiereNiveauSpecialite(idEtudiant, idNiveau, idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Matiere> findByUE(Long idUE) throws ServiceException {
        try {
            return matieredao.findListMatiereByUE(idUE);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Matiere> findBySpecialite(Long id) throws ServiceException {
        try {
            return matieredao.findBySpecialite(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Matiere> findListMatiereOpByEtudiantAnneeAc(Long idEtudiant, Long idAnneeAc) throws ServiceException {
        try {
            return matieredao.findListMatiereOpByEtudiantAnneeAc(idEtudiant, idAnneeAc);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Matiere findBySpecialiteAnneeNom(String nomMatiere, Long idSpecialite, Long idAnnee) throws ServiceException {
        try {
            return matieredao.findBySpecialiteAnneeNom(nomMatiere, idSpecialite, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISMatiereImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Matiere> findMasterBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException {
        try {
            return matieredao.findMasterBySpecialiteAnneeNom(idSpecialite, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Matiere> findBySpecialiteAnneeSemestre(Long idSpecialite, Long idAnnee, Long idSemestre) throws ServiceException {
        try {
            return matieredao.findBySpecialiteAnneeSemestre(idSpecialite, idAnnee, idSemestre);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Matiere> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException {
      try {
            return matieredao.findBySpecialiteAnnee(idSpecialite, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
