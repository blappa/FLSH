/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.service.Impl;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.dao.IAnneeAcademiquedao;
import com.univ.maroua.flsh.dao.IEtudiantSectiondao;
import com.univ.maroua.flsh.dao.IEtudiantdao;
import com.univ.maroua.flsh.dao.IMatieredao;
import com.univ.maroua.flsh.dao.IModuledao;
import com.univ.maroua.flsh.dao.INiveaudao;
import com.univ.maroua.flsh.dao.INotedao;
import com.univ.maroua.flsh.dao.ISpecialiteAnneedao;
import com.univ.maroua.flsh.dao.ISpecialiteEtudiantdao;
import com.univ.maroua.flsh.dao.ISpecialitedao;
import com.univ.maroua.flsh.dao.IStatistiquedao;

import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.EtudiantSection;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.entities.Statistique;

import com.univ.maroua.flsh.projection.DelStat;
import com.univ.maroua.flsh.projection.Pv;
import com.univ.maroua.flsh.service.ISNote;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.NoResultException;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author lappa
 */
@Transactional
public class ISNoteImpl implements ISNote {

    private INotedao notedao;
    private IModuledao moduledao;
    private IMatieredao matieredao;
    private IEtudiantdao etudiantdao;
    private IAnneeAcademiquedao anneeAcademiquedao;
    private ISpecialiteEtudiantdao specialiteEtudiantdao;
    private ISpecialitedao specialitedao;
    private INiveaudao niveaudao;
    private IStatistiquedao statistiquedao;
    private IEtudiantSectiondao etudiantSectiondao;
    private ISpecialiteAnneedao specialiteAnneedao;

    public ISpecialiteAnneedao getSpecialiteAnneedao() {
        return specialiteAnneedao;
    }

    public void setSpecialiteAnneedao(ISpecialiteAnneedao specialiteAnneedao) {
        this.specialiteAnneedao = specialiteAnneedao;
    }

    public IModuledao getModuledao() {
        return moduledao;
    }

    public void setModuledao(IModuledao moduledao) {
        this.moduledao = moduledao;
    }

    public IMatieredao getMatieredao() {
        return matieredao;
    }

    public void setMatieredao(IMatieredao matieredao) {
        this.matieredao = matieredao;
    }

    @Override
    public Note create(Note s) throws ServiceException {
        try {
            return notedao.create(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public void delete(Note s) throws ServiceException {
        try {
            Note n = notedao.findById(s.getId());
            notedao.delete(n);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void update(Note s) throws ServiceException {
        try {
            notedao.update(s);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Note> findAll() throws ServiceException {
        try {
            return notedao.findAll();
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Note> filters(Long matiereId, Long anneeAcademiqueId) throws ServiceException {
        List<Note> notes = null;
        try {
            notes = notedao.filters(matiereId, anneeAcademiqueId);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return notes;
    }

    @Override
    public Note findById(Long id) throws ServiceException {
        try {
            return notedao.findById(id);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Note calcul(Note n) {
        Matiere matiere = n.getMatiere();
        float td = 0.15f, tpe = 0.15f, cc = 0.2f, ex = 0.5f; //formule par defaut
        if (matiere.getFormule() != null) { //on parse la formule et on adapte
            try {
                StringTokenizer st = new StringTokenizer(matiere.getFormule(), "-");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    td = Integer.parseInt(tab[0]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    tpe = Integer.parseInt(tab[1]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    cc = Integer.parseInt(tab[2]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    ex = Integer.parseInt(tab[3]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
            } catch (NullPointerException ee) {
            }
        }
        boolean emptyData = false;
        float moy = 0;
        //on s'assure que les champs sont non vides
        if ((n.getTpe() == null) || (n.getTd() == null) || (n.getCc() == null) || (n.getExamen() == null)) {
            emptyData = true;
        }
        if (!emptyData) {
            moy = (float) ((n.getTpe() * tpe) + (n.getTd() * td) + (n.getCc() * cc) + (n.getExamen() * ex));
            try {
                if (n.getRattrapage() == null) {
                    moy = (float) ((n.getTpe() * tpe) + (n.getTd() * td) + (n.getCc() * cc) + (n.getExamen() * ex));
                } else {
                    int lev = n.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                    if (((moy > 11f) && (lev <= 3)) || ((moy >= 12f) && (lev > 3))) {
                        n.setRattrapage(null);
                        n.setAnonymatRat(null);
                        moy = (float) ((n.getTpe() * tpe) + (n.getTd() * td) + (n.getCc() * cc) + (n.getExamen() * ex));
                    } else {
                        moy = (float) ((n.getTpe() * tpe) + (n.getTd() * td) + (n.getCc() * cc) + (n.getRattrapage() * ex));
                    }
                }
            } catch (java.lang.NullPointerException exe) {
            }
            if ((moy >= 9.99) && (moy < 10)) {
                moy = 10f;
            }
            n.setMoy(moy);
            if (moy <= 20 && moy >= 16) {
                n.setGrade("A");
                n.setCote(4.00f);
            } else if (moy < 16 && moy >= 15) {
                n.setGrade("A-");
                n.setCote(3.70f);
            } else if (moy < 15 && moy >= 14) {
                n.setGrade("B+");
                n.setCote(3.30f);
            } else if (moy < 14 && moy >= 13) {
                n.setGrade("B");
                n.setCote(3.00f);
            } else if (moy < 13 && moy >= 12) {
                n.setGrade("B-");
                n.setCote(2.70f);
            } else if (moy < 12 && moy >= 11) {
                n.setGrade("C+");
                n.setCote(2.30f);
            } else if (moy < 11 && moy >= 10) {
                n.setGrade("C");
                n.setCote(2.00f);
            } else if (moy < 10 && moy >= 9) {
                n.setGrade("C-");
                n.setCote(1.70f);
            } else if (moy < 9 && moy >= 8) {
                n.setGrade("D+");
                n.setCote(1.30f);
            } else if (moy < 8 && moy >= 7) {
                n.setGrade("D");
                n.setCote(1.00f);
            } else if (moy < 7 && moy >= 6) {
                n.setGrade("E");
                n.setCote(0.00f);
            } else if (moy < 6 && moy >= 0) {
                n.setGrade("F");
                n.setCote(0.00f);
            }



            if (moy >= 11 && moy <= 20) {
                n.setDecision("CA");
            } else if (moy < 11 && moy >= 10) {
                n.setDecision("CANT");
            } else if (moy < 10 && moy >= 8) {
                n.setDecision("AJ");
            } else if (moy < 8 && moy >= 0) {
                n.setDecision("ECH");
            }
        }

        return n;
    }

    @Override
    public Note calculDeliberation(Note n, float noteDel, int session, DelStat del) {
        float td = 0.15f, tpe = 0.15f, cc = 0.2f, ex = 0.5f; //formule par defaut  
        if (n.getMatiere().getFormule() != null) { //on parse la formule et on adapte
            try {
                StringTokenizer st = new StringTokenizer(n.getMatiere().getFormule(), "-");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    td = Integer.parseInt(tab[0]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    tpe = Integer.parseInt(tab[1]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    cc = Integer.parseInt(tab[2]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    ex = Integer.parseInt(tab[3]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
            } catch (NullPointerException ee) {
            }
        }
        Float tmp = 0F;
        if ((n.getMoy() >= noteDel) && (n.getMoy() < 10.0)) {
            tmp = (10F - (n.getTpe() * tpe) - (n.getTd() * td) - (n.getCc() * cc)) / ex; //formule generique
            //1 pour session normale
            if (session == 1) {
                try {
                    if (n.getExamen() != null) {
                        n.setExamen(tmp);
                        n.setMoy(10.00f);
                        n = calcul(n); // pour les appreciations
                        //update(n);
                        Note ntmp = (Note) n.clone();
                        ntmp.setId(null);
                        delete(n);
                        ntmp = notedao.create(ntmp);
                        n = ntmp;
                        del.setSession(del.getSession() + 1);
                    }
                } catch (java.lang.NullPointerException exe) {
                } catch (DataAccessException ex1) {
                    Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (ServiceException ex1) {
                    Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            //2 pour session rattrapage
            if (session == 2) {
                try {
                    if (n.getRattrapage() != null) {
                        n.setRattrapage(tmp);
                        n.setMoy(10.00f);
                        n = calcul(n); // pour les appreciations
                        //update(n);
                        Note ntmp = (Note) n.clone();
                        ntmp.setId(null);
                        delete(n);
                        ntmp = notedao.create(ntmp);
                        n = ntmp;
                        del.setRat(del.getRat() + 1);
                    }
                } catch (java.lang.NullPointerException exeee) {
                } catch (DataAccessException ex1) {
                    Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
                } catch (ServiceException ex1) {
                    Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
        return n;
    }

    @Override
    public Note ajout(Note n, float noteDel, int session, DelStat del) {
        //1 pour session normale
        if (session == 1) {
            try {
                if (n.getExamen() != null) {
                    n.setExamen(n.getExamen() + noteDel);
                    n = calcul(n); // pour les appreciations
                    //update(n);
                    Note ntmp = (Note) n.clone();
                    ntmp.setId(null);
                    delete(n);
                    ntmp = notedao.create(ntmp);
                    n = ntmp;
                    del.setSession(del.getSession() + 1);
                }
            } catch (java.lang.NullPointerException exe) {
            } catch (DataAccessException ex1) {
                Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (ServiceException ex1) {
                Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        //2 pour session rattrapage
        if (session == 2) {
            try {
                if (n.getRattrapage() != null) {
                    n.setRattrapage(n.getRattrapage() + noteDel);
                    n = calcul(n); // pour les appreciations
                    //update(n);
                    Note ntmp = (Note) n.clone();
                    ntmp.setId(null);
                    delete(n);
                    ntmp = notedao.create(ntmp);
                    n = ntmp;
                    del.setRat(del.getRat() + 1);
                }
            } catch (java.lang.NullPointerException exeee) {
            } catch (DataAccessException ex1) {
                Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (ServiceException ex1) {
                Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        return n;
    }

    @Override
    public Note deliberationSpeciale(Note n) {
        float td = 0.15f, tpe = 0.15f, cc = 0.2f, ex = 0.5f; //formule par defaut  
        if (n.getMatiere().getFormule() != null) { //on parse la formule et on adapte
            try {
                StringTokenizer st = new StringTokenizer(n.getMatiere().getFormule(), "-");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    td = Integer.parseInt(tab[0]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    tpe = Integer.parseInt(tab[1]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    cc = Integer.parseInt(tab[2]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    ex = Integer.parseInt(tab[3]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
            } catch (NullPointerException ee) {
            }
        }
        Float tmp = 0F;
        if (n.getMoy() < 10.0) {
            tmp = (10F - (n.getTpe() * tpe) - (n.getTd() * td) - (n.getCc() * cc)) / ex; //formule generique
            try {
                if (n.getRattrapage() != null) {
                    n.setRattrapage(tmp);
                    n.setMoy(10.00f);
                    n = calcul(n); // pour les appreciations
                    //update(n);
                    Note ntmp = (Note) n.clone();
                    ntmp.setId(null);
                    delete(n);

                    ntmp = notedao.create(ntmp);
                    n = ntmp;

                } else if (n.getExamen() != null) {
                    n.setExamen(tmp);
                    //n = calcul(n);
                    //initule de recalculer la note et donc on mets directement la moy Ã  10.00
                    n.setMoy(10.00f);
                    n = calcul(n); // pour les appreciations
                    //update(n);
                    Note ntmp = (Note) n.clone();
                    ntmp.setId(null);
                    delete(n);
                    ntmp = notedao.create(ntmp);
                    n = ntmp;

                }

            } catch (java.lang.NullPointerException exe) {
            } catch (DataAccessException ex1) {
                Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (ServiceException ex1) {
                Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex1);
            }

        }
        return n;
    }

    @Override
    public String verifier(Float result) {
        if (result > 100) {
            return "Error";
        }
        if (result >= 80) {
            return "A";
        }
        if (result >= 75) {
            return "A-";
        }
        if (result >= 70) {
            return "B+";
        }
        if (result >= 65) {
            return "B";
        }
        if (result >= 60) {
            return "B-";
        }
        if (result > 55) {
            return "C+";
        }
        if (result >= 50) {
            return "C";
        }
        if (result >= 45) {
            return "C-";
        }
        if (result >= 40) {
            return "D";
        }
        if (result > 35) {
            return "E";
        } else {
            return "F";
        }

    }

    @Override
    public String mention(Float result) {
        if (result == 4.00F) {
            return "Très bien";
        } else if (result <= 3.70F && result >= 3.30F) {
            return "Bien";
        } else if (result <= 3.00F && result >= 2.70F) {
            return "Assez bien";
        } else if (result <= 2.30F && result >= 2.00F) {
            return "Passable";
        }

        return "Echec";
    }

    @Override
    public String decision(Float result) {
        if (result >= 2.00f) {
            return "ADMIS";
        } else {
            return "REDOUBLE";
        }
    }

    @Override
    public String getSession(Note n) {
        String r;
        if (n.getRattrapage() == null) {
            r = "1";
        } else {
            r = "2";
        }
        return r;
    }

    public INotedao getNotedao() {
        return notedao;
    }

    public void setNotedao(INotedao notedao) {
        this.notedao = notedao;
    }

    @Override
    public Note findNoteByEtudiantMatiere(Long etudiantId, Long matiereId) throws ServiceException {
        try {
            return notedao.findNoteByEtudiantMatiere(etudiantId, matiereId);




        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    @Override
    public Note calculMoyGenSem(Note note, Pv pvSemestre) {
        Float moySem;
        int somCote;
        if (pvSemestre.getCote1() != null && pvSemestre.getCoef1() != null && pvSemestre.getCote2() != null && pvSemestre.getCoef2() != null && pvSemestre.getCote3() != null && pvSemestre.getCoef3() != null && pvSemestre.getCote4() != null && pvSemestre.getCoef4() != null && pvSemestre.getCote5() != null && pvSemestre.getCoef5() != null && pvSemestre.getCote6() != null && pvSemestre.getCoef6() != null) {
            moySem = (((pvSemestre.getCote1() * pvSemestre.getCoef1()) + (pvSemestre.getCote2() * pvSemestre.getCoef2()) + (pvSemestre.getCote3() * pvSemestre.getCoef3()) + (pvSemestre.getCote4() * pvSemestre.getCoef4()) + (pvSemestre.getCote5() * pvSemestre.getCoef5()) + (pvSemestre.getCote6() * pvSemestre.getCoef6())) / (pvSemestre.getCoef1() + pvSemestre.getCoef2() + pvSemestre.getCoef3() + pvSemestre.getCoef4() + pvSemestre.getCoef5() + pvSemestre.getCoef6()));
            note.setMoy(moySem);
//            note.setCreditCap(moySem);
            pvSemestre.setMoy(note.getMoy());
//            pvSemestre.setCapSem(note.getCreditCap());
        }
//        note.setRang(null);
        return note;
    }

    @Override
    public Note findByUEEtudiant(Long idEtudiant, Long idUE) {
        Note note = null;
        /*try {
         idUE = findMasterUE(idUE);
         } catch (DataAccessException ex) {
         Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
         }*/
        try {
            List<Matiere> matieres = matieredao.findListMatiereByUE(idUE);
            if (matieres.size() == 1) {
                note = notedao.findNoteByEtudiantMatiere(idEtudiant, matieres.get(0).getId());
            } else {
                /*Long anneeId = (moduledao.findById(idUE)).getAnneeAcademique().getId();
                 List<Matiere> matieresOps = matieredao.findListMatiereOpByEtudiantAnneeAc(idEtudiant, anneeId);
                 for (Matiere matiere : matieres) {
                 for (Matiere matiere1 : matieresOps) {
                 if (matiere.getId() == matiere1.getId()) {
                 note = notedao.findNoteByEtudiantMatiere(idEtudiant, matiere.getId());
                 break;
                 }
                 }
                 }*/   //ici on ne tient pas compte des matieres opt
                for (Matiere matiere : matieres) {
                    try {
                        note = notedao.findNoteByEtudiantMatiere(idEtudiant, matiere.getId());
                        if (note.getMoy() != null) {
                            break;
                        }
                    } catch (NoResultException ex) {
                    } catch (NullPointerException ex) {
                    }
                }

            }

        } catch (DataAccessException ex) {
        }

        return note;
    }

    private void updateSemestre(Pv pv) throws ServiceException {
        if (pv.getReglementaire() == 0) {
            return;
        }
        EtudiantSection etudiantSection = null;

        try {
            Etudiant etudiant = etudiantdao.findByMatricule(pv.getMatEtudiant());
            Specialite specialite = specialitedao.findById(pv.getSpecialite());
            try {
                etudiantSection = etudiantSectiondao.findByEtudiantSection(etudiant.getId(), specialite.getNiveau().getSection().getId());
            } catch (NoResultException ex) {
                etudiantSection = new EtudiantSection();
                etudiantSection.setEtudiant(etudiant);
                etudiantSection.setSection(specialite.getNiveau().getSection());
                etudiantSection = etudiantSectiondao.create(etudiantSection);
            }

            AnneeAcademique anneeAcademique = anneeAcademiquedao.findByAnnee(pv.getAnneeAc());
            int semestre = pv.getSemestre();
            int niveau = Integer.parseInt(pv.getNiveau());
            SpecialiteEtudiant spe = new SpecialiteEtudiant();
            float cap = pv.getCap();
            int credit = (int) cap;
            //plutot findby etudinat section
            SpecialiteEtudiant ss = specialiteEtudiantdao.findByEtudiantSection(etudiant.getMatricule(), pv.getSectionId()).get(0);
            int studentLevel = ss.getSpecialite().getNiveau().getLevel();
            //inscrire un etudiant au niveau sup si toutes les conditions sont remplies
            switch (semestre) {
                case 1:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession1(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);

                    } else {
                        etudiantSection.setCredit1(credit);
                        etudiantSection.setMgp1(pv.getMoy());
                        etudiantSection.setMoy1(pv.getMoy2());
                        etudiantSection.setUeR1(pv.getUer());
                        etudiantSection.setNcR1(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 2:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession2(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit2(credit);
                        etudiantSection.setMgp2(pv.getMoy());
                        etudiantSection.setMoy2(pv.getMoy2());
                        etudiantSection.setUeR2(pv.getUer());
                        etudiantSection.setNcR2(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 3:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession3(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit3(credit);
                        etudiantSection.setMgp3(pv.getMoy());
                        etudiantSection.setMoy3(pv.getMoy2());
                        etudiantSection.setUeR3(pv.getUer());
                        etudiantSection.setNcR3(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 4:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession4(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit4(credit);
                        etudiantSection.setMgp4(pv.getMoy());
                        etudiantSection.setMoy4(pv.getMoy2());
                        etudiantSection.setUeR4(pv.getUer());
                        etudiantSection.setNcR4(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 5:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession5(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit5(credit);
                        etudiantSection.setMgp5(pv.getMoy());
                        etudiantSection.setMoy5(pv.getMoy2());
                        etudiantSection.setUeR5(pv.getUer());
                        etudiantSection.setNcR5(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);

                    }
                    break;
                case 6:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession6(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit6(credit);
                        etudiantSection.setMgp6(pv.getMoy());
                        etudiantSection.setMoy6(pv.getMoy2());
                        etudiantSection.setUeR6(pv.getUer());
                        etudiantSection.setNcR6(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 7:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession7(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit7(credit);
                        etudiantSection.setMgp7(pv.getMoy());
                        etudiantSection.setMoy7(pv.getMoy2());
                        etudiantSection.setUeR7(pv.getUer());
                        etudiantSection.setNcR7(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 8:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession8(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit8(credit);
                        etudiantSection.setMgp8(pv.getMoy());
                        etudiantSection.setMoy8(pv.getMoy2());
                        etudiantSection.setUeR8(pv.getUer());
                        etudiantSection.setNcR8(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 9:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession9(pv.getNormale());
                        etudiantSectiondao.update(etudiantSection);
                    } else {
                        etudiantSection.setCredit9(credit);
                        etudiantSection.setMgp9(pv.getMoy());
                        etudiantSection.setMoy9(pv.getMoy2());
                        etudiantSection.setUeR9(pv.getUer());
                        etudiantSection.setNcR9(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
                case 10:
                    if (pv.getFin() == 0) {
                        etudiantSection.setSession10(pv.getNormale());
                    } else {
                        etudiantSection.setCredit10(credit);
                        etudiantSection.setMgp10(pv.getMoy());
                        etudiantSection.setMoy10(pv.getMoy2());
                        etudiantSection.setUeR10(pv.getUer());
                        etudiantSection.setNcR10(pv.getNuer());
                        etudiantSectiondao.update(etudiantSection);
                    }
                    break;
            }
            if (pv.getFin() == 1) { //on veut voir s'il ny a pas de chevaucheurs
                int credit1 = etudiantSection.getCredit1();
                int credit2 = etudiantSection.getCredit2();
                int credit3 = etudiantSection.getCredit3();
                int credit4 = etudiantSection.getCredit4();
                String ue = "";
                int chevauche = 0;
                if (semestre % 2 != 0) { // dans les syntheses on voit seulement si es etudinats doivent chevaucher au 
                    switch (niveau) {        // premier semestre
                        case 1:
                            if (studentLevel > 1) {
                                break;
                            }
                            if (spe.getSem2() == 1) {
                                break;
                            }
                            if ((credit1 + credit2) >= 45) {//il passe au niveau 2
                                chevauche = 1;
                            }
                            ue = etudiantSection.getUeR1() + etudiantSection.getUeR2();
                            break;
                        case 2:
                            if (studentLevel > 2) {
                                break;
                            }
                            if (spe.getSem2() == 1) {
                                break;
                            }
                            int credits1 = 0;
                            if (etudiant.getMatricule().startsWith("14") && anneeAcademique.getAnnee().equals("2014/2015")) {//pour considerer les anciens
                                credits1 = 60;
                                etudiantSection.setAdmis2(1);
                            } else if (etudiantSection.getAdmis2() == 1) {
                                credits1 = 60;
                            } else {
                                credits1 = credit1 + credit2;
                            }
                            if ((credit3 + credit4) >= 45) {//il passe au niveau 3
                                if ((credits1) == 60) {
                                    chevauche = 1;
                                } else if (((credit3 + credit4) == 60) && ((etudiantSection.getNcR1() + etudiantSection.getNcR2()) == 1)) {//on voit s'il lui reste une matiere
                                    chevauche = 1;
                                }
                            }
                            ue = etudiantSection.getUeR1() + etudiantSection.getUeR2() + etudiantSection.getUeR3() + etudiantSection.getUeR4();
                            break;

                        //pour les master on verra si c'est possible de chevaucher
                       /* case 4:
                         if (studentLevel > 4) {
                         break;
                         }
                         if (spe.getSem2() == 1) {
                         break;
                         }
                         if ((credit7 + credit8) >= 45) {// ceci est temporaire en attendant les regles exactes de validation
                         chevauche = 1;
                         }
                         ue = etudiantSection.getUeR7() + etudiantSection.getUeR8();
                         break; */
                    }
                } else { //on signale a l'appli que nouvenons de calculer de second semestre
                    spe.setSem2(1);
                }
                try {
                    //on stocke les synhese une fois pour pouvoir envoyer
                    SpecialiteEtudiant spee = specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(etudiantdao.findByMatricule(pv.getMatEtudiant()).getId(), anneeAcademique.getId(), specialite.getId());
                    float capo = pv.getCap();
                    int capo1 = (int) capo;
                    spee.setCredits(Long.valueOf(capo1));
                    try {
                        ue = ue.replaceAll("null", "");
                    } catch (NullPointerException ex) {
                    }
                    spee.setUe(ue);
                    spee.setChevauche(chevauche);
                    specialiteEtudiantdao.update(spee);
                } catch (DataAccessException ex) {
                    Logger.getLogger(ISNoteImpl.class
                            .getName()).log(Level.SEVERE, null, ex);
                } catch (NoResultException ex) {
                }
                etudiantSectiondao.update(etudiantSection);


            }
        } catch (DataAccessException ex) {
            //System.out.println("une erreur sur la mise a jour du champ etudaint");
        }

    }

    @Override
    public List<Pv> traitementSemestriel(List<Pv> pvSemestres, int token) {
        //calcul de la moyenne annuelle et des credits
        int mat1 = 0;
        int mat2 = 0;
        int mat3 = 0;
        int mat4 = 0;
        int mat5 = 0;
        int mat6 = 0;

        int size = pvSemestres.size();
        String percent = "%";
        for (Pv pvAn : pvSemestres) {
            pvAn.setPercent("Nbre d'Etudiants ayant Validés (/" + size + ")");

            int coef = 0;
            float moyenne = 0;
            float moyenne2 = 0;
            float credits = 0;
            String ue = ""; //ue ramenees
            int nue = 0; //nombre d'ue ramenees

            int tok = 1; //pour voir si on ramene une ue
            try {
                coef += pvAn.getCoef1();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef2();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef3();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef4();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef5();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef6();
            } catch (NullPointerException ex) {
            }
            if (pvAn.getCote1() != null) {

                moyenne2 += pvAn.getNote1() * pvAn.getCoef1();;
                moyenne += pvAn.getCote1() * pvAn.getCoef1();
                if (pvAn.getNote1() >= 50) {
                    credits += pvAn.getCoef1();
                    mat1++;
                } else {
                    nue++;
                    tok = 0;
                    ue += pvAn.getUe1m() + " - ";
                }
            } else if (tok == 1) {
                ue += pvAn.getUe1m() + " - ";
                nue++;
            }
            tok = 1;
            if (pvAn.getCote2() != null) {
                moyenne2 += pvAn.getNote2() * pvAn.getCoef2();
                moyenne += pvAn.getCote2() * pvAn.getCoef2();
                if (pvAn.getNote2() >= 50) {
                    credits += pvAn.getCoef2();
                    mat2++;
                } else {
                    nue++;
                    tok = 0;
                    ue += pvAn.getUe2m() + " - ";
                }
            } else if (tok == 1) {
                ue += pvAn.getUe2m() + " - ";
                nue++;
            }
            tok = 1;
            if (pvAn.getCote3() != null) {
                moyenne2 += pvAn.getNote3() * pvAn.getCoef3();
                moyenne += pvAn.getCote3() * pvAn.getCoef3();
                if (pvAn.getNote3() >= 50) {
                    credits += pvAn.getCoef3();
                    mat3++;
                } else {
                    nue++;
                    tok = 0;
                    ue += pvAn.getUe3m() + " - ";
                }
            } else if (tok == 1) {
                ue += pvAn.getUe3m() + " - ";
                nue++;
            }
            tok = 1;
            if (pvAn.getCote4() != null) {
                moyenne2 += pvAn.getNote4() * pvAn.getCoef4();
                moyenne += pvAn.getCote4() * pvAn.getCoef4();
                if (pvAn.getNote4() >= 50) {
                    credits += pvAn.getCoef4();
                    mat4++;
                } else {
                    nue++;
                    tok = 0;
                    ue += pvAn.getUe4m() + " - ";
                }
            } else if (tok == 1) {
                ue += pvAn.getUe4m() + " - ";
                nue++;
            }
            tok = 1;
            if (pvAn.getCote5() != null) {
                moyenne2 += pvAn.getNote5() * pvAn.getCoef5();
                moyenne += pvAn.getCote5() * pvAn.getCoef5();
                if (pvAn.getNote5() >= 50) {
                    credits += pvAn.getCoef5();
                    mat5++;
                } else {
                    nue++;
                    tok = 0;
                    ue += pvAn.getUe5m() + " - ";
                }
            } else if (tok == 1) {
                ue += pvAn.getUe5m() + " - ";
                nue++;
            }
            tok = 1;
            if (pvAn.getCote6() != null) {
                moyenne2 += pvAn.getNote6() * pvAn.getCoef6();
                moyenne += pvAn.getCote6() * pvAn.getCoef6();
                if (pvAn.getNote6() >= 50) {
                    credits += pvAn.getCoef6();
                    mat6++;
                } else {
                    nue++;
                    tok = 0;
                    ue += pvAn.getUe6m() + " - ";
                }
            } else if (tok == 1) {
                ue += pvAn.getUe6m() + " - ";
                nue++;
            }
            tok = 1;
            float m = 0;
            if (coef != 0) {
                m = moyenne2 / (5 * coef);
            }
            float coteT = 0;
            pvAn.setMoy2(m);
            //pvAn.setMoy(moyenne);
            if (m <= 20 && m >= 16) {
                coteT = 4.00f;
            } else if (m < 16 && m >= 15) {
                coteT = 3.70f;
            } else if (m < 15 && m >= 14) {
                coteT = (3.30f);
            } else if (m < 14 && m >= 13) {
                coteT = (3.00f);
            } else if (m < 13 && m >= 12) {
                coteT = (2.70f);
            } else if (m < 12 && m >= 11) {
                coteT = (2.30f);
            } else if (m < 11 && m >= 10) {
                coteT = (2.00f);
            } else if (m < 10 && m >= 9) {
                coteT = (1.70f);
            } else if (m < 9 && m >= 8) {
                coteT = (1.30f);
            } else if (m < 8 && m >= 7) {
                coteT = (1.00f);
            } else if (m < 7 && m >= 6) {
                coteT = (0.00f);
            } else if (m < 6 && m >= 0) {
                coteT = (0.00f);
            }

            pvAn.setMoy(coteT);
            pvAn.setCap(credits);
            pvAn.setNuer(nue);
            pvAn.setUer(ue);
            try {
                if (token == 1) //on met a jour les credits et les passages de niveau
                {
                    updateSemestre(pvAn);
                }
                //sinon on ne fait rien ie on veut selement afficher les donnees
            } catch (ServiceException ex) {
                Logger.getLogger(ISNoteImpl.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        //scinder pour eviter de classer ceux  qui ramenent les ue
        List<Pv> shortables = new LinkedList<Pv>();
        List<Pv> noShortables = new LinkedList<Pv>();
        for (Pv pv : pvSemestres) {

            pv.setNmEtuVal1(mat1);
            pv.setPercentVal1(String.format("%.2f", (mat1 / (float) size * 100)) + percent);
            pv.setNmEtuVal2(mat2);
            pv.setPercentVal2(String.format("%.2f", (mat2 / (float) size * 100)) + percent);
            pv.setNmEtuVal3(mat3);
            pv.setPercentVal3(String.format("%.2f", (mat3 / (float) size * 100)) + percent);
            pv.setNmEtuVal4(mat4);
            pv.setPercentVal4(String.format("%.2f", (mat4 / (float) size * 100)) + percent);
            pv.setNmEtuVal5(mat5);
            pv.setPercentVal5(String.format("%.2f", (mat5 / (float) size * 100)) + percent);
            pv.setNmEtuVal6(mat6);
            pv.setPercentVal6(String.format("%.2f", (mat6 / (float) size * 100)) + percent);


            if (pv.getShortable() == 1) {
                shortables.add(pv);
            } else {
                noShortables.add(pv);
            }
        }

        //tri par moyenne anuelle
        Collections.sort(shortables, Pv.moyenneComparator);
        int i = 1;
        for (Pv pv : shortables) {
            if (pv.getReglementaire() == 1) {
                pv.setRang(i++);
            }
        }

        Set<Pv> hs = new HashSet<>(); //pour vider les doublons
        hs.addAll(noShortables);
        noShortables.clear();
        noShortables.addAll(hs);

        Collections.sort(shortables, Pv.nomComparator);
        Collections.sort(noShortables, Pv.nomComparator);

        for (Pv pv : noShortables) {
            shortables.add(pv);
        }


        i = 1;
        for (Pv pv : shortables) {
            pv.setId(i++);
        }
        if (token == 1) //on met a jour les credits et les passages de niveau
        {
            for (Pv pv : shortables) {
                try {
                    int niveau = Integer.parseInt(pv.getNiveau());
                    Specialite specialite = specialitedao.findById(pv.getSpecialite());
                    AnneeAcademique anneeAcademique = anneeAcademiquedao.findByAnnee(pv.getAnneeAc());
                    if (pv.getReglementaire() == 0) {
                        String message = "vous n'etes pas solvables au semestre " + pv.getSemestre() + " veuillez vous raprocher de la scolarité";
                        try {
                            //on stocke les synhese une fois pour pouvoir envoyer
                            SpecialiteEtudiant spee = specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(etudiantdao.findByMatricule(pv.getMatEtudiant()).getId(), anneeAcademique.getId(), specialite.getId());
                            if (pv.getSemestre() % 2 == 0) {
                                spee.setSynth2(message);
                            } else {
                                spee.setSynth1(message);
                            }
                            specialiteEtudiantdao.update(spee);
                        } catch (DataAccessException ex) {
                            Logger.getLogger(ISNoteImpl.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        } catch (NoResultException ex) {
                        }
                        continue;
                    }
                    String rang = "";
                    if (pv.getRang() != 0) {
                        rang = ",rang=" + pv.getRang();
                    }
                    String message = null;
                    message = "niveau" + pv.getNiveau() + "," + pv.getUe1() + "=" + ((pv.getNote1() != null) ? (pv.getNote1() / 5) : " ") + ","
                            + pv.getUe2() + "=" + ((pv.getNote2() != null) ? (pv.getNote2() / 5) : " ") + ","
                            + pv.getUe3() + "=" + ((pv.getNote3() != null) ? (pv.getNote3() / 5) : " ") + ","
                            + pv.getUe4() + "=" + ((pv.getNote4() != null) ? (pv.getNote4() / 5) : " ") + ","
                            + pv.getUe5() + "=" + ((pv.getNote5() != null) ? (pv.getNote5() / 5) : " ") + ","
                            + pv.getUe6() + "=" + ((pv.getNote6() != null) ? (pv.getNote6() / 5) : " ") + ","
                            + "moyenne=" + pv.getMoy2() + "," + "credits=" + pv.getCap() + rang;
                    try {
                        //on stocke les synhese une fois pour pouvoir envoyer
                        SpecialiteEtudiant spee = specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(etudiantdao.findByMatricule(pv.getMatEtudiant()).getId(), anneeAcademique.getId(), specialite.getId());
                        if (pv.getSemestre() % 2 == 0) {
                            spee.setSynth2(message);
                        } else {
                            spee.setSynth1(message);
                        }
                        specialiteEtudiantdao.update(spee);
                    } catch (DataAccessException ex) {
                        Logger.getLogger(ISNoteImpl.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (NoResultException ex) {
                    }
                } catch (DataAccessException ex) {
                    Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return shortables;
    }

    @Override
    public List<Pv> traitementAnnuel(List<Pv> pvSemestres, int tokenA) {
        //calcul de la moyenne annuelle et des credits
        int quotas = 45;
        Long idAnnee = null;
        Long idSpecialite = null;
        for (Pv pvAn : pvSemestres) {
            String result = "";
            String ue = "";
            idAnnee = pvAn.getIdAnnnee();
            idSpecialite = pvAn.getIdSpecialite();

            if (pvAn.getNiv().equals("3") || pvAn.getNiv().equals("5")) {
                quotas = 60;
            }
            float moyenne = 0;
            float coef = 0;
            float credits = 0;
            float moyenne2 = 0;
            int token = 1;
            try {
                coef += pvAn.getCoef1();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef2();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef3();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef4();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef5();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef6();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef7();
            } catch (NullPointerException ex) {
            }

            try {
                coef += pvAn.getCoef8();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef9();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef10();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef11();
            } catch (NullPointerException ex) {
            }
            try {
                coef += pvAn.getCoef12();
            } catch (NullPointerException ex) {
            }
            if (pvAn.getCote1() != null) {
                moyenne2 += pvAn.getNote1() * pvAn.getCoef1();
                moyenne += pvAn.getCote1() * pvAn.getCoef1();
                if (pvAn.getNote1() >= 50) {
                    credits += pvAn.getCoef1();
                } else {
                    token = 0;
                    ue += pvAn.getUe1m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe1m() + " - ";
            }

            token = 1;
            if (pvAn.getCote2() != null) {
                moyenne2 += pvAn.getNote2() * pvAn.getCoef2();
                moyenne += pvAn.getCote2() * pvAn.getCoef2();
                if (pvAn.getNote2() >= 50) {
                    credits += pvAn.getCoef2();

                } else {
                    token = 0;
                    ue += pvAn.getUe2m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe2m() + " - ";
            }
            token = 1;

            if (pvAn.getCote3() != null) {
                moyenne2 += pvAn.getNote3() * pvAn.getCoef3();
                moyenne += pvAn.getCote3() * pvAn.getCoef3();
                if (pvAn.getNote3() >= 50) {
                    credits += pvAn.getCoef3();

                } else {
                    token = 0;
                    ue += pvAn.getUe3m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe3m() + " - ";
            }

            token = 1;

            if (pvAn.getCote4() != null) {
                moyenne2 += pvAn.getNote4() * pvAn.getCoef4();
                moyenne += pvAn.getCote4() * pvAn.getCoef4();
                if (pvAn.getNote4() >= 50) {
                    credits += pvAn.getCoef4();

                } else {
                    token = 0;
                    ue += pvAn.getUe4m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe4m() + " - ";
            }
            token = 1;
            if (pvAn.getCote5() != null) {
                moyenne2 += pvAn.getNote5() * pvAn.getCoef5();
                moyenne += pvAn.getCote5() * pvAn.getCoef5();
                if (pvAn.getNote5() >= 50) {
                    credits += pvAn.getCoef5();

                } else {
                    token = 0;
                    ue += pvAn.getUe5m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe5m() + " - ";
            }
            token = 1;
            if (pvAn.getCote6() != null) {
                moyenne2 += pvAn.getNote6() * pvAn.getCoef6();
                moyenne += pvAn.getCote6() * pvAn.getCoef6();
                if (pvAn.getNote6() >= 50) {
                    credits += pvAn.getCoef6();

                } else {
                    token = 0;
                    ue += pvAn.getUe6m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe6m() + " - ";
            }
            token = 1;

            if (pvAn.getCote7() != null) {
                moyenne2 += pvAn.getNote7() * pvAn.getCoef7();
                moyenne += pvAn.getCote7() * pvAn.getCoef7();
                if (pvAn.getNote7() >= 50) {
                    credits += pvAn.getCoef7();

                } else {
                    token = 0;
                    ue += pvAn.getUe7m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe7m() + " - ";
            }
            token = 1;

            if (pvAn.getCote8() != null) {
                moyenne2 += pvAn.getNote8() * pvAn.getCoef8();
                moyenne += pvAn.getCote8() * pvAn.getCoef8();
                if (pvAn.getNote8() >= 50) {
                    credits += pvAn.getCoef8();
                } else {
                    token = 0;
                    ue += pvAn.getUe8m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe8m() + " - ";
            }
            token = 1;

            if (pvAn.getCote9() != null) {
                moyenne2 += pvAn.getNote9() * pvAn.getCoef9();
                moyenne += pvAn.getCote9() * pvAn.getCoef9();
                if (pvAn.getNote9() >= 50) {
                    credits += pvAn.getCoef9();

                } else {
                    token = 0;
                    ue += pvAn.getUe9m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe9m() + " - ";
            }
            token = 1;

            if (pvAn.getCote10() != null) {
                moyenne2 += pvAn.getNote10() * pvAn.getCoef10();
                moyenne += pvAn.getCote10() * pvAn.getCoef10();
                if (pvAn.getNote10() >= 50) {
                    credits += pvAn.getCoef10();

                } else {
                    token = 0;
                    ue += pvAn.getUe10m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe10m() + " - ";
            }
            token = 1;

            if (pvAn.getCote11() != null) {
                moyenne2 += pvAn.getNote11() * pvAn.getCoef11();
                moyenne += pvAn.getCote11() * pvAn.getCoef11();
                if (pvAn.getNote11() >= 50) {
                    credits += pvAn.getCoef11();

                } else {
                    token = 0;
                    ue += pvAn.getUe11m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe11m() + " - ";
            }
            token = 1;

            if (pvAn.getCote12() != null) {
                moyenne2 += pvAn.getNote12() * pvAn.getCoef12();
                moyenne += pvAn.getCote12() * pvAn.getCoef12();
                if (pvAn.getNote12() >= 50) {
                    credits += pvAn.getCoef12();

                } else {
                    token = 0;
                    ue += pvAn.getUe12m() + " - ";
                }
            } else if (token == 1) {
                ue += pvAn.getUe12m() + " - ";
            }
            float m = 0;
            if (coef != 0) {
                m = moyenne2 / (5 * coef);
            }
            float coteT = 0;
            pvAn.setMoy2(m);
            //pvAn.setMoy(moyenne);
            if (m <= 20 && m >= 16) {
                coteT = 4.00f;
            } else if (m < 16 && m >= 15) {
                coteT = 3.70f;
            } else if (m < 15 && m >= 14) {
                coteT = (3.30f);
            } else if (m < 14 && m >= 13) {
                coteT = (3.00f);
            } else if (m < 13 && m >= 12) {
                coteT = (2.70f);
            } else if (m < 12 && m >= 11) {
                coteT = (2.30f);
            } else if (m < 11 && m >= 10) {
                coteT = (2.00f);
            } else if (m < 10 && m >= 9) {
                coteT = (1.70f);
            } else if (m < 9 && m >= 8) {
                coteT = (1.30f);
            } else if (m < 8 && m >= 7) {
                coteT = (1.00f);
            } else if (m < 7 && m >= 6) {
                coteT = (0.00f);
            } else if (m < 6 && m >= 0) {
                coteT = (0.00f);
            }

            pvAn.setMoy(coteT);



            pvAn.setCap(credits);
            if (pvAn.getShortable() == 1 && pvAn.getReglementaire() == 1) {
                try {
                    EtudiantSection etudiantSection = new EtudiantSection();
                    AnneeAcademique anneeAcademique = anneeAcademiquedao.findById(idAnnee);
                    int niveau = Integer.parseInt(pvAn.getNiv());
                    Etudiant etudiant = etudiantdao.findByMatricule(pvAn.getMatEtudiant());
                    Specialite specialite = specialitedao.findById(idSpecialite);
                    try {
                        etudiantSection = etudiantSectiondao.findByEtudiantSection(etudiant.getId(), specialite.getNiveau().getSection().getId());
                    } catch (NoResultException ex) {
                        etudiantSection.setEtudiant(etudiant);
                        etudiantSection.setSection(specialite.getNiveau().getSection());
                        etudiantSectiondao.create(etudiantSection);
                    }
                    switch (niveau) {
                        case 1:
                            if (credits >= 45) {
                                pvAn.setDecision("AD");
                                if (credits == 60) {
                                    result = "Admis";
                                } else {
                                    result = "Admis*";
                                }
                            } else {
                                pvAn.setDecision("RD");
                                result = "Redouble";
                            }
                            ue = etudiantSection.getUeR1() + etudiantSection.getUeR2();
                            break;
                        case 2:
                            int credits1 = 0;
                            if (etudiant.getMatricule().startsWith("14") && anneeAcademique.getAnnee().equals("2014/2015")) { //pour gerer les anciens
                                credits1 = 60;
                                etudiantSection.setAdmis2(1);
                            } else if (etudiantSection.getAdmis2() == 1) {
                                credits1 = 60;
                            } else {
                                credits1 = (etudiantSection.getCredit1() + etudiantSection.getCredit2());
                            }
                            if (credits1 != 60) {
                                if ((etudiantSection.getNcR1() + etudiantSection.getNcR2()) == 1) {//on voit s'il lui reste une matiere
                                    if (credits == 60) {
                                        result = "Admis*";
                                        pvAn.setDecision("AD");
                                    } else {
                                        pvAn.setDecision("RD");
                                        result = "Redouble";
                                    }
                                } else {
                                    pvAn.setDecision("RD");
                                    result = "Redouble";
                                }
                            } else if (credits >= 45) { //il a 60 au niveau 1 et plus de 45 au niveau 2
                                pvAn.setDecision("AD");
                                if (credits == 60) {
                                    result = "Admis";
                                } else {
                                    result = "Admis*";
                                }
                            } else {
                                pvAn.setDecision("RD");
                                result = "Redouble";
                            }
                            ue = etudiantSection.getUeR1() + etudiantSection.getUeR2() + etudiantSection.getUeR3() + etudiantSection.getUeR4();
                            break;
                        case 3:
                            credits1 = etudiantSection.getCredit1();
                            int credits2 = etudiantSection.getCredit2();
                            if (etudiantSection.getAdmis2() == 1) {
                                credits2 = 60;
                            }
                            if (etudiantSection.getAdmis3() == 1) {
                                credits1 = 60;
                                credits2 = 60;
                            }
                            if (etudiant.getMatricule().startsWith("14") && anneeAcademique.getAnnee().equals("2014/2015")) { //pour gerer les anciens
                                credits1 = 60;
                                credits2 = 60;
                                etudiantSection.setAdmis3(1);
                            } else if (etudiant.getMatricule().startsWith("13") && anneeAcademique.getAnnee().equals("2014/2015")) { //pour gerer les anciens
                                credits1 = 60;
                                credits2 = 60;
                                etudiantSection.setAdmis3(1);
                            } else if (etudiant.getMatricule().startsWith("13") && anneeAcademique.getAnnee().equals("2013/2014")) { //pour gerer les anciens
                                credits1 = 60;
                                credits2 = 60;
                                etudiantSection.setAdmis3(1);
                            }
                            credits += credits1 + credits2;
                            if (credits == 180) {  //normalement il faut 180 on  verra cela
                                pvAn.setDecision("AD");
                                result = "Admis";
                            } else {
                                pvAn.setDecision("RD");
                                result = "Redouble";
                            }
                            ue = etudiantSection.getUeR1() + etudiantSection.getUeR2() + etudiantSection.getUeR3() + etudiantSection.getUeR4() + etudiantSection.getUeR5() + etudiantSection.getUeR6();
                            break; //on ne gere pas encore le master on verra cela

                        case 4:
                            if (credits == 60) { //il faut tout valider 
                                //on recherche la moyenne de passage en master 1
                                SpecialiteAnnee spa = new SpecialiteAnnee();
                                try {
                                    spa = specialiteAnneedao.findBySpecialiteAnnee(idSpecialite, idAnnee);
                                } catch (NoResultException nr) {
                                    spa = new SpecialiteAnnee();
                                    spa.setAnneeAcademique(anneeAcademiquedao.findById(idAnnee));
                                    spa.setSpecialite(specialitedao.findById(idSpecialite));
                                    spa = specialiteAnneedao.create(spa);
                                }
                                if (m >= spa.getMoyenne()) {
                                    pvAn.setDecision("AD");
                                    result = "Admis";
                                } else {
                                    pvAn.setDecision("RD");
                                    result = "Redouble";
                                }
                            } else {
                                pvAn.setDecision("RD");
                                result = "Redouble";
                            }
                            ue = etudiantSection.getUeR7() + etudiantSection.getUeR8();
                            break;


                        case 5:
                            credits1 = etudiantSection.getCredit7() + etudiantSection.getCredit8();
                            if (etudiantSection.getAdmis5() == 1) //si on l'a accepte ne master2 directement
                            {
                                credits1 = 60;
                            }
                            if ((credits == 60) && (credits1 == 60)) { //il faut tout valider 
                                //on recherche la moyenne de passage en master 1
                                SpecialiteAnnee spa = new SpecialiteAnnee();
                                try {
                                    spa = specialiteAnneedao.findBySpecialiteAnnee(idSpecialite, idAnnee);
                                } catch (NoResultException nr) {
                                    spa = new SpecialiteAnnee();
                                    spa.setAnneeAcademique(anneeAcademiquedao.findById(idAnnee));
                                    spa.setSpecialite(specialitedao.findById(idSpecialite));
                                    spa = specialiteAnneedao.create(spa);
                                }
                                if (m >= spa.getMoyenne()) {
                                    pvAn.setDecision("AD");
                                    result = "Admis";
                                } else {
                                    pvAn.setDecision("RD");
                                    result = "Redouble";
                                }
                            } else {
                                pvAn.setDecision("RD");
                                result = "Redouble";
                            }
                            ue = etudiantSection.getUeR9() + etudiantSection.getUeR10();
                            break;
                    }

                    if (tokenA != 0) {//pour voir si ca vaut la peine de faire une mise a jour
                        etudiantSectiondao.update(etudiantSection);
                    }
                } catch (DataAccessException ex) {
                    Logger.getLogger(ISNoteImpl.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                if (tokenA != 0) {//pour voir si ca vaut la peine de faire une mise a jour

                    try {
                        SpecialiteEtudiant spe = specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(etudiantdao.findByMatricule(pvAn.getMatEtudiant()).getId(), idAnnee, idSpecialite);
                        spe.setDecision(result);
                        float capo = pvAn.getCap();
                        int capo1 = (int) capo;
                        spe.setCredits(Long.valueOf(capo1));
                        ue = ue.replaceAll("null", "");
                        spe.setUe(ue);

                        specialiteEtudiantdao.update(spe);


                    } catch (DataAccessException ex) {
                        Logger.getLogger(ISNoteImpl.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (NoResultException ex) {
                    }
                }

            } else {
                if (tokenA != 0) {//pour voir si ca vaut la peine de faire une mise a jour

                    try {
                        SpecialiteEtudiant spe = specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(etudiantdao.findByMatricule(pvAn.getMatEtudiant()).getId(), idAnnee, idSpecialite);
                        spe.setDecision(null);
                        specialiteEtudiantdao.update(spe);


                    } catch (DataAccessException ex) {
                        Logger.getLogger(ISNoteImpl.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (NoResultException ex) {
                    }
                }
            }
        }

        //tri par moyenne anuelle
        List<Pv> shortables = new LinkedList<Pv>();
        List<Pv> noShortables = new LinkedList<Pv>();
        for (Pv pv : pvSemestres) {
            if (pv.getShortable() == 1) {
                shortables.add(pv);
            } else {
                noShortables.add(pv);
            }
        }

        //tri par moyenne anuelle
        Collections.sort(shortables, Pv.moyenneComparator);
        int i = 1;
        for (Pv pv : shortables) {
            pv.setRang(i++);
        }

        //on declasse les insolvables et on sauvegarde les donnees pour les stats
        int composes = shortables.size();
        int admis = 0;
        int reglementaires = composes;
        for (Pv pv : shortables) {
            if (pv.getReglementaire() == 0) {
                reglementaires--;
                pv.setRang(0);
                continue;
            }
            try {
                if (pv.getDecision().equals("AD")) {
                    admis++;
                }
            } catch (NullPointerException ex) {
            }
        }
        if (tokenA != 0) {//pour voir si ca vaut la peine de faire une mise a jour
            try {
                Statistique statistique = statistiquedao.findBySpecialiteAnnee(idAnnee, idSpecialite);
                statistique.setAdmis(admis);
                statistique.setInscrits(composes);
                statistique.setReglementaires(reglementaires);
                statistiquedao.update(statistique);

            } catch (NoResultException ex) {
                try {
                    Statistique statistique = new Statistique();
                    statistique.setAdmis(admis);
                    statistique.setInscrits(composes);
                    statistique.setReglementaires(reglementaires);
                    statistique.setAnneeAcademique(anneeAcademiquedao.findById(idAnnee));
                    statistique.setSpecialite(specialitedao.findById(idSpecialite));
                    statistiquedao.create(statistique);


                } catch (DataAccessException ex1) {
                    Logger.getLogger(ISNoteImpl.class
                            .getName()).log(Level.SEVERE, null, ex1);
                }


            } catch (DataAccessException ex) {
                Logger.getLogger(ISNoteImpl.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

        Collections.sort(shortables, Pv.nomComparator);
        Collections.sort(noShortables, Pv.nomComparator);

        for (Pv pv : noShortables) {
            shortables.add(pv);
        }


        i = 1;
        for (Pv pv : shortables) {
            pv.setId(i++);
        }

        if (tokenA != 0) {
            for (Pv pv : shortables) {
                try {
                    Specialite specialite = specialitedao.findById(pv.getIdSpecialite());
                    AnneeAcademique anneeAcademique = anneeAcademiquedao.findById(pv.getIdAnnnee());
                    if (pv.getReglementaire() == 0) {
                        String message = "vous n'etes pas enfin d'annee. veuillez vous raprocher de la scolarité";
                        try {
                            //on stocke les synhese une fois pour pouvoir envoyer
                            SpecialiteEtudiant spee = specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(etudiantdao.findByMatricule(pv.getMatEtudiant()).getId(), anneeAcademique.getId(), specialite.getId());
                            spee.setAnn(message);
                            specialiteEtudiantdao.update(spee);
                        } catch (DataAccessException ex) {
                            Logger.getLogger(ISNoteImpl.class
                                    .getName()).log(Level.SEVERE, null, ex);
                        } catch (NoResultException ex) {
                        }
                        continue;
                    }
                    String rang = "";
                    if (pv.getRang() != 0) {
                        rang = ",rang=" + pv.getRang() + ",decis.=" + pv.getDecision();
                    }
                    String message = "niveau" + specialite.getNiveau().getLevel() + "," + pv.getUe1() + "=" + ((pv.getNote1() != null) ? (pv.getNote1() / 5) : " ") + ","
                            + pv.getUe2() + "=" + ((pv.getNote2() != null) ? (pv.getNote2() / 5) : " ") + ","
                            + pv.getUe3() + "=" + ((pv.getNote3() != null) ? (pv.getNote3() / 5) : " ") + ","
                            + pv.getUe4() + "=" + ((pv.getNote4() != null) ? (pv.getNote4() / 5) : " ") + ","
                            + pv.getUe5() + "=" + ((pv.getNote5() != null) ? (pv.getNote5() / 5) : " ") + ","
                            + pv.getUe6() + "=" + ((pv.getNote6() != null) ? (pv.getNote6() / 5) : " ") + ","
                            + pv.getUe7() + "=" + ((pv.getNote7() != null) ? (pv.getNote7() / 5) : " ") + ","
                            + pv.getUe8() + "=" + ((pv.getNote8() != null) ? (pv.getNote8() / 5) : " ") + ","
                            + pv.getUe9() + "=" + ((pv.getNote9() != null) ? (pv.getNote9() / 5) : " ") + ","
                            + pv.getUe10() + "=" + ((pv.getNote10() != null) ? (pv.getNote10() / 5) : " ") + ","
                            + pv.getUe11() + "=" + ((pv.getNote11() != null) ? (pv.getNote11() / 5) : " ") + ","
                            + pv.getUe12() + "=" + ((pv.getNote12() != null) ? (pv.getNote12() / 5) : " ") + ","
                            + "moyenne=" + pv.getMoy2() + "," + "credits=" + pv.getCap() + rang;
                    try {
                        //on stocke les synhese une fois pour pouvoir envoyer
                        SpecialiteEtudiant spee = specialiteEtudiantdao.findByEtudiantSpecialiteAnnee(etudiantdao.findByMatricule(pv.getMatEtudiant()).getId(), anneeAcademique.getId(), specialite.getId());
                        spee.setAnn(message);
                        specialiteEtudiantdao.update(spee);
                    } catch (DataAccessException ex) {
                        Logger.getLogger(ISNoteImpl.class
                                .getName()).log(Level.SEVERE, null, ex);
                    } catch (NoResultException ex) {
                    }
                } catch (DataAccessException ex) {
                    Logger.getLogger(ISNoteImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        return shortables;

    }

    @Override
    public Note findAnonymatExMatiere(String anonymatName, Long matiereId) throws ServiceException {
        try {
            return notedao.findAnonymatExMatiere(anonymatName, matiereId);






        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Note findAnonymatRatMatiere(String anonymatName, Long matiereId) throws ServiceException {
        try {
            return notedao.findAnonymatRatMatiere(anonymatName, matiereId);






        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public IEtudiantdao getEtudiantdao() {
        return etudiantdao;
    }

    public void setEtudiantdao(IEtudiantdao etudiantdao) {
        this.etudiantdao = etudiantdao;
    }

    public IAnneeAcademiquedao getAnneeAcademiquedao() {
        return anneeAcademiquedao;
    }

    public void setAnneeAcademiquedao(IAnneeAcademiquedao anneeAcademiquedao) {
        this.anneeAcademiquedao = anneeAcademiquedao;
    }

    public ISpecialiteEtudiantdao getSpecialiteEtudiantdao() {
        return specialiteEtudiantdao;
    }

    public void setSpecialiteEtudiantdao(ISpecialiteEtudiantdao specialiteEtudiantdao) {
        this.specialiteEtudiantdao = specialiteEtudiantdao;
    }

    private AnneeAcademique nextAnnee(String annee) throws ServiceException {
        AnneeAcademique result = null;
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
            Logger.getLogger(ISAnneeAcademiqueImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return result;

    }

    public ISpecialitedao getSpecialitedao() {
        return specialitedao;
    }

    public void setSpecialitedao(ISpecialitedao specialitedao) {
        this.specialitedao = specialitedao;
    }

    public INiveaudao getNiveaudao() {
        return niveaudao;
    }

    public void setNiveaudao(INiveaudao niveaudao) {
        this.niveaudao = niveaudao;
    }

    @Override
    public Note calculMoyenne(Note n) {

        try {
            float moy = n.getMoy();
            if (moy <= 20 && moy >= 16) {
                n.setGrade("A");
                n.setCote(4.00f);
            } else if (moy < 16 && moy >= 15) {
                n.setGrade("A-");
                n.setCote(3.70f);
            } else if (moy < 15 && moy >= 14) {
                n.setGrade("B+");
                n.setCote(3.30f);
            } else if (moy < 14 && moy >= 13) {
                n.setGrade("B");
                n.setCote(3.00f);
            } else if (moy < 13 && moy >= 12) {
                n.setGrade("B-");
                n.setCote(2.70f);
            } else if (moy < 12 && moy >= 11) {
                n.setGrade("C+");
                n.setCote(2.30f);
            } else if (moy < 11 && moy >= 10) {
                n.setGrade("C");
                n.setCote(2.00f);
            } else if (moy < 10 && moy >= 9) {
                n.setGrade("C-");
                n.setCote(1.70f);
            } else if (moy < 9 && moy >= 8) {
                n.setGrade("D+");
                n.setCote(1.30f);
            } else if (moy < 8 && moy >= 7) {
                n.setGrade("D");
                n.setCote(1.00f);
            } else if (moy < 7 && moy >= 6) {
                n.setGrade("E");
                n.setCote(0.00f);
            } else if (moy < 6 && moy >= 0) {
                n.setGrade("F");
                n.setCote(0.00f);
            }



            if (moy >= 11 && moy <= 20) {
                n.setDecision("CA");
            } else if (moy < 11 && moy >= 10) {
                n.setDecision("CANT");
            } else if (moy < 10 && moy >= 8) {
                n.setDecision("AJ");
            } else if (moy < 8 && moy >= 0) {
                n.setDecision("ECH");
            }
        } catch (NullPointerException e) {
        }

        return n;

    }

    @Override
    public Long findMasterUE(Long id) throws DataAccessException {
        List<Matiere> matieres = matieredao.findListMatiereByUE(id);
        if (!matieres.isEmpty()) {
            return id;
        }
        Module module = moduledao.findById(id);
        List<Module> modules = moduledao.findMaterByCodeAnnee(module.getTargetCode(), module.getAnneeAcademique().getId(), module.getSpecialite().getNiveau().getId());
        for (Module module1 : modules) {
            if (module1.getSpecialite().getNom().equals("/"));
            return module1.getId();
        }
        return id;
    }

    @Override
    public List<Note> sortByAnnymat(List<Note> items, int session) throws ServiceException {
        List<Note> notes1 = new LinkedList<Note>();
        List<Note> notes2 = new LinkedList<Note>();

        switch (session) {
            case 0:
                Collections.sort(items);
                return items;
            case 1:
                for (Note note : items) {
                    try {
                        Integer.parseInt(note.getAnonymatEx());
                        notes1.add(note);
                    } catch (NumberFormatException e) {
                        notes2.add(note);
                    }
                }
                Collections.sort(notes1, Note.anonymatExComparator);
                for (Note note : notes2) {
                    notes1.add(note);
                }
                return notes1;
            case 2:
                for (Note note : items) {
                    try {
                        Integer.parseInt(note.getAnonymatRat());
                        notes1.add(note);
                    } catch (NumberFormatException e) {
                        notes2.add(note);
                    }
                }
                Collections.sort(notes1, Note.anonymatRatComparator);
                for (Note note : notes2) {
                    notes1.add(note);
                }
                return notes1;
        }
        return null;
    }

    @Override
    public void blanchirEtudiantAnnee(String matricule, Long idAnnee) throws ServiceException {
        try {
            List<Note> notes = notedao.findEtudiantAnnee(matricule, idAnnee);
            for (Note note : notes) {
                notedao.delete(note);


            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Note> findByNoteForProfil(String matricule) throws ServiceException {
        try {
            return notedao.findByNoteForProfil(matricule);


        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Note> findByEtudiant(String matricule) throws ServiceException {
        try {
            return notedao.findByEtudiant(matricule);


        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public Note calculNormale(Note n) {
        Matiere matiere = n.getMatiere();
        float td = 0.15f, tpe = 0.15f, cc = 0.2f, ex = 0.5f; //formule par defaut
        if (matiere.getFormule() != null) { //on parse la formule et on adapte
            try {
                StringTokenizer st = new StringTokenizer(matiere.getFormule(), "-");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    td = Integer.parseInt(tab[0]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    tpe = Integer.parseInt(tab[1]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    cc = Integer.parseInt(tab[2]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
                try {
                    ex = Integer.parseInt(tab[3]) / (float) 100;
                } catch (NumberFormatException eee) {
                }
            } catch (NullPointerException ee) {
            }
        }
        boolean emptyData = false;
        float moy = 0;
        //on s'assure que les champs sont non vides
        if ((n.getTpe() == null) || (n.getTd() == null) || (n.getCc() == null) || (n.getExamen() == null)) {
            emptyData = true;
        }
        //on s'assure que les champs sont non vides
        if (!emptyData) {
            moy = (float) ((n.getTpe() * tpe) + (n.getTd() * td) + (n.getCc() * cc) + (n.getExamen() * ex));

            n.setMoy(moy);
            if (moy <= 20 && moy >= 16) {
                n.setGrade("A");
                n.setCote(4.00f);
            } else if (moy < 16 && moy >= 15) {
                n.setGrade("A-");
                n.setCote(3.70f);
            } else if (moy < 15 && moy >= 14) {
                n.setGrade("B+");
                n.setCote(3.30f);
            } else if (moy < 14 && moy >= 13) {
                n.setGrade("B");
                n.setCote(3.00f);
            } else if (moy < 13 && moy >= 12) {
                n.setGrade("B-");
                n.setCote(2.70f);
            } else if (moy < 12 && moy >= 11) {
                n.setGrade("C+");
                n.setCote(2.30f);
            } else if (moy < 11 && moy >= 10) {
                n.setGrade("C");
                n.setCote(2.00f);
            } else if (moy < 10 && moy >= 9) {
                n.setGrade("C-");
                n.setCote(1.70f);
            } else if (moy < 9 && moy >= 8) {
                n.setGrade("D+");
                n.setCote(1.30f);
            } else if (moy < 8 && moy >= 7) {
                n.setGrade("D");
                n.setCote(1.00f);
            } else if (moy < 7 && moy >= 6) {
                n.setGrade("E");
                n.setCote(0.00f);
            } else if (moy < 6 && moy >= 0) {
                n.setGrade("F");
                n.setCote(0.00f);
            }



            if (moy >= 11 && moy <= 20) {
                n.setDecision("CA");
            } else if (moy < 11 && moy >= 10) {
                n.setDecision("CANT");
            } else if (moy < 10 && moy >= 8) {
                n.setDecision("AJ");
            } else if (moy < 8 && moy >= 0) {
                n.setDecision("ECH");
            }
        }

        return n;
    }

    @Override
    public void validerNoteEtudiantSemestre(String matricule, Long idAnnee, int semestre) throws ServiceException {
        try {
            List<Note> notes = notedao.findEtudiantAnnee(matricule, idAnnee);
            for (Note note : notes) {
                if (semestre % 2 == 0) {
                    note.setPaye(1);
                    notedao.update(note);
                } else {
                    if (note.getMatiere().getModule().getSemestre().getLevel() == semestre) {
                        note.setPaye(1);
                        notedao.update(note);


                    }
                }
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void inValiderNoteEtudiant(String matricule, Long idAnnee) throws ServiceException {
        try {
            List<Note> notes = notedao.findEtudiantAnnee(matricule, idAnnee);
            for (Note note : notes) {
                note.setPaye(0);
                notedao.update(note);


            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Note> findByAnnee(Long idAnnee) throws ServiceException {
        try {
            return notedao.findByAnnee(idAnnee);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    public IStatistiquedao getStatistiquedao() {
        return statistiquedao;
    }

    public void setStatistiquedao(IStatistiquedao statistiquedao) {
        this.statistiquedao = statistiquedao;
    }

    public IEtudiantSectiondao getEtudiantSectiondao() {
        return etudiantSectiondao;
    }

    public void setEtudiantSectiondao(IEtudiantSectiondao etudiantSectiondao) {
        this.etudiantSectiondao = etudiantSectiondao;
    }

    @Override
    public List<Note> findByEtudiantSectionNiveau(String matricule, int level, Long idSection) throws ServiceException {

        try {
            return notedao.findByEtudiantSectionNiveau(matricule, level, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Note> findByEtudiantSection(String matricule, Long idSection) throws ServiceException {

        try {
            return notedao.findByEtudiantSection(matricule, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void blanchirEtudiantAnnee(String matricule, Long idAnnee, Long idSection) throws ServiceException {
        List<Note> notes = null;
        try {
            if ((idAnnee == 1L) || (idAnnee == 2L)) {
                notes = notedao.findEtudiantAnnee(matricule, idAnnee);
            } else {
                notes = notedao.findEtudiantAnneeSection(matricule, idAnnee, idSection);
            }
            for (Note note : notes) {
                notedao.delete(note);
            }
        } catch (DataAccessException ex) {
            Logger.getLogger(ISNoteImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<Note> findByEtudiantSectionAnnee(String matricule, Long idSection, Long idAnnee) throws ServiceException {
        try {
            return notedao.findEtudiantAnneeSection(matricule, idAnnee, idSection);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<Note> findEtudiantAnneeSpecialite(String matricule, Long idAnnee, Long idSpecialite) throws ServiceException {
        try {
            return notedao.findEtudiantAnneeSpecialite(matricule,idAnnee, idSpecialite);
        } catch (DataAccessException ex) {
            Logger.getLogger(ISSPecialiteEtudiantImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
