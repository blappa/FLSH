/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.NivUtil;
import com.univ.maroua.flsh.beans.util.UserUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.EtudiantSection;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.projection.Pv;
import com.univ.maroua.flsh.projection.Solvable;
import com.univ.maroua.flsh.projection.Template;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISEtudiantSection;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.primefaces.event.FileUploadEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author william mekomou
 * <williammekomou@yahoo.com>
 */
@ManagedBean(name = "importationEtudiantBean")
@SessionScoped
public class ImportationEtudiantBean implements Serializable {

    @ManagedProperty(value = "#{ISHistInscrip}")
    private ISInscription histInsc;
    @ManagedProperty(value = "#{ISInscription}")
    private ISInscription iSInscription;
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
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant iSSpecialiteEtudiant;
    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique iSAnneeAcademique;
    @ManagedProperty(value = "#{ISEtudiantSection}")
    private ISEtudiantSection etudiantSection;
    @ManagedProperty(value = "#{ISUtilisateur}")
    private ISUtilisateur uti;
    private int typepayment;
    private List<Departement> departements;
    private List<Section> sections;
    private List<Niveau> niveaus;
    private List<Specialite> specialites;
    private List<Matiere> matieres;
    private List<AnneeAcademique> anneeAcademiques;
    //pour desactiver les combo boxes au lancement
    private boolean sectionDisable = true;
    private boolean niveauDisable = true;
    private boolean specialiteDisable = true;
    private boolean uploadVisible = false;
    //les valeurs des elements contenus dans la combobox
    private Long departementId;
    private Long sectionId;
    private Long niveauId;
    private Long specialiteId;
    private Long matiereId;
    private Long anneeId;
    private boolean cont;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private StringBuilder message;
    private int typeOp;
    private Map<String, Long> niveaux;
    private Map<String, Long> niveauxAll;

    public ImportationEtudiantBean() {
    }

    public void xlsSolvable(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
        JasperPrint jasperPrint;
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        List<Section> mods = iSSection.findAll();
        int i = 0, j = 0, k = 0;
        String moduleNames[] = new String[40];
        for (Section m : mods) {
            moduleNames[k++] = m.getId() + ""; //nom de la feuille
            tps = new LinkedList<>();
            tp = new Template();

            String comment = tp.getComment();
            comment += "\n" + m.getNom();
            tp.setComment(comment);
            tps.add(tp);
            jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "solvable.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
            list.add(jasperPrint);
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
        response.setHeader("content-disposition", "attachment; filename=" + "solvable.xls");
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

    public void xlsSolvable1(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        ArrayList<JasperPrint> list = new ArrayList<JasperPrint>();
        JasperPrint jasperPrint;
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        int i = 0, j = 0, k = 0;
        String moduleNames[] = new String[40];

        moduleNames[k++] = "premiere tranche"; //nom de la feuille
        tps = new LinkedList<>();
        List<Etudiant> etudiants = iSInscription.findByTypeAnnee(1, anneeId); //premiere tranche
        Set<Solvable> solvables = new HashSet<>();
        for (Etudiant etudiant : etudiants) {
            List<SpecialiteEtudiant> specialites = iSSpecialiteEtudiant.findByMatriculeEtudiant(etudiant.getMatricule());
            for (SpecialiteEtudiant specialiteEtudiant : specialites) {
                try {
                    Solvable solvable = new Solvable();
                    solvable.setMatricule(specialiteEtudiant.getEtudiant().getMatricule());
                    solvable.setNom(specialiteEtudiant.getEtudiant().getNom());
                    solvable.setSection(specialiteEtudiant.getSpecialite().getNiveau().getSection().getNom());
                    solvable.setNiveau(specialiteEtudiant.getSpecialite().getNiveau().getLevel() + "");
                    solvables.add(solvable);
                } catch (NullPointerException ex) {
                }
            }

        }
        List<Solvable> sols = new LinkedList<>();
        sols.addAll(solvables);
        for (Solvable solvable : sols) {
            tp = new Template();
            tp.setCell1(solvable.getMatricule());
            tp.setCell2(solvable.getNom());
            tp.setCell3(solvable.getSection());
            tp.setCell4(solvable.getNiveau());
            tps.add(tp);
        }
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "solvableGet.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);


        moduleNames[k++] = "seconde tranche"; //nom de la feuille
        tps = new LinkedList<>();
        etudiants = iSInscription.findByTypeAnnee(2, anneeId); //premiere tranche
        solvables = new HashSet<>();
        for (Etudiant etudiant : etudiants) {
            List<SpecialiteEtudiant> specialites = iSSpecialiteEtudiant.findByMatriculeEtudiant(etudiant.getMatricule());
            for (SpecialiteEtudiant specialiteEtudiant : specialites) {
                try {
                    Solvable solvable = new Solvable();
                    solvable.setMatricule(specialiteEtudiant.getEtudiant().getMatricule());
                    solvable.setNom(specialiteEtudiant.getEtudiant().getNom());
                    solvable.setSection(specialiteEtudiant.getSpecialite().getNiveau().getSection().getNom());
                    solvable.setNiveau(specialiteEtudiant.getSpecialite().getNiveau().getLevel() + "");
                    solvables.add(solvable);
                } catch (NullPointerException ex) {
                }
            }

        }
        sols = new LinkedList<>();
        sols.addAll(solvables);
        for (Solvable solvable : sols) {
            tp = new Template();
            tp.setCell1(solvable.getMatricule());
            tp.setCell2(solvable.getNom());
            tp.setCell3(solvable.getSection());
            tp.setCell4(solvable.getNiveau());
            tps.add(tp);
        }
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "solvableGet.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);


        moduleNames[k++] = "totalite"; //nom de la feuille
        tps = new LinkedList<>();
        etudiants = iSInscription.findByTypeAnnee(3, anneeId); //premiere tranche
        solvables = new HashSet<>();
        for (Etudiant etudiant : etudiants) {
            List<SpecialiteEtudiant> specialites = iSSpecialiteEtudiant.findByMatriculeEtudiant(etudiant.getMatricule());
            for (SpecialiteEtudiant specialiteEtudiant : specialites) {
                try {
                    Solvable solvable = new Solvable();
                    solvable.setMatricule(specialiteEtudiant.getEtudiant().getMatricule());
                    solvable.setNom(specialiteEtudiant.getEtudiant().getNom());
                    solvable.setSection(specialiteEtudiant.getSpecialite().getNiveau().getSection().getNom());
                    solvable.setNiveau(specialiteEtudiant.getSpecialite().getNiveau().getLevel() + "");
                    solvables.add(solvable);
                } catch (NullPointerException ex) {
                }
            }

        }
        sols = new LinkedList<>();
        sols.addAll(solvables);
        for (Solvable solvable : sols) {
            tp = new Template();
            tp.setCell1(solvable.getMatricule());
            tp.setCell2(solvable.getNom());
            tp.setCell3(solvable.getSection());
            tp.setCell4(solvable.getNiveau());
            tps.add(tp);
        }
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "solvableGet.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));
        list.add(jasperPrint);



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
        response.setHeader("content-disposition", "attachment; filename=" + "solvableAll.xls");
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

    public void xlsPreinscrit(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        JasperPrint jasperPrint;
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        tps.add(tp);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "preinscrit.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + "preinscrit.xls");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(byteArrayOutputStream.toByteArray());
        JRXlsExporter xlsxExporter = new JRXlsExporter();
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

    public void xlsInscritSup(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        JasperPrint jasperPrint;
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        tps.add(tp);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "inscritSup.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + "inscritSup.xls");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(byteArrayOutputStream.toByteArray());
        JRXlsExporter xlsxExporter = new JRXlsExporter();
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

    public void xlsUpdateEtudiant(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        JasperPrint jasperPrint;
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        tps.add(tp);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "updateetudiant.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + "updateetudiant.xls");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(byteArrayOutputStream.toByteArray());
        JRXlsExporter xlsxExporter = new JRXlsExporter();
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

    public void xlsInscrit(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        JasperPrint jasperPrint;
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        tps.add(tp);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "inscrit.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + "inscrit.xls");
        ServletOutputStream servletOutputStream = response.getOutputStream();
        servletOutputStream.write(byteArrayOutputStream.toByteArray());
        JRXlsExporter xlsxExporter = new JRXlsExporter();
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

    public void handleFileUpload(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Specialite specialite = iSSpecialite.findById(specialiteId);
        int i = 0, j = 0;
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

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String nom = "";
                String matricule = "";
                String sexe = "";
                String dateNais = "";
                String lieuNais = "";
                String region = "";
                String departement = "";
                String diplome = "";
                String anneeObtDipl = "";
                String nationalite = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            nom = cell.getStringCellValue();
                            break;
                        case 1:
                            matricule = cell.getStringCellValue();
                            break;
                        case 2:
                            sexe = cell.getStringCellValue();
                            break;
                        case 3:
                            dateNais = cell.getStringCellValue();
                            break;
                        case 4:
                            lieuNais = cell.getStringCellValue();
                            break;
                        case 5:
                            region = cell.getStringCellValue();
                            break;
                        case 6:
                            departement = cell.getStringCellValue();
                            break;
                        case 7:
                            diplome = cell.getStringCellValue();
                            break;
                        case 8:
                            anneeObtDipl = cell.getStringCellValue();
                            break;
                        case 9:
                            nationalite = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!nom.equals("NomPrenom")) || (!matricule.equals("Matricule"))
                        || (!sexe.equals("Sexe")) || (!dateNais.equals("DateNais"))
                        || (!lieuNais.equals("LieuNais")) || (!region.equals("Region"))
                        || (!departement.equals("Departement")) || (!diplome.equals("Diplome"))
                        || (!anneeObtDipl.equals("AnneeObtDipl")) || (!nationalite.equals("Nationalite"))) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }

            SpecialiteEtudiant specialiteEtudiant;
            String nom = "";
            String matricule = "";
            String sexe = "";
            String dateNais = "";
            String lieuNais = "";
            String region = "";
            String departement = "";
            String diplome = "";
            String anneeObtDipl = "";
            String nationalite = "";
            Etudiant etudiant = null;
            //Note note = new Note();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getColumnIndex()) {

                    case 0:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            nom = cell.getStringCellValue();
                        }
                        break;
                    case 1:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            matricule = cell.getStringCellValue();
                        }
                        break;
                    case 2:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            sexe = cell.getStringCellValue();
                        }
                        break;
                    case 3:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            dateNais = cell.getStringCellValue();
                        }
                        break;
                    case 4:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            lieuNais = cell.getStringCellValue();
                        }
                        break;
                    case 5:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            region = cell.getStringCellValue();
                        }
                        break;
                    case 6:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            departement = cell.getStringCellValue();
                        }
                        break;
                    case 7:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            diplome = cell.getStringCellValue();
                        }
                        break;
                    case 8:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            anneeObtDipl = cell.getStringCellValue();
                        }
                        break;
                    case 9:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            nationalite = cell.getStringCellValue();
                        }
                        break;
                }


            }

            //on verifie si c'est une ligne vide
            if (matricule != null || nom != null) {
                matricule = matricule.trim();
                specialiteEtudiant = new SpecialiteEtudiant();
                etudiant = new Etudiant();
                etudiant.setNom(nom);
                etudiant.setMatricule(matricule);

                etudiant.setSexe(sexe);
                etudiant.setDateNais(dateNais);
                etudiant.setLieuNais(lieuNais);
                etudiant.setDepartement(departement);
                etudiant.setRegion(region);
                etudiant.setDiplomEntree(diplome);
                etudiant.setAnneeObtDiplo(anneeObtDipl);
                etudiant.setNationalite(nationalite);
                try {
                    //Etudiant e = iSEtudiant.findByMatricule(matricule);
                    // etudiant.setId(e.getId());
                    etudiant = iSEtudiant.findByMatricule(matricule);
                } catch (NoResultException ex) {
                    etudiant = iSEtudiant.create(etudiant);
                }
                try {
                    List<SpecialiteEtudiant> spes = iSSpecialiteEtudiant.findByEtudiantNiveauAnnee(etudiant.getId(), niveauId, anneeId);
                    if (spes.size() == 1) {
                        SpecialiteEtudiant sp = spes.get(0);
                        if (!sp.getSpecialite().getId().equals(specialite.getId())) {
                            sp.setSpecialite(specialite);
                            iSSpecialiteEtudiant.update(sp);
                            j++;
                        }
                    }
                } catch (NoResultException ex) {
                    specialiteEtudiant.setEtudiant(etudiant);
                    specialiteEtudiant.setSpecialite(specialite);
                    specialiteEtudiant.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                    iSSpecialiteEtudiant.create(specialiteEtudiant);
                    i++;
                }
            }
        }
        JsfUtil.addSuccessMessage("En tout " + i + " etudiants(s) importe(s)");
        JsfUtil.addSuccessMessage("En tout " + j + " etudiants(s) mis a jour");
        AnneeAcademique aa = iSAnneeAcademique.findById(anneeId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des etudiants inscrits de la specialité " + specialite + "de l'année " + aa);

    }

    public void handleFileUploadPreinscrit(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        AnneeAcademique anneeAcademique = iSAnneeAcademique.findById(anneeId);
        Long rank = anneeAcademique.getRank() + 1;
        String start = anneeAcademique.getAnnee().substring(2, 4) + "D";
        String end = "FL";
        Specialite specialite = iSSpecialite.findById(specialiteId);
        int i = 0, j = 0;
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

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String nom = "";
                String sexe = "";
                String dateNais = "";
                String lieuNais = "";
                String region = "";
                String departement = "";
                String diplome = "";
                String anneeObtDipl = "";
                String nationalite = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            nom = cell.getStringCellValue();
                            break;
                        case 1:
                            sexe = cell.getStringCellValue();
                            break;
                        case 2:
                            dateNais = cell.getStringCellValue();
                            break;
                        case 3:
                            lieuNais = cell.getStringCellValue();
                            break;
                        case 4:
                            region = cell.getStringCellValue();
                            break;
                        case 5:
                            departement = cell.getStringCellValue();
                            break;
                        case 6:
                            diplome = cell.getStringCellValue();
                            break;
                        case 7:
                            anneeObtDipl = cell.getStringCellValue();
                            break;
                        case 8:
                            nationalite = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!nom.equals("NomPrenom")) || (!sexe.equals("Sexe")) || (!dateNais.equals("DateNais"))
                        || (!lieuNais.equals("LieuNais")) || (!region.equals("Region"))
                        || (!departement.equals("Departement")) || (!diplome.equals("Diplome"))
                        || (!anneeObtDipl.equals("AnneeObtDipl")) || (!nationalite.equals("Nationalite"))) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }

            SpecialiteEtudiant specialiteEtudiant;
            String nom = null;
            String sexe = "";
            String dateNais = "";
            String lieuNais = "";
            String region = "";
            String departement = "";
            String diplome = "";
            String anneeObtDipl = "";
            String nationalite = "";
            Etudiant etudiant = null;
            //Note note = new Note();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getColumnIndex()) {

                    case 0:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            nom = cell.getStringCellValue();
                        }
                        break;
                    case 1:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            sexe = cell.getStringCellValue();
                        }
                        break;
                    case 2:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            dateNais = cell.getStringCellValue();
                        }
                        break;
                    case 3:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            lieuNais = cell.getStringCellValue();
                        }
                        break;
                    case 4:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            region = cell.getStringCellValue();
                        }
                        break;
                    case 5:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            departement = cell.getStringCellValue();
                        }
                        break;
                    case 6:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            diplome = cell.getStringCellValue();
                        }
                        break;
                    case 7:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            anneeObtDipl = cell.getStringCellValue();
                        }
                        break;
                    case 8:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            nationalite = cell.getStringCellValue();
                        }
                        break;
                }


            }

            if (nom != null) {

                specialiteEtudiant = new SpecialiteEtudiant();
                etudiant = new Etudiant();
                etudiant.setNom(nom);
                etudiant.setSexe(sexe);
                etudiant.setDateNais(dateNais);
                etudiant.setLieuNais(lieuNais);
                etudiant.setDepartement(departement);
                etudiant.setRegion(region);
                etudiant.setDiplomEntree(diplome);
                etudiant.setAnneeObtDiplo(anneeObtDipl);
                etudiant.setNationalite(nationalite);
                String middle = rank + "";
                switch (middle.length()) {
                    case 1:
                        middle = "000" + middle;
                        break;
                    case 2:
                        middle = "00" + middle;
                        break;
                    case 3:
                        middle = "0" + middle;
                        break;
                }


                String matricule = start + middle + end;

                etudiant.setMatricule(matricule);
                rank++;
                etudiant = iSEtudiant.create(etudiant);

                specialiteEtudiant.setEtudiant(etudiant);
                specialiteEtudiant.setSpecialite(specialite);
                specialiteEtudiant.setAnneeAcademique(iSAnneeAcademique.findById(anneeId));
                iSSpecialiteEtudiant.create(specialiteEtudiant);
                i++;
            }


        }
        JsfUtil.addSuccessMessage("En tout " + i + " etudiants(s) preinscrits(s)");
        anneeAcademique.setRank(rank);
        iSAnneeAcademique.update(anneeAcademique);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des etudiants préinscrits de la specialité " + specialite + "de l'année " + anneeAcademique);

    }

    public void handleFileUploadInscritSup(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        AnneeAcademique anneeAcademique = iSAnneeAcademique.findById(anneeId);
        Specialite specialite = iSSpecialite.findById(specialiteId);
        int i = 0, j = 0;
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

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        Cell cell = null;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                while (cellIterator.hasNext()) {
                    cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                    }

                }
                if (!matricule.equals("Matricule")) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }
            SpecialiteEtudiant specialiteEtudiant;
            String matricule = null;
            Etudiant etudiant = null;
            //Note note = new Note();
            while (cellIterator.hasNext()) {
                cell = cellIterator.next();

                switch (cell.getColumnIndex()) {

                    case 0:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            matricule = cell.getStringCellValue();
                        }
                        break;
                }
            }
            if (matricule == null) {
                break;
            }

            matricule = matricule.trim();
            try {
                etudiant = iSEtudiant.findByMatricule(matricule);
            } catch (NoResultException ex) {
                JsfUtil.addErrorMessage("le matricule " + matricule + " n'est pas reconnu(ligne" + (cell.getRowIndex() + 1) + ")");
                return;
            }

            try {
                iSSpecialiteEtudiant.findByEtudiantSpecialiteAnnee(etudiant.getId(), anneeId, specialiteId);
            } catch (NoResultException ex) {
                JsfUtil.addErrorMessage("l'etudiant " + etudiant.getNom() + " ne fait pas la specialite(ligne" + (cell.getRowIndex() + 1) + ")");
                return;
            }

            int level = specialite.getNiveau().getLevel();
            try {
                EtudiantSection es = etudiantSection.findByEtudiantSection(etudiant.getId(), specialite.getNiveau().getSection().getId());
                switch (level) {
                    case 2:
                        es.setAdmis2(1);
                        break;
                    case 3:
                        es.setAdmis3(1);
                        break;
                    case 5:
                        es.setAdmis5(1);
                        break;
                }
                etudiantSection.update(es);
            } catch (NoResultException ex) {
                EtudiantSection es = new EtudiantSection();
                es.setEtudiant(etudiant);
                es.setSection(specialite.getNiveau().getSection());
                switch (level) {
                    case 2:
                        es.setAdmis2(1);
                        break;
                    case 3:
                        es.setAdmis3(1);
                        break;
                    case 5:
                        es.setAdmis5(1);
                        break;
                }
                etudiantSection.create(es);
            }
            i++;
        }
        JsfUtil.addSuccessMessage("En tout " + i + " etudiants(s)importes(s)");
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation  des étudiants inscrits à la spécialité " + specialite + " pour l'année académique " + anneeAcademique);

    }

    public void delete(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Specialite specialite = iSSpecialite.findById(specialiteId);
        int i = 0, j = 0;
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

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!matricule.equals("Matricule"))) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }

            SpecialiteEtudiant specialiteEtudiant;
            String matricule = "";
            Etudiant etudiant = null;
            //Note note = new Note();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getColumnIndex()) {

                    case 0:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            matricule = cell.getStringCellValue();
                        }
                        break;
                }


            }

            specialiteEtudiant = new SpecialiteEtudiant();
            etudiant = new Etudiant();
            matricule = matricule.trim();
            etudiant.setMatricule(matricule);

            try {
                //Etudiant e = iSEtudiant.findByMatricule(matricule);
                // etudiant.setId(e.getId());
                etudiant = iSEtudiant.findByMatricule(matricule);
            } catch (NoResultException ex) {
                JsfUtil.addErrorMessage("le matricule " + matricule + " est inconu");

            }
            try {
                SpecialiteEtudiant spe = iSSpecialiteEtudiant.findByEtudiantSpecialiteAnnee(etudiant.getId(), anneeId, specialiteId);
                iSSpecialiteEtudiant.delete(spe);
                j++;
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "suppression de la specialite: " + spe + "  des etudiants");
            } catch (NoResultException ex) {
                JsfUtil.addErrorMessage("l'étudiant  " + etudiant.getNom() + " n'est pas en " + specialite.getNom());
            }

        }
        AnneeAcademique anneeAcademique = iSAnneeAcademique.findById(anneeId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "supression des etudiants de la spécialité " + specialite + " pour l'année " + anneeAcademique);
        JsfUtil.addSuccessMessage("En tout " + j + " specialités supprimées");

    }

    public void handleFileUploadUpdate(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        int i = 0;
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

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String nom = "";
                String matricule = "";
                String sexe = "";
                String dateNais = "";
                String lieuNais = "";
                String region = "";
                String departement = "";
                String diplome = "";
                String anneeObtDipl = "";
                String nationalite = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            nom = cell.getStringCellValue();
                            break;
                        case 1:
                            matricule = cell.getStringCellValue();
                            break;
                        case 2:
                            sexe = cell.getStringCellValue();
                            break;
                        case 3:

                            dateNais = cell.getStringCellValue();
                            break;
                        case 4:
                            lieuNais = cell.getStringCellValue();
                            break;
                        case 5:
                            region = cell.getStringCellValue();
                            break;
                        case 6:
                            departement = cell.getStringCellValue();
                            break;
                        case 7:
                            diplome = cell.getStringCellValue();
                            break;
                        case 8:
                            anneeObtDipl = cell.getStringCellValue();
                            break;
                        case 9:
                            nationalite = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!nom.equals("NomPrenom")) || (!matricule.equals("Matricule"))
                        || (!sexe.equals("Sexe")) || (!dateNais.equals("DateNais"))
                        || (!lieuNais.equals("LieuNais")) || (!region.equals("Region"))
                        || (!departement.equals("Departement")) || (!diplome.equals("Diplome"))
                        || (!anneeObtDipl.equals("AnneeObtDipl")) || (!nationalite.equals("Nationalite"))) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }

            SpecialiteEtudiant specialiteEtudiant;
            String nom = null;
            String matricule = null;
            String sexe = null;
            String dateNais = null;
            String lieuNais = null;
            String region = null;
            String departement = null;
            String diplome = null;
            String anneeObtDipl = null;
            String nationalite = null;
            Etudiant etudiant = null;
            //Note note = new Note();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();

                switch (cell.getColumnIndex()) {

                    case 0:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            nom = cell.getStringCellValue();
                        }
                        break;
                    case 1:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            matricule = cell.getStringCellValue().trim();
                        }
                        break;
                    case 2:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            sexe = cell.getStringCellValue();
                        }
                        break;
                    case 3:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            dateNais = cell.getStringCellValue();
                        }
                        break;
                    case 4:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            lieuNais = cell.getStringCellValue();
                        }
                        break;
                    case 5:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            region = cell.getStringCellValue();
                        }
                        break;
                    case 6:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            departement = cell.getStringCellValue();
                        }
                        break;
                    case 7:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            diplome = cell.getStringCellValue();
                        }
                        break;
                    case 8:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            anneeObtDipl = cell.getStringCellValue();
                        }
                        break;
                    case 9:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            nationalite = cell.getStringCellValue();
                        }
                        break;
                }


            }

            if (matricule != null) {
                matricule = matricule.trim();
                try {
                    Etudiant e = iSEtudiant.findByMatricule(matricule);
                    if (nom != null) {
                        e.setNom(nom);
                    }
                    if (matricule != null) {
                        e.setMatricule(matricule);
                    }
                    if (sexe != null) {
                        e.setSexe(sexe);
                    }
                    if (dateNais != null) {
                        e.setDateNais(dateNais);
                    }
                    if (lieuNais != null) {
                        e.setLieuNais(lieuNais);
                    }
                    if (departement != null) {
                        e.setDepartement(departement);
                    }
                    if (region != null) {
                        e.setRegion(region);
                    }
                    if (diplome != null) {
                        e.setDiplomEntree(diplome);
                    }
                    if (anneeObtDipl != null) {
                        e.setAnneeObtDiplo(anneeObtDipl);
                    }
                    if (nationalite != null) {
                        e.setNationalite(nationalite);
                    }
                    iSEtudiant.update(e);
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "mise à jour de l' étudiant: " + e);
                    i++;
                } catch (NoResultException ex) {
                }
            }

        }
        JsfUtil.addSuccessMessage("En tout " + i + " etudiants(s) importe(s)");
    }

    public void handleFileUploadPaidOld(FileUploadEvent event) throws ServiceException {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        int i = 0, j = 0;
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

        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                    }

                }
                if (!matricule.equals("Matricule")) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }

            String matricule = "";
            Inscription inscription = null;
            Etudiant etudiant = null;
            AnneeAcademique ac = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (cell.getStringCellValue() != null) {
                            matricule = cell.getStringCellValue();
                        }
                        break;
                }
            }

            try {
                matricule = matricule.trim();
                etudiant = iSEtudiant.findByMatricule(matricule);
                inscription = new Inscription();
                inscription.setType(typepayment);
                ac = iSAnneeAcademique.findById(anneeId);
                inscription.setAnneeAcademique(ac);
                inscription.setEtudiant(etudiant);
                iSInscription.create(inscription);
                i++;
            } catch (NoResultException ex) {
                j++;
            }
        }
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation de " + i + " etudiants payes");
        JsfUtil.addSuccessMessage("En tout " + i + " etudiants(s) payes importe(s)");
        JsfUtil.addSuccessMessage("En tout " + j + " matricules non reconus");
    }

    public void handleFileUploadPaid(FileUploadEvent event) throws ServiceException {
        cont = true;
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
        message = new StringBuilder("");
        int nn = workbook.getNumberOfSheets();
        for (int i = 0; i < nn; i++) {
            if (!cont) {
                break;
            }
            handleFileUploadPaidGeneric(workbook.getSheetAt(i), i + 1, nn);
        }
    }

    public void handleFileUploadPaidGeneric(HSSFSheet sheet, int sheetNumber, int numbersOfSheets) throws ServiceException {
        AnneeAcademique anneeAcademique = iSAnneeAcademique.findById(anneeId);
        FacesContext facesContext = FacesContext.getCurrentInstance();
        int i = 0, j = 0, k = 0, sectId;
        try {
            sectId = Integer.parseInt(sheet.getSheetName());
        } catch (NumberFormatException ex) {
            message.append("<b>Feuille no ").append(sheetNumber).append("</b> <br/>");
            message.append("le nom n'est pas reconnu ").append("</b> <br/>");
            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
            fm.setSeverity(FacesMessage.SEVERITY_ERROR);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);
            cont = false;
            return;
        }
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String matricule = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                    }

                }
                if (!matricule.equals("Matricule")) {
                    message.append("<b>Feuille no ").append(sheetNumber).append("</b> <br/>");
                    message.append("le fichier n'est pas bien structure ").append("</b> <br/>");
                    FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
                    fm.setSeverity(FacesMessage.SEVERITY_ERROR);
                    FacesContext fctx = FacesContext.getCurrentInstance();
                    fctx.addMessage(null, fm);
                    cont = false;
                    return;
                }
                continue;
            }

            String matricule = "";
            Inscription inscription = null;
            Etudiant etudiant = null;
            AnneeAcademique ac = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getColumnIndex()) {
                    case 0:
                        if (cell.getStringCellValue() != null) {
                            matricule = cell.getStringCellValue();
                        }
                        break;
                }
            }

            try {
                matricule = matricule.trim();
                etudiant = iSEtudiant.findByMatricule(matricule);

                if (typeOp == 1) {
                    if (!iSInscription.aPayeByTypeAnnee(typepayment, anneeId, matricule, Long.valueOf(sectId))) {
                        inscription = new Inscription();
                        inscription.setType(typepayment);
                        ac = iSAnneeAcademique.findById(anneeId);
                        inscription.setAnneeAcademique(ac);
                        inscription.setEtudiant(etudiant);
                        inscription.setSection(iSSection.findById(Long.valueOf(sectId)));
                        iSInscription.create(inscription);
                        i++;
                    } else {
                        k++;
                    }
                    if (typepayment == 1) {
                        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation par defaut des étudiants ayant payé la première tranche de l'année " + anneeAcademique);
                    } else if (typepayment == 1) {
                        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation par defaut des étudiants ayant payé la seconde tranche de l'année " + anneeAcademique);
                    } else {
                        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation par defaut des étudinats ayant payé la totalité de l'année " + anneeAcademique);
                    }
                } else if (typeOp == 2) {
                    if (!iSInscription.aPayeByTypeAnnee(typepayment, anneeId, matricule, Long.valueOf(sectId))) {
                        inscription = new Inscription();
                        inscription.setType(typepayment);
                        ac = iSAnneeAcademique.findById(anneeId);
                        inscription.setAnneeAcademique(ac);
                        inscription.setEtudiant(etudiant);
                        inscription.setSection(iSSection.findById(Long.valueOf(sectId)));
                        iSInscription.create(inscription);
                        i++;
                    } else {
                        k++;
                    }

                    List<Note> notes = iSNote.findByEtudiantSectionAnnee(matricule, Long.valueOf(sectId), anneeId);
                    for (Note note : notes) {
                        note.setRequette(false);
                        note.setDaterequette(new Date());
                        Note n = (Note) note.clone();
                        iSNote.delete(note);
                        n.setId(null);
                        iSNote.create(n);

                    }

                    if (typepayment == 1) {
                        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation simple des étudiants ayant payé la première tranche de l'année " + anneeAcademique);
                    } else if (typepayment == 1) {
                        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation simple des étudiants ayant payé la seconde tranche de l'année " + anneeAcademique);
                    } else {
                        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation simple des étudinats ayant payé la totalité de l'année " + anneeAcademique);
                    }

                } else {
                    if (!iSInscription.aPayeByTypeAnnee(typepayment, anneeId, matricule, Long.valueOf(sectId))) {
                        inscription = new Inscription();
                        inscription.setType(typepayment);
                        ac = iSAnneeAcademique.findById(anneeId);
                        inscription.setAnneeAcademique(ac);
                        inscription.setEtudiant(etudiant);
                        inscription.setSection(iSSection.findById(Long.valueOf(sectId)));
                        iSInscription.create(inscription);
                        i++;
                    } else {
                        k++;
                    }
                    List<Note> notes = iSNote.findByEtudiantSectionAnnee(matricule, Long.valueOf(sectId), anneeId);
                    for (Note note : notes) {
                        note.setRequette(true);
                        note.setDaterequette(new Date());
                        Note n = (Note) note.clone();
                        iSNote.delete(note);
                        n.setId(null);
                        iSNote.create(n);
                    }


                }

            } catch (NoResultException ex) {
                j++;
            }
        }
        if (typepayment == 1) {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation pour requete des étudiants ayant payé la première tranche de l'année " + anneeAcademique);
        } else if (typepayment == 2) {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation pour requete des étudiants ayant payé la seconde tranche de l'année " + anneeAcademique);
        } else {
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation pour requete des étudiants ayant payé la totalité de l'année " + anneeAcademique);
        }
        message.append("<b>Feuille no ").append(sheetNumber).append("</b> <br/>");
        message.append("en tout ").append(i).append(" etudiants(s) payes importe(s)").append("</b> <br/>");
        message.append("en tout ").append(j).append(" matricules non reconus").append("</b> <br/>");
        message.append("en tout ").append(k).append(" ayant deja paye").append("</b> <br/>");

        if (sheetNumber == numbersOfSheets) {
            FacesMessage fm = new FacesMessage("RAPPORT", message.toString());
            fm.setSeverity(FacesMessage.SEVERITY_INFO);
            FacesContext fctx = FacesContext.getCurrentInstance();
            fctx.addMessage(null, fm);
        }
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

    public void processSection1() throws ServiceException {
        niveauDisable = false;
        specialiteDisable = true;
        uploadVisible = false;
        getNiveaus1();
    }

    public void processNiveau() throws ServiceException {
        specialiteDisable = false;
        uploadVisible = false;
        getSections();
    }

    public void processSpecialite() throws ServiceException {
        uploadVisible = true;
    }

    public List<Niveau> getNiveaus() throws ServiceException {

        try {
            niveaus = iSNiveau.findBySection(sectionId);//departement(iSDepartement.findById(Long.valueOf(dep)));
        } catch (NullPointerException ex) {

            niveaus = new ArrayList<Niveau>();
        }

        return niveaus;
    }

    public List<Niveau> getNiveaus1() throws ServiceException {

        try {
            niveaus = new LinkedList<>();
            List<Niveau> nv = iSNiveau.findBySection(sectionId);//departement(iSDepartement.findById(Long.valueOf(dep)));
            for (Niveau niveau : nv) {
                if ((niveau.getLevel() != 1) && (niveau.getLevel() != 4)) {
                    niveaus.add(niveau);
                }
            }
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

    public int getTypepayment() {
        return typepayment;
    }

    public void setTypepayment(int typepayment) {
        this.typepayment = typepayment;
    }

    public ISSpecialiteEtudiant getiSSpecialiteEtudiant() {
        return iSSpecialiteEtudiant;
    }

    public void setiSSpecialiteEtudiant(ISSpecialiteEtudiant iSSpecialiteEtudiant) {
        this.iSSpecialiteEtudiant = iSSpecialiteEtudiant;
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
        anneeAcademiques = new LinkedList<>();
        Utilisateur utilisateur = uti.findByUsername(UserUtil.getUsername());
        try {
            if (utilisateur.getAllyear() == true) {
                anneeAcademiques = iSAnneeAcademique.findAll();
                return anneeAcademiques;
            } else {
                anneeAcademiques = iSAnneeAcademique.findAll();
                for (AnneeAcademique anneeAcademique1 : anneeAcademiques) {
                    if (anneeAcademique1.getIstargetyear() == true) {
                        anneeAcademiques = new LinkedList<>();
                        anneeAcademiques.add(anneeAcademique1);
                        return anneeAcademiques;
                    }
                }
            }
        } catch (Exception e) {
        }
        anneeAcademiques = iSAnneeAcademique.findAll();
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

    public ISInscription getiSInscription() {
        return iSInscription;
    }

    public void setiSInscription(ISInscription iSInscription) {
        this.iSInscription = iSInscription;
    }

    public ISInscription getHistInsc() {
        return histInsc;
    }

    public void setHistInsc(ISInscription histInsc) {
        this.histInsc = histInsc;
    }

    public ISEtudiantSection getEtudiantSection() {
        return etudiantSection;
    }

    public void setEtudiantSection(ISEtudiantSection etudiantSection) {
        this.etudiantSection = etudiantSection;
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

    public Map<String, Long> getNiveaux() throws ServiceException {
        niveaux = NivUtil.getLevel(getNiveaus1());
        return niveaux;
    }

    public Map<String, Long> getNiveauxAll() throws ServiceException {
        niveauxAll = NivUtil.getLevel(getNiveaus());
        return niveauxAll;
    }
}
