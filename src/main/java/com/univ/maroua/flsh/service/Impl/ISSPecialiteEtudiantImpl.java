/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISSPecialiteEtudiantImpl implements ISSpecialiteEtudiant {

    private ISpecialiteEtudiantdao specialiteEtudiantdao;

    @Override
    public void create(SpecialiteEtudiant s) throws ServiceException {
        try {
            specialiteEtudiantdao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(SpecialiteEtudiant s) throws ServiceException {
        try {
            SpecialiteEtudiant speEtu = specialiteEtudiantdao.findById(s.getId());
            specialiteEtudiantdao.delete(speEtu);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(SpecialiteEtudiant s) throws ServiceException {
        try {
            specialiteEtudiantdao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<SpecialiteEtudiant> findAll() throws ServiceException {
        try {
            return specialiteEtudiantdao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SpecialiteEtudiant findById(Long id) throws ServiceException {
        try {
            return specialiteEtudiantdao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ISpecialiteEtudiantdao getSpecialiteEtudiantdao() {
        return specialiteEtudiantdao;
    }

    public void setSpecialiteEtudiantdao(ISpecialiteEtudiantdao specialiteEtudiantdao) {
        this.specialiteEtudiantdao = specialiteEtudiantdao;
    }

    @Override
    public List<SpecialiteEtudiant> findByMatriculeEtudiant(String matricule) throws ServiceException {
        try {
            return specialiteEtudiantdao.findByMatriculeEtudiant(matricule);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByEtudiantLevel(Long idEtudiant, int level) throws ServiceException {
        try {
            return specialiteEtudiantdao.findByEtudiantLevel(idEtudiant, level);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public SpecialiteEtudiant findByEtudiantSpecialiteAnnee(Long idEtudiant, Long idAnnee, Long idSpecialite) throws ServiceException {
        try {
            return specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(idEtudiant, idAnnee, idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByEtudiantNiveauAnnee(Long idEtudiant, Long idNiveau, Long idAnnee) throws ServiceException {
        try {
            List<SpecialiteEtudiant> result = specialiteEtudiantdao.findByEtudiantNiveauAnnee(idEtudiant, idNiveau, idAnnee);
            if (result.isEmpty()) {
                throw new NoResultException();
            } else {
                return result;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnnee) throws ServiceException {
        try {
            return specialiteEtudiantdao.findBySpecialiteAnnee(idSpecialite, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByAnneeId(Long idAnnee) throws ServiceException {
        try {
            return specialiteEtudiantdao.findByAnneeId(idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByDepartementAnnee(Long idDepartement, Long idAnnee) throws ServiceException {

        try {
            return specialiteEtudiantdao.findByDepartementAnnee(idDepartement, idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws ServiceException {

        try {
            return specialiteEtudiantdao.findByEtudiantSectionNiveau(matricule, level, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
     @Override
    public List<SpecialiteEtudiant> findByEtudiantSectionNiveauAnnee(String matricule, int level, Long idSection,Long anneeId) throws ServiceException {

        try {
            return specialiteEtudiantdao.findByEtudiantSectionNiveauAnnee(matricule, level, idSection,anneeId);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<SpecialiteEtudiant> findByEtudiantSection(String matricule, Long idSection) throws ServiceException {

        try {
            return specialiteEtudiantdao.findByEtudiantSection(matricule, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
