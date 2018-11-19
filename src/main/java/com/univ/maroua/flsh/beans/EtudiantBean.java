/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.EtudiantSection;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.projection.Profil;
import com.univ.maroua.flsh.projection.ProfilEtudiant;
import com.univ.maroua.flsh.projection.Reussite;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISEtudiantSection;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSemestre;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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
 * @author lappa
 */
@ManagedBean(name = "etudiantBean")
@SessionScoped
public class EtudiantBean implements Serializable {

    @ManagedProperty(value = "#{ISEtudiantSection}")
    private ISEtudiantSection etudiantSection;
    @ManagedProperty(value = "#{ISEtudiant}")
    private ISEtudiant etu;
    private Etudiant selected;
    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
    @ManagedProperty(value = "#{ISMatiere}")
    private ISMatiere mat;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant speEt;
    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    @ManagedProperty(value = "#{ISNote}")
    private ISNote not;
    @ManagedProperty(value = "#{ISNiveau}")
    private ISNiveau niv;
    @ManagedProperty(value = "#{ISSpecialte}")
    private ISSpecialite spe;
    @ManagedProperty(value = "#{ISSection}")
    private ISSection sec;
    @ManagedProperty(value = "#{ISInscription}")
    private ISInscription insc;
    @ManagedProperty(value = "#{ISSemestre}")
    private ISSemestre sem;
    private Niveau niveau;
    private AnneeAcademique anneeAcademique;
    private List<AnneeAcademique> listAnAc = new ArrayList<AnneeAcademique>();
    private Matiere matiere;
    private List<Matiere> listMatiereCom = new ArrayList<Matiere>();
    private List<Departement> listDepartement = new ArrayList<Departement>();
    private List<Section> listSection = new ArrayList<Section>();
    private List<Matiere> listMatiere = new ArrayList<Matiere>();
    private List<Specialite> listSpecialite = new ArrayList<Specialite>();
    private List<Matiere> listMat = new ArrayList<Matiere>();
    private List<SpecialiteEtudiant> listSpeEtu;
    private List<AnneeAcademique> listAnneeAcademique = new ArrayList<AnneeAcademique>();
    private Module module;
    private Note note;
    private List<Module> modules;
    private Long moduleId;
    private String matricule;
    private List<Etudiant> items;
    private ProfilEtudiant profilEtudiant;
    private ProfilEtudiant proetu;
    private ProfilEtudiant tmp;
    private List<ProfilEtudiant> listPE = new ArrayList<ProfilEtudiant>();
    private Long specialiteId;
    private Long departementId;
    private Long sectionId;
    private Long niveauId;
    private Long anneeAcademiqueid;
    private List<Note> notes;
    private boolean sectionDisable = true;
    private boolean specialiteDisable = true;
    private int level;
    private JasperPrint jasperPrint;
    private String reportName;
    private Etudiant etudiant;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "print";
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private String typeProfil;

    public EtudiantBean() {
        selected = new Etudiant();
        profilEtudiant = new ProfilEtudiant();
        tmp = new ProfilEtudiant();
        proetu = new ProfilEtudiant();
        note = new Note();
        niveau = new Niveau();
    }

    public void PDF(ActionEvent event) throws JRException, IOException, ServiceException { //remplace xlx pour imprimer en pdf
        if (matricule != null) {
            matricule = matricule.trim(); // on degage les espaces aux aentouts
        }
        try {
            etudiant = etu.findByMatricule(matricule);
        } catch (NoResultException ex) {
            JsfUtil.addErrorMessage("le matricule est non reconnu par le systeme");
            return;
        }
        init("profil");
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void init(String nom) throws JRException, ServiceException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(generic());
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "note" + File.separator + nom + ".jasper", new HashMap(), beanCollectionDataSource);
    }

    List<Profil> generic() throws ServiceException {
        Etudiant etudiant = etu.findByMatricule(matricule);
        reportName = "profil" + etudiant.getNom();
        reportName = reportName.replaceAll(" ", "");
        List<Profil> profils = new LinkedList<>();
        List<Note> notes = not.findByNoteForProfil(matricule);
        int i = 0, j = 0, k = 0, m = 0;
        String section = null;
        String niveau = null;
        String specialite = null;
        String annee = null;
        if (typeProfil.equals("sp")) {
            notes = new LinkedList<>();
        }
        if (notes.isEmpty()) {
            List<SpecialiteEtudiant> specialites = speEt.findByMatriculeEtudiant(matricule);
            for (SpecialiteEtudiant specialite1 : specialites) {
                i++;
                Profil profil = new Profil();
                profil.setDateNais(etudiant.getDateNais());
                profil.setDepartementO(etudiant.getDepartement());
                profil.setLieuNais(etudiant.getLieuNais());
                profil.setMatricule(etudiant.getMatricule());

                profil.setNiveau(specialite1.getSpecialite().getNiveau().getLevel() + "");
                profil.setNomPrenom(etudiant.getNom());
                profil.setRegionO(etudiant.getRegion());
                profil.setSection(specialite1.getSpecialite().getNiveau().getSection().getNom());
                profil.setSexe(etudiant.getSexe());
                profil.setSpecialite(specialite1.getSpecialite().getNom());
              
                String result = "";
                Long idSect = null;
                
                 
               profil.setAnnee(specialite1.getAnneeAcademique().getAnnee());
                
                  if (!profil.getAnnee().equals("2014/2015")) {
                idSect = specialite1.getSpecialite().getNiveau().getSection().getId();
            }
            if ((profil.getAnnee() + "").contains("2013/2014")) {
                profil.setSituation("totalite par defaut");  //solvabe ou pas
            } else {
                if (insc.aPayeByTypeAnnee(1, specialite1.getAnneeAcademique().getId(), specialite1.getEtudiant().getMatricule(), idSect)) {
                    result += "tranche 1 ";
                }
                if (insc.aPayeByTypeAnnee(2, specialite1.getAnneeAcademique().getId(), specialite1.getEtudiant().getMatricule(), idSect)) {
                    result += "tranche 2 ";
                }
                if (insc.aPayeByTypeAnnee(3, specialite1.getAnneeAcademique().getId(), specialite1.getEtudiant().getMatricule(), idSect)) {
                    result += "totalite ";
                }
                profil.setSituation(result);  //solvabe ou pas
            }

               



                if (section == null) {
                    section = profil.getSection();
                } else {
                    if (profil.getSection().equals(section)) {
                        profil.setSectionGroup(0);
                    } else {
                        section = profil.getSection();
                    }
                }

                if (niveau == null) {
                    niveau = profil.getNiveau();
                } else {
                    if (profil.getNiveau().equals(niveau)) {
                        profil.setNiveauGroup(0);
                    } else {
                        niveau = profil.getNiveau();
                        specialite = null;
                    }
                }


                if (specialite == null) {
                    annee = null;
                    specialite = profil.getSpecialite();
                } else {
                    if (profil.getSpecialite().equals(specialite)) {
                        profil.setSpecialiteGroup(0);
                    } else {
                        specialite = profil.getSpecialite();
                        annee = null;
                    }
                }

                if (annee == null) {
                    annee = profil.getAnnee();
                } else {
                    if (profil.getAnnee().equals(annee)) {
                        profil.setAnneeGroup(0);
                    } else {
                        annee = profil.getAnnee();
                    }
                }

                if (notes.size() == i) {
                    profil.setEnd(1);
                }
                profils.add(profil);
            }

        }
        for (Note note1 : notes) {
            Profil profil = new Profil();
            EtudiantSection etudiantSect = new EtudiantSection();
            try {
                etudiantSect = etudiantSection.findByEtudiantSection(note1.getEtudiant().getId(), note1.getMatiere().getModule().getSpecialite().getNiveau().getSection().getId());
            } catch (NoResultException no) {
                etudiantSect = new EtudiantSection();
                etudiantSect.setEtudiant(note1.getEtudiant());
                etudiantSect.setSection(note1.getMatiere().getModule().getSpecialite().getNiveau().getSection());
                etudiantSection.create(etudiantSect);
            }





            i++;

            profil.setAnnee(note1.getMatiere().getModule().getAnneeAcademique().getAnnee());
            profil.setAnoExam(note1.getAnonymatEx());
            profil.setAnoRat(note1.getAnonymatRat());
            profil.setApreciation(note1.getDecision());
            if (note1.getCc() != null) {
                profil.setCc(note1.getCc() + "");
            }
            profil.setCodeUE(note1.getMatiere().getModule().getTargetCode());
            try {
                if ((note1.getMatiere().getCode() != null)) {
                    profil.setCodeUE(note1.getMatiere().getCode());
                }
            } catch (NullPointerException aa) {
            }




            profil.setDateNais(note1.getEtudiant().getDateNais());
            profil.setDepartementO(note1.getEtudiant().getDepartement());
            if (note1.getExamen() != null) {
                profil.setExamen(note1.getExamen() + "");
            }
            profil.setGrade(note1.getGrade());
            profil.setIntituleMat(note1.getMatiere().getIntitule());
            profil.setLieuNais(note1.getEtudiant().getLieuNais());
            profil.setMatricule(note1.getEtudiant().getMatricule());
            if (note1.getMoy() != null) {
                profil.setMoyenne(note1.getMoy() + "");
            }
            profil.setNiveau(note1.getMatiere().getModule().getSpecialite().getNiveau().getLevel() + "");
            profil.setNomPrenom(note1.getEtudiant().getNom());
            profil.setPositionUE(note1.getMatiere().getModule().getCode());
            profil.setRegionO(note1.getEtudiant().getRegion());
            profil.setSection(note1.getMatiere().getModule().getSpecialite().getNiveau().getSection().getNom());
            profil.setSexe(note1.getEtudiant().getSexe());
            profil.setSpecialite(note1.getMatiere().getModule().getSpecialite().getNom());
            if (note1.getTd() != null) {
                profil.setTd(note1.getTd() + "");
            }
            if (note1.getTpe() != null) {
                profil.setTpe(note1.getTpe() + "");
            }
            if (note1.getRattrapage() != null) {
                profil.setRatrappage(note1.getRattrapage() + "");
            }


            try {
                SpecialiteEtudiant spe = speEt.findByEtudiantSpecialiteAnnee(note1.getEtudiant().getId(), note1.getMatiere().getModule().getAnneeAcademique().getId(), note1.getMatiere().getModule().getSpecialite().getId());
                if (spe.getCredits() != null) {
                    profil.setDecision(spe.getDecision());
                    profil.setCredits(spe.getCredits() + "");  //credits capitalises 60
                } else {
                    int niv = note1.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                    switch (niv) {
                        case 1:
                            profil.setCredits(etudiantSect.getCredit1() + etudiantSect.getCredit2() + "");
                            break;
                        case 2:
                            profil.setCredits(etudiantSect.getCredit3() + etudiantSect.getCredit4() + "");

                            break;
                        case 3:
                            profil.setCredits(etudiantSect.getCredit5() + etudiantSect.getCredit6() + "");

                            break;
                        case 4:
                            profil.setCredits(etudiantSect.getCredit7() + etudiantSect.getCredit8() + "");

                            break;
                        case 5:
                            profil.setCredits(etudiantSect.getCredit9() + etudiantSect.getCredit10() + "");
                            break;
                    }
                }
            } catch (NoResultException ex) {
                int niv = note1.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                switch (niv) {
                    case 1:
                        profil.setCredits(etudiantSect.getCredit1() + etudiantSect.getCredit2() + "");
                        break;
                    case 2:
                        profil.setCredits(etudiantSect.getCredit3() + etudiantSect.getCredit4() + "");

                        break;
                    case 3:
                        profil.setCredits(etudiantSect.getCredit5() + etudiantSect.getCredit6() + "");

                        break;
                    case 4:
                        profil.setCredits(etudiantSect.getCredit7() + etudiantSect.getCredit8() + "");

                        break;
                    case 5:
                        profil.setCredits(etudiantSect.getCredit9() + etudiantSect.getCredit10() + "");
                        break;
                }
            }

            String result = "";
            Long idSect = null;
           if (!profil.getAnnee().equals("2014/2015")) {
                idSect = note1.getMatiere().getModule().getSpecialite().getNiveau().getSection().getId();
            }
            if ((profil.getAnnee() + "").contains("2013/2014")) {
                profil.setSituation("totalite par defaut");  //solvabe ou pas
            } else {
                if (insc.aPayeByTypeAnnee(1, note1.getMatiere().getModule().getAnneeAcademique().getId(), note1.getEtudiant().getMatricule(), idSect)) {
                    result += "tranche 1 ";
                }
                if (insc.aPayeByTypeAnnee(2, note1.getMatiere().getModule().getAnneeAcademique().getId(), note1.getEtudiant().getMatricule(), idSect)) {
                    result += "tranche 2 ";
                }
                if (insc.aPayeByTypeAnnee(3, note1.getMatiere().getModule().getAnneeAcademique().getId(), note1.getEtudiant().getMatricule(), idSect)) {
                    result += "totalite ";
                }
                profil.setSituation(result);  //solvabe ou pas
            }




            if (section == null) {
                section = profil.getSection();
            } else {
                if (profil.getSection().equals(section)) {
                    profil.setSectionGroup(0);
                } else {
                    section = profil.getSection();
                }
            }

            if (niveau == null) {
                niveau = profil.getNiveau();
            } else {
                if (profil.getNiveau().equals(niveau)) {
                    profil.setNiveauGroup(0);
                } else {
                    niveau = profil.getNiveau();
                    specialite = null;
                }
            }


            if (specialite == null) {
                annee = null;
                specialite = profil.getSpecialite();
            } else {
                if (profil.getSpecialite().equals(specialite)) {
                    profil.setSpecialiteGroup(0);
                } else {
                    specialite = profil.getSpecialite();
                    annee = null;
                }
            }

            if (annee == null) {
                annee = profil.getAnnee();
            } else {
                if (profil.getAnnee().equals(annee)) {
                    profil.setAnneeGroup(0);
                } else {
                    annee = profil.getAnnee();
                }
            }

            if (notes.size() == i) {
                profil.setEnd(1);
            }
            profils.add(profil);
        }
          logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "édition du profil de l'etudiant:  " + etudiant);
        return profils;
    }

    public void saveNew(ActionEvent event) {
        try {
            etu.create(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation du l'etudiant:  " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        } catch (java.lang.IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        }
    }

    public void deleteStudentForLevel() {
        try {
            etu.deleteStudentForLevel(specialiteId, anneeAcademiqueid, level);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des etudiants de la specialite ID:  " + specialiteId + " pour l'annee academique d'id: " + anneeAcademiqueid + " du niveau :" + level);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression réalisée avec success!", ""));
        } catch (ServiceException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "erreur suppression!", ""));
        }

    }

    public void delete(ActionEvent event) {
        try {
            etu.delete(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de l'etudiant:  " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        } catch (org.hibernate.exception.ConstraintViolationException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible (ConstraintViolationException)", ""));
        }catch (org.springframework.dao.DataIntegrityViolationException er){
            JsfUtil.addErrorMessage("suppression impossible !");
       }
    }

    public void update(ActionEvent event) {
        try {
            Etudiant et = etu.findById(selected.getId());
            selected.setId(et.getId());
            etu.update(selected);          logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour de l'etudiant:  " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public List<Etudiant> getItems() throws ServiceException {
        if (items == null) {
            items = etu.findAll();
        }
        return items;
    }

  
    public void prepare() {
        selected = new Etudiant();
    }

    public Etudiant getSelected() {
        return selected;
    }

    public void setSelected(Etudiant selected) {
        this.selected = selected;
    }

    public ISEtudiant getEtu() {
        return etu;
    }

    public void setEtu(ISEtudiant etu) {
        this.etu = etu;
    }

    public ISMatiere getMat() {
        return mat;
    }

    public void setMat(ISMatiere mat) {
        this.mat = mat;
    }

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public List<Matiere> getListMatiereCom() {
        return listMatiereCom;
    }

    public void setListMatiereCom(List<Matiere> listMatiereCom) {
        this.listMatiereCom = listMatiereCom;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public ProfilEtudiant getProfilEtudiant() {
        return profilEtudiant;
    }

    public void setProfilEtudiant(ProfilEtudiant profilEtudiant) {
        this.profilEtudiant = profilEtudiant;
    }

    public List<Matiere> getListMat(Long idEtudiant, Long idNiveau, Long idSpecialite) {
        try {
            listMat = mat.findListMatiereNiveauSpecialite(idEtudiant, idNiveau, idSpecialite);
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listMat;
    }

    public List<Section> getListSection() throws ServiceException {
        listSection = sec.findByDepartement(departementId);
        return listSection;
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

    public List<Departement> getListDepartement() throws ServiceException {
        listDepartement = dep.findAll();
        return listDepartement;
    }

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }

    public void setListDepartement(List<Departement> listDepartement) {
        this.listDepartement = listDepartement;
    }

    public List<Matiere> getListMatiere() {
        return listMatiere;
    }

    public void setListMatiere(List<Matiere> listMatiere) {
        this.listMatiere = listMatiere;
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

    public Long getNiveauId() {
        return niveauId;
    }

    public void setNiveauId(Long niveauId) {
        this.niveauId = niveauId;
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

    public List<Specialite> getListSpecialite() throws ServiceException {
        List<Niveau> niveaus = niv.findBySection(sectionId);
        for (Niveau niveau : niveaus) {
            if (niveau.getLevel() == 3) {
                niveauId = niveau.getId();
            }
        }
        listSpecialite = spe.findByNiveau(niveauId);
        return listSpecialite;
    }

    public void processDepartement() throws ServiceException {
        sectionDisable = false;
        specialiteDisable = true;
        getListSection();
    }

    public void processSection() throws ServiceException {
        specialiteDisable = false;
        getListSpecialite();
    }

    public List<AnneeAcademique> getListAnneeAcademique() throws ServiceException {
        listAnneeAcademique = anAc.findAll();
        return listAnneeAcademique;
    }

    public void setListMat(List<Matiere> listMat) {
        this.listMat = listMat;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public List<ProfilEtudiant> getListPE() {
        return listPE;
    }

    public void setListPE(List<ProfilEtudiant> listPE) {
        this.listPE = listPE;
    }

    public ISSpecialiteEtudiant getSpeEt() {
        return speEt;
    }

    public void setSpeEt(ISSpecialiteEtudiant speEt) {
        this.speEt = speEt;
    }

    public List<SpecialiteEtudiant> getListSpeEtu() {
        try {
            listSpeEtu = speEt.findAll();
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listSpeEtu;
    }

    public void setListSpeEtu(List<SpecialiteEtudiant> listSpeEtu) {
        this.listSpeEtu = listSpeEtu;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public List<AnneeAcademique> getListAnAc(Long idEtudiant, Long idSpecialite) {
        try {
            listAnAc = anAc.findByEtudiantSpecialite(idEtudiant, idSpecialite);
        } catch (ServiceException ex) {
            Logger.getLogger(EtudiantBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listAnAc;
    }

    public void setListAnAc(List<AnneeAcademique> listAnAc) {
        this.listAnAc = listAnAc;
    }

    public ISNote getNot() {
        return not;
    }

    public void setNot(ISNote not) {
        this.not = not;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public ProfilEtudiant getTmp() {
        return tmp;
    }

    public void setTmp(ProfilEtudiant tmp) {
        this.tmp = tmp;
    }

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    public Long getAnneeAcademiqueid() {
        return anneeAcademiqueid;
    }

    public void setAnneeAcademiqueid(Long anneeAcademiqueid) {
        this.anneeAcademiqueid = anneeAcademiqueid;
    }

    public ProfilEtudiant getProetu() {
        return proetu;
    }

    public void setProetu(ProfilEtudiant proetu) {
        this.proetu = proetu;
    }

    public ISNiveau getNiv() {
        return niv;
    }

    public void setNiv(ISNiveau niv) {
        this.niv = niv;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public List<Note> getNotes() throws ServiceException {
        notes = not.findByEtudiant(matricule);
        /*for (Note note : notes) {
         not.calcul(note);
         }*/
        return notes;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public ISInscription getInsc() {
        return insc;
    }

    public void setInsc(ISInscription insc) {
        this.insc = insc;
    }

    public ISSemestre getSem() {
        return sem;
    }

    public void setSem(ISSemestre sem) {
        this.sem = sem;
    }

    public ISEtudiantSection getEtudiantSection() {
        return etudiantSection;
    }

    public void setEtudiantSection(ISEtudiantSection etudiantSection) {
        this.etudiantSection = etudiantSection;
    }

    public String getTypeProfil() {
        return typeProfil;
    }

    public void setTypeProfil(String typeProfil) {
        this.typeProfil = typeProfil;
    }
}
