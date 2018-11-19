/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.NivUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.projection.DelStat;
import com.univ.maroua.flsh.projection.PvMatiere;
import com.univ.maroua.flsh.projection.PvMatiereModel;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteAnnee;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author lappa
 */
@ManagedBean(name = "noteBean")
@SessionScoped
@ViewScoped
public class NoteBean implements Serializable {

    public static String ALL = "all";
    public static String NOTE_CC_EXAM = "ccExam";
    public static String NOTE_CC = "cc";
    public static String ANONYMAT_EXAMEN = "exama";
    public static String NOTE_EXAMEN = "examn";
    public static String ANONYMAT_RATRAPPAGE = "rata";
    public static String NOTE_RATRAPPAGE = "ratn";
    private Integer progress;
    JasperPrint jasperPrint;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "print";
    @ManagedProperty(value = "#{ISNote}")
    private ISNote not;
    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    @ManagedProperty(value = "#{ISMatiere}")
    private ISMatiere mat;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
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
    @ManagedProperty(value = "#{ISInscription}")
    private ISInscription insc;
    @ManagedProperty(value = "#{ISSPecialiteAnnee}")
    private ISSpecialiteAnnee specialiteAnnee;
    @ManagedProperty(value = "#{ISUtilisateur}")
    private ISUtilisateur uti;
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant iSSpecialiteEtudiant;
    private Inscription inscription;
    private Section section;
    private Etudiant etudiant;
    private AnneeAcademique anneeAcademique;
    private List<AnneeAcademique> listAnneeAcademique;
    private Note selected;
    private List<Note> items;
    private List<Note> listNotes = new ArrayList<Note>();
    private Matiere matiere;
    private Module module;
    private List<Matiere> listMatiere;
    private List<Section> listSections = new ArrayList<Section>();
    private List<Departement> listDepartement;
    private Departement departement;
    private List<Niveau> listNiveau;
    private Niveau niveau;
    private List<Specialite> listSpecialite;
    private Specialite specialite;
    private List<PvMatiere> listPvMatiere = new ArrayList<PvMatiere>();
    private List<Matiere> listMat = new ArrayList<Matiere>();
    private List<Etudiant> listEtud = new ArrayList<Etudiant>();
    private List<Module> listModule = new ArrayList<Module>();
    private List<Inscription> listHistIns = new ArrayList<Inscription>();
    private PvMatiere pvMatiere;
    private int numEtu = 0;
    private int temps = 0;
    private List<Section> sections;
    private List<Niveau> niveaus;
    private List<Module> modules;
    private List<Specialite> specialites;
    private List<Matiere> matieres;
    //pour desactiver les combo boxes au lancement
    private boolean sectionDisable = true;
    private boolean niveauDisable = true;
    private boolean specialiteDisable = true;
    private boolean anneeDisable = true;
    private boolean moduleDisable = true;
    private boolean matiereDisable = true;
    private boolean barDisable = false;
    private boolean delibereDisable = true;
    private boolean statusPannoDeliberation = false;
    //les valeurs des elements contenus dans la combobox
    private String typeReleve;
    private Long departementId;
    private Long sectionId;
    private Long niveauId;
    private Long specialiteId;
    private Long matiereId = null;
    private Long moduleId;
    private Long etudiantId;
    private Long anneeAcademiqueid;
    private Long niveauid;
    private Long level;
    private String moduleName;
    private Long delibereId;
    private Long matiereTransfertId;
    private Float noteDeliberationNorm;
    private Float noteDeliberationRat;
    private PvMatiere target = new PvMatiere();
    private String sessionDel;
    private PvMatiere selectedNote;
    private String typeImpression;
    private String typeSup;
    private PvMatiere[] selectedPvs;
    private PvMatiereModel pvMatiereModel;
    HashMap hm = new HashMap();
    private Float tpe;
    private String matricule;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private int tdf;
    private int tpef;
    private int ccf;
    private int exf;
    private int typeOp;
    private Date dateReq = new Date();
    private Map<String, Long> niveaux;

    public NoteBean() {
        hm.put(1, "un");
        hm.put(2, "deux");
        hm.put(3, "trois");
        hm.put(4, "quatre");
        hm.put(5, "cinq");
        hm.put(6, "six");
        hm.put(7, "un");
        hm.put(8, "deux");
        hm.put(9, "trois");
        hm.put(10, "quatre");


        section = new Section();
        selected = new Note();
        anneeAcademique = new AnneeAcademique();
        matiere = new Matiere();
        etudiant = new Etudiant();
        departement = new Departement();
        niveau = new Niveau();
        specialite = new Specialite();
        pvMatiere = new PvMatiere();
        selectedNote = new PvMatiere();
    }

    public void saveNew(ActionEvent event) {
        try {
            anneeAcademique = anAc.findById(anneeAcademiqueid);
            matiere = mat.findById(matiereId);
            etudiant = etu.findById(etudiantId);
            selected.setMatiere(matiere);
            selected.setEtudiant(etudiant);
            Note n = not.calcul(selected);
            not.create(n);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation de la note : " + n);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }

    public void filter(ActionEvent event) throws ServiceException {
        matiere = mat.findById(matiereId);
        anneeAcademique = anAc.findById(anneeAcademiqueid);
//        items = not.filters(matiereId, anneeAcademiqueid);
        delibereDisable = false;
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));

    }

    public void delete(ActionEvent event) {
        try {
            not.delete(selected);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de la note : " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Suppression avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suppression impossible", ""));
        }
    }

    public void update(ActionEvent event) {
        try {
            anneeAcademique = anAc.findById(anneeAcademiqueid);
            matiere = mat.findById(matiereId);
            etudiant = etu.findById(etudiantId);
            selected.setMatiere(matiere);
            selected.setEtudiant(etudiant);
            Note n = not.findById(selected.getId());
            selected.setId(n.getId());
            Note no = not.calcul(selected);
            not.update(no);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour de la note : avant " + n + " et apres: " + no);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public void init(String nom) throws JRException, ServiceException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(getListPvMatiere());
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "note" + File.separator + nom + ".jasper", new HashMap(), beanCollectionDataSource);
    }

    public void export(JRXlsxExporter xlsxExporter, ServletOutputStream servletOutputStream) throws JRException {
        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        xlsxExporter.exportReport();
    }

    public void xlsxPvMatiere(ActionEvent actionEven) throws JRException, IOException, ServiceException {
        String jasperName = "pvMatiere";
        try {
            if (typeImpression.equals("PE")) {
                jasperName = "pvMatiereEx";
            } else if (typeImpression.equals("VE")) {
                jasperName = "pvMatiereExVerif";
            } else if (typeImpression.equals("PR")) {
                jasperName = "pvMatiereRat";
            } else if (typeImpression.equals("VR")) {
                jasperName = "pvMatiereRatVerif";
            }
        } catch (NullPointerException ex) {
        }
        init(jasperName);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment; filename=" + moduleName + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        export(xlsxExporter, servletOutputStream);
    }

    public void exportNote() throws JRException, IOException, ServiceException { //remplace xlx pour imprimer en pdf
        matiere = mat.findById(matiereId);
        String jasperName = "pvMatiere";
        try {
            if (typeImpression.equals("PE")) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "publication des notes d'examen de la matière  : " + matiere);
                jasperName = "pvMatiereEx";
            } else if (typeImpression.equals("VE")) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "vérification des notes de ratrapage de la matière  : " + matiere);
                jasperName = "pvMatiereExVerif";
            } else if (typeImpression.equals("PR")) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "publication des notes d'examen de la matière  : " + matiere);
                jasperName = "pvMatiereRat";
            } else if (typeImpression.equals("VR")) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "vérification des notes de ratrapage de la matière  : " + matiere);
                jasperName = "pvMatiereRatVerif";
            } else if (typeImpression.equals("TPE")) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "publication des notes de classe de la matière  : " + matiere);
                jasperName = "pvMatiereCc";
            }
        } catch (NullPointerException ex) {
        }
        init(jasperName);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + moduleName + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public void xlsxPvMatiereAll(ActionEvent actionEven) throws JRException, IOException, ServiceException {
        List<Matiere> matieress = mat.findMasterBySpecialiteAnnee(specialiteId, anneeAcademiqueid);

        ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasperName = "pvMatiere";
        try {
            if (typeImpression.equals("PE")) {
                jasperName = "pvMatiereEx";
            } else if (typeImpression.equals("VE")) {
                jasperName = "pvMatiereExVerif";
            } else if (typeImpression.equals("PR")) {
                jasperName = "pvMatiereRat";
            } else if (typeImpression.equals("VR")) {
                jasperName = "pvMatiereRatVerif";
            } else if (typeImpression.equals("TPE")) {
                jasperName = "pvMatiereCc";
            }
        } catch (NullPointerException ex) {
        }
        String moduleNames[] = new String[matieress.size()];
        int j = 0;
        for (Matiere ma : matieress) {
            setMatiereId(ma.getId());
            listPvMatiere = getListPvMatiere();
            if (listPvMatiere.size() > 0) {
                moduleNames[j++] = ma.getModule().getTargetCode();
                try {
                    if (ma.getCode() != null) {
                        moduleNames[j - 1] = ma.getCode();
                    }
                } catch (NullPointerException aa) {
                }
                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listPvMatiere);
                jasperPrint = JasperFillManager.fillReport(path + File.separator + "note" + File.separator + jasperName + ".jasper", new HashMap(), beanCollectionDataSource);
                list.add(jasperPrint);
            }
        }


        jasperPrint = list.get(0);
        int i = 0;
        for (JasperPrint jas : list) {
            if (i == 0) {
                i++;
            } else {
                try {
                    List<JRPrintPage> pages = new ArrayList<JRPrintPage>(jas.getPages());
                    for (int count = 0; count < pages.size(); count++) {
                        jasperPrint.addPage(i, (JRPrintPage) pages.get(count));
                    }
                    i++;
                } catch (java.lang.IndexOutOfBoundsException ex) {
                }
            }
        }

        section = sec.findById(sectionId);
        specialite = spe.findById(specialiteId);
        niveau = niv.findById(level);
        AnneeAcademique anc = anAc.findById(anneeAcademiqueid);

        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "exportation sur excel  de toutes les notes  de la spécialité  : " + specialite + " de l'année " + anc);


        String reportName = section.getSigle() + (specialite.getNom().equals("/") ? "" : specialite.getNom()) + niveau.getLevel();
        reportName = reportName.replaceAll(" ", "");

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment; filename=" + reportName + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        xlsxExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, moduleNames);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        xlsxExporter.exportReport();
    }

    public void xlsxPvMatiereRequette(ActionEvent actionEven) throws JRException, IOException, ServiceException {
        List<Matiere> matieress = mat.findMasterBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
        ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasperName = "rqMatiereEx";
        if (typeOp == 1) {
            jasperName = "rqMatiereEx";
        } else if (typeOp == 2) {
            jasperName = "rqMatiereRat";
        } else {
            JsfUtil.addErrorMessage("choisissez le type");
            return;
        }
        String moduleNames[] = new String[matieress.size()];
        int j = 0;
        for (Matiere ma : matieress) {
            setMatiereId(ma.getId());
            listPvMatiere = getListPvMatiereRat();
            if (listPvMatiere.size() > 0) {
                moduleNames[j++] = ma.getModule().getTargetCode();
                try {
                    if (ma.getCode() != null) {
                        moduleNames[j - 1] = ma.getCode();
                    }
                } catch (NullPointerException aa) {
                }
                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listPvMatiere);
                jasperPrint = JasperFillManager.fillReport(path + File.separator + "note" + File.separator + jasperName + ".jasper", new HashMap(), beanCollectionDataSource);
                list.add(jasperPrint);
            }
        }

        if (list.isEmpty()) {
            JsfUtil.addSuccessMessage("aucun élément trouvé!");
            return;
        }

        jasperPrint = list.get(0);

        int i = 0;
        for (JasperPrint jas : list) {
            if (i == 0) {
                i++;
            } else {
                try {
                    List<JRPrintPage> pages = new ArrayList<JRPrintPage>(jas.getPages());
                    for (int count = 0; count < pages.size(); count++) {
                        jasperPrint.addPage(i, (JRPrintPage) pages.get(count));
                    }
                    i++;
                } catch (java.lang.IndexOutOfBoundsException ex) {
                }
            }
        }

        section = sec.findById(sectionId);
        specialite = spe.findById(specialiteId);
        niveau = niv.findById(level);
        AnneeAcademique anc = anAc.findById(anneeAcademiqueid);


        if (typeOp == 1) {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "exportation sur excel  des requettes d'examen  de la spécialité  : " + specialite + " de l'annee " + anc);
            jasperName = "rqMatiereEx";
        } else if (typeOp == 2) {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "exportation sur excel  des requettes de ratrappage  de la spécialité  : " + specialite + " de l'annee " + anc);
            jasperName = "rqMatiereRat";
        } else {
            JsfUtil.addErrorMessage("choisissez le type");
            return;
        }

        String reportName = jasperName + section.getSigle() + (specialite.getNom().equals("/") ? "" : specialite.getNom()) + niveau.getLevel();
        reportName = reportName.replaceAll(" ", "");

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment; filename=" + reportName + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        xlsxExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, moduleNames);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        xlsxExporter.exportReport();
    }

    public void xlsxPvMatiereAllPDF(ActionEvent actionEven) throws JRException, IOException, ServiceException {
        List<Matiere> matieress = mat.findMasterBySpecialiteAnnee(specialiteId, anneeAcademiqueid);

        ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
        String jasperName = "pvMatiere";
        try {
            if (typeImpression.equals("PE")) {
                jasperName = "pvMatiereEx";
            } else if (typeImpression.equals("VE")) {
                jasperName = "pvMatiereExVerif";
            } else if (typeImpression.equals("PR")) {
                jasperName = "pvMatiereRat";
            } else if (typeImpression.equals("VR")) {
                jasperName = "pvMatiereRatVerif";
            } else if (typeImpression.equals("TPE")) {
                jasperName = "pvMatiereCc";
            }
        } catch (NullPointerException ex) {
        }
        String moduleNames[] = new String[matieress.size()];
        int j = 0;
        for (Matiere ma : matieress) {
            setMatiereId(ma.getId());
            listPvMatiere = getListPvMatiere();
            if (listPvMatiere.size() > 0) {
                moduleNames[j++] = ma.getModule().getTargetCode();
                try {
                    if (ma.getCode() != null) {
                        moduleNames[j - 1] = ma.getModule().getTargetCode();
                    }
                } catch (NullPointerException aa) {
                }
                JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listPvMatiere);
                jasperPrint = JasperFillManager.fillReport(path + File.separator + "note" + File.separator + jasperName + ".jasper", new HashMap(), beanCollectionDataSource);
                list.add(jasperPrint);

            }
        }


        jasperPrint = list.get(0);
        int i = 0;
        for (JasperPrint jas : list) {
            if (i == 0) {
                i++;
            } else {
                try {
                    List<JRPrintPage> pages = new ArrayList<JRPrintPage>(jas.getPages());
                    for (int count = 0; count < pages.size(); count++) {
                        jasperPrint.addPage(i, (JRPrintPage) pages.get(count));
                    }
                    i++;
                } catch (java.lang.IndexOutOfBoundsException ex) {
                }
            }
        }

        section = sec.findById(sectionId);
        specialite = spe.findById(specialiteId);
        niveau = niv.findById(level);

        String reportName = section.getSigle() + (specialite.getNom().equals("/") ? "" : specialite.getNom()) + niveau.getLevel();
        reportName = reportName.replaceAll(" ", "");


        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public List<PvMatiere> getListPvMatiere() throws ServiceException {
        if (matiereId != null) {
            matiere = mat.findById(matiereId);
        }
        Date date = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.FRENCH);
        try {
            items = new LinkedList<Note>();
            listPvMatiere = new LinkedList<PvMatiere>();
            items = not.filters(matiereId, anneeAcademiqueid);
            int size = items.size();
            int compose = 0;
            int noCompose = 0;


            try {
                if (typeImpression.equals("ALL") || typeImpression.equals("TPE")) {
                    items = not.filters(matiereId, anneeAcademiqueid);
                    for (Note note : items) {
                        if ((note.getExamen() == null) && (note.getRattrapage() == null)) {
                            if ((note.getTpe() == null) && (note.getTd() == null) && (note.getCc() == null) && (note.getAnonymatEx() == null) && (note.getAnonymatRat() == null)) {
                                not.delete(note);
                            }
                        }
                    }
                    typeReleve = "2";
                } else if (typeImpression.equals("PE")) {
                    typeReleve = "1";
                    items = not.filters(matiereId, anneeAcademiqueid);
                    for (Note note : items) {
                        if ((note.getExamen() == null) && (note.getRattrapage() == null)) {
                            if ((note.getTpe() == null) && (note.getTd() == null) && (note.getCc() == null) && (note.getAnonymatEx() == null) && (note.getAnonymatRat() == null)) {
                                not.delete(note);
                            }
                        }
                    }
                    items = not.sortByAnnymat(items, 0);
                } else if (typeImpression.equals("VE")) {
                    typeReleve = "2";
                    items = not.filters(matiereId, anneeAcademiqueid);
                    for (Note note : items) {
                        if ((note.getExamen() == null) && (note.getRattrapage() == null)) {
                            if ((note.getTpe() == null) && (note.getTd() == null) && (note.getCc() == null) && (note.getAnonymatEx() == null) && (note.getAnonymatRat() == null)) {
                                not.delete(note);
                            }
                        }
                    }
                    List<Note> data = new LinkedList<>();
                    for (Note n : items) {
                        if (n.getAnonymatEx() == null) {
                            data.add(n);
                        } else if (n.getAnonymatEx().isEmpty()) {
                            data.add(n);
                        }
                    }
                    for (Note note : data) {
                        items.remove(note);
                    }
                    items = not.sortByAnnymat(items, 1);
                } else if (typeImpression.equals("PR")) {
                    typeReleve = "2";
                    items = not.filters(matiereId, anneeAcademiqueid);
                    for (Note note : items) {
                        if ((note.getExamen() == null) && (note.getRattrapage() == null)) {
                            if ((note.getTpe() == null) && (note.getTd() == null) && (note.getCc() == null) && (note.getAnonymatEx() == null) && (note.getAnonymatRat() == null)) {
                                not.delete(note);
                            }
                        }
                    }
                    List<Note> data = new LinkedList<Note>();
                    for (Note n : items) {
                        if ((n.getRattrapage() == null) && (n.getAnonymatRat() == null)) {
                            data.add(n);
                        }
                    }
                    for (Note note : data) {
                        items.remove(note);
                    }

                    items = not.sortByAnnymat(items, 0);

                } else if (typeImpression.equals("VR")) {
                    typeReleve = "2";
                    items = not.filters(matiereId, anneeAcademiqueid);
                    for (Note note : items) {
                        if ((note.getExamen() == null) && (note.getRattrapage() == null)) {
                            if ((note.getTpe() == null) && (note.getTd() == null) && (note.getCc() == null) && (note.getAnonymatEx() == null) && (note.getAnonymatRat() == null)) {
                                not.delete(note);
                            }
                        }
                    }
                    items = not.sortByAnnymat(items, 2);
                    List<Note> data = new LinkedList<Note>();
                    for (Note n : items) {
                        if (n.getAnonymatRat() == null) {
                            data.add(n);
                        } else if (n.getAnonymatRat().isEmpty()) {
                            data.add(n);
                        }
                    }
                    for (Note note : data) {
                        items.remove(note);
                    }
                }
            } catch (NullPointerException ex) {
                JsfUtil.addErrorMessage("choisissez d'abord le type d'operations à éffectuer");
                return null;
            }


            int semestre = mat.findById(matiereId).getModule().getSemestre().getLevel();
            departement = dep.findById(departementId);
            section = sec.findById(sectionId);
            specialite = spe.findById(specialiteId);
            module = matiere.getModule();
            int numEtu = 0;
            niveau = niv.findById(level);

            Long idSect = null;
            if (!module.getAnneeAcademique().getAnnee().equals("2014/2015")) {
                idSect = niveau.getSection().getId();
            }


            //calcul des stats

            int exam = items.size();
            int valides = 0;
            int echec = 0;
            int note95 = 0;
            int note9 = 0;
            int note8 = 0;
            int note85 = 0;
            int cap = 0;
            int cant = 0;
            int aj = 0;
            for (Note note1 : items) {
                //not.calcul(note1); //par precaution losqu'on importe les notes de cc apres. on verrra ca
                pvMatiere = new PvMatiere();
                pvMatiere.setNumEtu(++numEtu);
                pvMatiere.setMatricule(note1.getEtudiant().getMatricule());
                pvMatiere.setNomEtudiant(note1.getEtudiant().getNom());
                pvMatiere.setNoteId(note1.getId());
                if (!specialite.getNom().equals("/")) {
                    pvMatiere.setSpecialite("Specialité: " + specialite.getNom());
                }


                pvMatiere.setDate(dateFormat.format(date));
                try {
                    if (note1.getMoy() >= 10) {
                        valides++;
                        if (note1.getMoy() >= 11) {
                            cap++;
                        } else {
                            cant++;
                        }
                    } else if (note1.getMoy() < 10 && note1.getMoy() >= 8) {
                        aj++;
                        if (note1.getMoy() < 10 && note1.getMoy() >= 9.5) {
                            note95++;
                        } else if (note1.getMoy() < 9.5 && note1.getMoy() >= 9) {
                            note9++;
                        } else if (note1.getMoy() < 9 && note1.getMoy() >= 8.5) {
                            note85++;
                        } else if (note1.getMoy() < 8.5 && note1.getMoy() >= 8) {
                            note8++;
                        }
                    } else {
                        if ((note1.getMoy() != null)) {
                            echec++;
                        }
                    }
                } catch (NullPointerException ex) {
                    //nothing to do
                }

                compose = valides + aj + echec;
                noCompose = size - compose;

                pvMatiere.setNombreEtu(size);
                pvMatiere.setNombreCompose(compose);
                pvMatiere.setNombreExam(exam);
                pvMatiere.setNombreNoCompose(noCompose);
                pvMatiere.setNombreVa(valides);
                pvMatiere.setNombre95(note95);
                pvMatiere.setNombre9(note9);
                pvMatiere.setNombre85(note85);
                pvMatiere.setNombre8(note8);
                pvMatiere.setNombreEch(echec);
                pvMatiere.setNombreCa(cap);
                pvMatiere.setNombreCant(cant);
                pvMatiere.setNombreAj(aj);

                pvMatiere.setPourcentVa(compose == 0 ? 0 : 100 * valides / (float) compose);
                pvMatiere.setPourcentEch(compose == 0 ? 0 : 100 * echec / (float) compose);
                pvMatiere.setPourcent95(compose == 0 ? 0 : 100 * note95 / (float) compose);
                pvMatiere.setPourcent9(compose == 0 ? 0 : 100 * note9 / (float) compose);
                pvMatiere.setPourcent8(compose == 0 ? 0 : 100 * note8 / (float) compose);
                pvMatiere.setPourcent85(compose == 0 ? 0 : 100 * note85 / (float) compose);
                pvMatiere.setPourcentCa(compose == 0 ? 0 : 100 * cap / (float) compose);
                pvMatiere.setPourcentAj(compose == 0 ? 0 : 100 * aj / (float) compose);
                pvMatiere.setPourcentCant(compose == 0 ? 0 : 100 * cant / (float) compose);

                if (typeReleve.equals("1")) {
                    if (insc.estEligible(semestre, anneeAcademiqueid, note1.getEtudiant().getMatricule(), idSect)) {
                        pvMatiere.setTpe(note1.getTpe());
                        pvMatiere.setTd(note1.getTd());
                        pvMatiere.setEe(note1.getExamen());
                        pvMatiere.setCc(note1.getCc());
                        pvMatiere.setRat(note1.getRattrapage());
                        pvMatiere.setMoy(note1.getMoy());
                        pvMatiere.setCoef(note1.getCote());
                        pvMatiere.setGrade(note1.getGrade());
                        pvMatiere.setDecision(note1.getDecision());
                        pvMatiere.setAnoExam(note1.getAnonymatEx());
                        pvMatiere.setAnoRat(note1.getAnonymatRat());

                    }
                } else {
                    pvMatiere.setTpe(note1.getTpe());
                    pvMatiere.setTd(note1.getTd());
                    pvMatiere.setEe(note1.getExamen());
                    pvMatiere.setCc(note1.getCc());
                    pvMatiere.setRat(note1.getRattrapage());
                    pvMatiere.setMoy(note1.getMoy());
                    pvMatiere.setCoef(note1.getCote());
                    pvMatiere.setGrade(note1.getGrade());
                    pvMatiere.setDecision(note1.getDecision());
                    pvMatiere.setAnoExam(note1.getAnonymatEx());
                    pvMatiere.setAnoRat(note1.getAnonymatRat());

                }
                pvMatiere.setNomDep("Département:" + " " + departement.getNom());


                if ((typeImpression.equals("PR")) || (typeImpression.equals("VR"))) {
                    pvMatiere.setSem("Examen de rattrapage  du semestre  " + hm.get(module.getSemestre().getLevel()));
                } else {
                    pvMatiere.setSem("Examen de fin du semestre  " + hm.get(module.getSemestre().getLevel()));
                }

                pvMatiere.setAnAc("Année Académique " + module.getAnneeAcademique().getAnnee());
                pvMatiere.setNiveau(fomatRomain(niveau.getLevel()));
                pvMatiere.setSection("Section: " + section.getNom());
                pvMatiere.setIntitule(matiere.getIntitule());
                moduleName = module.getTargetCode().replaceAll(" ", "");// le nom du fichier d'import
                pvMatiere.setUe(module.getTargetCode());
                try {
                    if (matiere.getCode() != null) {
                        pvMatiere.setUe(matiere.getCode());
                    }
                } catch (NullPointerException aa) {
                }


                pvMatiere.setCredit(module.getCredit());
                listPvMatiere.add(pvMatiere);

            }


        } catch (IllegalArgumentException e) {
            System.err.println("illegal " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("null " + e.getMessage());
        }

        return listPvMatiere;
    }

    public List<PvMatiere> getListPvMatiereRat() throws ServiceException {
        Date dat = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.FRENCH);

        numEtu = 0;
        if (matiereId != null) {
            matiere = mat.findById(matiereId);
        }
        try {
            items = new LinkedList<Note>();
            listPvMatiere = new LinkedList<PvMatiere>();
            items = not.filters(matiereId, anneeAcademiqueid);
            for (Note note : items) {
                if ((note.getExamen() == null) && (note.getRattrapage() == null)) {
                    if ((note.getTpe() == null) && (note.getTd() == null) && (note.getCc() == null) && (note.getAnonymatEx() == null) && (note.getAnonymatRat() == null)) {
                        not.delete(note);
                    }
                }
            }
            int semestre = mat.findById(matiereId).getModule().getSemestre().getLevel();
            departement = dep.findById(departementId);
            section = sec.findById(sectionId);
            specialite = spe.findById(specialiteId);
            module = matiere.getModule();
            niveau = niv.findById(level);

            Long idSect = null;
            if (!module.getAnneeAcademique().getAnnee().equals("2014/2015")) {
                idSect = niveau.getSection().getId();
            }


            for (Note note1 : items) {
                try {
                    if (insc.estEligible(semestre, anneeAcademiqueid, note1.getEtudiant().getMatricule(), idSect) && (note1.getRequette() == true)) {
                        if (typeOp == 2) {
                            if ((note1.getRattrapage() == null) && (note1.getAnonymatRat() == null)) {
                                continue;
                            }
                        }
                        Calendar cal1 = Calendar.getInstance();
                        Calendar cal2 = Calendar.getInstance();
                        cal1.setTime(dateReq); //la date choisie par le user
                        cal2.setTime(note1.getDaterequette()); //la date de la requette

                        if (cal2.compareTo(cal1) < 0) {
                            continue;
                        }
                        pvMatiere = new PvMatiere();
                        pvMatiere.setNumEtu(++numEtu);
                        pvMatiere.setMatricule(note1.getEtudiant().getMatricule());
                        pvMatiere.setNomEtudiant(note1.getEtudiant().getNom());
                        pvMatiere.setNoteId(note1.getId());
                        if (!specialite.getNom().equals("/")) {
                            pvMatiere.setSpecialite("Specialité: " + specialite.getNom());
                        }
                        pvMatiere.setDate(dateFormat.format(dat));
                        pvMatiere.setTpe(note1.getTpe());
                        pvMatiere.setTd(note1.getTd());
                        pvMatiere.setEe(note1.getExamen());
                        pvMatiere.setCc(note1.getCc());
                        pvMatiere.setRat(note1.getRattrapage());
                        pvMatiere.setMoy(note1.getMoy());
                        pvMatiere.setCoef(note1.getCote());
                        pvMatiere.setGrade(note1.getGrade());
                        pvMatiere.setDecision(note1.getDecision());
                        pvMatiere.setAnoExam(note1.getAnonymatEx());
                        pvMatiere.setAnoRat(note1.getAnonymatRat());
                    } else {
                        continue;
                    }

                    pvMatiere.setNomDep("Département:" + " " + departement.getNom());


                    if (typeOp == 2) {
                        pvMatiere.setSem("Examen de rattrapage  du semestre  " + hm.get(module.getSemestre().getLevel()));
                    } else {
                        pvMatiere.setSem("Examen de fin du semestre  " + hm.get(module.getSemestre().getLevel()));
                    }

                    pvMatiere.setAnAc("Année Académique " + module.getAnneeAcademique().getAnnee());
                    pvMatiere.setNiveau(fomatRomain(niveau.getLevel()));
                    pvMatiere.setSection("Section: " + section.getNom());
                    pvMatiere.setIntitule(matiere.getIntitule());
                    moduleName = module.getTargetCode().replaceAll(" ", "");// le nom du fichier d'import
                    pvMatiere.setUe(module.getTargetCode());
                    try {
                        if (matiere.getCode() != null) {
                            pvMatiere.setUe(matiere.getCode());
                        }
                    } catch (NullPointerException aa) {
                    }

                    pvMatiere.setCredit(module.getCredit());
                    listPvMatiere.add(pvMatiere);

                } catch (IllegalArgumentException e) {
                    System.err.println("illegal " + e.getMessage());
                } catch (NullPointerException e) {
                    System.err.println("null " + e.getMessage());
                }

            }


        } catch (IllegalArgumentException e) {
            System.err.println("illegal " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("null " + e.getMessage());
        }

        return listPvMatiere;
    }

    public void activePandeliberation(ActionEvent actionEven) {
        statusPannoDeliberation = true;
    }

    public void doDeliberation(ActionEvent actionEven) throws ServiceException {
        //essayons de savoir de quel semestre il est question
        int semestre = items.get(0).getMatiere().getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = spe.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = spe.findByNiveau(not.findById(selectedNote.getNoteId()).getMatiere().getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {

                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }
        DelStat del = new DelStat();
        for (Note note1 : items) {
            if (sessionDel.equals("SN")) {
                if (noteDeliberationNorm == null) {
                    JsfUtil.addErrorMessage("la moyenne est vide!");
                    return;
                }
                try {
                    not.calculDeliberation(note1, noteDeliberationNorm, 1, del);

                } catch (NullPointerException e) {
                }
            } else if (sessionDel.equals("SR")) {
                if (noteDeliberationRat == null) {
                    JsfUtil.addErrorMessage("la moyenne est vide!");
                    return;
                }
                try {
                    not.calculDeliberation(note1, noteDeliberationRat, 2, del);
                } catch (NullPointerException e) {
                }
            }
        }
        Matiere matiere = mat.findById(matiereId);
        if (sessionDel.equals("SN")) {
            matiere.setNoteDelS(noteDeliberationNorm);
            mat.update(matiere);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "deliberation de la session normale de la matière " + matiere + " sous la note  : " + noteDeliberationNorm);
            JsfUtil.addSuccessMessage("en tout " + del.getSession() + " notes deliberees");
        } else if (sessionDel.equals("SR")) {
            matiere.setNoteDelR(noteDeliberationRat);
            mat.update(matiere);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "deliberation de la session de rattrapage de la matière " + matiere + " sous la note  : " + noteDeliberationRat);
            JsfUtil.addSuccessMessage("en tout " + del.getRat() + " notes deliberees");
        }
    }

    public void doBonus(ActionEvent actionEven) throws ServiceException {
        //essayons de savoir de quel semestre il est question
        int semestre = items.get(0).getMatiere().getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = spe.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = spe.findByNiveau(not.findById(selectedNote.getNoteId()).getMatiere().getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {

                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }
        DelStat del = new DelStat();
        for (Note note1 : items) {
            if (sessionDel.equals("SN")) {
                if (noteDeliberationNorm == null) {
                    JsfUtil.addErrorMessage("la moyenne est vide!");
                    return;
                }
                if (noteDeliberationNorm <= 0f) {
                    JsfUtil.addErrorMessage("le bonus doit etre strctement positif!");
                    return;
                }

                try {
                    not.ajout(note1, noteDeliberationNorm, 1, del);

                } catch (NullPointerException e) {
                }
            } else if (sessionDel.equals("SR")) {
                if (noteDeliberationRat == null) {
                    JsfUtil.addErrorMessage("la moyenne est vide!");
                    return;
                }
                if (noteDeliberationRat <= 0f) {
                    JsfUtil.addErrorMessage("le bonus doit etre strctement positif!");
                    return;
                }
                try {
                    not.ajout(note1, noteDeliberationRat, 2, del);
                } catch (NullPointerException e) {
                }
            }
        }
        Matiere matiere = mat.findById(matiereId);
        if (sessionDel.equals("SN")) {
            matiere.setNoteBonusS(noteDeliberationNorm);
            mat.update(matiere);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "ajout du bonus  : " + noteDeliberationNorm + " a la matiere " + matiere);
            JsfUtil.addSuccessMessage("en tout " + del.getSession() + " notes d'examen ajoutées");
        } else if (sessionDel.equals("SR")) {
            matiere.setNoteBonusR(noteDeliberationRat);
            mat.update(matiere);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "ajout du bonus  : " + noteDeliberationRat + " a la matiere " + matiere);
            JsfUtil.addSuccessMessage("en tout " + del.getRat() + " notes d'examen ajoutées");
        }
    }

    public void prepare(ActionEvent ac) {
        selected = new Note();
    }

    public Note getSelected() {
        return selected;
    }

    public void setSelected(Note selected) {
        this.selected = selected;
    }

    public ISNote getNot() {
        return not;
    }

    public void setNot(ISNote not) {
        this.not = not;
    }

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public Long getAnneeAcademiqueid() {
        return anneeAcademiqueid;
    }

    public void setAnneeAcademiqueid(Long anneeAcademiqueid) {
        this.anneeAcademiqueid = anneeAcademiqueid;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public ISMatiere getMat() {
        return mat;
    }

    public void setMat(ISMatiere mat) {
        this.mat = mat;
    }

    public ISEtudiant getEtu() {
        return etu;
    }

    public void setEtu(ISEtudiant etu) {
        this.etu = etu;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public Matiere getMatiere() {
        return matiere;
    }

    public void setMatiere(Matiere matiere) {
        this.matiere = matiere;
    }

    public List<Matiere> getListMatiere() throws ServiceException {
        listMatiere = mat.findAll();
        return listMatiere;
    }

    public void setListMatiere(List<Matiere> listMatiere) {
        this.listMatiere = listMatiere;
    }

    public List<AnneeAcademique> getListAnneeAcademique() throws ServiceException {
        listAnneeAcademique = new LinkedList<>();
        Utilisateur utilisateur = uti.findByUsername(UserUtil.getUsername());
        try {
            if (utilisateur.getAllyear() == true) {
                listAnneeAcademique = anAc.findAll();
                return listAnneeAcademique;
            } else {
                listAnneeAcademique = anAc.findAll();
                for (AnneeAcademique anneeAcademique1 : listAnneeAcademique) {
                    if (anneeAcademique1.getIstargetyear() == true) {
                        listAnneeAcademique = new LinkedList<>();
                        listAnneeAcademique.add(anneeAcademique1);
                        return listAnneeAcademique;
                    }
                }
            }
        } catch (NullPointerException ex) {
        }
        listAnneeAcademique = anAc.findAll();
        return listAnneeAcademique;
    }

    public void setListAnneeAcademique(List<AnneeAcademique> listAnneeAcademique) {
        this.listAnneeAcademique = listAnneeAcademique;
    }

    public List<Departement> getListDepartement() throws ServiceException {
        listDepartement = dep.findAll();
        return listDepartement;
    }

    public void setListDepartement(List<Departement> listDepartement) {
        this.listDepartement = listDepartement;
    }

    public List<Niveau> getListNiveau() throws ServiceException {
        listNiveau = niv.findAll();
        return listNiveau;
    }

    public void setListNiveau(List<Niveau> listNiveau) {
        this.listNiveau = listNiveau;
    }

    public List<Specialite> getListSpecialite() throws ServiceException {
        listSpecialite = spe.findAll();
        return listSpecialite;
    }

    public void setListSpecialite(List<Specialite> listSpecialite) {
        this.listSpecialite = listSpecialite;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public ISSection getSec() {
        return sec;
    }

    public void setSec(ISSection sec) {
        this.sec = sec;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Section> getListSections() throws ServiceException {
        listSections = sec.findAll();
        return listSections;
    }

    public void setListSections(List<Section> listSections) {
        this.listSections = listSections;
    }

    public List<Matiere> getListMat() throws ServiceException {
        listMat = mat.findAll();
        return listMat;
    }

    public void setListMat(List<Matiere> listMat) {
        this.listMat = listMat;
    }

    public List<Etudiant> getListEtud() throws ServiceException {
        listEtud = etu.findAll();
        return listEtud;
    }

    public void setListEtud(List<Etudiant> listEtud) {
        this.listEtud = listEtud;
    }

    public PvMatiere getPvMatiere() {
        return pvMatiere;
    }

    public void setPvMatiere(PvMatiere pvMatiere) {
        this.pvMatiere = pvMatiere;
    }

    public int getNumEtu() {
        return numEtu;
    }

    public void setNumEtu(int numEtu) {
        this.numEtu = numEtu;
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

    public Long getDepartementId() {
        return departementId;
    }

    public void setDepartementId(Long departementId) {
        this.departementId = departementId;
    }

    public Long getNiveauid() {
        return niveauid;
    }

    public void setNiveauid(Long niveauid) {
        this.niveauid = niveauid;
    }

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    public List<Note> getListNotes() throws ServiceException {
        listNotes = not.findAll();
        return listNotes;
    }

    public void setListNotes(List<Note> listNotes) {
        this.listNotes = listNotes;
    }

    public void processDepartement() throws ServiceException {
        sectionDisable = false;
        niveauDisable = true;
        specialiteDisable = true;

        getSections();
    }

    public void processSection() throws ServiceException {
        niveauDisable = false;
        specialiteDisable = true;
        getNiveaus();
    }

    public void processNiveau() throws ServiceException {
        specialiteDisable = false;
        getSpecialites();
    }

    public void processSpecialite() throws ServiceException {
        anneeDisable = false;
        moduleDisable = true;
        matiereDisable = true;
        getModules();
    }

    public void processAnnee() throws ServiceException {
        moduleDisable = false;
        matiereDisable = true;
        getModules();
    }

    public void processModule() throws ServiceException {
        matiereDisable = false;
        getMatieres();
    }

    public List<Matiere> getMatieres() throws ServiceException {
        try {
            matieres = mat.findByUE(moduleId);
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage(("cette matiere a ete composse en tronc commun"));
            matieres = null;
        }
        return matieres;
    }

    public List<Niveau> getNiveaus() throws ServiceException {

        try {

            niveaus = niv.findBySection(sectionId);
        } catch (NullPointerException ex) {

            niveaus = new ArrayList<Niveau>();
        }

        return niveaus;
    }

    public List<Section> getSections() throws ServiceException {

        try {

            sections = sec.findByDepartement(departementId);
            //this.niveauDisable = false;
        } catch (NullPointerException ex) {

            sections = new ArrayList<Section>();
        }

        return sections;
    }

    public void setNiveaus(List<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public List<Specialite> getSpecialites() throws ServiceException {

        try {
            specialites = spe.findByNiveau((long) level);
        } catch (NullPointerException ex) {

            specialites = new ArrayList<Specialite>();
        }
        return specialites;
    }

    public List<Module> getModules() throws ServiceException {
        try {
            List<Module> modu = mod.findByAnneeAcSpecialite(anneeAcademiqueid, specialiteId);
            modules = new ArrayList<Module>();
            for (Module module1 : modu) {
                List<Matiere> mati = mat.findByUE(module1.getId());
                if (!mati.isEmpty()) {
                    modules.add(module1);
                }
            }

        } catch (NullPointerException ex) {

            modules = new ArrayList<Module>();
        }
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public boolean isMatiereDisable() {
        return matiereDisable;
    }

    public void setMatiereDisable(boolean matiereDisable) {
        this.matiereDisable = matiereDisable;
    }

    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
    }

    public void setMatieres(List<Matiere> matieres) {
        this.matieres = matieres;
    }

    public boolean isNiveauDisable() {
        return niveauDisable;
    }

    public void setNiveauDisable(boolean niveauDisable) {
        this.niveauDisable = niveauDisable;
    }

    public boolean isSpecialiteDisable() {
        return specialiteDisable;
    }

    public void setSpecialiteDisable(boolean specialiteDisable) {
        this.specialiteDisable = specialiteDisable;
    }

    public boolean isModuleDisable() {
        return moduleDisable;
    }

    public void setModuleDisable(boolean moduleDisable) {
        this.moduleDisable = moduleDisable;
    }

    public boolean isSectionDisable() {
        return sectionDisable;
    }

    public void setSectionDisable(boolean sectionDisable) {
        this.sectionDisable = sectionDisable;
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

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Module> getListModule() throws ServiceException {
        listModule = mod.findAll();
        return listModule;
    }

    public void setListModule(List<Module> listModule) {
        this.listModule = listModule;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }
    /*   public List<PvSemestiel> syntheseSemestrielle(Long idSpecialite,Long semestreId, Long anneeId) throws ServiceException, DataAccessException {
     List<Matiere> Mesmatiere=new ArrayList<Matiere>();
     List<PvSemestiel>pvSemestres=new ArrayList<PvSemestiel>();
     PvSemestiel pvSemestre=new PvSemestiel();
     listEtud=etu.findEtudiantBySpecialite(idSpecialite);
     
     
     for (Etudiant etudiant : listEtud) {
     Mesmatiere=etu.findListMatiereByEtudiant(etudiant.getId(), anneeId, semestreId);
     for (Matiere matiere1 : Mesmatiere) {
                
     selected=not.findNoteByEtudiantMatiere(etudiant.getId(), matiere1.getId()).get(0);
     pvSemestre.setMatiere(matiere1);
     pvSemestre.setNote(not.calcul(selected));
     pvSemestre.setEtudiant(etudiant);
     }
            
     pvSemestres.add(pvSemestre);
     }
    
    
     return pvSemestres;
    
     }*/

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public void onComplete() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Progress Completed", "Progress Completed"));
    }

    public void onCancel() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Progress Cancelled", "Progress Cancelled"));
    }

    public boolean isBarDisable() {
        return barDisable;
    }

    public void setBarDisable(boolean barDisable) {
        this.barDisable = barDisable;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public ISInscription getInsc() {
        return insc;
    }

    public void setInsc(ISInscription insc) {
        this.insc = insc;
    }

    public Inscription getInscription() {
        return inscription;
    }

    public void setInscription(Inscription inscription) {
        this.inscription = inscription;
    }

    public String getTypeReleve() {
        return typeReleve;
    }

    public void setTypeReleve(String typeReleve) {
        this.typeReleve = typeReleve;
    }

    public List<Inscription> getListHistIns() {
        try {
            listHistIns = insc.findAll();
        } catch (ServiceException ex) {
            Logger.getLogger(NoteBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listHistIns;
    }

    public void setListHistIns(List<Inscription> listHistIns) {
        this.listHistIns = listHistIns;
    }

    public boolean isStatusPannoDeliberation() {
        return statusPannoDeliberation;
    }

    public void setStatusPannoDeliberation(boolean statusPannoDeliberation) {
        this.statusPannoDeliberation = statusPannoDeliberation;
    }

    public Long getDelibereId() {
        return delibereId;
    }

    public void setDelibereId(Long delibereId) {
        this.delibereId = delibereId;
    }

    public Float getNoteDeliberationNorm() throws ServiceException {
        try {
            noteDeliberationNorm = mat.findById(matiereId).getNoteDelS();
            return noteDeliberationNorm;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public void setNoteDeliberationNorm(Float noteDeliberationNorm) {
        this.noteDeliberationNorm = noteDeliberationNorm;
    }

    public Float getNoteDeliberationRat() throws ServiceException {
        try {
            noteDeliberationRat = mat.findById(matiereId).getNoteDelR();
            return noteDeliberationRat;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public void setNoteDeliberationRat(Float noteDeliberationRat) {
        this.noteDeliberationRat = noteDeliberationRat;
    }

    public boolean isDelibereDisable() {
        return delibereDisable;
    }

    public void setDelibereDisable(boolean delibereDisable) {
        this.delibereDisable = delibereDisable;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public PvMatiere getTarget() {
        return target;
    }

    public void setTarget(PvMatiere target) {
        this.target = target;
    }

    public String getSessionDel() {
        return sessionDel;
    }

    public void setSessionDel(String sessionDel) {
        this.sessionDel = sessionDel;
    }

    public List<Note> getItems() {
        return items;
    }

    public void setItems(List<Note> items) {
        this.items = items;
    }

    public boolean isAnneeDisable() {
        return anneeDisable;
    }

    public void setAnneeDisable(boolean anneeDisable) {
        this.anneeDisable = anneeDisable;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public PvMatiere[] getSelectedPvs() {
        return selectedPvs;
    }

    public void setSelectedPvs(PvMatiere[] selectedPvs) {
        this.selectedPvs = selectedPvs;
    }

    public PvMatiereModel getPvMatiereModel() throws ServiceException {
        pvMatiereModel = new PvMatiereModel(getListPvMatiere());
        return pvMatiereModel;
    }

    public void setPvMatiereModel(PvMatiereModel pvMatiereModel) {
        this.pvMatiereModel = pvMatiereModel;
    }

    public void check(SelectEvent event) {
    }

    public void doDelete(ActionEvent actionEven) throws ServiceException {

        int semestre = not.findById(selectedNote.getNoteId()).getMatiere().getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = spe.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = spe.findByNiveau(not.findById(selectedNote.getNoteId()).getMatiere().getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {

                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }

        Note note = not.findById(selectedNote.getNoteId());
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de la note  : " + note);
        not.delete(note);
        JsfUtil.addSuccessMessage("la note de " + selectedNote.getNomEtudiant() + " a été supprimé");
    }

    public void doFermer(ActionEvent actionEven) throws ServiceException {

        int semestre = not.findById(selectedNote.getNoteId()).getMatiere().getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = spe.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = spe.findByNiveau(not.findById(selectedNote.getNoteId()).getMatiere().getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {

                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }

        Note note = not.findById(selectedNote.getNoteId());
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à 10 de la moyenne de la note  : " + note);
        not.deliberationSpeciale(note);
        JsfUtil.addSuccessMessage("la moyenne de " + selectedNote.getNomEtudiant() + " a été mise a 10");
    }

    public void doClear(ActionEvent actionEven) throws ServiceException {
        Matiere matiere = mat.findById(matiereId);
        matiere.setDate(null);
        mat.update(matiere);
        List<Note> notes = not.filters(matiereId, anneeAcademiqueid);
        Specialite spec = spe.findById(specialiteId);

        int semestre = notes.get(0).getMatiere().getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();

            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = spe.findByNiveau(spec.getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {
                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }


        if (typeSup.equals(NOTE_CC)) {
            for (Note note : notes) {
                Note n = (Note) note.clone();
                n.setId(null);
                n.setTpe(null);
                n.setTd(null);
                n.setCc(null);
                n.setMoy(null);
                n.setCote(null);
                n.setGrade(null);
                n.setDecision(null);
                not.delete(note);
                not.create(n);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des   notes de CC  de la matiere   : " + matiere);
            JsfUtil.addSuccessMessage("toutes les notes de cc sont suprimmes");
        } else if (typeSup.equals(ANONYMAT_EXAMEN)) {
            for (Note note : notes) {
                Note n = (Note) note.clone();
                n.setId(null);
                n.setAnonymatEx(null);
                n.setExamen(null);
                n.setMoy(null);
                n.setCote(null);
                n.setGrade(null);
                n.setDecision(null);
                not.delete(note);
                not.create(n);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des   anonymats d'examen de la matiere   : " + matiere);
            JsfUtil.addSuccessMessage("toutes les anonymats d'examen sont suprimmes");

        } else if (typeSup.equals(NOTE_EXAMEN)) {
            for (Note note : notes) {
                Note n = (Note) note.clone();
                n.setId(null);
                n.setExamen(null);
                n.setMoy(null);
                n.setCote(null);
                n.setGrade(null);
                n.setDecision(null);
                not.delete(note);
                not.create(n);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des   notes de d'examen de la matiere   : " + matiere);
            JsfUtil.addSuccessMessage("toutes les notes de d'examnen sont suprimmes");

        } else if (typeSup.equals(ANONYMAT_RATRAPPAGE)) {
            for (Note note : notes) {
                Note n = (Note) note.clone();
                n.setId(null);
                n.setAnonymatRat(null);
                n.setRattrapage(null);
                n.setMoy(null);
                n.setCote(null);
                n.setGrade(null);
                n.setDecision(null);
                n = not.calcul(n);
                not.delete(note);
                not.create(n);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des  anonymat de rattrapge de la matiere   : " + matiere);
            JsfUtil.addSuccessMessage("toutes les anonymats de ratrapage sont suprimmes");


        } else if (typeSup.equals(NOTE_RATRAPPAGE)) {
            for (Note note : notes) {
                Note n = (Note) note.clone();
                n.setId(null);
                n.setRattrapage(null);
                n.setMoy(null);
                n.setCote(null);
                n.setGrade(null);
                n.setDecision(null);
                n.setDecision(null);
                n = not.calcul(n);
                not.delete(note);
                not.create(n);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des   notes de rattrapage de la matiere   : " + matiere);
            JsfUtil.addSuccessMessage("toutes les notes de ratrapage sont suprimmes");

        } else if (typeSup.equals(ALL)) {
            for (Note note : notes) {
                not.delete(note);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de toutes les notes de la matiere   : " + matiere);
            JsfUtil.addSuccessMessage("toutes les notes de cette matiere sont suprimmes");

        }
    }

    public void doUpdate(ActionEvent actionEven) throws ServiceException {
        Note note = not.findById(selectedNote.getNoteId());
        Note init = not.findById(selectedNote.getNoteId());
        int semestre = note.getMatiere().getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = spe.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);

            } else {
                specialites = spe.findByNiveau(not.findById(selectedNote.getNoteId()).getMatiere().getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {

                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }
        try {
            if (selectedNote.getCc() > 20f) {
                JsfUtil.addErrorMessage("le cc ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selectedNote.getTpe() > 20f) {
                JsfUtil.addErrorMessage("le tpe ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selectedNote.getTd() > 20f) {
                JsfUtil.addErrorMessage("le td ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selectedNote.getEe() > 20f) {
                JsfUtil.addErrorMessage("l'examen ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selectedNote.getRat() > 20f) {
                JsfUtil.addErrorMessage("le ratrapage ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        note.setTpe(selectedNote.getTpe());
        note.setTd(selectedNote.getTd());
        note.setCc(selectedNote.getCc());
        note.setAnonymatEx(selectedNote.getAnoExam());
        note.setAnonymatRat(selectedNote.getAnoRat());
        note.setExamen(selectedNote.getEe());
        note.setRattrapage(selectedNote.getRat());
        if (typeOp == 2) { //pour une requette
            if ((!typeImpression.equals("PE")) && (!typeImpression.equals("PR"))) {
                JsfUtil.addErrorMessage("le type d'impression doit etre publier les examen ou ratrappage!");
                return;
            }
            note.setRequette(true);
            note.setDaterequette(new Date());
        } else {
            note.setRequette(false);
        }
        note.setMoy(null);
        note.setGrade(null);
        note.setDecision(null);
        note.setCote(null);
        note = not.calcul(note);
        Note ntmp = (Note) note.clone();
        ntmp.setIsSmsSend(false);
        ntmp.setId(null);
        not.delete(note);
        note = not.create(ntmp);
        //on voit si on a ajoute des notes a l'examen
        Matiere mat1 = mat.findById(matiereId);
        if (mat1.getNoteBonusS() != null) {
            note = not.findById(note.getId());
            note = not.ajout(note, mat1.getNoteBonusS(), 1, new DelStat());
        }
        if (mat1.getNoteBonusR() != null) {
            note = not.findById(note.getId());
            note = not.ajout(note, mat1.getNoteBonusR(), 2, new DelStat());
        }

        //on voit si on a delibere our qu'il en profite
        if (mat1.getNoteDelS() != null) {
            note = not.findById(note.getId());
            not.calculDeliberation(note, mat1.getNoteDelS(), 1, new DelStat());
        }
        if (mat1.getNoteDelR() != null) {
            note = not.findById(note.getId());
            not.calculDeliberation(note, mat1.getNoteDelR(), 2, new DelStat());
        }

        if (typeOp == 2) {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour des  notes marquées comme requete avant: " + init + " apres : " + note);
        } else {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour des  notes marquées comme modification simple  avant: " + init + " apres : " + note);
        }
        JsfUtil.addSuccessMessage("les notes de " + note.getEtudiant().getNom() + " ont ete mis a jour");
    }

    public void doCreate(ActionEvent actionEven) throws ServiceException {
        niveauId = level;
        try {
            if (selected.getCc() > 20f) {
                JsfUtil.addErrorMessage("le cc ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selected.getTpe() > 20f) {
                JsfUtil.addErrorMessage("le tpe ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selected.getTd() > 20f) {
                JsfUtil.addErrorMessage("le td ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selected.getExamen() > 20f) {
                JsfUtil.addErrorMessage("l'examen ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        try {
            if (selected.getRattrapage() > 20f) {
                JsfUtil.addErrorMessage("le ratrapage ne doit pas depasser la note de 20!");
                return;
            }
        } catch (NullPointerException ee) {
        }
        Specialite specialite = spe.findById(specialiteId);
        Etudiant etudiant;
        Matiere matiere = mat.findById(matiereId);
        if (matiere.getDate() != null) {
            matiere.setDate(new Date());
            mat.update(matiere);
        }
        int semestre = matiere.getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = spe.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = spe.findByNiveau(matiere.getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {

                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }
        try {
            etudiant = etu.findByMatricule(matricule);
        } catch (NoResultException ee) {
            JsfUtil.addErrorMessage("matricule " + matricule + " n'est pas reconnu ");
            return;
        }


        boolean ancien = false;
        try {
            List<SpecialiteEtudiant> spes = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveauId, anneeAcademiqueid);
            if (spes.size() == 1) {
                SpecialiteEtudiant sp = spes.get(0);
                if (!specialite.getNom().equals("/")) {
                    if (sp.getSpecialite().getNom().equals("/")) {
                        sp.setSpecialite(specialite);
                        iSSpecialiteEtudiant.update(sp);
                    }
                }
            }
        } catch (NoResultException ee) {
            ancien = true;
            int level = specialite.getNiveau().getLevel();
            switch (level) {
                case 1: //on cherche aux niveaux 2 et 3
                    niveau = niv.nextNiveau(niveauId);
                    try {
                        List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveau.getId(), anneeAcademiqueid);
                    } catch (NoResultException eee) {
                        niveau = niv.nextNiveau(niveau.getId());
                        try {
                            List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveau.getId(), anneeAcademiqueid);
                        } catch (NoResultException eeee) {
                            JsfUtil.addErrorMessage("Le matricule " + matricule + " n'est pas autorise a composer cette matiere");
                            return;
                        }
                    }
                    break;
                case 2: //on cherche au niveau 3
                    niveau = niv.nextNiveau(niveauId);
                    try {
                        List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveau.getId(), anneeAcademiqueid);
                    } catch (NoResultException eeee) {
                        JsfUtil.addErrorMessage("Le matricule " + matricule + " n'est pas autorise a composer cette matiere");
                        return;
                    }
                    break;
                default:
                    JsfUtil.addErrorMessage("Le matricule " + matricule + " n'est pas autorise a composer cette matiere");
                    return;

            }

        }

        //si c'est un ancien on verifie s'il a valide
        if (ancien) {
            try {
                Module curentModule = mod.findLastUE(etudiant.getId(), matiere.getModule().getId(), 0);
                Note selected = not.findByUEEtudiant(etudiant.getId(), curentModule.getId());
                try {
                    int lev = selected.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                    Float moy = selected.getMoy();
                    if (((moy.compareTo(10f) >= 0) && (lev <= 3)) || ((moy.compareTo(12f) >= 0) && (lev > 3))) {
                        JsfUtil.addErrorMessage("Le matricule " + matricule + " a deja eu a valider cette matiere");
                        return;
                    }
                } catch (NullPointerException ex1) {
                }
            } catch (NoResultException ex2) { //on peut laisser les nouveaux preinscrits au niveau inférieur
                           /* message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                 message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                 FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                 fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                 FacesContext fctx = FacesContext.getCurrentInstance();
                 fctx.addMessage(null, fm);
                 return;*/
            }
        }



        try {
            Note note = not.findNoteByEtudiantMatiere(etudiant.getId(), matiereId);
            JsfUtil.addErrorMessage("l'etudiant " + etudiant.getNom() + " a déja une note");
        } catch (NoResultException ex) {
            selected.setMatiere(matiere);
            selected.setEtudiant(etudiant);
            if (typeOp == 2) { //pour une requette
                if ((!typeImpression.equals("PE")) && (!typeImpression.equals("PR"))) {
                    JsfUtil.addErrorMessage("le type d'impression doit etre publier les examen ou ratrappage!");
                    return;
                }
                selected.setRequette(true);
                selected.setDaterequette(new Date());
            } else {
                selected.setRequette(false);
            }

            selected = not.calcul(selected);
            selected = not.create(selected);


            //on voit si on a delibere pour qu'il en profite
            Matiere mat1 = mat.findById(matiereId);
            if (mat1.getNoteDelS() != null) {
                not.calculDeliberation(selected, mat1.getNoteDelS(), 1, new DelStat());
            }
            if (mat1.getNoteDelR() != null) {
                selected = not.findById(selected.getId());
                not.calculDeliberation(selected, mat1.getNoteDelR(), 2, new DelStat());
            }

            if (typeOp == 2) { //pour une requette
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation comme requete de la note  " + selected);
            } else {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation comme modification simple de la note  " + selected);
            }

            selected = new Note();
            matricule = null;


            JsfUtil.addSuccessMessage("la note de  " + etudiant.getNom() + " a été crée ");
        }
    }

    public void doSetFormule(ActionEvent actionEven) throws ServiceException {
        Matiere matiere = mat.findById(matiereId);
        String formula = tdf + "-" + tpef + "-" + ccf + "-" + exf;
        if (!formula.isEmpty()) {
            matiere.setFormule(formula);
            mat.update(matiere);
        }
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "changement de formule par defaut pour la matiere  : " + matiere);
        JsfUtil.addSuccessMessage("la note de  " + etudiant.getNom() + " a été crée ");

    }

    public void doTransfert(ActionEvent actionEven) throws ServiceException {
        Note note = not.findById(selectedNote.getNoteId());

        int semestre = note.getMatiere().getModule().getSemestre().getLevel();
        try {
            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = spe.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = spe.findByNiveau(note.getMatiere().getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {
                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(spe.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                    spa.setSpecialite(spe.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }

            }

        } catch (IllegalArgumentException ex) {
        }

        Note noteNew = (Note) note.clone();
        not.delete(note);
        try {
            Note n = not.findNoteByEtudiantMatiere(noteNew.getEtudiant().getId(), matiereTransfertId);
            not.delete(n);

            Matiere matiere = mat.findById(matiereTransfertId);
            noteNew.setMatiere(matiere);
            not.update(noteNew);
            JsfUtil.addSuccessMessage("la note on ete transférée vers la " + noteNew.getMatiere().getModule().getTargetCode());

        } catch (NoResultException ex) {
            Matiere matiere = mat.findById(matiereTransfertId);
            noteNew.setMatiere(matiere);
            not.update(noteNew);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + " transfert vers la " + noteNew.getMatiere().getModule().getTargetCode());
            JsfUtil.addSuccessMessage("la note on ete transférée vers la " + noteNew.getMatiere().getModule().getTargetCode());

        }
        Matiere matiere = mat.findById(matiereTransfertId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + " transfert de la note " + note + " vers la matiere " + matiere);

    }

    public String getTypeImpression() {
        return typeImpression;
    }

    public void setTypeImpression(String typeImpression) {
        this.typeImpression = typeImpression;
    }

    public String getTypeSup() {
        return typeSup;
    }

    public void setTypeSup(String typeSup) {
        this.typeSup = typeSup;
    }

    public PvMatiere getSelectedNote() {
        return selectedNote;
    }

    public void setSelectedNote(PvMatiere selectedNote) {
        this.selectedNote = selectedNote;
    }

    public Float getTpe() {
        return tpe;
    }

    public void setTpe(Float tpe) {
        this.tpe = tpe;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Long getMatiereTransfertId() {
        return matiereTransfertId;
    }

    public void setMatiereTransfertId(Long matiereTransfertId) {
        this.matiereTransfertId = matiereTransfertId;
    }

    public String fomatRomain(int level) {
        if (level == 1) {
            return "Niveau: 1  Cycle: Licence";
        }
        if (level == 2) {
            return "Niveau: 2  Cycle: Licence";
        }
        if (level == 3) {
            return "Niveau: 3  Cycle: Licence";
        }
        if (level == 4) {
            return "Niveau: 1  Cycle: Master";
        }
        if (level == 5) {
            return "Niveau: 2  Cycle: Master";
        }
        return null;
    }

    public int getTdf() {
        return tdf;
    }

    public void setTdf(int tdf) {
        this.tdf = tdf;
    }

    public int getTpef() {
        return tpef;
    }

    public void setTpef(int tpef) {
        this.tpef = tpef;
    }

    public int getCcf() {
        return ccf;
    }

    public void setCcf(int ccf) {
        this.ccf = ccf;
    }

    public int getExf() {
        return exf;
    }

    public void setExf(int exf) {
        this.exf = exf;
    }

    public ISSpecialiteAnnee getSpecialiteAnnee() {
        return specialiteAnnee;
    }

    public void setSpecialiteAnnee(ISSpecialiteAnnee specialiteAnnee) {
        this.specialiteAnnee = specialiteAnnee;
    }

    public ISUtilisateur getUti() {
        return uti;
    }

    public void setUti(ISUtilisateur uti) {
        this.uti = uti;
    }

    public int getTypeOp() {
        return typeOp;
    }

    public void setTypeOp(int typeOp) {
        this.typeOp = typeOp;
    }

    public ISSpecialiteEtudiant getiSSpecialiteEtudiant() {
        return iSSpecialiteEtudiant;
    }

    public void setiSSpecialiteEtudiant(ISSpecialiteEtudiant iSSpecialiteEtudiant) {
        this.iSSpecialiteEtudiant = iSSpecialiteEtudiant;
    }

    public Date getDateReq() {
        return dateReq;
    }

    public void setDateReq(Date dateReq) {
        this.dateReq = dateReq;
    }

    public Map<String, Long> getNiveaux() throws ServiceException {
        niveaux = NivUtil.getLevel(getNiveaus());
        return niveaux;
    }
}
