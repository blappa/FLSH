/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.UserUtil;
import com.douwe.generic.dao.DataAccessException;
import com.sun.faces.component.visit.FullVisitContext;
import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.NivUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.projection.Pv;
import com.univ.maroua.flsh.projection.Rapport;
import com.univ.maroua.flsh.projection.Template;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteAnnee;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sound.midi.SysexMessage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.dialog.Dialog;
import org.primefaces.component.growl.Growl;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author william mekomou
 * <williammekomou@yahoo.com>
 */
@ManagedBean(name = "importationNoteBean")
@SessionScoped
@ViewScoped
public class ImportationNoteBean implements Serializable {

    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique iSAnneeAcademique;
    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement iSDepartement;
    @ManagedProperty(value = "#{ISNiveau}")
    private ISNiveau iSNiveau;
    @ManagedProperty(value = "#{ISSpecialte}")
    private ISSpecialite iSSpecialite;
    @ManagedProperty(value = "#{ISEtudiant}")
    private ISEtudiant iSEtudiant;
    @ManagedProperty(value = "#{ISMatiere}")
    private ISMatiere iSMatiere;
    @ManagedProperty(value = "#{ISNote}")
    private ISNote iSNote;
    @ManagedProperty(value = "#{ISSection}")
    private ISSection iSSection;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule iSModule;
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant iSSpecialiteEtudiant;
    @ManagedProperty(value = "#{ISSPecialiteAnnee}")
    private ISSpecialiteAnnee specialiteAnnee;
    private String operation;
    private String comportement;
    public static String ALL = "all";
    public static String NOTE_CC_EXAM = "ccExam";
    public static String NOTE_CC = "cc";
    public static String ANONYMAT_EXAMEN = "exama";
    public static String NOTE_EXAMEN = "examn";
    public static String ANONYMAT_RATRAPPAGE = "rata";
    public static String NOTE_RATRAPPAGE = "ratn";
    private List<Departement> departements;
    private List<Section> sections;
    private List<Niveau> niveaus;
    private List<Specialite> specialites;
    private List<Matiere> matieres;
    private List<AnneeAcademique> anneeAcademiques;
    private List<Module> modules;
    private static List<Rapport> rapports;
    //pour desactiver les combo boxes au lancement
    private boolean uploadVisible = false;
    private boolean sectionDisable = true;
    private boolean moduleDisable = true;
    private boolean niveauDisable = true;
    private boolean specialiteDisable = true;
    private boolean matiereDisable = true;
    //les valeurs des elements contenus dans la combobox
    private Long departementId;
    private Long sectionId;
    private Long niveauId;
    private Long specialiteId;
    private Long matiereId;
    private Long anneeId;
    private Long moduleId;
    private String matricule;
    private Note targetNote;
    private String noteTPE = null;
    private String noteTD = null;
    private String noteCC = null;
    private String noteAnoE = null;
    private String noteEE = null;
    private String noteAnoR = null;
    private String noteRA = null;
    private Rapport selectedrRapport;
    private static String something;
    private DataTable table;
    private Growl growl = new Growl();
    private String class1;
    private StringBuilder message;
    private boolean cont = false;
    private List<Section> listSection = new ArrayList<Section>();
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private Niveau niv = null;
    private Map<String, Long> niveaux;

    public ImportationNoteBean() {
        table = new DataTable();
        targetNote = new Note();
        rapports = new ArrayList<Rapport>();
    }

    public DataTable getTable() {
        table.setEmptyMessage(something);
        return table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public void xlsNoteMatiere(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
        String moduleNames[] = new String[5];
        moduleNames[0] = "tpe td et cc";
        moduleNames[1] = "anonymat d'examens";
        moduleNames[2] = "notes d'examen";
        moduleNames[3] = "anonymat de rattrapage";
        moduleNames[4] = "notes de rattrapage";
        JasperPrint jasperPrint;


        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";




        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        tp.setCell1("Matricule");
        tp.setCell2("TPE");
        tp.setCell3("TD");
        tp.setCell4("CC");
        tps.add(tp);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "notematiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);


        tps = new LinkedList<>();
        tp = new Template();
        tp.setCell1("Matricule");
        tp.setCell2("Anonymat");
        tps.add(tp);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "notematiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);

        tps = new LinkedList<>();
        tp = new Template();

        tp.setCell1("Anonymat");
        tp.setCell2("Note");
        tps.add(tp);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "notematiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);

        tps = new LinkedList<>();
        tp = new Template();
        tp.setCell1("Matricule");
        tp.setCell2("Anonymat");
        tps.add(tp);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "notematiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);

        tps = new LinkedList<>();
        tp = new Template();
        tp.setCell1("Anonymat");
        tp.setCell2("Note");
        tps.add(tp);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "notematiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);


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

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + "notematiere.xls");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(byteArrayOutputStream.toByteArray());
        JRXlsExporter xlsxExporter = new JRXlsExporter();
        xlsxExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, moduleNames);
        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        xlsxExporter.exportReport();

        servletOutputStream.flush();
        servletOutputStream.close();

    }

    public void xlsBySpecialite(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {

        try {
            ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
            JasperPrint jasperPrint;
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("") + File.separator + "print";
            List<Template> tps = new LinkedList<>();
            Template tp = new Template();
            List<Module> mods = iSModule.findByAnneeAcSpecialite(anneeId, specialiteId);
            int i = 0, j = 0, k = 0;
            String moduleNames[] = new String[40];
            for (Module m : mods) {
                i++;
                String tmp = i + "";
                if (tmp.length() < 2) {
                    tmp = "0" + tmp;
                }
                List<Matiere> matieres = iSMatiere.findByUE(m.getId());
                if (matieres.size() == 1) {
                    moduleNames[k++] = tmp; //nom de la feuille
                    tps = new LinkedList<>();
                    tp = new Template();

                    String comment = tp.getComment();
                    String code = (matieres.get(0).getCode() != null) ? matieres.get(0).getCode() : matieres.get(0).getModule().getTargetCode();
                    comment += "\n" + code + ": " + matieres.get(0).getIntitule();
                    tp.setComment(comment);
                    tps.add(tp);
                    jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "noteparspecialite.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
                    list.add(jasperPrint);
                } else if (matieres.size() > 1) {
                    for (Matiere matiere : matieres) {
                        j++;
                        moduleNames[k++] = tmp + j; //nom de la feuille
                        tps = new LinkedList<>();
                        tp = new Template();
                        String comment = tp.getComment();
                        String code = (matiere.getCode() != null) ? matiere.getCode() : matiere.getModule().getTargetCode();
                        comment += "\n" + code + ": " + matiere.getIntitule();
                        tp.setComment(comment);
                        tps.add(tp);
                        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "noteparspecialite.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
                        list.add(jasperPrint);
                    }
                    j = 0;
                }
            }
            jasperPrint = list.get(0);
            int l = 0;
            for (JasperPrint jas : list) {
                if (l == 0) {
                    l++;
                } else {
                    try {
                        List<JRPrintPage> pages = new ArrayList<JRPrintPage>(jas.getPages());
                        for (int count = 0; count < pages.size(); count++) {
                            jasperPrint.addPage(l, (JRPrintPage) pages.get(count));
                        }
                        l++;
                    } catch (java.lang.IndexOutOfBoundsException ex) {
                    }
                }
            }
            Specialite ss = iSSpecialite.findById(specialiteId);
            String reportName = "noteparspecialite" + iSSection.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + iSNiveau.findById(niveauId).getLevel();
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition", "attachment; filename=" + reportName + ".xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(byteArrayOutputStream.toByteArray());
            JRXlsExporter xlsxExporter = new JRXlsExporter();
            xlsxExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, moduleNames);
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            xlsxExporter.exportReport();

            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (java.lang.IndexOutOfBoundsException ex) {
            JsfUtil.addErrorMessage("selectionnez les champs");
        }

    }

    public void xlsBySpecialite1(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        Specialite ss = iSSpecialite.findById(specialiteId);
        String reportName = "noteparspecialite" + iSSection.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + iSNiveau.findById(niveauId).getLevel();


        if (operation.equals(NOTE_CC)) {
            reportName += "Cc";
        } else if (operation.equals(ANONYMAT_EXAMEN)) {
            reportName += "Ae";
        } else if (operation.equals(NOTE_EXAMEN)) {
            reportName += "Ne";
        } else if (operation.equals(ANONYMAT_RATRAPPAGE)) {
            reportName += "Ar";
        } else if (operation.equals(NOTE_RATRAPPAGE)) {
            reportName += "Nr";
        }


        try {
            ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
            JasperPrint jasperPrint;
            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String path = servletContext.getRealPath("") + File.separator + "print";
            List<Template> tps = new LinkedList<>();
            Template tp = new Template();
            List<Module> mods = iSModule.findByAnneeAcSpecialite(anneeId, specialiteId);
            int i = 0, j = 0, k = 0;
            String moduleNames[] = new String[40];
            for (Module m : mods) {
                i++;
                String tmp = i + "";
                if (tmp.length() < 2) {
                    tmp = "0" + tmp;
                }
                List<Matiere> matieres = iSMatiere.findByUE(m.getId());
                if (matieres.size() == 1) {
                    moduleNames[k++] = tmp; //nom de la feuille
                    tps = new LinkedList<>();
                    tp = new Template();

                    String comment = tp.getComment();
                    String code = (matieres.get(0).getCode() != null) ? matieres.get(0).getCode() : matieres.get(0).getModule().getTargetCode();
                    comment += "\n" + code + ": " + matieres.get(0).getIntitule();
                    tp.setComment(comment);
                    if (operation.equals(NOTE_CC)) {
                        tp.setCell1("Matricule");
                        tp.setCell2("TPE");
                        tp.setCell3("TD");
                        tp.setCell4("CC");
                    } else if (operation.equals(ANONYMAT_EXAMEN)) {
                        tp.setCell1("Matricule");
                        tp.setCell2("Anonymat");
                    } else if (operation.equals(NOTE_EXAMEN)) {
                        tp.setCell1("Anonymat");
                        tp.setCell2("Note");
                    } else if (operation.equals(ANONYMAT_RATRAPPAGE)) {
                        tp.setCell1("Matricule");
                        tp.setCell2("Anonymat");
                    } else if (operation.equals(NOTE_RATRAPPAGE)) {
                        tp.setCell1("Anonymat");
                        tp.setCell2("Note");
                    }
                    tps.add(tp);
                    jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "notematiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
                    list.add(jasperPrint);
                } else if (matieres.size() > 1) {
                    for (Matiere matiere : matieres) {
                        j++;
                        moduleNames[k++] = tmp + j; //nom de la feuille
                        tps = new LinkedList<>();
                        tp = new Template();
                        String comment = tp.getComment();
                        String code = (matiere.getCode() != null) ? matiere.getCode() : matiere.getModule().getTargetCode();
                        comment += "\n" + code + ": " + matiere.getIntitule();
                        tp.setComment(comment);
                        if (operation.equals(NOTE_CC)) {
                            tp.setCell1("Matricule");
                            tp.setCell2("TPE");
                            tp.setCell3("TD");
                            tp.setCell4("CC");
                        } else if (operation.equals(ANONYMAT_EXAMEN)) {
                            tp.setCell1("Matricule");
                            tp.setCell2("Anonymat");
                        } else if (operation.equals(NOTE_EXAMEN)) {
                            tp.setCell1("Anonymat");
                            tp.setCell2("Note");
                        } else if (operation.equals(ANONYMAT_RATRAPPAGE)) {
                            tp.setCell1("Matricule");
                            tp.setCell2("Anonymat");
                        } else if (operation.equals(NOTE_RATRAPPAGE)) {
                            tp.setCell1("Anonymat");
                            tp.setCell2("Note");
                        }
                        tps.add(tp);
                        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "notematiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
                        list.add(jasperPrint);
                    }
                    j = 0;
                }
            }
            jasperPrint = list.get(0);
            int l = 0;
            for (JasperPrint jas : list) {
                if (l == 0) {
                    l++;
                } else {
                    try {
                        List<JRPrintPage> pages = new ArrayList<JRPrintPage>(jas.getPages());
                        for (int count = 0; count < pages.size(); count++) {
                            jasperPrint.addPage(l, (JRPrintPage) pages.get(count));
                        }
                        l++;
                    } catch (java.lang.IndexOutOfBoundsException ex) {
                    }
                }
            }

            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-disposition", "attachment; filename=" + reportName + ".xls");
            ServletOutputStream servletOutputStream = response.getOutputStream();
            servletOutputStream.write(byteArrayOutputStream.toByteArray());
            JRXlsExporter xlsxExporter = new JRXlsExporter();
            xlsxExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, moduleNames);
            xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            xlsxExporter.exportReport();

            servletOutputStream.flush();
            servletOutputStream.close();
        } catch (java.lang.IndexOutOfBoundsException ex) {
            JsfUtil.addErrorMessage("selectionnez les champs");
        }

    }

    public void handleFileUploadBySpecialite(FileUploadEvent event) throws ServiceException {
        message = new StringBuilder("");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();
        if (event.getFile() == null) {
            JsfUtil.addErrorMessage("le fichier envoye est vide");
            // facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le fichier est vide", ""));
        }
        InputStream file;
        HSSFWorkbook workbook = null;
        try {
            file = event.getFile().getInputstream();
            workbook = new HSSFWorkbook(file);
        } catch (IOException e) {
            JsfUtil.addErrorMessage("le fichier est endomage ou illisible");
        }

        if (operation == null) {
            JsfUtil.addErrorMessage("choisir le type d'operations");
            return;
        }

        if (comportement == null) {
            JsfUtil.addErrorMessage("choisir le type de comportement");
            return;
        }


        List<Module> mods = iSModule.findByAnneeAcSpecialite(anneeId, specialiteId);




        int semestre = mods.get(0).getSemestre().getLevel();
        try {

            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = iSSpecialite.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = iSSpecialite.findByNiveau(mods.get(0).getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {
                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeId);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                    spa.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }




        Specialite specialite = iSSpecialite.findById(specialiteId);
        int n = workbook.getNumberOfSheets();

        for (int m = 0; m < n; m++) {
            HSSFSheet sheet = workbook.getSheetAt(m);
            String sheetName = sheet.getSheetName();
            int i = 0;
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (row.getRowNum() == 0) {
                    String matricule = "";
                    String tpe = "";
                    String td = "";
                    String cc = "";
                    String ex = "";
                    String rat = "";
                    String moy = "";
                    String anoExam = "";
                    String anoRat = "";
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        switch (cell.getColumnIndex()) {
                            case 0:
                                matricule = cell.getStringCellValue();
                                break;
                            case 1:
                                tpe = cell.getStringCellValue();
                                break;
                            case 2:
                                td = cell.getStringCellValue();
                                break;
                            case 3:
                                cc = cell.getStringCellValue();
                                break;
                            case 4:
                                anoExam = cell.getStringCellValue();
                                break;
                            case 5:
                                ex = cell.getStringCellValue();
                                break;
                            case 6:
                                anoRat = cell.getStringCellValue();
                                break;
                            case 7:
                                rat = cell.getStringCellValue();
                                break;
                            case 8:
                                moy = cell.getStringCellValue();
                                break;
                        }

                    }
                    if ((!tpe.equals("TPE")) || (!td.equals("TD")) || (!cc.equals("CC")) || (!matricule.equals("Matricule"))
                            || (!ex.equals("EE")) || (!rat.equals("RA")) || (!moy.equals("Moy"))
                            || (!anoExam.equals("Ano.EE")) || (!anoRat.equals("Ano.RA"))) {

                        message.append("<b>Feuille no ").append(m + 1).append("</b> <br/>");
                        message.append("la feuille est mal structurée: verifiez les entetes (erreur)").append("</b> <br/>");
                        FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fctx = FacesContext.getCurrentInstance();
                        fctx.addMessage(null, fm);

                        return;
                    }
                    continue;
                }

                //on cherche l'id de la matiere
                Matiere matiere = null;
                if (sheetName.length() < 3) {
                    int index = Integer.parseInt(sheetName) - 1;
                    List<Matiere> matieres = iSMatiere.findByUE(mods.get(index).getId());
                    matiere = matieres.get(0);
                } else {
                    int matiereIndex = Integer.parseInt(sheetName.charAt(2) + "") - 1;
                    int moduleIndex = Integer.parseInt(sheetName.substring(0, 2)) - 1;
                    List<Matiere> matieres = iSMatiere.findByUE(mods.get(moduleIndex).getId());
                    matiere = matieres.get(matiereIndex);
                }

                if (matiere.getDate() != null) {
                    matiere.setDate(new Date());
                    iSMatiere.update(matiere);
                }
                Float tpe = null;
                Float td = null;
                Float cc = null;
                String matricule = null;
                Float ex = null;
                Float rat = null;
                String anoEx = null;
                String anoRat = null;
                Float moy = null;
                Note note = null;
                Etudiant etudiant = null;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    switch (cell.getColumnIndex()) {
                        case 0:
                            if (!cell.getStringCellValue().isEmpty()) {
                                matricule = cell.getStringCellValue().trim();
                                //etape 1: on s'assure que le matricule est nn vide
                                if (matricule == null) {

                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le matricule est vide (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            }
                            break;
                        case 1:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    tpe = Float.parseFloat(cell.getStringCellValue());
                                } catch (NumberFormatException e) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne TPE n'est pas correct (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                                if (tpe > 20f) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne TPE ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }

                            }
                            break;
                        case 2:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    td = Float.parseFloat(cell.getStringCellValue());
                                } catch (NumberFormatException e) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne TD n'est pas correct (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                                if (td > 20f) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne TD ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }

                            }
                            break;
                        case 3:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    cc = Float.parseFloat(cell.getStringCellValue());
                                } catch (NumberFormatException e) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne CC n'est pas correct (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                                if (cc > 20f) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne CC ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }

                            }
                            break;


                        case 4:
                            if (!cell.getStringCellValue().isEmpty()) {
                                anoEx = cell.getStringCellValue();

                            }
                            break;

                        case 5:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    ex = Float.parseFloat(cell.getStringCellValue());
                                } catch (NumberFormatException e) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne EX n'est pas correct (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                                if (ex > 20f) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne Examen ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }

                            }
                            break;

                        case 6:
                            if (!cell.getStringCellValue().isEmpty()) {
                                anoRat = cell.getStringCellValue();

                            }
                            break;

                        case 7:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    rat = Float.parseFloat(cell.getStringCellValue());
                                } catch (NumberFormatException e) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne RAT n'est pas correct (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                                if (rat > 20f) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne Ratrappage ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            }
                            break;
                        case 8:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    moy = Float.parseFloat(cell.getStringCellValue());
                                } catch (NumberFormatException e) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne Moy n'est pas correct (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                                if (moy > 20f) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                    message.append("le nombre a la colonne Moyennne ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            }
                            break;
                    }
                }

                if (tpe != null) {
                    if (note == null) {
                        note = new Note();
                    }
                    note.setTpe(tpe);
                }
                if (td != null) {
                    if (note == null) {
                        note = new Note();
                    }
                    note.setTd(td);
                }
                if (cc != null) {
                    if (note == null) {
                        note = new Note();
                    }

                    note.setCc(cc);
                }

                if (anoEx != null) {
                    if (note == null) {
                        note = new Note();
                    }
                    note.setAnonymatEx(anoEx);
                }
                if (anoRat != null) {
                    if (note == null) {
                        note = new Note();
                    }
                    note.setAnonymatRat(anoRat);
                }
                if (ex != null) {
                    if (note == null) {
                        note = new Note();
                    }
                    note.setExamen(ex);
                }

                if (rat != null) {
                    if (note == null) {
                        note = new Note();
                    }

                    note.setRattrapage(rat);
                }
                if (moy != null) {
                    if (note == null) {
                        note = new Note();
                    }
                    note.setMoy(moy);
                }

                if (note != null && matricule != null) {
                    matricule = matricule.trim();
                    try {
                        etudiant = iSEtudiant.findByMatricule(matricule);
                    } catch (NoResultException e) {
                        message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                        message.append("Le matricule ").append(matricule).append(" n'est pas reconu par le systeme (erreur)").append("</b> <br/>");
                        FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                        FacesContext fctx = FacesContext.getCurrentInstance();
                        fctx.addMessage(null, fm);
                        return;
                    }
                    boolean ancien = false;
                    try {
                        List<SpecialiteEtudiant> spes = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveauId, anneeId);
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
                                Niveau niv = iSNiveau.nextNiveau(niveauId);
                                try {
                                    List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                                } catch (NoResultException eee) {
                                    niv = iSNiveau.nextNiveau(niv.getId());
                                    try {
                                        List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                                    } catch (NoResultException eeee) {
                                        message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                        message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                        FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                        FacesContext fctx = FacesContext.getCurrentInstance();
                                        fctx.addMessage(null, fm);
                                        return;
                                    }
                                }
                                break;
                            case 2: //on cherche au niveau 3
                                niv = iSNiveau.nextNiveau(niveauId);
                                try {
                                    List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                                } catch (NoResultException eeee) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                    message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                                break;
                            default:
                                message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;

                        }

                    }

                    //si c'est un ancien on verifie s'il a valide
                    if (ancien) {
                        try {
                            Module curentModule = iSModule.findLastUE(etudiant.getId(), matiere.getModule().getId(), 0);
                            Note selected = iSNote.findByUEEtudiant(etudiant.getId(), curentModule.getId());
                            try {
                                int lev = selected.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                                Float moy1 = selected.getMoy();
                                if (((moy1.compareTo(10f) >= 0) && (lev <= 3)) || ((moy1.compareTo(12f) >= 0) && (lev > 3))) {
                                    message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                    message.append("L'etudiant de matricule ").append(matricule).append(" a deja eu a valider cette matiere (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            } catch (NullPointerException ex1) {
                            }
                        } catch (NoResultException ex2) {
                            /* message.append("<b>Feuille no ").append(m + 1).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                             message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                             FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                             fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                             FacesContext fctx = FacesContext.getCurrentInstance();
                             fctx.addMessage(null, fm);
                             return;*/
                        }
                    }


                    try {
                        Note no = iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiere.getId());
                        if (comportement.equals("rec")) {
                            iSNote.delete(no);
                            note.setEtudiant(etudiant);
                            note.setMatiere(matiere);
                            if (operation.equals("nocalcul")) {
                                note = iSNote.calculMoyenne(note); //pour les grades uniquement
                            } else {
                                note = iSNote.calcul(note); //oncalcule la moyenne et les grades
                            }
                            iSNote.create(note);
                            i++;
                        }

                    } catch (NoResultException e) {
                        note.setEtudiant(etudiant);
                        note.setMatiere(matiere);
                        if (operation.equals("nocalcul")) {
                            note = iSNote.calculMoyenne(note); //pour les grades uniquement
                        } else {
                            note = iSNote.calcul(note); //oncalcule la moyenne et les grades
                        }
                        iSNote.create(note);
                        i++;
                    }

                }
            }

            message.append("<b>Feuille no ").append(m + 1).append("</b> <br/>");
            message.append("En tout ").append(i).append(" notes importees (bon)").append("</b> <br/>");
            if (m == (n - 1)) {
                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                fm.setSeverity(FacesMessage.SEVERITY_INFO);
                FacesContext fctx = FacesContext.getCurrentInstance();
                fctx.addMessage(null, fm);
            }
        }
        Specialite spp = iSSpecialite.findById(specialiteId);
        AnneeAcademique aa = iSAnneeAcademique.findById(anneeId);
        if (operation.equals("calcul")) {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des notes complètes avec calcul de la moyenne de la specialite " + spp + " pour l'année " + aa);
        } else {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des notes complètes sans calcul de la moyenne de la specialite " + spp + " pour l'année " + aa);
        }
    }

    public void handleFileUploadBySpecialite1(FileUploadEvent event) throws ServiceException {
        cont = true;
        message = new StringBuilder("");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        RequestContext context = RequestContext.getCurrentInstance();

        if (event.getFile() == null) {
            JsfUtil.addErrorMessage("le fichier envoye est vide");
            return;
        }
        InputStream file;
        HSSFWorkbook workbook = null;
        try {
            file = event.getFile().getInputstream();
            workbook = new HSSFWorkbook(file);
        } catch (IOException e) {
            JsfUtil.addErrorMessage("le fichier est endomage ou illisible");
        }

        if (operation == null) {
            JsfUtil.addErrorMessage("choisir le type d'operations");
            return;
        }

        if (comportement == null) {
            JsfUtil.addErrorMessage("choisir le type de comportement");
            return;
        }



        List<Module> mods = iSModule.findByAnneeAcSpecialite(anneeId, specialiteId);


        int semestre = mods.get(0).getSemestre().getLevel();
        try {

            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = iSSpecialite.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = iSSpecialite.findByNiveau(mods.get(0).getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {


                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeId);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                    spa.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }


        Specialite specialite = iSSpecialite.findById(specialiteId);
        int n = workbook.getNumberOfSheets();

        for (int m = 0; m < n; m++) {
            HSSFSheet sheet = workbook.getSheetAt(m);
            String sheetName = sheet.getSheetName();
            int i = 0;
            //on cherche l'id de la matiere
            Matiere matiere = null;
            if (sheetName.length() < 3) {
                int index = Integer.parseInt(sheetName) - 1;
                List<Matiere> matieres = iSMatiere.findByUE(mods.get(index).getId());
                matiere = matieres.get(0);
            } else {
                int matiereIndex = Integer.parseInt(sheetName.charAt(2) + "") - 1;
                int moduleIndex = Integer.parseInt(sheetName.substring(0, 2)) - 1;
                List<Matiere> matieres = iSMatiere.findByUE(mods.get(moduleIndex).getId());
                matiere = matieres.get(matiereIndex);
            }
            if (matiere.getDate() != null) {
                matiere.setDate(new Date());
                iSMatiere.update(matiere);
            }
            matiereId = matiere.getId();
            if (cont == true) {
                if (operation.equals(NOTE_CC)) {
                    ccOperation(sheet, m + 1);
                } else if (operation.equals(ANONYMAT_EXAMEN)) {
                    anonOperation(sheet, m + 1);
                } else if (operation.equals(NOTE_EXAMEN)) {
                    examnOperation(sheet, m + 1);
                } else if (operation.equals(ANONYMAT_RATRAPPAGE)) {
                    anonOperationRat(sheet, m + 1);
                } else if (operation.equals(NOTE_RATRAPPAGE)) {
                    ratnOperation(sheet, m + 1);
                }
            }
        }

        if (cont == true) {
            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);
        }
    }

    public void handleFileUploadAll(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (event.getFile() == null) {
            JsfUtil.addErrorMessage("le fichier envoye est vide");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le fichier est vide", ""));
        }
        InputStream file;
        HSSFWorkbook workbook = null;
        try {
            file = event.getFile().getInputstream();
            workbook = new HSSFWorkbook(file);
        } catch (IOException e) {
            JsfUtil.addErrorMessage("le fichier est endomage ou illisible");
        }

        int n = workbook.getNumberOfSheets();



        HSSFSheet sheet = workbook.getSheetAt(0);
        int i = 0;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                String tpe = "";
                String td = "";
                String cc = "";
                String ex = "";
                String rat = "";
                String moy = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                        case 1:
                            tpe = cell.getStringCellValue();
                            break;
                        case 2:
                            td = cell.getStringCellValue();
                            break;
                        case 3:
                            cc = cell.getStringCellValue();
                            break;
                        case 4:
                            ex = cell.getStringCellValue();
                            break;
                        case 5:
                            rat = cell.getStringCellValue();
                            break;
                        case 6:
                            moy = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!tpe.equals("TPE")) || (!td.equals("TD")) || (!cc.equals("CC")) || (!matricule.equals("Matricule"))
                        || (!ex.equals("EE")) || (!rat.equals("RA")) || (!moy.equals("Moy"))) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }

            Matiere matiere = iSMatiere.findById(matiereId);
            Float tpe = null;
            Float td = null;
            Float cc = null;
            String matricule = null;
            Float ex = null;
            Float rat = null;
            Float moy = null;
            Note note = null;
            Etudiant etudiant = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matricule = cell.getStringCellValue();
                        }
                        break;
                    case 1:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                tpe = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException e) {
                            }

                        }
                        break;
                    case 2:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                td = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException e) {
                            }

                        }
                        break;
                    case 3:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                cc = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException e) {
                            }

                        }
                        break;

                    case 4:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                ex = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException e) {
                            }

                        }
                        break;

                    case 5:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                rat = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException e) {
                            }

                        }
                        break;
                    case 6:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                moy = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException e) {
                            }

                        }
                        break;

                }


            }

            //etape 1: on s'assure que le matricule est nn vide
            if (matricule == null) {
                JsfUtil.addErrorMessage("la ligne " + i + " de la colonne matricule est vide");
                return;
            }
            matricule = matricule.trim();
            note = new Note();
            if (tpe != null) {
                note.setTpe(tpe);
            }
            if (td != null) {
                note.setTd(td);
            }
            if (cc != null) {
                note.setCc(cc);
            }

            if (ex != null) {
                note.setExamen(ex);
            }

            if (rat != null) {
                note.setRattrapage(rat);
            }
            if (moy != null) {
                note.setMoy(moy);
            }

            try {
                etudiant = iSEtudiant.findByMatricule(matricule);
            } catch (NoResultException e) {
                JsfUtil.addErrorMessage("le matricule " + matricule + " n'estpas reconu par le systeme");
            }
            try {
                iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiereId);
            } catch (Exception e) {
                note = iSNote.calculMoyenne(note);
                note.setEtudiant(etudiant);
                note.setMatiere(matiere);
                iSNote.create(note);
                i++;
            }

        }
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des notes de cc,tpe et examen de la specialite " + specialiteId);
        JsfUtil.addSuccessMessage("En tout " + i + " notes(s) de cc, tpe et examen importe(s)");


    }

    public void handleFileUpload(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        if (event.getFile() == null) {
            JsfUtil.addErrorMessage("le fichier envoye est vide");
            facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le fichier est vide", ""));
        }
        InputStream file;
        HSSFWorkbook workbook = null;
        HSSFSheet sheet = null;
        HSSFSheet sheet1 = null;
        HSSFSheet sheet2 = null;
        HSSFSheet sheet3 = null;
        HSSFSheet sheet4 = null;
        try {
            file = event.getFile().getInputstream();
            workbook = new HSSFWorkbook(file);
        } catch (IOException e) {
            JsfUtil.addErrorMessage("le fichier est endomage ou illisible");
            return;
        }

        if (comportement == null) {
            JsfUtil.addErrorMessage("choisir le type de comportement");
            return;
        }



        int semestre = iSMatiere.findById(matiereId).getModule().getSemestre().getLevel();
        try {

            //si on est en tronc commun calcule les specialites
            List<Specialite> specialites = new LinkedList<>();
            Specialite spec = iSSpecialite.findById(specialiteId);
            if (!spec.getNom().equals("/")) {
                specialites.add(spec);
            } else {
                specialites = iSSpecialite.findByNiveau(iSMatiere.findById(matiereId).getModule().getSpecialite().getNiveau().getId());
            }
            for (Specialite specialite1 : specialites) {
                try { //le but est de mettre tous les champs ici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialite1.getId(), anneeId);
                    if (semestre % 2 == 0) {//nous sommes au secnd semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel2() == false && spa.getSemestriel2N() == false && spa.getSemestriel2R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                            snew.setSemestriel1(spa.getSemestriel1());
                            snew.setSemestriel1N(spa.getSemestriel1N());
                            snew.setSemestriel1R(spa.getSemestriel1R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    } else { //nous sommes forcement au premier semestre
                        if (!(spa.getAnnuel() == false && spa.getSemestriel1() == false && spa.getSemestriel1N() == false && spa.getSemestriel1R() == false)) {

                            SpecialiteAnnee snew = new SpecialiteAnnee();
                            snew.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                            snew.setSemestriel2(spa.getSemestriel2());
                            snew.setSemestriel2N(spa.getSemestriel2N());
                            snew.setSemestriel2R(spa.getSemestriel2R());
                            snew.setMoyenne(spa.getMoyenne());
                            snew.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                            specialiteAnnee.delete(spa);
                            snew = specialiteAnnee.create(snew);
                        }
                    }
                } catch (NoResultException nr) {
                    SpecialiteAnnee spa = new SpecialiteAnnee();
                    spa.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                    spa.setSpecialite(iSSpecialite.findById(specialite1.getId()));
                    spa = specialiteAnnee.create(spa);
                }
            }
        } catch (IllegalArgumentException ex) {
        }



        try {
            sheet = workbook.getSheetAt(0);
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage("la feuille 1 est inacessible");
            return;
        } catch (IllegalArgumentException ex) {
            JsfUtil.addErrorMessage("la feuille 1 est inacessible");
            return;
        }
        try {
            sheet1 = workbook.getSheetAt(1);
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage("la feuille 2 est inacessible");
            return;
        } catch (IllegalArgumentException ex) {
            JsfUtil.addErrorMessage("la feuille 2 est inacessible");
            return;
        }
        try {
            sheet2 = workbook.getSheetAt(2);
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage("la feuille 3 est inacessible");
            return;
        } catch (IllegalArgumentException ex) {
            JsfUtil.addErrorMessage("la feuille 3 est inacessible");
            return;
        }

        try {
            sheet3 = workbook.getSheetAt(3);
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage("la feuille 4 est inacessible");
            return;
        } catch (IllegalArgumentException ex) {
            JsfUtil.addErrorMessage("la feuille 4 est inacessible");
            return;
        }

        try {
            sheet4 = workbook.getSheetAt(4);
        } catch (NullPointerException ex) {
            JsfUtil.addErrorMessage("la feuille 5 est inacessible");
            return;
        } catch (IllegalArgumentException ex) {
            JsfUtil.addErrorMessage("la feuille 5 est inacessible");
            return;
        }
        message = new StringBuilder("");


        Matiere matiere = iSMatiere.findById(matiereId);

        if (matiere.getDate() != null) {
            matiere.setDate(new Date());
            iSMatiere.update(matiere);
        }

        ccOperation(sheet, 1);
        if (cont == true) {
            anonOperation(sheet1, 2);
        }
        if (cont == true) {
            examnOperation(sheet2, 3);
        }
        if (cont == true) {
            anonOperationRat(sheet3, 4);
        }
        if (cont == true) {
            ratnOperation(sheet4, 5);
        }

        if (cont == true) {
            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);

        }

    }

    public void ccOperation(HSSFSheet sheet, int sheetName) throws ServiceException {
        cont = false;
        AnneeAcademique anneeAcademique = iSAnneeAcademique.findById(anneeId);
        //on recherche l'annee  precedent afin de s'assurer que l'etudaint ne sort pas de la section
        AnneeAcademique oldAnnee = iSAnneeAcademique.previous(anneeAcademique.getAnnee());
        Specialite specialite = iSSpecialite.findById(specialiteId);

        int j = 0;
        int i = 0;
        Cell cell = null;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                String tpe = "";
                String td = "";
                String cc = "";
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                        case 1:
                            tpe = cell.getStringCellValue();
                            break;
                        case 2:
                            td = cell.getStringCellValue();
                            break;
                        case 3:
                            cc = cell.getStringCellValue();
                            break;
                    }

                }

                if ((!tpe.equals("TPE")) || (!td.equals("TD")) || (!cc.equals("CC")) || (!matricule.equals("Matricule"))) {
                    message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
                    message.append("le fichier n'est pas bien structure ").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                continue;
            }

            Matiere matiere = iSMatiere.findById(matiereId);
            Float tpe = null;
            Float td = null;
            Float cc = null;
            String matricule = null;
            Note note = null;
            Etudiant etudiant = null;
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matricule = cell.getStringCellValue().trim();
                            //etape 1: on s'assure que le matricule est nn vide
                            if (matricule == null) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le matricule est vide (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                        }
                        break;
                    case 1:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                tpe = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException n) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre a la colonne TPE n'est pas correct (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            if (tpe > 20f) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre a la colonne TPE ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                        }

                        break;
                    case 2:
                        System.out.println("cel3    " + cell.getColumnIndex());

                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                td = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException n) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre a la colonne TD n'est pas correct (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            if (td > 20f) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre a la colonne TD ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                        }

                        break;
                    case 3:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                cc = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException n) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre a la colonne CC n'est pas correct (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            if (cc > 20f) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre a la colonne CC ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                        }

                        break;
                }
            }



            if ((matricule != null) || (tpe != null) || (td != null) || (cc != null)) {
                try {
                    matricule = matricule.trim();
                } catch (NullPointerException ex) {
                }
                //etape 2: on verifie que l'etudant est inscrit en faculte
                try {
                    etudiant = iSEtudiant.findByMatricule(matricule);
                } catch (NoResultException e) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                    message.append("le matricule ").append(matricule).append(" n'existe pas (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }

                //etape 3: on verifie si l'etudiant a le droit de composer la matiere

                boolean ancien = false;
                try {
                    List<SpecialiteEtudiant> spes = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveauId, anneeId);
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
                            niv = iSNiveau.nextNiveau(niveauId);
                            try {
                                List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                            } catch (NoResultException eee) {
                                niv = iSNiveau.nextNiveau(niv.getId());
                                try {
                                    List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                                } catch (NoResultException eeee) {
                                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                    message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            }
                            break;
                        case 2: //on cherche au niveau 3
                            niv = iSNiveau.nextNiveau(niveauId);
                            try {
                                List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                            } catch (NoResultException eeee) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            break;
                        default:
                            message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                            message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fctx = FacesContext.getCurrentInstance();
                            fctx.addMessage(null, fm);
                            return;

                    }

                }

                //si c'est un ancien on verifie s'il a valide
                if (ancien) {
                    try {
                        Module curentModule = iSModule.findLastUE(etudiant.getId(), matiere.getModule().getId(), 0);
                        Note selected = iSNote.findByUEEtudiant(etudiant.getId(), curentModule.getId());
                        try {
                            int lev = selected.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                            Float moy1 = selected.getMoy();
                            if (((moy1.compareTo(10f) >= 0) && (lev <= 3)) || ((moy1.compareTo(12f) >= 0) && (lev > 3))) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                message.append("L'etudiant de matricule ").append(matricule).append(" a deja eu a valider cette matiere (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
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
                    Note n = iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiereId);
                    if (comportement.equals("rec")) {
                        if (tpe != null) {
                            n.setTpe(tpe);
                        }
                        if (td != null) {
                            n.setTd(td);
                        }
                        if (cc != null) {
                            n.setCc(cc);
                        }

                        //iSNote.update(n);
                        Note not = (Note) n.clone();
                        iSNote.delete(n);
                        not.setId(null);
                        not = iSNote.calcul(not);
                        iSNote.create(not);
                        j++;
                    }
                } catch (NoResultException e) {
                    note = null;
                    if (tpe != null) {
                        if (note == null) {
                            note = new Note();
                        }
                        note.setTpe(tpe);
                    }
                    if (td != null) {
                        if (note == null) {
                            note = new Note();
                        }
                        note.setTd(td);
                    }
                    if (cc != null) {
                        if (note == null) {
                            note = new Note();
                        }
                        note.setCc(cc);
                    }

                    if (note != null) {
                        note.setEtudiant(etudiant);
                        note.setMatiere(matiere);
                        note = iSNote.calcul(note);
                        iSNote.create(note);
                        i++;
                    }

                }

            }
        }
        cont = true;
        message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
        message.append("En tout ").append(i).append(" notes(s) de cc, tpe et examen ajoutes(s) (bon)").append("</b> <br/>");
        message.append("En tout ").append(j).append(" notes(s) de cc, tpe et examen mis a jour (bon)").append("</b> <br/>");
        Specialite spp = iSSpecialite.findById(specialiteId);
        AnneeAcademique aa = iSAnneeAcademique.findById(anneeId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des notes de cc de la specialite " + spp + " pour l'année " + aa);
    }

    public void anonOperation(HSSFSheet sheet, int sheetName) throws ServiceException {
        Specialite specialite = iSSpecialite.findById(specialiteId);
        cont = false;
        int i = 0;
        Cell cell = null;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                String anonymat = "";
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                        case 1:
                            anonymat = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!anonymat.equals("Anonymat")) || (!matricule.equals("Matricule"))) {
                    message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
                    message.append("le fichier n'est pas bien structure ").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                continue;
            }

            Matiere matiere = iSMatiere.findById(matiereId);
            String anonymat = null;
            String matricule = null;
            Note note = null;
            Etudiant etudiant = null;
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matricule = cell.getStringCellValue().trim();
                        }
                        break;
                    case 1:
                        if (!cell.getStringCellValue().isEmpty()) {
                            anonymat = cell.getStringCellValue().trim();
                        }
                        break;
                }
            }

            if ((matricule != null) || (anonymat != null)) {

                if (anonymat == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                    message.append("l'anonymat est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                if (matricule == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                    message.append("le matricule est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                try {
                    matricule = matricule.trim();
                    etudiant = iSEtudiant.findByMatricule(matricule);
                } catch (NoResultException ee) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                    message.append("le matricule ").append(matricule).append(" n'est pas reconnu (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }


                //on verifie s'il doit composer

                boolean ancien = false;
                try {
                    List<SpecialiteEtudiant> spes = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveauId, anneeId);
                    if (spes.size() == 1) {
                        SpecialiteEtudiant sp = spes.get(0);
                        if (!specialite.getNom().equals("/")) {
                            if (sp.getSpecialite().getNom().equals("/")) {
                                sp.setSpecialite(specialite);
                                iSSpecialiteEtudiant.update(sp);
                            }
                        }
                    }
                } catch (NonUniqueResultException eeex) {
                } catch (NoResultException ee) {
                    ancien = true;
                    int level = specialite.getNiveau().getLevel();
                    switch (level) {
                        case 1: //on cherche aux niveaux 2 et 3
                            niv = iSNiveau.nextNiveau(niveauId);
                            try {
                                List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                            } catch (NoResultException eee) {
                                niv = iSNiveau.nextNiveau(niv.getId());
                                try {
                                    List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                                } catch (NoResultException eeee) {
                                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                    message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            }
                            break;
                        case 2: //on cherche au niveau 3
                            niv = iSNiveau.nextNiveau(niveauId);
                            try {
                                List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                            } catch (NoResultException eeee) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            break;
                        default:
                            message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                            message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fctx = FacesContext.getCurrentInstance();
                            fctx.addMessage(null, fm);
                            return;

                    }

                }

                //si c'est un ancien on verifie s'il a valide
                if (ancien) {
                    try {
                        Module curentModule = iSModule.findLastUE(etudiant.getId(), matiere.getModule().getId(), 0);
                        Note selected = iSNote.findByUEEtudiant(etudiant.getId(), curentModule.getId());
                        try {
                            int lev = selected.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                            Float moy1 = selected.getMoy();
                            if (((moy1.compareTo(10f) >= 0) && (lev <= 3)) || ((moy1.compareTo(12f) >= 0) && (lev > 3))) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                message.append("L'etudiant de matricule ").append(matricule).append(" a deja eu a valider cette matiere (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                        } catch (NullPointerException ex1) {
                        }
                    } catch (NoResultException ex2) {
                        /* message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                         message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                         FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                         fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                         FacesContext fctx = FacesContext.getCurrentInstance();
                         fctx.addMessage(null, fm);
                         return; */
                    }
                }



                try {
                    note = iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiere.getId());
                    if (comportement.equals("rec")) {
                        note.setAnonymatEx(anonymat);
                        //iSNote.update(note);
                        Note not = (Note) note.clone();
                        iSNote.delete(note);
                        not.setId(null);
                        iSNote.create(not);
                    } else {
                        try {
                            note = iSNote.findAnonymatExMatiere(anonymat, matiere.getId());
                        } catch (NoResultException nr) {
                            note.setAnonymatEx(anonymat);
                            //iSNote.update(note);
                            Note not = (Note) note.clone();
                            iSNote.delete(note);
                            not.setId(null);
                            iSNote.create(not);
                        } catch (NonUniqueResultException n) {
                            message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                            message.append("L'etudiant de matricule ").append(matricule).append(" a plusieurs anonymats pour une matiere (erreur)").append("</b> <br/>");
                            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fctx = FacesContext.getCurrentInstance();
                            fctx.addMessage(null, fm);
                            return;
                        }
                    }
                } catch (NoResultException eee) {
                    note = new Note();
                    note.setEtudiant(etudiant);
                    note.setMatiere(matiere);
                    note.setAnonymatEx(anonymat);
                    iSNote.create(note);

                } catch (NonUniqueResultException n) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                    message.append("L'etudiant de matricule ").append(matricule).append(" a plusieurs anonymats pour une matiere (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }

                i++;
            }
        }
        cont = true;
        message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
        message.append("En tout ").append(i).append(" numeros d'anonymats importes (bon)").append("</b> <br/>");
        Specialite spp = iSSpecialite.findById(specialiteId);
        AnneeAcademique aa = iSAnneeAcademique.findById(anneeId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des anonymats d'examens de la specialite " + spp + " pour l'année " + aa);


    }

    public void examnOperation(HSSFSheet sheet, int sheetName) throws ServiceException {
        //on met a jour la date d'importation de la matiere
        Matiere matiere = iSMatiere.findById(matiereId);
        matiere.setDate(new Date());
        iSMatiere.update(matiere);
        cont = false;
        int i = 0;
        Cell cell = null;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String anonymat = "";
                String note = "";
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            anonymat = cell.getStringCellValue();
                            break;
                        case 1:
                            note = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!anonymat.equals("Anonymat")) || (!note.equals("Note"))) {
                    message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
                    message.append("le fichier n'est pas bien structure ").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                continue;
            }


            String anonymat = null;
            Float examen = null;
            Note note = null;
            Etudiant etudiant = null;
            //Note note = new Note();
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (!cell.getStringCellValue().isEmpty()) {
                            anonymat = cell.getStringCellValue();
                        }
                        break;
                    case 1:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                examen = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException n) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("verifiez le nombre de a colonne examen (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            if (examen > 20f) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre de a colonne examen ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                        }

                        break;
                }


            }

            if ((anonymat != null) || (examen != null)) {

                if (anonymat == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                    message.append("l'anonymat est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }

                if (examen == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                    message.append("l'examen est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                try {
                    note = iSNote.findAnonymatExMatiere(anonymat, matiereId);
                    if (comportement.equals("rec")) {
                        note.setExamen(examen);
                        note = iSNote.calcul(note);
                        //iSNote.update(note);
                        Note not = (Note) note.clone();
                        iSNote.delete(note);
                        not.setId(null);
                        iSNote.create(not);
                        i++;
                    } else if (note.getExamen() == null) {
                        note.setExamen(examen);
                        note = iSNote.calcul(note);
                        //iSNote.update(note);
                        Note not = (Note) note.clone();
                        iSNote.delete(note);
                        not.setId(null);
                        iSNote.create(not);
                    }
                } catch (NoResultException e) {
                } catch (NonUniqueResultException ec) {
                }

            }
        }
        cont = true;
        message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
        message.append("En tout ").append(i).append(" codes(s) d'examens importe(s) (bon)").append("</b> <br/>");
        Specialite spp = iSSpecialite.findById(specialiteId);
        AnneeAcademique aa = iSAnneeAcademique.findById(anneeId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des notes d'examens de la specialite " + spp + " pour l'année " + aa);

    }

    public void anonOperationRat(HSSFSheet sheet, int sheetName) throws ServiceException {
        Specialite specialite = iSSpecialite.findById(specialiteId);
        cont = false;
        int i = 0;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                String anonymat = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                        case 1:
                            anonymat = cell.getStringCellValue().trim();
                            break;
                    }

                }
                if ((!anonymat.equals("Anonymat")) || (!matricule.equals("Matricule"))) {
                    message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
                    message.append("le fichier n'est pas bien structure ").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                continue;
            }

            Matiere matiere = iSMatiere.findById(matiereId);
            String anonymat = null;
            String matricule = null;
            Note note = null;
            Etudiant etudiant = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matricule = cell.getStringCellValue().trim();
                        }
                        break;
                    case 1:
                        if (!cell.getStringCellValue().isEmpty()) {
                            anonymat = cell.getStringCellValue();
                        }
                        break;
                }
            }

            if ((matricule != null) || (anonymat != null)) {
                if (anonymat == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(i + 1).append("</b> <br/>");
                    message.append("la colone anonymat est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);

                    //JsfUtil.addErrorMessage("la ligne " + (i + 1) + " de la colonne anonymat est vide");
                    return;
                }
                if (matricule == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(i + 1).append("</b> <br/>");
                    message.append("la colonne matricule est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    //JsfUtil.addErrorMessage("la ligne " + (i + 1) + " de la colonne matricule est vide");
                    return;
                }


                try {
                    matricule = matricule.trim();
                    etudiant = iSEtudiant.findByMatricule(matricule);
                } catch (NoResultException ee) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(i + 1).append("</b> <br/>");
                    message.append("le matricule ").append(matricule).append(" n'est pas reconnu (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }


                //on verifie si l'etudiant est autorise
                boolean ancien = false;
                try {
                    List<SpecialiteEtudiant> spes = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveauId, anneeId);
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
                            Niveau niv = iSNiveau.nextNiveau(niveauId);
                            try {
                                List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                            } catch (NoResultException eee) {
                                niv = iSNiveau.nextNiveau(niv.getId());
                                try {
                                    List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                                } catch (NoResultException eeee) {
                                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                    message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            }
                            break;
                        case 2: //on cherche au niveau 3
                            niv = iSNiveau.nextNiveau(niveauId);
                            try {
                                List<SpecialiteEtudiant> spe = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niv.getId(), anneeId);
                            } catch (NoResultException eeee) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            break;
                        default:
                            message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                            message.append("Le matricule ").append(matricule).append(" n'est pas autorise a composer cette matiere (erreur)").append("</b> <br/>");
                            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                            FacesContext fctx = FacesContext.getCurrentInstance();
                            fctx.addMessage(null, fm);
                            return;

                    }

                }

                //si c'est un ancien on verifie s'il a valide
                if (ancien) {
                    try {
                        Module curentModule = iSModule.findLastUE(etudiant.getId(), matiere.getModule().getId(), 0);
                        Note selected = iSNote.findByUEEtudiant(etudiant.getId(), curentModule.getId());
                        try {
                            int lev = selected.getMatiere().getModule().getSpecialite().getNiveau().getLevel();
                            Float moy1 = selected.getMoy();
                            AnneeAcademique aaa = iSAnneeAcademique.findById(anneeId);
                            if (!selected.getMatiere().getModule().getAnneeAcademique().getAnnee().equals(aaa.getAnnee())) {
                                if (((moy1.compareTo(10f) >= 0) && (lev <= 3)) || ((moy1.compareTo(12f) >= 0) && (lev > 3))) {
                                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(row.getRowNum() + 1).append("</b> <br/>");
                                    message.append("L'etudiant de matricule ").append(matricule).append(" a deja eu a valider cette matiere (erreur)").append("</b> <br/>");
                                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                    FacesContext fctx = FacesContext.getCurrentInstance();
                                    fctx.addMessage(null, fm);
                                    return;
                                }
                            }
                        } catch (NullPointerException ex1) {
                        }
                    } catch (NoResultException ex2) {
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
                    note = iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiere.getId());
                    comportement = "rec";
                    if (comportement.equals("rec")) {
                        note.setAnonymatRat(anonymat);
                        //iSNote.update(note);
                        Note not = (Note) note.clone();
                        iSNote.delete(note);
                        not.setId(null);
                        iSNote.create(not);
                    } else {
                        try {
                            note = iSNote.findAnonymatRatMatiere(anonymat, matiere.getId());
                        } catch (NoResultException nr) {
                            note.setAnonymatEx(anonymat);
                            //iSNote.update(note);
                            Note not = (Note) note.clone();
                            iSNote.delete(note);
                            not.setId(null);
                            iSNote.create(not);
                        }
                    }
                } catch (NoResultException eee) {
                    note = new Note();
                    note.setEtudiant(etudiant);
                    note.setMatiere(matiere);
                    note.setAnonymatRat(anonymat);
                    iSNote.create(note);
                }

                i++;
            }
        }
        cont = true;
        Specialite spp = iSSpecialite.findById(specialiteId);
        AnneeAcademique aa = iSAnneeAcademique.findById(anneeId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des anonymats de ratrapage de la specialite " + spp + " pour l'année " + aa);
        message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
        message.append("En tout ").append(i).append("   anonymats de ratrappage(s) importe(s) (bon)").append("</b> <br/>");

    }

    public void ratnOperation(HSSFSheet sheet, int sheetName) throws ServiceException {
        cont = false;
        int i = 0;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String anonymat = "";
                String note = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            anonymat = cell.getStringCellValue();
                            break;
                        case 1:
                            note = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!anonymat.equals("Anonymat")) || (!note.equals("Note"))) {
                    message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
                    message.append("le fichier n'est pas bien structure ").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }
                continue;
            }

            Matiere matiere = iSMatiere.findById(matiereId);
            String anonymat = null;
            Float rat = null;
            Note note = null;
            Etudiant etudiant = null;
            //Note note = new Note();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (!cell.getStringCellValue().isEmpty()) {
                            anonymat = cell.getStringCellValue();
                        }
                        break;
                    case 1:
                        if (!cell.getStringCellValue().isEmpty()) {
                            try {
                                rat = Float.parseFloat(cell.getStringCellValue());
                            } catch (NumberFormatException n) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("verifiez le nombre de a colonne ratrappage (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                            if (rat > 20f) {
                                message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(cell.getRowIndex() + 1).append("</b> <br/>");
                                message.append("le nombre de a colonne ratrappage ne doit pas depasser 20 (erreur)").append("</b> <br/>");
                                FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                                fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                                FacesContext fctx = FacesContext.getCurrentInstance();
                                fctx.addMessage(null, fm);
                                return;
                            }
                        }

                        break;
                }


            }

            if ((anonymat != null) || (rat != null)) {

                if (anonymat == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(i + 1).append("</b> <br/>");
                    message.append("l'anonymat est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    return;
                }

                if (rat == null) {
                    message.append("<b>Feuille no ").append(sheetName).append(" Ligne ").append(i + 1).append("</b> <br/>");
                    message.append("le rattrapage est vide (erreur)").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    //  JsfUtil.addErrorMessage("la ligne " + (i + 1) + " de la colonne exammen est vide");
                    return;
                }
                try {
                    note = iSNote.findAnonymatRatMatiere(anonymat, matiereId);
                    comportement = "rec";
                    if (comportement.equals("rec")) {
                        note.setRattrapage(rat);
                        note = iSNote.calcul(note);
                        //iSNote.update(note);
                        Note not = (Note) note.clone();
                        iSNote.delete(note);
                        not.setId(null);
                        iSNote.create(not);
                        i++;
                    } else if (note.getExamen() == null) {
                        note.setRattrapage(rat);
                        note = iSNote.calcul(note);
                        //iSNote.update(note);
                        Note not = (Note) note.clone();
                        iSNote.delete(note);
                        not.setId(null);
                        iSNote.create(not);
                    }
                } catch (NoResultException e) {
                } catch (NonUniqueResultException ec) {
                }

            }
        }

        cont = true;
        Specialite spp = iSSpecialite.findById(specialiteId);
        AnneeAcademique aa = iSAnneeAcademique.findById(anneeId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des notes de ratrappage de la specialite " + spp + " pour l'année " + aa);
        message.append("<b>Feuille no ").append(sheetName).append("</b> <br/>");
        message.append("En tout ").append(i).append(" notes(s) de ratrapage importe(s) (bon)").append("</b> <br/>");


    }

    public ISNiveau getiSNiveau() {
        return iSNiveau;
    }

    public void setiSNiveau(ISNiveau iSNiveau) {
        this.iSNiveau = iSNiveau;
    }

    public ISSpecialite getiSSpecialite() {
        return iSSpecialite;
    }

    public void setiSSpecialite(ISSpecialite iSSpecialite) {
        this.iSSpecialite = iSSpecialite;
    }

    public ISMatiere getiSMatiere() {
        return iSMatiere;
    }

    public void setiSMatiere(ISMatiere iSMatiere) {
        this.iSMatiere = iSMatiere;
    }

    public ISDepartement getiSDepartement() {
        return iSDepartement;
    }

    public void setiSDepartement(ISDepartement iSDepartement) {
        this.iSDepartement = iSDepartement;
    }

    public List<Departement> getDepartements() throws ServiceException {
        departements = iSDepartement.findAll();
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public void processDepartement() throws ServiceException {
        sectionDisable = false;
        niveauDisable = true;
        specialiteDisable = true;
        uploadVisible = false;
        getSections();
    }

    public void processSection() throws ServiceException {
        niveauDisable = false;
        specialiteDisable = true;
        uploadVisible = false;
        getNiveaus();
    }

    public void processNiveau() throws ServiceException {
        specialiteDisable = false;
        getSpecialites();
    }

    public void processAnnee() throws ServiceException {
        moduleDisable = false;
        matiereDisable = true;
        getModules();
    }

    public void processUE() throws ServiceException {
        matiereDisable = false;
        getMatieres();
    }

    public void processMatiere() throws ServiceException {
        uploadVisible = true;
        getMatieres();
    }

    public void processMatricule() throws ServiceException {
        sectionDisable = false;
        getListSection();
    }

    public List<Section> getListSection() throws ServiceException {
        try {
            listSection = iSSection.findByEtudiant(matricule);
            return listSection;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public List<Niveau> getNiveaus() throws ServiceException {

        try {

            niveaus = iSNiveau.findBySection(sectionId);//departement(iSDepartement.findById(Long.valueOf(dep)));
        } catch (NullPointerException ex) {

            niveaus = new ArrayList<Niveau>();
        }

        return niveaus;
    }

    public List<Section> getSections() throws ServiceException {

        try {

            sections = iSSection.findByDepartement(departementId);//departement(iSDepartement.findById(Long.valueOf(dep)));
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
            specialites = iSSpecialite.findByNiveau(niveauId);
        } catch (NullPointerException ex) {

            specialites = new ArrayList<Specialite>();
        }
        return specialites;
    }

    public List<Matiere> getMatieres() throws ServiceException {

        try {
            matieres = iSMatiere.findByUE(moduleId);
        } catch (NullPointerException ex) {

            matieres = new ArrayList<Matiere>();
        }
        return matieres;
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

    public boolean isMatiereDisable() {
        return matiereDisable;
    }

    public void setMatiereDisable(boolean matiereDisable) {
        this.matiereDisable = matiereDisable;
    }

    public ISEtudiant getiSEtudiant() {
        return iSEtudiant;
    }

    public void setiSEtudiant(ISEtudiant iSEtudiant) {
        this.iSEtudiant = iSEtudiant;
    }

    public ISNote getiSNote() {
        return iSNote;
    }

    public void setiSNote(ISNote iSNote) {
        this.iSNote = iSNote;
    }

    public ISSection getiSSection() {
        return iSSection;
    }

    public void setiSSection(ISSection iSSection) {
        this.iSSection = iSSection;
    }

    public boolean isSectionDisable() {
        return sectionDisable;
    }

    public void setSectionDisable(boolean sectionDisable) {
        this.sectionDisable = sectionDisable;
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

    public Long getSpecialiteId() {
        return specialiteId;
    }

    public void setSpecialiteId(Long specialiteId) {
        this.specialiteId = specialiteId;
    }

    public Long getMatiereId() {
        return matiereId;
    }

    public void setMatiereId(Long matiereId) {
        this.matiereId = matiereId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public boolean isUploadVisible() {
        return uploadVisible;
    }

    public void setUploadVisible(boolean uploadVisible) {
        this.uploadVisible = uploadVisible;
    }

    public ISAnneeAcademique getiSAnneeAcademique() {
        return iSAnneeAcademique;
    }

    public void setiSAnneeAcademique(ISAnneeAcademique iSAnneeAcademique) {
        this.iSAnneeAcademique = iSAnneeAcademique;
    }

    public List<AnneeAcademique> getAnneeAcademiques() throws ServiceException {
        try {
            anneeAcademiques = iSAnneeAcademique.findAll();
        } catch (NullPointerException ex) {

            specialites = new ArrayList<Specialite>();
        }
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public Long getAnneeId() {
        return anneeId;
    }

    public void setAnneeId(Long anneeId) {
        this.anneeId = anneeId;
    }

    public ISModule getiSModule() {
        return iSModule;
    }

    public void setiSModule(ISModule iSModule) {
        this.iSModule = iSModule;
    }

    public List<Module> getModules() throws ServiceException {
        try {
            modules = iSModule.findByAnneeAcSpecialite(anneeId, specialiteId);
        } catch (NullPointerException ex) {

            modules = new ArrayList<Module>();
        }
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isModuleDisable() {
        return moduleDisable;
    }

    public void setModuleDisable(boolean moduleDisable) {
        this.moduleDisable = moduleDisable;
    }

    public ISSpecialiteEtudiant getiSSpecialiteEtudiant() {
        return iSSpecialiteEtudiant;
    }

    public void setiSSpecialiteEtudiant(ISSpecialiteEtudiant iSSpecialiteEtudiant) {
        this.iSSpecialiteEtudiant = iSSpecialiteEtudiant;
    }

    public void doSomething(ActionEvent actionEven) throws ServiceException {
        List<Note> notes = iSNote.filters(matiereId, anneeId);
        if (operation.equals(NOTE_CC)) {
            for (Note note : notes) {
                note.setCc(null);
                note.setMoy(null);
                note.setCote(null);
                note.setGrade(null);
                note.setDecision(null);
                iSNote.update(note);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des notes de cc d'une matiere");
            JsfUtil.addSuccessMessage("toutes les notes de cc sont suprimmes");
        } else if (operation.equals(ANONYMAT_EXAMEN)) {
            for (Note note : notes) {
                note.setAnonymatEx(null);
                note.setMoy(null);
                note.setCote(null);
                note.setGrade(null);
                note.setDecision(null);
                iSNote.update(note);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des anonymats d'examen d'une matiere");
            JsfUtil.addSuccessMessage("toutes les anonymats d'examen sont suprimmes");

        } else if (operation.equals(NOTE_EXAMEN)) {
            for (Note note : notes) {
                note.setExamen(null);
                note.setMoy(null);
                note.setCote(null);
                note.setGrade(null);
                note.setDecision(null);
                iSNote.update(note);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des notes d'examen d'une matiere");
            JsfUtil.addSuccessMessage("toutes les notes de d'examnen sont suprimmes");

        } else if (operation.equals(ANONYMAT_RATRAPPAGE)) {
            for (Note note : notes) {
                note.setAnonymatRat(null);
                note.setMoy(null);
                note.setCote(null);
                note.setGrade(null);
                note.setDecision(null);
                iSNote.update(note);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des anonymat de rattrapage d'une matiere");
            JsfUtil.addSuccessMessage("toutes les anonymats de ratrapage sont suprimmes");


        } else if (operation.equals(NOTE_RATRAPPAGE)) {
            for (Note note : notes) {
                note.setRattrapage(null);
                note.setMoy(null);
                note.setCote(null);
                note.setGrade(null);
                note.setDecision(null);
                iSNote.update(note);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des notes de rattrapage d'une matiere");
            JsfUtil.addSuccessMessage("toutes les notes de ratrapage sont suprimmes");

        } else if (operation.equals(ALL)) {
            for (Note note : notes) {
                iSNote.delete(note);
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression des notes d'une matiere ");
            JsfUtil.addSuccessMessage("toutes les notes de cette matiere sont suprimmes");

        }
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Note getTargetNote() {
        return targetNote;
    }

    public void setTargetNote(Note targetNote) {
        this.targetNote = targetNote;
    }

    public void doRequete(ActionEvent actionEven) throws ServiceException {
        Etudiant etudiant;
        Matiere matiere = iSMatiere.findById(matiereId);
        try {
            etudiant = iSEtudiant.findByMatricule(matricule);
        } catch (NoResultException ee) {
            JsfUtil.addErrorMessage("matricule non reconnu ");
            return;
        }
        try {
            Note note = iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiereId);
            if ((noteTPE != null) && (!noteTPE.isEmpty())) {
                try {
                    note.setTpe(Float.parseFloat(noteTPE));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de tpe doit etre un chiffre ");
                    return;
                }
            }
            if ((noteTD != null) && (!noteTD.isEmpty())) {
                try {
                    note.setTd(Float.parseFloat(noteTD));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de td doit etre un chiffre ");
                    return;
                }
            }

            if ((noteCC != null) && (!noteCC.isEmpty())) {
                try {
                    note.setCc(Float.parseFloat(noteCC));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de cc doit etre un chiffre ");
                    return;
                }
            }

            if ((noteAnoE != null) && (!noteAnoE.isEmpty())) {
                note.setAnonymatEx(noteAnoE);
            }

            if ((noteEE != null) && (!noteEE.isEmpty())) {
                try {
                    note.setExamen(Float.parseFloat(noteEE));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note d'examen doit etre un chiffre ");
                    return;
                }
            }

            if ((noteAnoR != null) && (!noteAnoR.isEmpty())) {
                note.setAnonymatRat(noteAnoR);
            }

            if ((noteRA != null) && (!noteRA.isEmpty())) {
                try {
                    note.setRattrapage(Float.parseFloat(noteRA));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de ratrappage doit etre un chiffre ");
                    return;
                }
            }
            note = iSNote.calcul(note);
            iSNote.update(note);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour des  notes de l'etudiant " + etudiant);
            JsfUtil.addSuccessMessage("les notes de " + etudiant.getNom() + " ont ete mis a jour");
        } catch (NoResultException ex) {
            Note note = new Note();
            if ((noteTPE != null) && (!noteTPE.isEmpty())) {
                try {
                    note.setTpe(Float.parseFloat(noteTPE));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de tpe doit etre un chiffre ");
                    return;
                }
            }
            if ((noteTD != null) && (!noteTD.isEmpty())) {
                try {
                    note.setTd(Float.parseFloat(noteTD));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de td doit etre un chiffre ");
                    return;
                }
            }

            if ((noteCC != null) && (!noteCC.isEmpty())) {
                try {
                    note.setCc(Float.parseFloat(noteCC));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de cc doit etre un chiffre ");
                    return;
                }
            }

            if ((noteAnoE != null) && (!noteAnoE.isEmpty())) {
                note.setAnonymatEx(noteAnoE);
            }

            if ((noteEE != null) && (!noteEE.isEmpty())) {
                try {
                    note.setExamen(Float.parseFloat(noteEE));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note d'examen doit etre un chiffre ");
                    return;
                }
            }

            if ((noteAnoR != null) && (!noteAnoR.isEmpty())) {
                note.setAnonymatRat(noteAnoR);
            }

            if ((noteRA != null) && (!noteRA.isEmpty())) {
                try {
                    note.setRattrapage(Float.parseFloat(noteRA));
                } catch (NumberFormatException n) {
                    JsfUtil.addErrorMessage("la note de ratrappage doit etre un chiffre ");
                    return;
                }
            }
            note = iSNote.calcul(note);
            note.setMatiere(matiere);
            note.setEtudiant(etudiant);
            iSNote.create(note);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation des  notes de l'etudiant " + etudiant);
            JsfUtil.addSuccessMessage("les notes de " + etudiant.getNom() + " ont été creés");
        }
    }

    public void doDelete(ActionEvent actionEven) throws ServiceException {
        Etudiant etudiant;
        try {
            etudiant = iSEtudiant.findByMatricule(matricule);
        } catch (NoResultException ee) {
            JsfUtil.addErrorMessage("matricule non reconnu ");
            return;
        }
        Note note = iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiereId);
        iSNote.delete(note);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppresion des  notes de l'etudiant " + etudiant);
        JsfUtil.addSuccessMessage("toutes les notes ont ete supprimees");
    }

    public void doDeliberer(ActionEvent actionEven) throws ServiceException {
        Etudiant etudiant;
        try {
            etudiant = iSEtudiant.findByMatricule(matricule);
        } catch (NoResultException ee) {
            JsfUtil.addErrorMessage("matricule non reconnu ");
            return;
        }
        Note note = iSNote.findNoteByEtudiantMatiere(etudiant.getId(), matiereId);
        iSNote.deliberationSpeciale(note);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à 10 de la   note de l'etudiant " + etudiant + "pour la matiere d'id: " + matiereId);
        JsfUtil.addSuccessMessage("la moyenne a ete mise a 10");
    }

    public void doBlanchir(ActionEvent actionEven) throws ServiceException {
        Etudiant etudiant;
        try {
            etudiant = iSEtudiant.findByMatricule(matricule);
        } catch (NoResultException ee) {
            JsfUtil.addErrorMessage("matricule non reconnu ");
            return;
        }
        AnneeAcademique annee = iSAnneeAcademique.findById(anneeId);
        iSNote.blanchirEtudiantAnnee(matricule, anneeId, sectionId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "blanchiment de l'etudiant " + etudiant + "pour l'année: " + annee);
        JsfUtil.addSuccessMessage(etudiant.getNom() + " a été blanchi pour l'annee " + annee.getAnnee());
    }

    public String getNoteTPE() {
        return noteTPE;
    }

    public void setNoteTPE(String noteTPE) {
        this.noteTPE = noteTPE;
    }

    public String getNoteTD() {
        return noteTD;
    }

    public void setNoteTD(String noteTD) {
        this.noteTD = noteTD;
    }

    public String getNoteCC() {
        return noteCC;
    }

    public void setNoteCC(String noteCC) {
        this.noteCC = noteCC;
    }

    public String getNoteAnoE() {
        return noteAnoE;
    }

    public void setNoteAnoE(String noteAnoE) {
        this.noteAnoE = noteAnoE;
    }

    public String getNoteEE() {
        return noteEE;
    }

    public void setNoteEE(String noteEE) {
        this.noteEE = noteEE;
    }

    public String getNoteAnoR() {
        return noteAnoR;
    }

    public void setNoteAnoR(String noteAnoR) {
        this.noteAnoR = noteAnoR;
    }

    public String getNoteRA() {
        return noteRA;
    }

    public void setNoteRA(String noteRA) {
        this.noteRA = noteRA;
    }

    public List<Rapport> getRapports() {
        /*FacesContext ctx = FacesContext.getCurrentInstance();
         HttpSession s = (HttpSession) ctx.getExternalContext().getSession(false);
         String message = (String) s.getAttribute("rap");                     //session.setAttribute("rap", "testons");


         /* 
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
         // return (List<Rapport>) session.getAttribute("rapports");
         */

//        FacesContext ctt = FacesContext.getCurrentInstance();
//        HttpServletRequest request = (HttpServletRequest) ctt.getExternalContext().getRequest();
//        HttpSession httpSession = request.getSession(false);
//        String message = (String) httpSession.getAttribute("testParam");
//        Rapport rapport = new Rapport();
//        rapport.setMessage(message);
//        rapport.setNo("1");
//        rapports.add(rapport);
        return rapports;
    }

    public void setRapports(List<Rapport> rapports) {
        this.rapports = rapports;
    }

    public Rapport getSelectedrRapport() {
        return selectedrRapport;
    }

    public void setSelectedrRapport(Rapport selectedrRapport) {
        this.selectedrRapport = selectedrRapport;
    }

    public String getSomething() {
        return something;
    }

    public void setSomething(String something) {
        this.something = something;
    }

    private UIComponent findComponent(final String id) {

        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        final UIComponent[] found = new UIComponent[1];

        root.visitTree(new FullVisitContext(context), new VisitCallback() {
            @Override
            public VisitResult visit(VisitContext context, UIComponent component) {
                if (component.getId().equals(id)) {
                    found[0] = component;
                    return VisitResult.COMPLETE;
                }
                return VisitResult.ACCEPT;
            }
        });

        return found[0];

    }

    public Growl getGrowl() {
        growl.setSticky(true);
        return growl;
    }

    public void setGrowl(Growl growl) {
        this.growl = growl;
    }

    public String getClass1() {
        class1 = "specialiteImport";
        return class1;
    }

    public void setClass1(String class1) {
        this.class1 = class1;
    }

    public static boolean isRowEmpty(Row row) {
        for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
            Cell cell = row.getCell(c);
            if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK) {
                return false;
            }
        }
        return true;
    }

    public String getComportement() {
        return comportement;
    }

    public void setComportement(String comportement) {
        this.comportement = comportement;
    }

    public ISSpecialiteAnnee getSpecialiteAnnee() {
        return specialiteAnnee;
    }

    public void setSpecialiteAnnee(ISSpecialiteAnnee specialiteAnnee) {
        this.specialiteAnnee = specialiteAnnee;
    }

    private String removeIllegal(String matricule) {
        for (int i = 0; i < matricule.length(); i++) {
            char object = matricule.charAt(i);
            if (!Character.isLetterOrDigit(object)) {
                matricule = matricule.replaceAll(object + "", "");
            }
        }
        return matricule;
    }

    public Map<String, Long> getNiveaux() throws ServiceException {
        niveaux = NivUtil.getLevel(getNiveaus());
        return niveaux;
    }
}
