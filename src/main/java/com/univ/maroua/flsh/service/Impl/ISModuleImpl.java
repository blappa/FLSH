/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IAnneeAcademiquedao;
import com.univ.maroua.flsh.dao.IEtudiantdao;
import com.univ.maroua.flsh.dao.IInscriptiondao;
import com.univ.maroua.flsh.dao.IMatieredao;
import com.univ.maroua.flsh.dao.IModuledao;
import com.univ.maroua.flsh.dao.INotedao;
import com.univ.maroua.flsh.dao.ISemestredao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISModule;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISModuleImpl implements ISModule {

    private IModuledao moduledao;
    private IMatieredao matieredao;
    private ISpecialiteEtudiantdao specialiteEtudiantdao;
    private IInscriptiondao inscriptiondao;
    private ISemestredao semestredao;
    private IAnneeAcademiquedao anneeAcademiquedao;
    private IEtudiantdao etudiantdao;

    @Override
    public Module create(Module s) throws ServiceException {
        try {
            return moduledao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void delete(Module s) throws ServiceException {
        try {
            Module mod = moduledao.findById(s.getId());
            moduledao.delete(mod);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Module s) throws ServiceException {
        try {
            moduledao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Module> findAll() throws ServiceException {
        try {
            return moduledao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Module findById(Long id) throws ServiceException {
        try {
            return moduledao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IModuledao getModuledao() {
        return moduledao;
    }

    public void setModuledao(IModuledao moduledao) {
        this.moduledao = moduledao;
    }

    @Override
    public List<Module> findBySemestreAnneeAcSpecialite(Long idAneeAc, Long idsemestre, Long idSpecialite) throws ServiceException {
        try {
            List<Module> modules = moduledao.findBySemestreAnneeAcSpecialite(idAneeAc, idsemestre, idSpecialite);
            modules = findbyMasterUE(modules);
            Collections.sort(modules);
            return modules;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Module> findByAnneeAcSpecialite(Long idAneeAc, Long idSpecialite) throws ServiceException {
        try {
            List<Module> modules = moduledao.findByAnneeAcSpecialite(idAneeAc, idSpecialite);
            Collections.sort(modules);
            return modules;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Module> findByAnnee(Long idAneeAc) throws ServiceException {
        try {
            return moduledao.findByAnnee(idAneeAc);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Module findLastUE(Long idEtudiant, Long idUE, int reglementaire) throws ServiceException {
        Module module = null;
        List<Module> modules = null;
        List<Semestre> semestres = null;
        try {
            Etudiant etudiant = etudiantdao.findById(idEtudiant);
            module = moduledao.findById(idUE);
            modules = moduledao.findByCodeModule(idEtudiant, module.getCode(), module.getSpecialite().getId());
            //chercher le semestre 2 de la specialite et voire avec insc s'il est eligible pour une annee
            semestres = semestredao.findBySpecialite(module.getSpecialite().getId());
            for (Module module1 : modules) {
                if (module1.getAnneeAcademique().getId().compareTo(module.getAnneeAcademique().getId()) == 0) {
                    return module1;
                } else if (module1.getAnneeAcademique().getId().compareTo(module.getAnneeAcademique().getId()) < 0) {
                    if (module1.getAnneeAcademique().getAnnee().equals("2013/2014")) { //on laisse il n'y avait pas de liste
                        return module1;
                    } else {//la c une annee anterierue et on voit s'il est slvable
                        if (estEligible(semestres.get(1).getLevel(), module1.getAnneeAcademique().getId(), etudiant.getMatricule(), module.getSpecialite().getId())) {
                            return module1;
                        }
                    }
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return module;
    }

    @Override
    public Module findbyCode(String code) throws ServiceException {
        try {
            return moduledao.findbyCode(code);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Module> findbyMasterUE(List<Module> modules) throws ServiceException {
        List<Module> result = new LinkedList<Module>();
        for (Module module : modules) {
            try {
                List<Matiere> matieres = matieredao.findListMatiereByUE(module.getId());
                if (!matieres.isEmpty()) {
                    result.add(module);
                } else {
                    List<Module> mods = moduledao.findMaterByCodeAnnee(module.getCode(), module.getAnneeAcademique().getId(), module.getSpecialite().getNiveau().getId());
                    for (Module module1 : mods) {
                        if (module1.getSpecialite().getNom().equals("/")) {
                            result.add(module1);
                            break;
                        }
                    }
                }
            } catch (DataAccessException ex) {
                Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return result;
    }

    public ISpecialiteEtudiantdao getSpecialiteEtudiantdao() {
        return specialiteEtudiantdao;
    }

    public void setSpecialiteEtudiantdao(ISpecialiteEtudiantdao specialiteEtudiantdao) {
        this.specialiteEtudiantdao = specialiteEtudiantdao;
    }

    public IInscriptiondao getInscriptiondao() {
        return inscriptiondao;
    }

    public void setInscriptiondao(IInscriptiondao inscriptiondao) {
        this.inscriptiondao = inscriptiondao;
    }

    public ISemestredao getSemestredao() {
        return semestredao;
    }

    public void setSemestredao(ISemestredao semestredao) {
        this.semestredao = semestredao;
    }

    private boolean estEligible(int semestre, Long idAnnee, String matricule, Long idSection) throws ServiceException {
        try {
            AnneeAcademique an = anneeAcademiquedao.findById(idAnnee);
            if (an.getAnnee().equals("2013/2014")) {
                return true;
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (semestre % 2 == 0) { //si nous sommes au second semestre
            if (aPayeByTypeAnnee(3, idAnnee, matricule, idSection)) //s'il a paye les deux tranches
            {
                return true;
            } else if (aPayeByTypeAnnee(1, idAnnee, matricule, idSection) && aPayeByTypeAnnee(2, idAnnee, matricule, idSection)) //s'il a pye separement
            {
                return true;
            }
        } else { //nous sommes forcement au premier semestre
            if (aPayeByTypeAnnee(1, idAnnee, matricule, idSection)) //s'il a paye la premiere tranche
            {
                return true;
            } else if (aPayeByTypeAnnee(3, idAnnee, matricule, idSection)) //ou s'il a paye la totalite
            {
                return true;
            }
        }
        return false;
    }

    private boolean aPayeByTypeAnnee(int type, Long idAnnee, String matricule, Long idSection) throws ServiceException {
        try {
            if (idAnnee == 2L) { //ie 2014/2015
                return inscriptiondao.aPayeByTypeAnnee(type, idAnnee, matricule);
            }
            return inscriptiondao.aPayeByTypeAnneeSection(type, idAnnee, matricule, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISIncriptionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public IAnneeAcademiquedao getAnneeAcademiquedao() {
        return anneeAcademiquedao;
    }

    public void setAnneeAcademiquedao(IAnneeAcademiquedao anneeAcademiquedao) {
        this.anneeAcademiquedao = anneeAcademiquedao;
    }

    public IEtudiantdao getEtudiantdao() {
        return etudiantdao;
    }

    public void setEtudiantdao(IEtudiantdao etudiantdao) {
        this.etudiantdao = etudiantdao;
    }

    @Override
    public String getIntitules(Long moduleId) throws ServiceException {
        String result = "";
        try {
            List<Matiere> matieres = matieredao.findListMatiereByUE(moduleId);
            for (Matiere matiere : matieres) {
                if (matiere.getIntitule() != null) {
                    if (!matiere.getIntitule().isEmpty()) {
                        result += matiere.getIntitule() + "/";
                    }
                }
            }
            if (!result.isEmpty()) {
                result = result.substring(0, (result.length() - 1));
            }else {
                Module module = moduledao.findById(moduleId);
                result = module.getIntitule();
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public String getCodes(Long moduleId) throws ServiceException {
        String result = "";
        try {
            List<Matiere> matieres = matieredao.findListMatiereByUE(moduleId);
            for (Matiere matiere : matieres) {
                if (matiere.getCode() != null) {
                    result += matiere.getCode() + "/";
                }
            }
            if (!result.isEmpty()) {
                result = result.substring(0, (result.length() - 1));
            } else {
                Module module = moduledao.findById(moduleId);
                result = module.getTargetCode();
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    @Override
    public List<Module> findByCodeSpecialiteAnnee(Long idAnnee, String codeUE, Long idSpecialite) throws ServiceException {
    try {
            List<Module> modules = moduledao.findByCodeSpecialiteAnnee(idAnnee, codeUE, idSpecialite);
            return modules;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISModuleImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    
    }
}
