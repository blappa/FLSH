/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.NivUtil;
import com.univ.maroua.flsh.beans.util.PasswordUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.projection.Releve;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSemestre;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteAnnee;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author koossery
 */
@ManagedBean(name = "releveBean")
@SessionScoped
public class ReleveBean implements Serializable {

    /**
     * Creates a new instance of ReleveBean
     */
    @ManagedProperty(value = "#{ISEtudiant}")
    private ISEtudiant etu;
    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
    @ManagedProperty(value = "#{ISNiveau}")
    private ISNiveau niv;
    @ManagedProperty(value = "#{ISSpecialte}")
    private ISSpecialite spe;
    @ManagedProperty(value = "#{ISSection}")
    private ISSection sec;
    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    @ManagedProperty(value = "#{ISNote}")
    private ISNote not;
    @ManagedProperty(value = "#{ISMatiere}")
    private ISMatiere mat;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant iSSpecialiteEtudiant;
    @ManagedProperty(value = "#{ISSemestre}")
    private ISSemestre sem;
    @ManagedProperty(value = "#{ISInscription}")
    private ISInscription insc;
    @ManagedProperty(value = "#{ISSPecialiteAnnee}")
    private ISSpecialiteAnnee specialiteAnnee;
     @ManagedProperty(value = "#{ISUtilisateur}")
    private ISUtilisateur uti;
    private List<Departement> listDepartement = new ArrayList<Departement>();
    private List<Section> listSection = new ArrayList<Section>();
    private List<Section> listSection1 = new ArrayList<Section>();
    private List<Specialite> listSpecialite = new ArrayList<Specialite>();
    private List<AnneeAcademique> listAnneeAcademique = new ArrayList<AnneeAcademique>();
    private List<Etudiant> etudiants = new ArrayList<Etudiant>();
    private List<Matiere> matieres = new ArrayList<Matiere>();
    private List<Module> modules = new ArrayList<Module>();
    private Matiere matiere;
    private Niveau niveau;
    private List<Niveau> niveaus;
    private List<Niveau> niveausAll;
    private Long departementId;
    private Long sectionId;
    private Long niveauId;
    private Long specialiteId;
    private Long anneeAcademiqueid;
    private String matricule;
    private String nomEtudiant;
    private boolean sectionDisable = true;
    private boolean specialiteDisable = true;
    private boolean niveauDisable = true;
    private boolean actif = false;
    private int num = 0;
    private Long level;
    private JasperPrint jasperPrint;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String path = servletContext.getRealPath("") + File.separator + "print";
    private List<Releve> releves = new ArrayList<Releve>();
    private List<Releve> relevePersonnel = new ArrayList<Releve>();
    private List<Releve> releveGlobal = new ArrayList<Releve>();
//  private Releve releve = new Releve("10 Novembre 2014", "Mekomou Kejemto William", "10s409s", "16 Janvier 1990", "Mbe", "Baccalaureat", "Histoire", "2014", "2.80", "Assez Bien");
    private Releve releve;
    private Releve select;
    private Module curentModule;
    private Note note;
    private Etudiant etudiant;
    HashMap hm = new HashMap();
    private String reportName;
    private int niveauLevel;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private String year;
    private Map<String, Long> niveaux;

    public ReleveBean() {
        hm.put(1, "UN");
        hm.put(2, "DEUX");
        hm.put(3, "TROIS");
        hm.put(4, "QUATRE");
        hm.put(5, "CINQ");
        hm.put(6, "SIX");
        hm.put(7, "SEPT");
        hm.put(8, "HUIT");
        hm.put(9, "NEUF");
        hm.put(10, "DIX");
        etudiant = new Etudiant();
        select = new Releve();
        releve = new Releve();
        note = new Note();
        matiere = new Matiere();
        niveau = new Niveau();
        curentModule = new Module();
    }

    public void init(String nom, List<Releve> releves) throws JRException, ServiceException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(releves);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "releve" + File.separator + nom + ".jasper", new HashMap(), beanCollectionDataSource);
    }

    public void PDF(int token) throws JRException, IOException, ServiceException {
        
        Utilisateur util = uti.findByUsername(UserUtil.getUsername());
        if (util.getPassword().equals(PasswordUtil.getChenem()) || util.getPassword().equals(PasswordUtil.getNdo())) {
            JsfUtil.addErrorMessage("veuillez calculer toutes les syntheses d'abord!");
            return;
        }
        
        releves = generic(token); //un global, zero personnel
        String annee = anAc.findById(anneeAcademiqueid).getAnnee();
        if (!releves.isEmpty()) {
            try {
                try { //le but est de mettre tous les champsici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);

                    if (spa.getAnnuel() == false) {
                        JsfUtil.addErrorMessage("vous devez d'abord calculer les syntheses annuelles de cette specialité " + annee);
                        return;
                    }
                } catch (NoResultException nr) {
                    JsfUtil.addErrorMessage("vous devez d'abord calculer les syntheses annuelles de cette specialité " + annee);
                    return;
                }
            } catch (IllegalArgumentException ex) {
            }
            init("releve", releves);
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            FacesContext.getCurrentInstance().responseComplete();
        } else {
            if (token == 0) {
                JsfUtil.addErrorMessage("cet etudiant n'est pas solvable pour l'annee " + year);
                return;
            } else {
                JsfUtil.addErrorMessage("aucun élément trouvé!");
                return;
            }
        }
    }

    public List<Departement> getListDepartement() throws ServiceException {
        listDepartement = dep.findAll();
        return listDepartement;

    }
//

    public void filterReleveGlobal(ActionEvent actionEvent) throws ServiceException {
        actif = true;
        releves = globalDataTable(1);
    }

    public void filterRelevePersonnel(ActionEvent actionEvent) throws ServiceException {
        try {
            actif = true;
            releves = globalDataTable(0);
        } catch (javax.persistence.NoResultException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Matricule Etudiant Invalide!", ""));
        }
    }

    public List<Releve> generic(int token) throws ServiceException {
        List<Module> listModule1 = new ArrayList<Module>();
        List<Module> listModule2 = new ArrayList<Module>();
        Long sem1 = null;
        Long sem2 = null;
        Float notei = 0F;
        Float cotei = 0F;
        Float totalCreditCap = 0F;
        int hasCoposed;
        releveGlobal = new ArrayList<Releve>();
        etudiants = new ArrayList<Etudiant>();
        Specialite spec = new Specialite();
        Etudiant etud = new Etudiant();
        Specialite ss;
        AnneeAcademique anneeAcademique = new AnneeAcademique();

        if (token == 0) {
            try {
                level = Long.valueOf(niveauLevel);
                Niveau niveau = niv.findById(level);
                if (matricule != null) {
                    matricule = matricule.trim();
                }
                etud = etu.findByMatricule(matricule);

                //on cherche la derniere fois ou il composee un matiere de cette specialite afin de recuperer l'id;
                List<Note> notes = not.findByEtudiantSectionNiveau(matricule, niveauLevel, sectionId);
                //essayons de chercher une anne differente de l'annee courante
                AnneeAcademique targetYear = new AnneeAcademique();
                List<AnneeAcademique> annees = anAc.findAll();
                for (AnneeAcademique anneeAcademique1 : annees) {
                    if (anneeAcademique1.getIstargetyear()) {
                        targetYear = anneeAcademique1;
                        break;
                    }
                }
                for (Note note : notes) {
                    if (note.getMatiere().getModule().getAnneeAcademique().compareTo(targetYear) < 0) {
                        anneeAcademiqueid = note.getMatiere().getModule().getAnneeAcademique().getId();
                        anneeAcademique = note.getMatiere().getModule().getAnneeAcademique();
                        break;
                    }
                }
                year = anneeAcademique.getAnnee();
                //on cherche la derniere fois ou il a effectue la specialite afin de trouver l'id
                List<SpecialiteEtudiant> spes = iSSpecialiteEtudiant.findByEtudiantSectionNiveauAnnee(matricule, niveauLevel, sectionId, anneeAcademiqueid);
                if (spes.size() == 1) {
                    specialiteId = spes.get(0).getSpecialite().getId();
                } else {
                    for (SpecialiteEtudiant specialiteEtudiant : spes) {
                        if (!specialiteEtudiant.getSpecialite().getNom().equals("/")) {
                            specialiteId = specialiteEtudiant.getSpecialite().getId();
                            break;
                        }
                    }
                }
                spec = spe.findById(specialiteId);
                ss = spe.findById(specialiteId);

                niveau = spec.getNiveau();
                etudiants.add(etud);
                reportName = "releve" + niveauLevel + etud.getNom();
                reportName = reportName.replaceAll(" ", "");
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition du releve de l'etudaint  : " + etud + " pour le niveau " + niveau);
            } catch (NoResultException e) {
                JsfUtil.addErrorMessage("il n'y a aucun etudiant ayant ce matricule !");
            } catch (IndexOutOfBoundsException e) {
                JsfUtil.addErrorMessage("l'etudiant " + etud.getNom() + " n'a pas fait le niveau " + level + " ici !");
            }

        } else {
            etudiants = etu.findBySpecialiteAnneeForSyntheses(specialiteId, anneeAcademiqueid);
            level = Long.valueOf(niv.findById(niveauId).getLevel());
            spec = spe.findById(specialiteId);
            ss = spe.findById(specialiteId);
            niveau = niv.findById(niveauId);
            reportName = "releve" + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niveau.getLevel();
            reportName = reportName.replaceAll(" ", "");
            anneeAcademique = anAc.findById(anneeAcademiqueid);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des releves des etudiants de la specialite  : " + ss);

        }

        sem1 = getSemestres().get(0).getId();
        sem2 = getSemestres().get(1).getId();
        listModule1 = getModules(sem1);
        listModule2 = getModules(sem2);


        Long idSect = null;
        if (!anneeAcademique.getAnnee().equals("2014/2015")) {
            idSect = niveau.getSection().getId();
        }



        for (Etudiant etudiant1 : etudiants) {
            hasCoposed = 0;
            totalCreditCap = 0F;
            note = new Note();
            releve = new Releve();
            releve.setMatricule(etudiant1.getMatricule());
            releve.setNomEtu(etudiant1.getNom());
            releve.setNum("" + (++num));
            releve.setDatNais(etudiant1.getDateNais());
            try {
                String lieu = etudiant1.getLieuNais().toLowerCase().trim(); //pout mettre la premiere lettre en Majuscules et le reste en miniscules

                String start = lieu.charAt(0) + "";
                start = start.toUpperCase();
                lieu = start + lieu.substring(1);
                releve.setLieuNais(lieu);
            } catch (IndexOutOfBoundsException ex) {
            } catch (NullPointerException e) {
            }

            try {
                releve.setNationalite(formatNat(etudiant1.getNationalite()));
            } catch (NullPointerException ex) {
            }
            releve.setParcours(spec.getNiveau().getSection().getNom());
            releve.setSpecialite("" + spec.getNom());
            releve.setSem1(hm.get(Integer.valueOf(sem1 + "")) + "");
            releve.setSem2(hm.get(Integer.valueOf(sem2 + "")) + "");


            if (level < 4) {
                releve.setNiveau("Licence " + fomatRomain(level));
                releve.setCycle("I");
            } else {
                releve.setCycle("II");
                releve.setNiveau("Master " + fomatRomain(level));
            }

            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(0).getId(), 1);
                releve.setCodeUe1(listModule1.get(0).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe1(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }



                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe1("" + note.getMatiere().getIntitule().trim());

                    if (note.getMoy() != null) {

                        releve.setCredit1(0F);
                        releve.setGrade1("" + note.getGrade());
                        releve.setCote1(note.getCote());
                        releve.setAnnee1(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit1(listModule1.get(0).getCredit());
                            totalCreditCap += releve.getCredit1();
                        }


                        notei = notei + note.getMoy() * 5;
                        releve.setNote1(+note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession1("S" + sem1);
                        } else {
                            releve.setSession1("R" + sem1);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe1("" + mat.findByUE(listModule1.get(0).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }

            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(1).getId(), 1);
                releve.setCodeUe2(listModule1.get(1).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe2(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe2("" + note.getMatiere().getIntitule().trim());



                    if (note.getMoy() != null) {
                        releve.setCredit2(0F);
                        releve.setGrade2("" + note.getGrade());
                        releve.setCote2(note.getCote());

                        releve.setAnnee2(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit2(listModule1.get(1).getCredit());
                            totalCreditCap += releve.getCredit2();

                        }

                        notei = notei + note.getMoy() * 5;
                        releve.setNote2(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession2("S" + sem1);
                        } else {
                            releve.setSession2("R" + sem1);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe2("" + mat.findByUE(listModule1.get(1).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }


            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(2).getId(), 1);
                releve.setCodeUe3(listModule1.get(2).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe3(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe3("" + note.getMatiere().getIntitule().trim());



                    if (note.getMoy() != null) {
                        releve.setCredit3(0F);
                        releve.setGrade3("" + note.getGrade());
                        releve.setCote3(note.getCote());

                        releve.setAnnee3(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit3(listModule1.get(2).getCredit());
                            totalCreditCap += releve.getCredit3();

                        }

                        if (note.getCote() != null) {
                            cotei = cotei + note.getCote();
                        }
                        notei = notei + note.getMoy() * 5;
                        releve.setNote3(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession3("S" + sem1);
                        } else {
                            releve.setSession3("R" + sem1);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe3("" + mat.findByUE(listModule1.get(2).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }

            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(3).getId(), 1);
                releve.setCodeUe4(listModule1.get(3).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe4(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe4("" + note.getMatiere().getIntitule().trim());




                    if (note.getMoy() != null) {
                        releve.setCredit4(0F);
                        releve.setGrade4("" + note.getGrade());
                        releve.setCote4(note.getCote());

                        releve.setAnnee4(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit4(listModule1.get(3).getCredit());
                            totalCreditCap += releve.getCredit4();

                        }

                        if (note.getCote() != null) {
                            cotei = cotei + note.getCote();
                        }
                        notei = notei + note.getMoy() * 5;
                        releve.setNote4(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession4("S" + sem1);
                        } else {
                            releve.setSession4("R" + sem1);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe4("" + mat.findByUE(listModule1.get(3).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }
                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }


            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(4).getId(), 1);
                releve.setCodeUe5(listModule1.get(4).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe5(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe5("" + note.getMatiere().getIntitule().trim());



                    if (note.getMoy() != null) {
                        releve.setCredit5(0F);
                        releve.setGrade5("" + note.getGrade());
                        releve.setCote5(note.getCote());

                        releve.setAnnee5(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit5(listModule1.get(4).getCredit());
                            totalCreditCap += releve.getCredit5();

                        }

                        if (note.getCote() != null) {
                            cotei = cotei + note.getCote();
                        }
                        notei = notei + note.getMoy() * 5;
                        releve.setNote5(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession5("S" + sem1);
                        } else {
                            releve.setSession5("R" + sem1);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe5("" + mat.findByUE(listModule1.get(4).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }


            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(5).getId(), 1);
                releve.setCodeUe6(listModule1.get(5).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe6(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe6("" + note.getMatiere().getIntitule().trim());



                    if (note.getMoy() != null) {
                        releve.setCredit6(0F);
                        releve.setGrade6("" + note.getGrade());
                        releve.setCote6(note.getCote());

                        releve.setAnnee6(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit6(listModule1.get(5).getCredit());
                            totalCreditCap += releve.getCredit6();

                        }
                        notei = notei + note.getMoy() * 5;
                        releve.setNote6(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession6("S" + sem1);
                        } else {
                            releve.setSession6("R" + sem1);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe6("" + mat.findByUE(listModule1.get(5).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }

            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(0).getId(), 1);
                releve.setCodeUe7(listModule2.get(0).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe7(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe7("" + note.getMatiere().getIntitule().trim());


                    if (note.getMoy() != null) {
                        releve.setCredit7(0F);

                        releve.setGrade7("" + note.getGrade());
                        releve.setCote7(note.getCote());

                        releve.setAnnee7(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit7(listModule2.get(0).getCredit());
                            totalCreditCap += releve.getCredit7();

                        }

                        notei = notei + note.getMoy() * 5;
                        releve.setNote7(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession7("S" + sem2);
                        } else {
                            releve.setSession7("R" + sem2);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe7("" + mat.findByUE(listModule2.get(0).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }


            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(1).getId(), 1);
                releve.setCodeUe8(listModule2.get(1).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe8(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe8("" + note.getMatiere().getIntitule().trim());



                    if (note.getMoy() != null) {
                        releve.setCredit8(0F);
                        releve.setGrade8("" + note.getGrade());
                        releve.setCote8(note.getCote());

                        releve.setAnnee8(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit8(listModule2.get(1).getCredit());
                            totalCreditCap += releve.getCredit8();

                        }

                        notei = notei + note.getMoy() * 5;
                        releve.setNote8(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession8("S" + sem2);
                        } else {
                            releve.setSession8("R" + sem2);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe8("" + mat.findByUE(listModule2.get(1).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }

            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(2).getId(), 1);
                releve.setCodeUe9(listModule2.get(2).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe9(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe9("" + note.getMatiere().getIntitule().trim());


                    if (note.getMoy() != null) {
                        releve.setCredit9(0F);

                        releve.setGrade9("" + note.getGrade());
                        releve.setCote9(note.getCote());

                        releve.setAnnee9(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit9(listModule2.get(2).getCredit());
                            totalCreditCap += releve.getCredit9();


                        }

                        if (note.getCote() != null) {
                            cotei = cotei + note.getCote();
                        }
                        notei = notei + note.getMoy() * 5;
                        releve.setNote9(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession9("S" + sem2);
                        } else {
                            releve.setSession9("R" + sem2);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe9("" + mat.findByUE(listModule2.get(2).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }


            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(3).getId(), 1);
                releve.setCodeUe10(listModule2.get(3).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe10(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe10("" + note.getMatiere().getIntitule().trim());



                    if (note.getMoy() != null) {
                        releve.setCredit10(0F);
                        releve.setGrade10("" + note.getGrade());
                        releve.setCote10(note.getCote());

                        releve.setAnnee10(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit10(listModule2.get(3).getCredit());
                            totalCreditCap += releve.getCredit10();

                        }
                        notei = notei + note.getMoy() * 5;
                        releve.setNote10(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession10("S" + sem2);
                        } else {
                            releve.setSession10("R" + sem2);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe10("" + mat.findByUE(listModule2.get(3).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }


            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(4).getId(), 1);
                releve.setCodeUe11(listModule2.get(4).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe11(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }

                    releve.setIntituleUe11("" + note.getMatiere().getIntitule().trim());



                    if (note.getMoy() != null) {
                        releve.setCredit11(0F);
                        releve.setGrade11("" + note.getGrade());
                        releve.setCote11(note.getCote());

                        releve.setAnnee11(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit11(listModule2.get(4).getCredit());
                            totalCreditCap += releve.getCredit11();

                        }

                        notei = notei + note.getMoy() * 5;
                        releve.setNote11(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession11("S" + sem2);
                        } else {
                            releve.setSession11("R" + sem2);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe11("" + mat.findByUE(listModule2.get(4).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }

                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }


            try {
                curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(5).getId(), 1);
                releve.setCodeUe12(listModule2.get(5).getTargetCode());
                try {
                    note = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    try {
                        if ((note.getMatiere().getCode() != null)) {
                            releve.setCodeUe12(note.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }


                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                    releve.setIntituleUe12("" + note.getMatiere().getIntitule().trim());


                    if (note.getMoy() != null) {
                        releve.setCredit12(0F);

                        releve.setGrade12("" + note.getGrade());
                        releve.setCote12(note.getCote());

                        releve.setAnnee12(note.getMatiere().getModule().getAnneeAcademique().getAnnee());
                        if (note.getCote() >= 2.0) {
                            releve.setCredit12(listModule2.get(5).getCredit());
                            totalCreditCap += releve.getCredit12();

                        }
                        notei = notei + note.getMoy() * 5;
                        releve.setNote12(note.getMoy() * 5);
                        if (note.getRattrapage() == null) {
                            releve.setSession12("S" + sem2);
                        } else {
                            releve.setSession12("R" + sem2);
                        }
                    }

                } catch (NoResultException ex) {
                    releve.setIntituleUe12("" + mat.findByUE(listModule2.get(5).getId()).get(0).getIntitule().trim());
                } catch (NullPointerException ex) {
                }



                note = new Note();
                curentModule = new Module();
            } catch (IndexOutOfBoundsException ex) {
            }

            if ((notei == 0f) && (token == 1)) {
                continue;
            }

            String decision;
            Float quotas = 45F;
            releve.setNoteT(notei / 12);
            Note noteTest = new Note();
            noteTest.setMoy(notei / 12 / 5);
            noteTest = not.calculMoyenne(noteTest);
            releve.setGradeT(noteTest.getGrade());
            releve.setCoteT(noteTest.getCote());
            releve.setTotalCreditCap(totalCreditCap);

            if (level == 3 || level == 5) {
                quotas = 60F; //on a fixe a revoir
            }
            if (totalCreditCap >= quotas) {
                decision = "ADMIS";
            } else {
                decision = "REDOUBLE";
            }

            releve.setDecision("" + decision);


            notei = 0F;
            cotei = 0F;
            totalCreditCap = 0F;

            if (hasCoposed == 1) {
                if (insc.estEligible(listModule2.get(0).getSemestre().getLevel(), anneeAcademiqueid, etudiant1.getMatricule(), idSect)) {
                    releveGlobal.add(releve);
                }
            }
        }

        Collections.sort(releveGlobal);

        return releveGlobal;
    }

    public List<Releve> globalDataTable(int token) throws ServiceException {
        releveGlobal = generic(token);
        List<Releve> clonedReleves = new ArrayList<Releve>();
        relevePersonnel = new ArrayList<Releve>();
        //copier releve global dans cloned releves
        for (Releve item : releveGlobal) {
            clonedReleves.add((Releve) item.clone());
        }
        for (Releve releve : clonedReleves) {
            Releve tmp = new Releve();
            tmp.setNum(releve.getNum());
            tmp.setMatricule(releve.getMatricule());
            tmp.setNomEtu(releve.getNomEtu());
            tmp.setCodeUe1("==============================================");
            tmp.setIntituleUe1("==============================================");
//            tmp.setCredit1("==============================================");
            // tmp.setNote1("==============================================");
            tmp.setGrade1("==============================================");
            // tmp.setCote1("==============================================");
            tmp.setSession1("==============================================");
            tmp.setAnnee1("==============================================");
            relevePersonnel.add(tmp);


            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe1());
            tmp.setIntituleUe1(releve.getIntituleUe1());
            tmp.setCredit1(releve.getCredit1());
            tmp.setGrade1(releve.getGrade1());
            tmp.setCote1(releve.getCote1());
            tmp.setSession1(releve.getSession1());
            tmp.setAnnee1(releve.getAnnee1());
            relevePersonnel.add(tmp); //ue no1

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe2());
            tmp.setIntituleUe1(releve.getIntituleUe2());
            tmp.setCredit1(releve.getCredit2());
            tmp.setGrade1(releve.getGrade2());
            tmp.setCote1(releve.getCote2());
            tmp.setSession1(releve.getSession2());
            tmp.setAnnee1(releve.getAnnee2());
            relevePersonnel.add(tmp); //ue no2

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe3());
            tmp.setIntituleUe1(releve.getIntituleUe3());
            tmp.setCredit1(releve.getCredit3());
            tmp.setGrade1(releve.getGrade3());
            tmp.setCote1(releve.getCote3());
            tmp.setSession1(releve.getSession3());
            tmp.setAnnee1(releve.getAnnee3());
            relevePersonnel.add(tmp); //ue no3

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe4());
            tmp.setIntituleUe1(releve.getIntituleUe4());
            tmp.setCredit1(releve.getCredit4());
            tmp.setGrade1(releve.getGrade4());
            tmp.setCote1(releve.getCote4());
            tmp.setSession1(releve.getSession4());
            tmp.setAnnee1(releve.getAnnee4());
            relevePersonnel.add(tmp); //ue no4

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe5());
            tmp.setIntituleUe1(releve.getIntituleUe5());
            tmp.setCredit1(releve.getCredit5());
            tmp.setGrade1(releve.getGrade5());
            tmp.setCote1(releve.getCote5());
            tmp.setSession1(releve.getSession5());
            tmp.setAnnee1(releve.getAnnee5());
            relevePersonnel.add(tmp); //ue no5

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe6());
            tmp.setIntituleUe1(releve.getIntituleUe6());
            tmp.setCredit1(releve.getCredit6());
            tmp.setGrade1(releve.getGrade6());
            tmp.setCote1(releve.getCote6());
            tmp.setSession1(releve.getSession6());
            tmp.setAnnee1(releve.getAnnee6());
            relevePersonnel.add(tmp); //ue no6

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe7());
            tmp.setIntituleUe1(releve.getIntituleUe7());
            tmp.setCredit1(releve.getCredit7());
            tmp.setGrade1(releve.getGrade7());
            tmp.setCote1(releve.getCote7());
            tmp.setSession1(releve.getSession7());
            tmp.setAnnee1(releve.getAnnee7());
            relevePersonnel.add(tmp); //ue no7

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe8());
            tmp.setIntituleUe1(releve.getIntituleUe8());
            tmp.setCredit1(releve.getCredit8());
            tmp.setGrade1(releve.getGrade8());
            tmp.setCote1(releve.getCote8());
            tmp.setSession1(releve.getSession8());
            tmp.setAnnee1(releve.getAnnee8());
            relevePersonnel.add(tmp); //ue no8

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe9());
            tmp.setIntituleUe1(releve.getIntituleUe9());
            tmp.setCredit1(releve.getCredit9());
            tmp.setGrade1(releve.getGrade9());
            tmp.setCote1(releve.getCote9());
            tmp.setSession1(releve.getSession9());
            tmp.setAnnee1(releve.getAnnee9());
            relevePersonnel.add(tmp); //ue no9

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe10());
            tmp.setIntituleUe1(releve.getIntituleUe10());
            tmp.setCredit1(releve.getCredit10());
            tmp.setGrade1(releve.getGrade10());
            tmp.setCote1(releve.getCote10());
            tmp.setSession1(releve.getSession10());
            tmp.setAnnee1(releve.getAnnee10());
            relevePersonnel.add(tmp); //ue no10

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe11());
            tmp.setIntituleUe1(releve.getIntituleUe11());
            tmp.setCredit1(releve.getCredit11());
            tmp.setGrade1(releve.getGrade11());
            tmp.setCote1(releve.getCote11());
            tmp.setSession1(releve.getSession11());
            tmp.setAnnee1(releve.getAnnee11());
            relevePersonnel.add(tmp); //ue no11

            tmp = new Releve();
            tmp.setNum("==============================================");
            tmp.setMatricule("==============================================");
            tmp.setNomEtu("==============================================");
            tmp.setCodeUe1(releve.getCodeUe12());
            tmp.setIntituleUe1(releve.getIntituleUe12());
            tmp.setCredit1(releve.getCredit12());
            tmp.setGrade1(releve.getGrade12());
            tmp.setCote1(releve.getCote12());
            tmp.setSession1(releve.getSession12());
            tmp.setAnnee1(releve.getAnnee12());
            relevePersonnel.add(tmp); //ue no12


        }
        return relevePersonnel;
    }

    public void setRelevePersonnel(List<Releve> relevePersonnel) {
        this.relevePersonnel = relevePersonnel;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setReleveGlobal(List<Releve> releveGlobal) {
        this.releveGlobal = releveGlobal;
    }

    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public List<Section> getListSection() throws ServiceException {
        listSection = sec.findByDepartement(departementId);
        return listSection;
    }

    public List<Section> getListSection1() throws ServiceException {
        try {
            listSection1 = sec.findByEtudiantIdNiveau(matricule, niveauLevel);
            return listSection1;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public List<Specialite> getListSpecialite() throws ServiceException {
        listSpecialite = spe.findByNiveau(niveauId);

        if (listSpecialite.size() == 1) {
            return listSpecialite;
        } else { //on enleve le slash
            List<Specialite> ne = new LinkedList<>();
            for (Specialite specialite1 : listSpecialite) {
                if (!specialite1.getNom().equals("/")) {
                    ne.add(specialite1);
                }
            }
            listSpecialite = ne;
        }

        return listSpecialite;
    }

    public void processDepartement() throws ServiceException {
        sectionDisable = false;
        niveauDisable = true;
        specialiteDisable = true;
        getListSection();
    }

    public void processSection() throws ServiceException {
        niveauDisable = false;
        specialiteDisable = true;
        getNiveaus();
    }

    public void processNiveau1() throws ServiceException {
        sectionDisable = false;
        getListSection1();
    }

    public void processNiveau() throws ServiceException {
        specialiteDisable = false;
        getListSpecialite();
    }

    public List<AnneeAcademique> getListAnneeAcademique() throws ServiceException {
        listAnneeAcademique = anAc.findAll();
        return listAnneeAcademique;
    }

    public ISEtudiant getEtu() {
        return etu;
    }

    public void setEtu(ISEtudiant etu) {
        this.etu = etu;
    }

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }

    public ISNiveau getNiv() {
        return niv;
    }

    public void setNiv(ISNiveau niv) {
        this.niv = niv;
    }

    public ISSpecialite getSpe() {
        return spe;
    }

    public void setSpe(ISSpecialite spe) {
        this.spe = spe;
    }

    public ISSection getSec() {
        return sec;
    }

    public void setSec(ISSection sec) {
        this.sec = sec;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public Long getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(Long niveauId) {
        this.niveauId = niveauId;
    }

    public Long getAnneeAcademiqueid() {
        return anneeAcademiqueid;
    }

    public void setAnneeAcademiqueid(Long anneeAcademiqueid) {
        this.anneeAcademiqueid = anneeAcademiqueid;
    }

    public boolean isSectionDisable() {
        return sectionDisable;
    }

    public void setSectionDisable(boolean sectionDisable) {
        this.sectionDisable = sectionDisable;
    }

    public boolean isSpecialiteDisable() {
        return specialiteDisable;
    }

    public void setSpecialiteDisable(boolean specialiteDisable) {
        this.specialiteDisable = specialiteDisable;
    }

    public Releve getReleve() {
        return releve;
    }

    public void setReleve(Releve releve) {
        this.releve = releve;
    }

    public List<Matiere> getMatieres() {
        return matieres;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

    public List<Etudiant> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public boolean isNiveauDisable() {
        return niveauDisable;
    }

    public void setNiveauDisable(boolean niveauDisable) {
        this.niveauDisable = niveauDisable;
    }

    public List<Niveau> getNiveaus() throws ServiceException {
        try {
            niveaus = niv.findBySection(sectionId);
        } catch (NullPointerException ex) {

            niveaus = new ArrayList<Niveau>();
        }
        return niveaus;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getNomEtudiant() {
        return nomEtudiant;
    }

    public void setNomEtudiant(String nomEtudiant) {
        this.nomEtudiant = nomEtudiant;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Releve> getReleves() {
        return releves;
    }

    public void setReleves(List<Releve> releves) {
        this.releves = releves;
    }

    public List<Niveau> getNiveausAll() throws ServiceException {
        try {
            niveaus = niv.findAll();
        } catch (NullPointerException ex) {

            niveaus = new ArrayList<Niveau>();
        }
        return niveaus;
    }

    public ISNote getNot() {
        return not;
    }

    public void setNot(ISNote not) {
        this.not = not;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public ISMatiere getMat() {
        return mat;
    }

    public void setMat(ISMatiere mat) {
        this.mat = mat;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }

    public Releve getSelect() {
        return select;
    }

    public void setSelect(Releve select) {
        this.select = select;
    }

    public Module getCurentModule() {
        return curentModule;
    }

    public void setCurentModule(Module curentModule) {
        this.curentModule = curentModule;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public boolean isActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public ISSpecialiteEtudiant getiSSpecialiteEtudiant() {
        return iSSpecialiteEtudiant;
    }

    public void setiSSpecialiteEtudiant(ISSpecialiteEtudiant iSSpecialiteEtudiant) {
        this.iSSpecialiteEtudiant = iSSpecialiteEtudiant;
    }

    public String fomatRomain(Long level) {
        if (level == 1F) {
            return "I";
        }
        if (level == 2F) {
            return "II";
        }
        if (level == 3F) {
            return "III";
        }
        if (level == 4F) {
            return "I";
        }
        if (level == 5F) {
            return "II";
        }
        return null;
    }

    public ISSemestre getSem() {
        return sem;
    }

    public void setSem(ISSemestre sem) {
        this.sem = sem;
    }

    public List<Semestre> getSemestres() throws ServiceException {
        List<Semestre> semestres = sem.findBySpecialite(specialiteId);
        Collections.sort(semestres);
        return semestres;
    }

    public List<Module> getModules(Long semId) throws ServiceException {
        try {
            List<Module> modules1 = mod.findBySemestreAnneeAcSpecialite(anneeAcademiqueid, semId, specialiteId);
            return modules1;
        } catch (ServiceException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IndexOutOfBoundsException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }


        return null;
    }

    private String formatNat(String nationalite) {
        if (nationalite.toLowerCase().startsWith("cam")) {
            return "Camerounaise";
        } else if (nationalite.toLowerCase().startsWith("tchad")) {
            return "Tchadienne";
        } else if (nationalite.toLowerCase().startsWith("cen")) {
            return "Centrafricaine";
        }
        return nationalite;
    }

    public ISInscription getInsc() {
        return insc;
    }

    public void setInsc(ISInscription insc) {
        this.insc = insc;
    }

    public int getNiveauLevel() {
        return niveauLevel;
    }

    public void setNiveauLevel(int niveauLevel) {
        this.niveauLevel = niveauLevel;
    }

    public ISSpecialiteAnnee getSpecialiteAnnee() {
        return specialiteAnnee;
    }

    public void setSpecialiteAnnee(ISSpecialiteAnnee specialiteAnnee) {
        this.specialiteAnnee = specialiteAnnee;
    }

    public Map<String, Long> getNiveaux() throws ServiceException {
        niveaux = NivUtil.getLevel(getNiveaus());
        return niveaux;
    }

    public ISUtilisateur getUti() {
        return uti;
    }

    public void setUti(ISUtilisateur uti) {
        this.uti = uti;
    }
    
    
}
