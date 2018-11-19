/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IAnneeAcademiquedao;
import com.univ.maroua.flsh.dao.IEtudiantdao;
import com.univ.maroua.flsh.dao.IMatiereOptionnelledao;
import com.univ.maroua.flsh.dao.IMatieredao;
import com.univ.maroua.flsh.dao.INiveaudao;
import com.univ.maroua.flsh.dao.INotedao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.dao.ISpecialitedao;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.MatiereOptionnelle;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.projection.Pv;
import com.univ.maroua.flsh.service.ISEtudiant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISEtudiantImpl implements ISEtudiant {

    private IEtudiantdao etudiantdao;
    private IMatieredao matieredao;
    private IMatiereOptionnelledao matiereOptionnelledao;
    private ISpecialitedao specialitedao;
    private ISpecialiteEtudiantdao iSpecialiteEtudiantdao;
    private IAnneeAcademiquedao anneeAcademiquedao;
    private INiveaudao niveaudao;
    private INotedao notedao;

    @Override
    public Etudiant create(Etudiant s) throws ServiceException {
        Etudiant etudiant = null;
        try {
            etudiant = etudiantdao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return etudiant;
    }

    @Override
    public void delete(Etudiant s) throws ServiceException {
        try {
            Etudiant e = etudiantdao.findById(s.getId());
            etudiantdao.delete(e);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Etudiant s) throws ServiceException {
        try {
            etudiantdao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Etudiant> findAll() throws ServiceException {
        try {
            return etudiantdao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Etudiant findById(Long id) throws ServiceException {
        try {
            return etudiantdao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Etudiant findByMatricule(String matricule) throws ServiceException {
        try {
            return etudiantdao.findByMatricule(matricule);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IEtudiantdao getEtudiantdao() {
        return etudiantdao;
    }

    public void setEtudiantdao(IEtudiantdao etudiantdao) {
        this.etudiantdao = etudiantdao;
    }

    @Override
    public Specialite findSpecialiteEtudiant(Long idEtudiant) throws ServiceException {
        try {
            return etudiantdao.findSpecialiteEtudiant(idEtudiant);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param idEtudiant
     * @param idAnnée
     * @param idSemestre
     * @return
     * @throws ServiceException cette methode retoune la liste des matiere
     * disponible pour un etudiant donné à un semestre donné et une année
     * accademique donné
     */
    @Override
    public List<Matiere> findListMatiereByEtudiant(Long idEtudiant, Long idSpecialite) throws ServiceException {
        List<Matiere> matiereTmp = null;
        try {
            Specialite s = etudiantdao.findSpecialiteEtudiant(idEtudiant);

            List<Module> listUEBySpecialite = specialitedao.findUEBySpecialiteId(s.getId());

            List<MatiereOptionnelle> matiereOp = new ArrayList<MatiereOptionnelle>();

            List<Matiere> listmatiereByUE = new ArrayList<Matiere>();

            matiereTmp = new ArrayList<Matiere>();

            matiereOp = matiereOptionnelledao.findListMatiereOpByEtudiant(idEtudiant, idSpecialite);
            for (Module module : listUEBySpecialite) {
                listmatiereByUE = matieredao.findListMatiereByUE(module.getId());


                if (listmatiereByUE.size() == 1) {
                    matiereTmp.add(listmatiereByUE.get(0));

                } else {
                    for (Matiere matiere : listmatiereByUE) {

                        for (MatiereOptionnelle matop : matiereOp) {

                            if (matiere.getId().equals(matop.getMatiere().getId())) {
                                matiereTmp.add(matiere);



                            }

                        }
                    }
                }
            }

            //   return matiereTmp;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matiereTmp;
    }

    public IMatieredao getMatieredao() {
        return matieredao;
    }

    public void setMatieredao(IMatieredao matieredao) {
        this.matieredao = matieredao;
    }

    public ISpecialitedao getSpecialitedao() {
        return specialitedao;
    }

    public void setSpecialitedao(ISpecialitedao specialitedao) {
        this.specialitedao = specialitedao;
    }

    @Override
    public List<Etudiant> findEtudiantBySpecialite(Long idSpecialite) throws ServiceException {
        try {
            return etudiantdao.findEtudiantBySpecialite(idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IMatiereOptionnelledao getMatiereOptionnelledao() {
        return matiereOptionnelledao;
    }

    public void setMatiereOptionnelledao(IMatiereOptionnelledao matiereOptionnelledao) {
        this.matiereOptionnelledao = matiereOptionnelledao;
    }

    @Override
    public List<Etudiant> findBySpecialiteAnnee(Long idSpecialite, Long idAnneeAc) throws ServiceException {
        try {
            Specialite specialite = specialitedao.findById(idSpecialite);
            if (specialite.getNom().equals("/")) {
                List<Etudiant> result = etudiantdao.findByNiveauAnnee(specialite.getNiveau().getId(), idAnneeAc);
                Set<Etudiant> hs = new HashSet<>(); //pour vider les doublons
                hs.addAll(result);
                result.clear();
                result.addAll(hs);
                return result;
            }
            return etudiantdao.findBySpecialiteAnnee(idSpecialite, idAnneeAc);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Etudiant> RameneByMatiere(Long idMatiere, int niveau) throws ServiceException {
        try {
            return etudiantdao.RameneByMatiere(idMatiere, niveau);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public boolean esNouveau(Long idEtudiant, Long idSpecialite, Long idAnnee) throws ServiceException {
        try {
            List<AnneeAcademique> annees = anneeAcademiquedao.findByEtudiantSpecialite(idEtudiant, idSpecialite);
            Collections.sort(annees);
            for (int i = 0; i < annees.size(); i++) {
                AnneeAcademique anneeAcademique = annees.get(i);
                if (anneeAcademique.getId().equals(idAnnee)) {
                    try {
                        anneeAcademique = annees.get(i - 1);
                        return false;
                    } catch (IndexOutOfBoundsException ex) {
                        return true;
                    }
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;

    }

    public IAnneeAcademiquedao getAnneeAcademiquedao() {
        return anneeAcademiquedao;
    }

    public void setAnneeAcademiquedao(IAnneeAcademiquedao anneeAcademiquedao) {
        this.anneeAcademiquedao = anneeAcademiquedao;
    }

    @Override
    public List<Etudiant> findByMatiere(Long idMatiere) throws ServiceException {
        try {
            Matiere matiere = matieredao.findById(idMatiere);
            Specialite specialite = matiere.getModule().getSpecialite();
            AnneeAcademique anneeAcademique = matiere.getModule().getAnneeAcademique();
            //on recupere tous les etudiants d'une specialite precise
            List<Etudiant> etudiants = etudiantdao.findBySpecialiteAnnee(specialite.getId(), anneeAcademique.getId());
            //on recupere les specialites de niveau superieur
            int level = specialite.getNiveau().getLevel();
            List<Niveau> niveaus = niveaudao.findBySection(specialite.getNiveau().getSection().getId());
            level++;
            Niveau curentNiveau = null;
            for (Niveau niv : niveaus) {
                if (niv.getLevel() == level) {
                    curentNiveau = niv;
                }
            }
            List<Specialite> specialites = specialitedao.findByNiveau(curentNiveau.getId());
            //on recupere les etudaint du niveau superieur qui ramenet la matiere
            for (Specialite specialite1 : specialites) {
                List<Etudiant> ets = etudiantdao.findBySpecialiteMatiere(specialite1.getId(), idMatiere);
                for (Etudiant e : ets) {
                    etudiants.add(e);
                }

            }
            return etudiants;
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
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
    public List<Etudiant> findBySexe(String sexe) throws ServiceException {
        try {
            return etudiantdao.findBySexe(sexe);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Etudiant> findBySpecialiteAnneeForSyntheses(Long idSpecialite, Long idAnneeAc) throws ServiceException {
        try {
            List<Etudiant> etudiantsCUr = findBySpecialiteAnnee(idSpecialite, idAnneeAc);
            List<Etudiant> etudiantsSup = new LinkedList<>();
            List<Etudiant> etudiantsFinal = new LinkedList<>();
            Specialite specialite = null;
            specialite = specialitedao.findById(idSpecialite);
            int niveau = specialite.getNiveau().getLevel();
            switch (niveau) {
                case 1: //on ajoute les etudiants des niveaus 2 et 3
                    Specialite sp2 = specialitedao.nextSpecialite(idSpecialite); //niveau 2
                    List<Etudiant> etudiants = findBySpecialiteAnnee(sp2.getId(), idAnneeAc);
                    for (Etudiant etudiant : etudiants) {
                        etudiantsSup.add(etudiant);
                    }

                    //niveau 3 en partant chercher dans les specialites
                    List<Specialite> specialites = specialitedao.findByNiveau(specialitedao.nextSpecialite(sp2.getId()).getNiveau().getId());
                    for (Specialite specialite1 : specialites) {
                        List<Etudiant> etudiants1 = findBySpecialiteAnnee(specialite1.getId(), idAnneeAc);
                        for (Etudiant etudiant : etudiants1) {
                            etudiantsSup.add(etudiant);
                        }
                    }
                    break;
                case 2: //on ajoute les etudiants du niveau 3
                    List<Specialite> specialites3 = specialitedao.findByNiveau(specialitedao.nextSpecialite(idSpecialite).getNiveau().getId());
                    for (Specialite specialite1 : specialites3) {
                        List<Etudiant> etudiants1 = findBySpecialiteAnnee(specialite1.getId(), idAnneeAc);
                        for (Etudiant etudiant : etudiants1) {
                            etudiantsSup.add(etudiant);
                        }
                    }
                    break;
                //on ne chevauche pas en master
               /* case 4: //on ajoute les etudiants de master 2
                 Specialite sp5 = specialitedao.nextSpecialite(idSpecialite);
                 List<Etudiant> etudiants5 = findBySpecialiteAnnee(sp5.getId(), idAnneeAc);
                 for (Etudiant etudiant : etudiants5) {
                 etudiantsSup.add(etudiant);
                 }
                 break;*/
            }




            for (int i = 0; i < etudiantsSup.size(); i++) {
                Etudiant etudiant = etudiantsSup.get(i);
                etudiant.setShortable(1);
                etudiantsFinal.add(etudiant);
            }

            for (int i = 0; i < etudiantsCUr.size(); i++) {
                Etudiant etudiant = etudiantsCUr.get(i);
                etudiant.setShortable(0);
                etudiantsFinal.add(etudiant);
            }

            Set<Etudiant> hs = new HashSet<>(); //pour vider les doublons
            hs.addAll(etudiantsFinal);
            etudiantsFinal.clear();
            etudiantsFinal.addAll(hs);

            return etudiantsFinal;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Etudiant> findByDepartement(Long idDepartement) throws ServiceException {
        try {
            return etudiantdao.findByDepartement(idDepartement);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Etudiant> findBySection(Long idSection) throws ServiceException {
        try {
            return etudiantdao.findBySection(idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Etudiant> findByAnnee(Long idAnnee) throws ServiceException {
        try {
            return etudiantdao.findByAnnee(idAnnee);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void deleteStudentForLevel(Long idSpec, Long idAn, int level) throws ServiceException {
        try {
            List<Matiere> matieres = matieredao.findMasterBySpecialiteAnneeNom(idSpec, idAn);
            List<Etudiant> etudiants = etudiantdao.findBySpecialiteAnneeNiveau(idSpec, idAn, level);
            List<SpecialiteEtudiant> specialiteEtudiants = iSpecialiteEtudiantdao.findBySpecialiteAnnee(idSpec, idAn); // 71 94 pour niveau 1
            for (SpecialiteEtudiant specialiteEtudiant : specialiteEtudiants) {
                iSpecialiteEtudiantdao.delete(specialiteEtudiant);
            }
            for (Etudiant etudiant : etudiants) {
                for (Matiere matiere : matieres) {
                    Note note = notedao.findNoteByEtudiantMatiere(etudiant.getId(), matiere.getId());
                    notedao.delete(note);
                }
                etudiantdao.delete(etudiant);
            }
        } catch (DataAccessException ex) {
        }
    }

    public ISpecialiteEtudiantdao getiSpecialiteEtudiantdao() {
        return iSpecialiteEtudiantdao;
    }

    public void setiSpecialiteEtudiantdao(ISpecialiteEtudiantdao iSpecialiteEtudiantdao) {
        this.iSpecialiteEtudiantdao = iSpecialiteEtudiantdao;
    }

    public INotedao getNotedao() {
        return notedao;
    }

    public void setNotedao(INotedao notedao) {
        this.notedao = notedao;
    }

    @Override
    public List<Etudiant> findByAnneeDepartementType(Long anneeId, Long departementId, int type) throws ServiceException {
        try {
            return etudiantdao.findByAnneeDepartementType(anneeId, departementId, type);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Etudiant> findByAnneeSectionType(Long anneeId, Long sectionId, int type) throws ServiceException {
        try {
            return etudiantdao.findByAnneeSectionType(anneeId, sectionId, type);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
