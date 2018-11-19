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
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.projection.Template;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISMatiere;
import com.univ.maroua.flsh.service.ISModule;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSemestre;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
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
@ManagedBean(name = "importationMatiereBean")
@SessionScoped
public class ImportationMatiereBean implements Serializable {

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
    @ManagedProperty(value = "#{ISSemestre}")
    private ISSemestre sem;
    private String operation;
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
   private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
private  Map<String, Long> niveaux;
   
    public ImportationMatiereBean() {
    }

    public void xlsMatiere(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        JasperPrint jasperPrint;
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        String comment = tp.getComment();
        comment += " à la ligne:" + "\n" + "-> 2 entrez le code de l'UE. ex: SIGE 250"
                + "\n" + "-> 3 entrez le credit de l'UE. ex: 6"
                + "\n" + "-> 4 et plus entrez les matieres de l'UE. ex: génie logiciel";
        tp.setComment(comment);
        tps.add(tp);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "matiere.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + "matiere.xls");
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

        HSSFSheet sheet = workbook.getSheetAt(0);
        AnneeAcademique anneeAcademique = iSAnneeAcademique.findById(anneeId);
        Specialite specialite = iSSpecialite.findById(specialiteId);
        int level = specialite.getNiveau().getLevel();

        Module module1 = null;
        Module module2 = null;
        Module module3 = null;
        Module module4 = null;
        Module module5 = null;
        Module module6 = null;
        Module module7 = null;
        Module module8 = null;
        Module module9 = null;
        Module module10 = null;
        Module module11 = null;
        Module module12 = null;



        int i = 0;
        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {

            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            if (row.getRowNum() == 0) {
                String m1 = "";
                String m2 = "";
                String m3 = "";
                String m4 = "";
                String m5 = "";
                String m6 = "";
                String m7 = "";
                String m8 = "";
                String m9 = "";
                String m10 = "";
                String m11 = "";
                String m12 = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            m1 = cell.getStringCellValue();
                            break;
                        case 1:
                            m2 = cell.getStringCellValue();
                            break;
                        case 2:
                            m3 = cell.getStringCellValue();
                            break;
                        case 3:
                            m4 = cell.getStringCellValue();
                            break;
                        case 4:
                            m5 = cell.getStringCellValue();
                            break;
                        case 5:
                            m6 = cell.getStringCellValue();
                            break;
                        case 6:
                            m7 = cell.getStringCellValue();
                            break;
                        case 7:
                            m8 = cell.getStringCellValue();
                            break;
                        case 8:
                            m9 = cell.getStringCellValue();
                            break;
                        case 9:
                            m10 = cell.getStringCellValue();
                            break;
                        case 10:
                            m11 = cell.getStringCellValue();
                            break;
                        case 11:
                            m12 = cell.getStringCellValue();
                            break;
                    }
                }


                if ((!m1.equals("UE1")) || (!m2.equals("UE2")) || (!m3.equals("UE3")) || (!m4.equals("UE4"))
                        || (!m5.equals("UE5")) || (!m6.equals("UE6"))
                        || (!m7.equals("UE7")) || (!m8.equals("UE8")) || (!m9.equals("UE9")) || (!m10.equals("UE10"))
                        || (!m11.equals("UE11")) || (!m12.equals("UE12"))) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }

                continue;
            }



            if (row.getRowNum() == 1) {
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    switch (cell.getColumnIndex()) {
                        case 0:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module1 = new Module();
                                module1.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 1:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module2 = new Module();
                                module2.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 2:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module3 = new Module();
                                module3.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 3:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module4 = new Module();
                                module4.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 4:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module5 = new Module();
                                module5.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 5:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module6 = new Module();
                                module6.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 6:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module7 = new Module();
                                module7.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 7:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module8 = new Module();
                                module8.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 8:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module9 = new Module();
                                module9.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 9:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module10 = new Module();
                                module10.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 10:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module11 = new Module();
                                module11.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                        case 11:
                            if (!cell.getStringCellValue().isEmpty()) {
                                module12 = new Module();
                                module12.setTargetCode(cell.getStringCellValue());
                            }
                            break;
                    }
                }

                continue;
            }


            if (row.getRowNum() == 2) {
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    switch (cell.getColumnIndex()) {
                        case 0:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module1.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 1:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module2.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 2:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module3.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 3:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module4.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 4:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module5.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 5:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module6.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 6:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module7.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 7:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module8.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 8:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module9.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 9:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module10.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 10:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module11.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                        case 11:
                            if (!cell.getStringCellValue().isEmpty()) {
                                try {
                                    module12.setCredit(Float.parseFloat(cell.getStringCellValue()));
                                } catch (NumberFormatException e) {
                                } catch (NullPointerException e) {
                                }
                            }
                            break;
                    }
                }
                int semestre = level * 2 - 1;
                try {
                    module1.setAnneeAcademique(anneeAcademique);
                    module1.setSpecialite(specialite);
                    module1.setSemestre(sem.findByLevel(semestre));
                    module1.setCode("1");
                    module1 = iSModule.create(module1);
                } catch (NullPointerException ee) {
                }
                try {
                    module2.setAnneeAcademique(anneeAcademique);
                    module2.setSpecialite(specialite);
                    module2.setSemestre(sem.findByLevel(semestre));
                    module2.setCode("2");
                    module2 = iSModule.create(module2);
                } catch (NullPointerException ee) {
                }
                try {
                    module3.setAnneeAcademique(anneeAcademique);
                    module3.setSpecialite(specialite);
                    module3.setSemestre(sem.findByLevel(semestre));
                    module3.setCode("3");
                    module3 = iSModule.create(module3);
                } catch (NullPointerException ee) {
                }
                try {
                    module4.setAnneeAcademique(anneeAcademique);
                    module4.setSpecialite(specialite);
                    module4.setSemestre(sem.findByLevel(semestre));
                    module4.setCode("4");
                    module4 = iSModule.create(module4);
                } catch (NullPointerException ee) {
                }
                try {
                    module5.setAnneeAcademique(anneeAcademique);
                    module5.setSpecialite(specialite);
                    module5.setSemestre(sem.findByLevel(semestre));
                    module5.setCode("5");
                    module5 = iSModule.create(module5);
                } catch (NullPointerException ee) {
                }
                try {
                    module6.setAnneeAcademique(anneeAcademique);
                    module6.setSpecialite(specialite);
                    module6.setSemestre(sem.findByLevel(semestre));
                    module6.setCode("6");
                    module6 = iSModule.create(module6);
                } catch (NullPointerException ee) {
                }
                try {
                    module7.setAnneeAcademique(anneeAcademique);
                    module7.setSpecialite(specialite);
                    module7.setSemestre(sem.findByLevel(semestre + 1));
                    module7.setCode("7");
                    module7 = iSModule.create(module7);
                } catch (NullPointerException ee) {
                }
                try {
                    module8.setAnneeAcademique(anneeAcademique);
                    module8.setSpecialite(specialite);
                    module8.setSemestre(sem.findByLevel(semestre + 1));
                    module8.setCode("8");
                    module8 = iSModule.create(module8);
                } catch (NullPointerException ee) {
                }
                try {
                    module9.setAnneeAcademique(anneeAcademique);
                    module9.setSpecialite(specialite);
                    module9.setSemestre(sem.findByLevel(semestre + 1));
                    module9.setCode("9");
                    module9 = iSModule.create(module9);
                } catch (NullPointerException ee) {
                }
                try {
                    module10.setAnneeAcademique(anneeAcademique);
                    module10.setSpecialite(specialite);
                    module10.setSemestre(sem.findByLevel(semestre + 1));
                    module10.setCode("10");
                    module10 = iSModule.create(module10);
                } catch (NullPointerException ee) {
                }
                try {
                    module11.setAnneeAcademique(anneeAcademique);
                    module11.setSpecialite(specialite);
                    module11.setSemestre(sem.findByLevel(semestre + 1));
                    module11.setCode("11");
                    module11 = iSModule.create(module11);
                } catch (NullPointerException ee) {
                }
                try {
                    module12.setAnneeAcademique(anneeAcademique);
                    module12.setSpecialite(specialite);
                    module12.setSemestre(sem.findByLevel(semestre + 1));
                    module12.setCode("12");
                    module12 = iSModule.create(module12);
                } catch (NullPointerException ee) {
                }

                continue;
            }

            Matiere matiere1 = null;
            Matiere matiere2 = null;
            Matiere matiere3 = null;
            Matiere matiere4 = null;
            Matiere matiere5 = null;
            Matiere matiere6 = null;
            Matiere matiere7 = null;
            Matiere matiere8 = null;
            Matiere matiere9 = null;
            Matiere matiere10 = null;
            Matiere matiere11 = null;
            Matiere matiere12 = null;
            int m = 0;

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                cell.setCellType(Cell.CELL_TYPE_STRING);
                switch (cell.getColumnIndex()) {

                    case 0:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere1 = new Matiere();
                            matiere1.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 1:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere2 = new Matiere();
                            matiere2.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 2:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere3 = new Matiere();
                            matiere3.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 3:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere4 = new Matiere();
                            matiere4.setIntitule(cell.getStringCellValue());
                        }
                        break;

                    case 4:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere5 = new Matiere();
                            matiere5.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 5:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere6 = new Matiere();
                            matiere6.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 6:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere7 = new Matiere();
                            matiere7.setIntitule(cell.getStringCellValue());
                        }
                        break;

                    case 7:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere8 = new Matiere();
                            matiere8.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 8:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere9 = new Matiere();
                            matiere9.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 9:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere10 = new Matiere();
                            matiere10.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 10:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere11 = new Matiere();
                            matiere11.setIntitule(cell.getStringCellValue());
                        }
                        break;
                    case 11:
                        if (!cell.getStringCellValue().isEmpty()) {
                            matiere12 = new Matiere();
                            matiere12.setIntitule(cell.getStringCellValue());
                        }
                        break;
                }
                m++;
            }

            String intitule;

            try {

                StringTokenizer st = new StringTokenizer(module1.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere1.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere1.setModule(module1);
                iSMatiere.create(matiere1);
                intitule = module1.getIntitule();
                intitule = intitule.equals("") ? matiere1.getIntitule() : intitule + "/" + matiere1.getIntitule();
                module1.setIntitule(intitule);
                iSModule.update(module1);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {
                StringTokenizer st = new StringTokenizer(module2.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere2.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere2.setModule(module2);
                iSMatiere.create(matiere2);
                intitule = module2.getIntitule();
                intitule = intitule.equals("") ? matiere2.getIntitule() : intitule + "/" + matiere2.getIntitule();
                module2.setIntitule(intitule);
                iSModule.update(module2);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {
                StringTokenizer st = new StringTokenizer(module3.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere3.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }


                matiere3.setModule(module3);
                iSMatiere.create(matiere3);
                intitule = module3.getIntitule();
                intitule = intitule.equals("") ? matiere3.getIntitule() : intitule + "/" + matiere3.getIntitule();
                module3.setIntitule(intitule);
                iSModule.update(module3);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module4.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere4.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere4.setModule(module4);
                iSMatiere.create(matiere4);
                intitule = module4.getIntitule();
                intitule = intitule.equals("") ? matiere4.getIntitule() : intitule + "/" + matiere4.getIntitule();
                module4.setIntitule(intitule);
                iSModule.update(module4);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {


                StringTokenizer st = new StringTokenizer(module5.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere5.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere5.setModule(module5);
                iSMatiere.create(matiere5);
                intitule = module5.getIntitule();
                intitule = intitule.equals("") ? matiere5.getIntitule() : intitule + "/" + matiere5.getIntitule();
                module5.setIntitule(intitule);
                iSModule.update(module5);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module6.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere6.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }

                matiere6.setModule(module6);
                iSMatiere.create(matiere6);
                intitule = module6.getIntitule();
                intitule = intitule.equals("") ? matiere6.getIntitule() : intitule + "/" + matiere6.getIntitule();
                module6.setIntitule(intitule);
                iSModule.update(module6);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module7.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere7.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere7.setModule(module7);
                iSMatiere.create(matiere7);
                intitule = module7.getIntitule();
                intitule = intitule.equals("") ? matiere7.getIntitule() : intitule + "/" + matiere7.getIntitule();
                module7.setIntitule(intitule);
                iSModule.update(module7);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module8.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere8.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }

                matiere8.setModule(module8);
                iSMatiere.create(matiere8);
                intitule = module8.getIntitule();
                intitule = intitule.equals("") ? matiere8.getIntitule() : intitule + "/" + matiere8.getIntitule();
                module8.setIntitule(intitule);
                iSModule.update(module8);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module9.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere9.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere9.setModule(module9);
                iSMatiere.create(matiere9);
                intitule = module9.getIntitule();
                intitule = intitule.equals("") ? matiere9.getIntitule() : intitule + "/" + matiere9.getIntitule();
                module9.setIntitule(intitule);
                iSModule.update(module9);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module10.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere10.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }



                matiere10.setModule(module10);
                iSMatiere.create(matiere10);
                intitule = module10.getIntitule();
                intitule = intitule.equals("") ? matiere10.getIntitule() : intitule + "/" + matiere10.getIntitule();
                module10.setIntitule(intitule);
                iSModule.update(module10);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module11.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere11.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere11.setModule(module11);
                iSMatiere.create(matiere11);
                intitule = module11.getIntitule();
                intitule = intitule.equals("") ? matiere11.getIntitule() : intitule + "/" + matiere11.getIntitule();
                module11.setIntitule(intitule);
                iSModule.update(module11);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }


            try {

                StringTokenizer st = new StringTokenizer(module12.getTargetCode(), "/");
                String tab[] = new String[10];
                int l = 0;
                while (st.hasMoreElements()) {
                    tab[l++] = st.nextElement().toString();
                }
                try {
                    matiere12.setCode(tab[i]);
                } catch (NullPointerException ee) {
                }
                matiere12.setModule(module12);
                iSMatiere.create(matiere12);
                intitule = module12.getIntitule();
                intitule = intitule.equals("") ? matiere12.getIntitule() : intitule + "/" + matiere12.getIntitule();
                module12.setIntitule(intitule);
                iSModule.update(module12);
            } catch (PersistenceException ex) {
            } catch (NullPointerException ex) {
            }

            i++;
        }
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des matieres de la specialite "+specialite+" pour l'année "+anneeAcademique);
        JsfUtil.addSuccessMessage(" matieres importees avec succès");
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

    public ISSemestre getSem() {
        return sem;
    }

    public void setSem(ISSemestre sem) {
        this.sem = sem;
    }
    
     public Map<String, Long> getNiveaux() throws ServiceException {
        niveaux= NivUtil.getLevel(getNiveaus());
        return niveaux;
    }
    
}
