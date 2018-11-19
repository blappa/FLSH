/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.INiveaudao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISNiveau;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISNiveauImpl implements ISNiveau {

    private INiveaudao niveaudao;
    private ISpecialiteEtudiantdao specialiteEtudiantdao;

    @Override
    public void create(Niveau s) throws ServiceException {
        try {
            niveaudao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNiveauImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(Niveau s) throws ServiceException {
        try {
            Niveau n = niveaudao.findById(s.getId());
            niveaudao.delete(n);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNiveauImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Niveau s) throws ServiceException {
        try {
            niveaudao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNiveauImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Niveau> findAll() throws ServiceException {
        try {
            return niveaudao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNiveauImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Niveau findById(Long id) throws ServiceException {
        try {
            return niveaudao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNiveauImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public INiveaudao getNiveaudao() {
        return niveaudao;
    }

    public void setNiveaudao(INiveaudao niveaudao) {
        this.niveaudao = niveaudao;
    }

    @Override
    public Niveau findByLevel(int level) throws ServiceException {
        try {
            return niveaudao.findByLevel(level);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNiveauImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Niveau> findBySection(Long id) throws ServiceException {
        try {
            return niveaudao.findBySection(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Niveau findLastLevel(Long idEtudiant) throws ServiceException {
        try {
            
            List<SpecialiteEtudiant> spe = specialiteEtudiantdao.findByIdEtudiant(idEtudiant);
            return spe.get(0).getSpecialite().getNiveau();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSectionImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public String[] formatSemestre(Long level) {
        String[] tmp = new String[5];
        if (level == 1L) {
            tmp[0] = "UN";
            tmp[1] = "DEUX";
        }
        if (level == 2L) {
            tmp[0] = "TROIS";
            tmp[1] = "QUATRE";
        }
        if (level == 3L) {
            tmp[0] = "CINQ";
            tmp[1] = "SIX";
        }
        if (level == 4L) {
            tmp[0] = "SEPT";
            tmp[1] = "HUIT";
        }
        if (level == 5L) {
            tmp[0] = "NEUF";
            tmp[1] = "DIX";
        }
        return tmp;
    }

    public ISpecialiteEtudiantdao getSpecialiteEtudiantdao() {
        return specialiteEtudiantdao;
    }

    public void setSpecialiteEtudiantdao(ISpecialiteEtudiantdao specialiteEtudiantdao) {
        this.specialiteEtudiantdao = specialiteEtudiantdao;
    }

    @Override
    public Niveau nextNiveau(Long idNiveau) throws ServiceException {
        Niveau niveau=null;
           niveau = findById(idNiveau);
            Section section = niveau.getSection();
            List<Niveau> niveaus = findBySection(section.getId());
            if (niveau.getLevel() == 3 || niveau.getLevel() == 5) { //on retrouve la premiere specialite du meme niveau
               return niveau;
            } else {
                for (int i = 0; i < niveaus.size(); i++) {
                    Niveau niveau1 = niveaus.get(i);
                    if (niveau1.getLevel() == niveau.getLevel()) {
                        niveau = niveaus.get(i + 1);
                        break;
                    }
                }
            }   
            return niveau;
      }
}
