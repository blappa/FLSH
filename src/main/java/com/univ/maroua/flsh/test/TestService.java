/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.test;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;

import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISAutorisation;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISMatiereOptionnelle;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSemestre;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author lateu
 */
public class TestService {

    private static ISDepartement iSDepartement;
    private static ISMatiere iSMatiere;
    private static ISInscription iSHistoriqueInscription;
    private static ISNiveau iSNiveau;
    private static ISNote iSNote;
    private static ISAnneeAcademique iSAnneeAcademique;
    private static ISSpecialite iSSpecialite;
    private static ISSemestre iSemestre;
    private static ISSection iSSection;
    private static ISEtudiant iSEtudiant;
    private static ISModule iSModule;
    private static ISMatiereOptionnelle iSMatiereOptionnelle;
    private static ISUtilisateur iServiceUtilisateur;
    private static ISSpecialiteEtudiant iSPecialiteEtudiant;
    private static ISAutorisation aut;

    public static void main(String[] args) throws DataAccessException, ServiceException, ParseException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        iSSection = (ISSection) ctx.getBean("ISSection");
        iSDepartement = (ISDepartement) ctx.getBean("ISDepartement");
        iSMatiere = (ISMatiere) ctx.getBean("ISMatiere");
        iSNiveau = (ISNiveau) ctx.getBean("ISNiveau");
        iSNote = (ISNote) ctx.getBean("ISNote");
        iSAnneeAcademique = (ISAnneeAcademique) ctx.getBean("ISAnneeAcademique");
        iSSpecialite = (ISSpecialite) ctx.getBean("ISSpecialte");
        iSemestre = (ISSemestre) ctx.getBean("ISSemestre");
        iSEtudiant = (ISEtudiant) ctx.getBean("ISEtudiant");
        iSModule = (ISModule) ctx.getBean("ISModule");
        iSMatiereOptionnelle = (ISMatiereOptionnelle) ctx.getBean("ISMatiereOptionnelle");
        iServiceUtilisateur = (ISUtilisateur) ctx.getBean("ISUtilisateur");
        iSPecialiteEtudiant = (ISSpecialiteEtudiant) ctx.getBean("ISSpecialteEtudiant");
        aut = (ISAutorisation) ctx.getBean("ISAutorisation");


        /* //premiere tache: on cherche les matricules dont la taille est infieure a 5 et on les supprime
         List<Etudiant> etudiants = iSEtudiant.findAll();
         for (Etudiant etudiant : etudiants) {
         if (etudiant.getMatricule().length() < 5) {
         iSEtudiant.delete(etudiant);
         }
         }*/


        /* //on met tous les 9.99 a 10
         List<Note> notes = iSNote.findByAnnee(3L);
         for (Note note : notes) {
         try {
         if ((note.getMoy() > 9.99f) && (note.getMoy() < 10f)) {
         note = iSNote.calcul(note);
         Note tmp=(Note)note.clone();
         tmp.setId(null);
         iSNote.delete(note);
         iSNote.create(tmp);
         i++;
         }
         } catch (NullPointerException ee) {
         }
         }*/

        //on voit ceux qui ont valide et se sont vu attribuer des notes random 2014-2015 et 2015-2016
        //il faut calculer 2013-2014    2013-2015  2014-2015
        Long targetYear = 2L;
        Long oldYear = 1L;
        List<String> found = new LinkedList<>();
        List<Specialite> specialites = iSSpecialite.findAll();
        for (Specialite specialite : specialites) {
            List<Etudiant> etudian = iSEtudiant.findBySpecialiteAnnee(specialite.getId(), targetYear);//on cherche les etudiants de 2014
            for (Etudiant etudiant : etudian) {
                List<Note> notes = iSNote.findEtudiantAnneeSpecialite(etudiant.getMatricule(), targetYear, specialite.getId());
                for (Note note : notes) {
                    if (note.getMoy() != null) {
                        if (note.getMoy() >= 10F) {
                            continue;
                        }
                    }
                    List<Note> notes2 = iSNote.findEtudiantAnneeSpecialite(etudiant.getMatricule(), oldYear, specialite.getId());

                    for (Note note1 : notes2) {
                        if (note.getMatiere().getModule().getCode().equals(note1.getMatiere().getModule().getCode())) {
                            if (note1.getMoy() != null) {
                                String code = note.getMatiere().getModule().getTargetCode();
                                if (note.getMatiere().getCode() != null) {
                                    code = note.getMatiere().getCode();
                                }
                                if (note1.getMoy() >= 10F) {
                                    found.add("matri " + etudiant.getMatricule() + " mat:" + code);//on supprime note
                                }
                            }
                        }
                    }
                }
            }
        }

        for (String result : found) {
            System.out.println(result);
        }
    }
}
