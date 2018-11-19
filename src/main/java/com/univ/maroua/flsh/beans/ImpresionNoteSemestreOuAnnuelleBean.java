package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.UserUtil;
import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.NivUtil;
import com.univ.maroua.flsh.beans.util.PasswordUtil;
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
import com.univ.maroua.flsh.projection.Pv;
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
import com.univ.maroua.flsh.service.ISSpecialiteAnnee;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ISAnneeAcademiqueImpl;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author lappa
 */
@ManagedBean(name = "print")
@SessionScoped
@ViewScoped
public class ImpresionNoteSemestreOuAnnuelleBean implements Serializable {

    JasperPrint jasperPrint;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "print";
    @ManagedProperty(value = "#{ISNote}")
    private ISNote not;
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
    @ManagedProperty(value = "#{ISMatiere}")
    private ISMatiere mat;
    @ManagedProperty(value = "#{ISSemestre}")
    private ISSemestre sem;
    @ManagedProperty(value = "#{ISModule}")
    private ISModule mod;
    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    @ManagedProperty(value = "#{ISInscription}")
    private ISInscription insc;
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant speEt;
    @ManagedProperty(value = "#{ISEtudiantSection}")
    private ISEtudiantSection etudiantSection;
    @ManagedProperty(value = "#{ISSPecialiteAnnee}")
    private ISSpecialiteAnnee specialiteAnnee;
    @ManagedProperty(value = "#{ISUtilisateur}")
    private ISUtilisateur uti;
    private Semestre semestre;
    private Etudiant etudiant;
    private List<Etudiant> listEtudiant;
    private Note selected;
    private List<Matiere> listMat = new ArrayList<Matiere>();
    private List<Note> listNotes = new ArrayList<Note>();
    private List<Etudiant> listEtud = new ArrayList<Etudiant>();
    private List<Module> listModule = new ArrayList<Module>();
    private List<Module> listModule1 = new ArrayList<Module>();
    private List<Module> listModule2 = new ArrayList<Module>();
    private List<Matiere> listMatiere = new ArrayList<Matiere>();
    private List<Section> listSection = new ArrayList<Section>();
    private List<Niveau> listNiveau = new ArrayList<Niveau>();
    private List<Semestre> listSemestre = new ArrayList<Semestre>();
    private List<Semestre> semestres = new ArrayList<Semestre>();
    private List<AnneeAcademique> listAnneeAcademique = new ArrayList<AnneeAcademique>();
    private List<Departement> listDepartement = new ArrayList<Departement>();
    private List<Specialite> listSpecialite = new ArrayList<Specialite>();
    private List<Specialite> listSpecialite1 = new ArrayList<Specialite>();
    private List<Pv> pvs = new ArrayList<Pv>();
    private List<Pv> pvsemestre = new ArrayList<Pv>();
    private List<Pv> pvsannuel = new ArrayList<Pv>();
    //les valeurs des elements contenus dans la combobox
    private Long departementId;
    private Long sectionId;
    private Long niveauId;
    private Long specialiteId;
    private Long matiereId;
    private Long etudiantId;
    private Long anneeAcademiqueid;
    private Long niveauid;
    private Long semestreid;
    private Departement departement;
    private Section section;
    private AnneeAcademique anneeAcademique;
    private Matiere matiere;
    private Niveau niveau;
    private Note note;
    private Specialite specialite;
    private Pv pv;
    private int temps = 0;
    private Integer progress;
    private boolean sectionDisable = true;
    private boolean niveauDisable = true;
    private boolean specialiteDisable = true;
    private boolean moduleDisable = true;
    private boolean semestreDisable = true;
    private boolean barDisable = false;
    private List<Etudiant> etudiants = new ArrayList<Etudiant>();
    private List<Module> modules = new ArrayList<Module>();
    private List<Module> modules1 = new ArrayList<Module>();
    private List<Module> modules2 = new ArrayList<Module>();
    private boolean token;
    private String typeReleve;
    private StreamedContent genreateExcel;
    private String reportName = "";
    private String typeSynthese;
    private String typeAnnuel = "avec calcul"; //on suppose qu'il faudra toujours calculer les syntheses
    private String typeOperation = "avec calcul"; //on suppose qu'il faudra toujours calculer les syntheses
    private String opAnn;
    private static Map<String, Object> synthType;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private Map<String, String> types;
    private SpecialiteAnnee spa = new SpecialiteAnnee();
    private Float moyenne;
    private boolean statusPannoDeliberation = false;
    private Map<String, Long> niveaux;

    public ImpresionNoteSemestreOuAnnuelleBean() {
        token = false;
        section = new Section();
        selected = new Note();
        anneeAcademique = new AnneeAcademique();
        matiere = new Matiere();
        etudiant = new Etudiant();
        departement = new Departement();
        niveau = new Niveau();
        specialite = new Specialite();
        pv = new Pv();
        semestre = new Semestre();
        note = new Note();
    }

    public void init(String nom, List<Pv> data) throws JRException, ServiceException, DataAccessException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(data);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "note" + File.separator + nom + ".jasper", new HashMap(), beanCollectionDataSource);
    }

    public StreamedContent getGenreateExcel() throws JRException, ServiceException, DataAccessException {
        init("pvSemestre", pvs);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JRXlsExporter xlsxExporter = new JRXlsExporter();
        xlsxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        xlsxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        xlsxExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        xlsxExporter.exportReport();
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        genreateExcel = new DefaultStreamedContent(bais, "application/vnd.ms-excel", reportName + ".xls");
        return genreateExcel;
    }

    public void semPDF() throws JRException, IOException, ServiceException, DataAccessException { //remplace xlx pour imprimer en pdf

        Utilisateur util = uti.findByUsername(UserUtil.getUsername());
        if (util.getPassword().equals(PasswordUtil.getChenem()) || util.getPassword().equals(PasswordUtil.getNdo())) {
            JsfUtil.addErrorMessage("veuillez debloquer les annees d'abord!");
            return;
        }


        List<Pv> an = new LinkedList<>();
        try {
            spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
            semestre = sem.findById(semestreid);
            if (semestre.getLevel() % 2 == 0) { //nous sommes au semestre 2
                if (spa.getSemestriel2() == true) {
                    typeOperation = "sans"; //on demande de ne plus mettre a jour les syntheses
                }
            } else {
                if (spa.getSemestriel1() == true) {
                    typeOperation = "sans"; //on demande de ne plus mettre a jour les syntheses
                }
            }
        } catch (NoResultException nr) {
            SpecialiteAnnee spp = new SpecialiteAnnee();
            spp.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
            spp.setSpecialite(spe.findById(specialiteId));
            specialiteAnnee.create(spp);
        }


        an = syntheseSemestrielle(specialiteId, semestreid, anneeAcademiqueid);
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(an);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "note" + File.separator + "pvSemestre" + ".jasper", new HashMap(), beanCollectionDataSource);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
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

    public void xlsxSemestre(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        token = true;
        init("pvSemestre", pvsemestre);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment; filename=" + reportName + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        export(xlsxExporter, servletOutputStream);
    }

    public void xlsxAnnuelle(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        List<Pv> an = new LinkedList<>();
        an = syntheseAnnuelle(specialiteId, anneeAcademiqueid);
        init("pvAnnuelle", an);

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment; filename=" + reportName + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        export(xlsxExporter, servletOutputStream);
    }

    public void annuelPDF() throws JRException, IOException, ServiceException, DataAccessException { //remplace xlx pour imprimer en pdf
        try {
            try { //le but est de mettre tous les champsici a false
                SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);

                if (spa.getAnnuel() != true) {
                    //on voit si les syntheses on été calculées
                    Niveau nivv = niv.findById(niveauId);
                    if (nivv.getLevel() == 3 || nivv.getLevel() == 5) {
                        //on voit si les syntheses 1 on ete calculees
                        if (!(spa.getSemestriel1() == true && spa.getSemestriel1N() == true && spa.getSemestriel1R() == true)) {
                            JsfUtil.addErrorMessage("Calculer les synteses du premier semestre de cette spécialité !");
                            return;
                        }

                        //on voit si les syntheses 2 on ete calculees
                        if (!(spa.getSemestriel2() == true && spa.getSemestriel2N() == true && spa.getSemestriel2R() == true)) {
                            JsfUtil.addErrorMessage("Calculer les synteses du second semestre de cette spécialité !");
                            return;
                        }
                    } else {
                        if (!(spa.getSemestriel1() == true)) { //les syntheses du premier semestre on ete calculees?
                            JsfUtil.addErrorMessage("Calculer la syntèse finale du premier semestre de cette spécialité !");
                            return;
                        }
                        if (!(spa.getSemestriel2() == true)) { //les syntheses du second semestre on ete calculees?
                            JsfUtil.addErrorMessage("Calculer la syntèse finale du second semestre de cette spécialité !");
                            return;
                        }
                    }

                } else {
                    typeAnnuel = "sans"; //on demande de ne plus mettre a jour les syntheses
                }
            } catch (NoResultException nr) {
                JsfUtil.addErrorMessage("Calculer toutes les syntheses semestrielles de cette specialite !");
                return;
            }
        } catch (IllegalArgumentException ex) {
        }

        List<Pv> an = new LinkedList<>();
        an = syntheseAnnuelle(specialiteId, anneeAcademiqueid);
        init("pvAnnuelle", an);
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
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

    public ISNote getNot() {
        return not;
    }

    public void setNot(ISNote not) {
        this.not = not;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public Note getSelected() {
        return selected;
    }

    public void setSelected(Note selected) {
        this.selected = selected;
    }

    /**
     *
     * @param idSpecialite
     * @param semestreId
     * @param anneeId
     * @return
     * @throws ServiceException
     * @throws DataAccessException methode qui produit la synthese pour un
     * semestre donné
     */
    public List<Pv> syntheseSemestrielle(Long idSpecialite, Long semestreId, Long anneeId) throws ServiceException, DataAccessException {
        try {
            Date date = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.FRENCH);
            listModule = new ArrayList<Module>();
            semestre = sem.findById(semestreid);
            listEtud = getEtudiants();
            listModule = getModules();
            departement = dep.findById(departementId);
            niveau = niv.findById(niveauId);
            anneeAcademique = anAc.findById(anneeAcademiqueid);
            Long idSect = null;
            if (!anneeAcademique.getAnnee().equals("2014/2015")) {
                idSect = niveau.getSection().getId();
            }
            pvs = new LinkedList<>();
            Specialite ss = spe.findById(specialiteId);
            int hasCoposed;
            etudiants = etu.findBySpecialiteAnneeForSyntheses(specialiteId, anneeAcademiqueid);
            for (Etudiant etudiant1 : etudiants) {
                hasCoposed = 0;
                pv = new Pv();
                pv.setShortable(etudiant1.getShortable() == 0 ? 1 : 0);//on inverse  a cause de la bd qui ne met pas 1 par defaut au lieu de 0
                pv.setDate(dateFormat.format(date));
                listMatiere = new ArrayList<>();
                note = new Note();
                selected = new Note();
                pv.setSpecialite(specialiteId);
                pv.setNomDep("DEPARTEMENT:" + " " + departement.getNom());
                pv.setSection("SECTION: " + niveau.getSection().getNom());
                pv.setSectionId(niveau.getSection().getId());
                pv.setSpec("SPECIALITE: " + spe.findById(specialiteId).getNom());
                pv.setAnneeAc(anneeAcademique.getAnnee());

                pv.setMatEtudiant(etudiant1.getMatricule());
                pv.setNomEtudiant(etudiant1.getNom());
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule.get(0).getId(), 0);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());

                    if (selected.getMatiere().getModule().getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        if (typeSynthese.equals("N")) {
                            hasCoposed = 1;
                            pv.setNormale(1);
                            selected = not.calculNormale(selected);
                        } else if (typeSynthese.equals("R")) {
                            if (selected.getRattrapage() != null) {
                                hasCoposed = 1;
                                pv.setNormale(0);
                            }
                        } else {
                            pv.setFin(1);
                            hasCoposed = 1;
                        }
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                pv.setIntitule1(mod.getIntitules(listModule.get(0).getId()));
                pv.setUe1(mod.getCodes(listModule.get(0).getId()));
                try {
                    try {
                        if ((selected.getMatiere().getCode() == null)) {
                            pv.setUe1m(pv.getUe1());
                        } else {
                            pv.setUe1m(selected.getMatiere().getCode());
                        }
                    } catch (NullPointerException aa) {
                    }

                    pv.setNote1(selected.getMoy() * 5);
                    pv.setGrade1(selected.getGrade());
                    pv.setCote1(selected.getCote());

                } catch (NullPointerException e) {
                }
                pv.setCoef1(listModule.get(0).getCredit());
                pv.setSemestre(semestre.getLevel());
                pv.setNiveau(String.valueOf(niveau.getLevel()));
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule.get(1).getId(), 0);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (selected.getMatiere().getModule().getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        if (typeSynthese.equals("N")) {
                            hasCoposed = 1;
                            pv.setNormale(1);
                            selected = not.calculNormale(selected);
                        } else if (typeSynthese.equals("R")) {
                            if (selected.getRattrapage() != null) {
                                hasCoposed = 1;
                                pv.setNormale(0);
                            }
                        } else {
                            pv.setFin(1);
                            hasCoposed = 1;
                        }
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }

                pv.setIntitule2(mod.getIntitules(listModule.get(1).getId()));
                pv.setUe2(mod.getCodes(listModule.get(1).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe2m(pv.getUe2());
                    } else {
                        pv.setUe2m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }

                try {
                    pv.setNote2(selected.getMoy() * 5);
                    pv.setGrade2(selected.getGrade());
                    pv.setCote2(selected.getCote());
                } catch (NullPointerException e) {
                }
                pv.setCoef2(listModule.get(1).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule.get(2).getId(), 0);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (selected.getMatiere().getModule().getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        if (typeSynthese.equals("N")) {
                            hasCoposed = 1;
                            pv.setNormale(1);
                            selected = not.calculNormale(selected);
                        } else if (typeSynthese.equals("R")) {
                            if (selected.getRattrapage() != null) {
                                hasCoposed = 1;
                                pv.setNormale(0);
                            }
                        } else {
                            pv.setFin(1);
                            hasCoposed = 1;
                        }
                    }
                } catch (NoResultException | NullPointerException e) {
                }
                try {
                    pv.setNote3(selected.getMoy() * 5);
                    pv.setGrade3(selected.getGrade());
                    pv.setCote3(selected.getCote());
                } catch (NullPointerException e) {
                }

                pv.setIntitule3(mod.getIntitules(listModule.get(2).getId()));
                pv.setUe3(mod.getCodes(listModule.get(2).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe3m(pv.getUe3());
                    } else {
                        pv.setUe3m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef3(listModule.get(2).getCredit());

                selected = new Note();

                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule.get(3).getId(), 0);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (selected.getMatiere().getModule().getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        if (typeSynthese.equals("N")) {
                            hasCoposed = 1;
                            pv.setNormale(1);
                            selected = not.calculNormale(selected);
                        } else if (typeSynthese.equals("R")) {
                            if (selected.getRattrapage() != null) {
                                hasCoposed = 1;
                                pv.setNormale(0);
                            }
                        } else {
                            pv.setFin(1);
                            hasCoposed = 1;
                        }
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                try {
                    pv.setNote4(selected.getMoy() * 5);
                    pv.setGrade4(selected.getGrade());
                    pv.setCote4(selected.getCote());
                } catch (NullPointerException e) {
                }

                pv.setIntitule3(mod.getIntitules(listModule.get(2).getId()));
                pv.setUe3(mod.getCodes(listModule.get(2).getId()));

                pv.setIntitule4(mod.getIntitules(listModule.get(3).getId()));
                pv.setUe4(mod.getCodes(listModule.get(3).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe4m(pv.getUe4());
                    } else {
                        pv.setUe4m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef4(listModule.get(3).getCredit());

                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule.get(4).getId(), 0);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (selected.getMatiere().getModule().getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        if (typeSynthese.equals("N")) {
                            hasCoposed = 1;
                            pv.setNormale(1);
                            selected = not.calculNormale(selected);
                        } else if (typeSynthese.equals("R")) {
                            if (selected.getRattrapage() != null) {
                                hasCoposed = 1;
                                pv.setNormale(0);
                            }
                        } else {
                            pv.setFin(1);
                            hasCoposed = 1;
                        }
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                try {
                    pv.setNote5(selected.getMoy() * 5);
                    pv.setGrade5(selected.getGrade());
                    pv.setCote5(selected.getCote());
                } catch (NullPointerException e) {
                }
                pv.setIntitule5(mod.getIntitules(listModule.get(4).getId()));
                pv.setUe5(mod.getCodes(listModule.get(4).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe5m(pv.getUe5());
                    } else {
                        pv.setUe5m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef5(listModule.get(4).getCredit());

                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule.get(5).getId(), 0);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (selected.getMatiere().getModule().getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        if (typeSynthese.equals("N")) {
                            hasCoposed = 1;
                            pv.setNormale(1);
                            selected = not.calculNormale(selected);
                        } else if (typeSynthese.equals("R")) {
                            if (selected.getRattrapage() != null) {
                                hasCoposed = 1;
                                pv.setNormale(0);
                            }
                        } else {
                            pv.setFin(1);
                            hasCoposed = 1;
                        }
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                try {
                    pv.setNote6(selected.getMoy() * 5);
                    pv.setGrade6(selected.getGrade());
                    pv.setCote6(selected.getCote());
                } catch (NullPointerException e) {
                }

                pv.setIntitule6(mod.getIntitules(listModule.get(5).getId()));
                pv.setUe6(mod.getCodes(listModule.get(5).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe6m(pv.getUe6());
                    } else {
                        pv.setUe6m(selected.getMatiere().getCode());
                    }

                } catch (NullPointerException aa) {
                }



                pv.setCoef6(listModule.get(5).getCredit());




                if (typeSynthese.equals("N")) {
                    pv.setNiv("   " + fomatRomain(niveau.getLevel()) + "   -   SYNTHESE DES NOTES DE SESSION NORMALE DU SEMESTRE " + semestre.getLevel() + "    ANNEE ACADEMIQUE " + anneeAcademique.getAnnee());
                    reportName = "semestrielNormale" + semestre.getLevel() + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niv.findById(niveauId).getLevel();
                } else if (typeSynthese.equals("R")) {
                    pv.setNiv("   " + fomatRomain(niveau.getLevel()) + "   -   SYNTHESE DES NOTES DE RATRAPPAGE DU SEMESTRE " + semestre.getLevel() + "    ANNEE ACADEMIQUE " + anneeAcademique.getAnnee());
                    reportName = "semestrielRattrapage" + semestre.getLevel() + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niv.findById(niveauId).getLevel();
                } else {
                    pv.setNiv("   " + fomatRomain(niveau.getLevel()) + "  -  SYNTHESE DES NOTES FINALES DU SEMESTRE " + semestre.getLevel() + "    ANNEE ACADEMIQUE " + anneeAcademique.getAnnee());
                    reportName = "semestrielFinal" + semestre.getLevel() + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niv.findById(niveauId).getLevel();
                }

                if (hasCoposed == 1) {
                    //il n'a pas paye, donc on blanchit les notes
                    if (!insc.estEligible(semestre.getLevel(), anneeAcademiqueid, etudiant1.getMatricule(), idSect)) {
                        pv.setReglementaire(0);
                        pv.setNote1(null);
                        pv.setGrade1(null);
                        pv.setCote1(null);
                        pv.setNote2(null);
                        pv.setGrade2(null);
                        pv.setCote2(null);
                        pv.setNote3(null);
                        pv.setGrade3(null);
                        pv.setCote3(null);
                        pv.setNote4(null);
                        pv.setGrade4(null);
                        pv.setCote4(null);
                        pv.setNote5(null);
                        pv.setGrade5(null);
                        pv.setCote5(null);
                        pv.setNote6(null);
                        pv.setGrade6(null);
                        pv.setCote6(null);
                    }
                    pvs.add(pv);
                }
            }
            if (typeOperation.equals("sans")) { //on calcule et on affiche seulement
                pvs = not.traitementSemestriel(pvs, 0);
            } else {
                pvs = not.traitementSemestriel(pvs, 1); //on met ajour les credits et les passages de niveau
            }


            if (typeSynthese.equals("N")) {
                try {
                    try { //le but est de mettre tous les champs ici a false
                        SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
                        if (semestre.getLevel() % 2 == 0) { //nous sommes au semestre 2
                            if (spa.getSemestriel2N() != true) {
                                SpecialiteAnnee snew = new SpecialiteAnnee();
                                snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                                snew.setSemestriel2N(true);//pour dire qu'on vient de calculer la session normale
                                //peut etre les autres on été calculées
                                snew.setSemestriel2R(spa.getSemestriel2R());
                                snew.setSemestriel2(spa.getSemestriel2());

                                snew.setSemestriel1R(spa.getSemestriel1R());
                                snew.setSemestriel1N(spa.getSemestriel1N());
                                snew.setSemestriel1(spa.getSemestriel1());
                                snew.setMoyenne(spa.getMoyenne());
                                snew.setSpecialite(spe.findById(specialiteId));
                                specialiteAnnee.delete(spa);
                                snew = specialiteAnnee.create(snew);
                            }
                        } else {
                            if (spa.getSemestriel1N() != true) {
                                SpecialiteAnnee snew = new SpecialiteAnnee();
                                snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                                snew.setSemestriel1N(true);//pour dire qu'on vient de calculer la session normale
                                //peut etre les autres on été calculées
                                snew.setSemestriel1R(spa.getSemestriel1R());
                                snew.setSemestriel1(spa.getSemestriel1());

                                snew.setSemestriel2R(spa.getSemestriel2R());
                                snew.setSemestriel2N(spa.getSemestriel2N());
                                snew.setSemestriel2(spa.getSemestriel2());
                                snew.setMoyenne(spa.getMoyenne());
                                snew.setSpecialite(spe.findById(specialiteId));
                                specialiteAnnee.delete(spa);
                                snew = specialiteAnnee.create(snew);
                            }
                        }
                    } catch (NoResultException nr) {
                        SpecialiteAnnee spa = new SpecialiteAnnee();
                        if (semestre.getLevel() % 2 == 0) { //nous sommes au semestre 2                            
                            spa.setSemestriel2N(true);
                        } else {
                            spa.setSemestriel1N(true);
                        }
                        spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                        spa.setSpecialite(spe.findById(specialiteId));
                        spa = specialiteAnnee.create(spa);
                    }
                } catch (IllegalArgumentException ex) {
                }

            } else if (typeSynthese.equals("R")) {
                try {
                    try { //le but est de mettre tous les champs ici a false
                        SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
                        if (semestre.getLevel() % 2 == 0) { //nous sommes au semestre 2
                            if (spa.getSemestriel2R() != true) {
                                SpecialiteAnnee snew = new SpecialiteAnnee();
                                snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                                snew.setSemestriel2R(true);//pour dire qu'on vient de calculer la session normale
                                //peut etre les autres on été calculées
                                snew.setSemestriel2N(spa.getSemestriel2N());
                                snew.setSemestriel2(spa.getSemestriel2());

                                snew.setSemestriel1R(spa.getSemestriel1R());
                                snew.setSemestriel1N(spa.getSemestriel1N());
                                snew.setSemestriel1(spa.getSemestriel1());
                                snew.setMoyenne(spa.getMoyenne());
                                snew.setSpecialite(spe.findById(specialiteId));
                                specialiteAnnee.delete(spa);
                                snew = specialiteAnnee.create(snew);
                            }
                        } else {
                            if (spa.getSemestriel1R() != true) {
                                SpecialiteAnnee snew = new SpecialiteAnnee();
                                snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                                snew.setSemestriel1R(true);//pour dire qu'on vient de calculer la session normale
                                //peut etre les autres on été calculées
                                snew.setSemestriel1N(spa.getSemestriel1N());
                                snew.setSemestriel1(spa.getSemestriel1());

                                snew.setSemestriel2R(spa.getSemestriel2R());
                                snew.setSemestriel2N(spa.getSemestriel2N());
                                snew.setSemestriel2(spa.getSemestriel2());
                                snew.setMoyenne(spa.getMoyenne());
                                snew.setSpecialite(spe.findById(specialiteId));
                                specialiteAnnee.delete(spa);
                                snew = specialiteAnnee.create(snew);
                            }
                        }
                    } catch (NoResultException nr) {
                        SpecialiteAnnee spa = new SpecialiteAnnee();
                        if (semestre.getLevel() % 2 == 0) { //nous sommes au semestre 2                            
                            spa.setSemestriel2R(true);
                        } else {
                            spa.setSemestriel1R(true);
                        }
                        spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                        spa.setSpecialite(spe.findById(specialiteId));
                        spa = specialiteAnnee.create(spa);
                    }
                } catch (IllegalArgumentException ex) {
                }




            } else {

                try {
                    try { //le but est de mettre tous les champs ici a false
                        SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
                        if (semestre.getLevel() % 2 == 0) { //nous sommes au semestre 2
                            if (spa.getSemestriel2() != true) {
                                SpecialiteAnnee snew = new SpecialiteAnnee();
                                snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                                snew.setSemestriel2(true);//pour dire qu'on vient de calculer la session normale
                                //peut etre les autres on été calculées
                                snew.setSemestriel2R(spa.getSemestriel2R());
                                snew.setSemestriel2N(spa.getSemestriel2N());

                                snew.setSemestriel1R(spa.getSemestriel1R());
                                snew.setSemestriel1N(spa.getSemestriel1N());
                                snew.setSemestriel1(spa.getSemestriel1());
                                snew.setMoyenne(spa.getMoyenne());
                                snew.setSpecialite(spe.findById(specialiteId));
                                specialiteAnnee.delete(spa);
                                snew = specialiteAnnee.create(snew);
                            }
                        } else {
                            if (spa.getSemestriel1() != true) {
                                SpecialiteAnnee snew = new SpecialiteAnnee();
                                snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                                snew.setSemestriel1(true);//pour dire qu'on vient de calculer la session normale
                                //peut etre les autres on été calculées
                                snew.setSemestriel1R(spa.getSemestriel1R());
                                snew.setSemestriel1N(spa.getSemestriel1N());

                                snew.setSemestriel2R(spa.getSemestriel2R());
                                snew.setSemestriel2N(spa.getSemestriel2N());
                                snew.setSemestriel2(spa.getSemestriel2());
                                snew.setMoyenne(spa.getMoyenne());
                                snew.setSpecialite(spe.findById(specialiteId));
                                specialiteAnnee.delete(spa);
                                snew = specialiteAnnee.create(snew);
                            }
                        }
                    } catch (NoResultException nr) {
                        SpecialiteAnnee spa = new SpecialiteAnnee();
                        if (semestre.getLevel() % 2 == 0) { //nous sommes au semestre 2                            
                            spa.setSemestriel2(true);
                        } else {
                            spa.setSemestriel1(true);
                        }
                        spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                        spa.setSpecialite(spe.findById(specialiteId));
                        spa = specialiteAnnee.create(spa);
                    }
                } catch (IllegalArgumentException ex) {
                }


            }

            reportName = reportName.replaceAll(" ", "");

            if (typeSynthese.equals("N")) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des synthèses semestrielles de session normale du semestre " + semestre + " de la spécialité " + ss + " de l'année " + anneeAcademique);
            } else if (typeSynthese.equals("R")) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des synthèses semestrielles de session de ratrappage du semestre " + semestre + " de la spécialité " + ss + " de l'année " + anneeAcademique);
            } else {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des synthèses semestrielles finales du semestre " + semestre + " de la spécialité " + ss + " de l'année " + anneeAcademique);
            }
            return pvs;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public List<Pv> syntheseAnnuelle(Long idSpecialite, Long anneeId) throws ServiceException, DataAccessException {
        try {
            Date date = new Date();
            DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.FRENCH);
            listModule1 = new ArrayList<Module>();
            listModule2 = new ArrayList<Module>();
            pvs = new LinkedList<Pv>();
            listModule1 = getModules1();
            listModule2 = getModules2();
            departement = dep.findById(departementId);
            niveau = niv.findById(niveauId);
            anneeAcademique = anAc.findById(anneeAcademiqueid);
            Long idSect = null;
            if (!anneeAcademique.getAnnee().equals("2014/2015")) {
                idSect = niveau.getSection().getId();
            }
            Specialite ss = spe.findById(specialiteId);
            reportName = "annuel" + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niv.findById(niveauId).getLevel();
            reportName = reportName.replaceAll(" ", "");
            int hasCoposed;
            int hasNonNullMoyenne;
            etudiants = etu.findBySpecialiteAnneeForSyntheses(specialiteId, anneeAcademiqueid);




            for (Etudiant etudiant1 : etudiants) {
                hasCoposed = 0;
                hasNonNullMoyenne = 0;
                pv = new Pv();
                pv.setShortable(etudiant1.getShortable() == 0 ? 1 : 0);
                listMatiere = new ArrayList<Matiere>();
                note = new Note();
                selected = new Note();
                listModule1 = getModules1();
                listModule2 = getModules2();
                pv.setDate(dateFormat.format(date));
                pv.setNomDep("DEPARTEMENT: " + " " + departement.getNom());
                pv.setAnneeAc("ANNEE ACADEMIQUE " + anneeAcademique.getAnnee());
                pv.setSection("SECTION: " + niveau.getSection().getNom());
                pv.setSpec("SPECIALITE: " + spe.findById(specialiteId).getNom());
                pv.setNiveau(fomatRomain(niveau.getLevel()) + "       -      SYNTHESE ANNUELLE DES NOTES      -            ANNEE ACADEMIQUE " + anneeAcademique.getAnnee());
                pv.setNiv(niveau.getLevel() + "");
                pv.setMatEtudiant(etudiant1.getMatricule());
                pv.setNomEtudiant(etudiant1.getNom());
                pv.setIdAnnnee(anneeAcademiqueid);
                pv.setIdSpecialite(specialiteId);
                pv.setNumSemF("SEMESTRE " + String.valueOf(listModule1.get(0).getSemestre().getLevel()));
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(0).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                try {
                    pv.setNote1(selected.getMoy() * 5);
                    pv.setGrade1(selected.getGrade());
                    pv.setCote1(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule1(mod.getIntitules(listModule1.get(0).getId()));
                pv.setUe1(mod.getCodes(listModule1.get(0).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe1m(pv.getUe1());
                    } else {
                        pv.setUe1m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef1(listModule1.get(0).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(1).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                try {
                    pv.setNote2(selected.getMoy() * 5);
                    pv.setGrade2(selected.getGrade());
                    pv.setCote2(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule2(mod.getIntitules(listModule1.get(1).getId()));
                pv.setUe2(mod.getCodes(listModule1.get(1).getId()));

                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe2m(pv.getUe2());
                    } else {
                        pv.setUe2m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef2(listModule1.get(1).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(2).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                try {
                    pv.setNote3(selected.getMoy() * 5);
                    pv.setGrade3(selected.getGrade());
                    pv.setCote3(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule3(mod.getIntitules(listModule1.get(2).getId()));
                pv.setUe3(mod.getCodes(listModule1.get(2).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe3m(pv.getUe3());
                    } else {
                        pv.setUe3m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef3(listModule1.get(2).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(3).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }
                try {

                    pv.setNote4(selected.getMoy() * 5);
                    pv.setGrade4(selected.getGrade());
                    pv.setCote4(selected.getCote());
                    hasNonNullMoyenne = 1;


                } catch (NullPointerException e) {
                }

                pv.setIntitule4(mod.getIntitules(listModule1.get(3).getId()));
                pv.setUe4(mod.getCodes(listModule1.get(3).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe4m(pv.getUe4());
                    } else {
                        pv.setUe4m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef4(listModule1.get(3).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(4).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }

                try {
                    pv.setNote5(selected.getMoy() * 5);
                    pv.setGrade5(selected.getGrade());
                    pv.setCote5(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }


                pv.setIntitule5(mod.getIntitules(listModule1.get(4).getId()));
                pv.setUe5(mod.getCodes(listModule1.get(4).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe5m(pv.getUe5());
                    } else {
                        pv.setUe5m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }




                pv.setCoef5(listModule1.get(4).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule1.get(5).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }

                try {
                    pv.setNote6(selected.getMoy() * 5);
                    pv.setGrade6(selected.getGrade());
                    pv.setCote6(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }


                pv.setIntitule6(mod.getIntitules(listModule1.get(5).getId()));
                pv.setUe6(mod.getCodes(listModule1.get(5).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe6m(pv.getUe6());
                    } else {
                        pv.setUe6m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef6(listModule1.get(5).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(0).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }


                pv.setNumSemS("SEMESTRE " + String.valueOf(listModule2.get(0).getSemestre().getLevel()));


                try {
                    pv.setNote7(selected.getMoy() * 5);
                    pv.setGrade7(selected.getGrade());
                    pv.setCote7(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule7(mod.getIntitules(listModule2.get(0).getId()));
                pv.setUe7(mod.getCodes(listModule2.get(0).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe7m(pv.getUe7());
                    } else {
                        pv.setUe7m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }




                pv.setCoef7(listModule2.get(0).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(1).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException | NullPointerException e) {
                }

                try {
                    pv.setNote8(selected.getMoy() * 5);
                    pv.setGrade8(selected.getGrade());
                    pv.setCote8(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule8(mod.getIntitules(listModule2.get(1).getId()));
                pv.setUe8(mod.getCodes(listModule2.get(1).getId()));

                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe8m(pv.getUe8());
                    } else {
                        pv.setUe8m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef8(listModule2.get(1).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(2).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }

                try {
                    pv.setNote9(selected.getMoy() * 5);
                    pv.setGrade9(selected.getGrade());
                    pv.setCote9(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule9(mod.getIntitules(listModule2.get(2).getId()));
                pv.setUe9(mod.getCodes(listModule2.get(2).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe9m(pv.getUe9());
                    } else {
                        pv.setUe9m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef9(listModule2.get(2).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(3).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }

                try {
                    pv.setNote10(selected.getMoy() * 5);
                    pv.setGrade10(selected.getGrade());
                    pv.setCote10(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule10(mod.getIntitules(listModule2.get(3).getId()));
                pv.setUe10(mod.getCodes(listModule2.get(3).getId()));
                try {

                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe10m(pv.getUe10());
                    } else {
                        pv.setUe10m(selected.getMatiere().getCode());
                    }

                } catch (NullPointerException aa) {
                }



                pv.setCoef10(listModule2.get(3).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(4).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }

                try {
                    pv.setNote11(selected.getMoy() * 5);
                    pv.setGrade11(selected.getGrade());
                    pv.setCote11(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }


                pv.setIntitule11(mod.getIntitules(listModule2.get(4).getId()));
                pv.setUe11(mod.getCodes(listModule2.get(4).getId()));
                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe11m(pv.getUe11());
                    } else {
                        pv.setUe11m(selected.getMatiere().getCode());
                    }
                } catch (NullPointerException aa) {
                }



                pv.setCoef11(listModule2.get(4).getCredit());
                selected = new Note();
                try {
                    Module curentModule = mod.findLastUE(etudiant1.getId(), listModule2.get(5).getId(), 1);
                    selected = not.findByUEEtudiant(etudiant1.getId(), curentModule.getId());
                    if (curentModule.getAnneeAcademique().getAnnee().equals(anneeAcademique.getAnnee())) {
                        hasCoposed = 1;
                    }
                } catch (NoResultException e) {
                } catch (NullPointerException e) {
                }

                try {
                    pv.setNote12(selected.getMoy() * 5);
                    pv.setGrade12(selected.getGrade());
                    pv.setCote12(selected.getCote());
                    hasNonNullMoyenne = 1;

                } catch (NullPointerException e) {
                }

                pv.setIntitule12(mod.getIntitules(listModule2.get(5).getId()));
                pv.setUe12(mod.getCodes(listModule2.get(5).getId()));

                try {
                    if ((selected.getMatiere().getCode() == null)) {
                        pv.setUe12m(pv.getUe12());
                    } else {
                        pv.setUe12m(selected.getMatiere().getCode());
                    }

                } catch (NullPointerException aa) {
                }



                pv.setCoef12(listModule2.get(5).getCredit());

                if (hasCoposed == 1 && hasNonNullMoyenne == 1) {
                    if (!insc.estEligible(listModule2.get(0).getSemestre().getLevel(), anneeAcademiqueid, etudiant1.getMatricule(), idSect)) {
                        //not.inValiderNoteEtudiant(etudiant1.getMatricule(), anneeAcademiqueid);
                        pv = new Pv(); //on vide les notes
                        pv.setReglementaire(0);
                        pv.setShortable(etudiant1.getShortable() == 0 ? 1 : 0);
                        pv.setDate(dateFormat.format(date));
                        pv.setNomDep("DEPARTEMENT: " + " " + departement.getNom());
                        pv.setAnneeAc("ANNEE ACADEMIQUE " + anneeAcademique.getAnnee());
                        pv.setSection("SECTION: " + niveau.getSection().getNom());
                        pv.setSpec("SPECIALITE: " + spe.findById(specialiteId).getNom());
                        pv.setNiveau(fomatRomain(niveau.getLevel()) + "       -      SYNTHESE ANNUELLE DES NOTES      -            ANNEE ACADEMIQUE " + anneeAcademique.getAnnee());
                        pv.setNiv(niveau.getLevel() + "");
                        pv.setMatEtudiant(etudiant1.getMatricule());
                        pv.setNomEtudiant(etudiant1.getNom());
                        pv.setNumSemF("SEMESTRE " + String.valueOf(listModule1.get(0).getSemestre().getLevel()));
                        pv.setNumSemS("SEMESTRE " + String.valueOf(listModule2.get(0).getSemestre().getLevel()));
                        pv.setMatEtudiant(pv.getMatEtudiant());
                        pv.setIdAnnnee(anneeAcademiqueid);
                        pv.setIdSpecialite(specialiteId);


                        pv.setIntitule1(mod.getIntitules(listModule1.get(0).getId()));
                        pv.setUe1(mod.getCodes(listModule1.get(0).getId()));
                        pv.setCoef1(listModule1.get(0).getCredit());
                        pv.setIntitule2(mod.getIntitules(listModule1.get(1).getId()));
                        pv.setUe2(mod.getCodes(listModule1.get(1).getId()));
                        pv.setCoef2(listModule1.get(1).getCredit());
                        pv.setIntitule3(mod.getIntitules(listModule1.get(2).getId()));
                        pv.setUe3(mod.getCodes(listModule1.get(2).getId()));
                        pv.setCoef3(listModule1.get(2).getCredit());
                        pv.setIntitule4(mod.getIntitules(listModule1.get(3).getId()));
                        pv.setUe4(mod.getCodes(listModule1.get(3).getId()));
                        pv.setCoef4(listModule1.get(3).getCredit());
                        pv.setIntitule5(mod.getIntitules(listModule1.get(4).getId()));
                        pv.setUe5(mod.getCodes(listModule1.get(4).getId()));
                        pv.setCoef5(listModule1.get(4).getCredit());
                        pv.setIntitule6(mod.getIntitules(listModule1.get(5).getId()));
                        pv.setUe6(mod.getCodes(listModule1.get(5).getId()));
                        pv.setCoef6(listModule1.get(5).getCredit());
                        pv.setIntitule7(mod.getIntitules(listModule2.get(0).getId()));
                        pv.setUe7(mod.getCodes(listModule2.get(0).getId()));
                        pv.setCoef7(listModule2.get(0).getCredit());
                        pv.setIntitule8(mod.getIntitules(listModule2.get(1).getId()));
                        pv.setUe8(mod.getCodes(listModule2.get(1).getId()));
                        pv.setCoef8(listModule2.get(1).getCredit());
                        pv.setIntitule9(mod.getIntitules(listModule2.get(2).getId()));
                        pv.setUe9(mod.getCodes(listModule2.get(2).getId()));
                        pv.setCoef9(listModule2.get(2).getCredit());
                        pv.setIntitule10(mod.getIntitules(listModule2.get(3).getId()));
                        pv.setUe10(mod.getCodes(listModule2.get(3).getId()));
                        pv.setCoef10(listModule2.get(3).getCredit());
                        pv.setIntitule11(mod.getIntitules(listModule2.get(4).getId()));
                        pv.setUe11(mod.getCodes(listModule2.get(4).getId()));
                        pv.setCoef11(listModule2.get(4).getCredit());
                        pv.setIntitule12(mod.getIntitules(listModule2.get(5).getId()));
                        pv.setUe12(mod.getCodes(listModule2.get(5).getId()));
                        pv.setCoef12(listModule2.get(5).getCredit());
                    }
                    pvs.add(pv);
                }
            }
            if (typeAnnuel.equals("sans")) { //on calcule et on affiche seulement

                pvs = not.traitementAnnuel(pvs, 0);
            } else {
                pvs = not.traitementAnnuel(pvs, 1); //on met ajour les credits et les passages de niveau
            }


            try {
                SpecialiteAnnee spaa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
                SpecialiteAnnee snew = new SpecialiteAnnee();
                snew.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                snew.setAnnuel(true);
                snew.setSemestriel1(spaa.getSemestriel1());
                snew.setSemestriel1N(spaa.getSemestriel1N());
                snew.setSemestriel1R(spaa.getSemestriel1R());

                snew.setSemestriel2(spaa.getSemestriel2());
                snew.setSemestriel2N(spaa.getSemestriel2N());
                snew.setSemestriel2R(spaa.getSemestriel2R());

                snew.setMoyenne(spaa.getMoyenne());
                snew.setSpecialite(spe.findById(specialiteId));
                specialiteAnnee.delete(spaa);
                specialiteAnnee.create(snew);
            } catch (Exception e) {
            }
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des synthèses annuelles de la spécialité " + ss + " de l'année " + anneeAcademique);
            return pvs;
        } catch (IllegalArgumentException e) {
        }
        return null;
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

    public List<Etudiant> getListEtudiant() throws ServiceException {
        listEtudiant = etu.findAll();
        return listEtudiant;
    }

    public List<Specialite> getListSpecialite() throws ServiceException {
        listSpecialite = spe.findByNiveau(niveauId);
        return listSpecialite;
    }

    public List<Specialite> getListSpecialite1() throws ServiceException {
        listSpecialite1 = spe.findByNiveau(niveauId);
        if (listSpecialite1.size() == 1) {
            return listSpecialite1;
        } else { //on enleve le slash
            List<Specialite> ne = new LinkedList<>();
            for (Specialite specialite1 : listSpecialite1) {
                if (!specialite1.getNom().equals("/")) {
                    ne.add(specialite1);
                }
            }
            listSpecialite1 = ne;
        }
        return listSpecialite1;
    }

    public void setListSpecialite(List<Specialite> listSpecialite) {
        this.listSpecialite = listSpecialite;
    }

    public void setListEtudiant(List<Etudiant> listEtudiant) {
        this.listEtudiant = listEtudiant;
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

    public ISMatiere getMat() {
        return mat;
    }

    public void setMat(ISMatiere mat) {
        this.mat = mat;
    }

    public List<Note> getListNotes() throws ServiceException {
        listNotes = not.filters(matiereId, anneeAcademiqueid);
        return listNotes;
    }

    public void setListNotes(List<Note> listNotes) {
        this.listNotes = listNotes;
    }

    public List<Matiere> getListMat() {
        return listMat;
    }

    public void setListMat(List<Matiere> listMat) {
        this.listMat = listMat;
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

    public Long getEtudiantId() {
        return etudiantId;
    }

    public void setEtudiantId(Long etudiantId) {
        this.etudiantId = etudiantId;
    }

    public Long getAnneeAcademiqueid() {
        return anneeAcademiqueid;
    }

    public void setAnneeAcademiqueid(Long anneeAcademiqueid) {
        this.anneeAcademiqueid = anneeAcademiqueid;
    }

    public Long getNiveauid() {
        return niveauid;
    }

    public void setNiveauid(Long niveauid) {
        this.niveauid = niveauid;
    }

    public ISSemestre getSem() {
        return sem;
    }

    public void setSem(ISSemestre sem) {
        this.sem = sem;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public Long getSemestreid() {
        return semestreid;
    }

    public void setSemestreid(Long semestreid) {
        this.semestreid = semestreid;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
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

    public Pv getPvSemestre() {
        return pv;
    }

    public void setPvSemestre(Pv pv) {
        this.pv = pv;
    }

    public List<Etudiant> getListEtud() throws ServiceException {
        listEtudiant = etu.findAll();
        return listEtud;
    }

    public void setListEtud(List<Etudiant> listEtud) {
        this.listEtud = listEtud;
    }

    public List<Matiere> getListMatiere() {
        return listMatiere;
    }

    public void setListMatiere(List<Matiere> listMatiere) {
        this.listMatiere = listMatiere;
    }

    public List<Pv> getPvSemestres() {
        return pvs;
    }

    public void setPvSemestres(List<Pv> pvs) {
        this.pvs = pvs;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public ISModule getMod() {
        return mod;
    }

    public void setMod(ISModule mod) {
        this.mod = mod;
    }

    public void processDepartement() throws ServiceException {
        sectionDisable = false;
        niveauDisable = true;
        specialiteDisable = true;
        semestreDisable = true;
        getListSection();
    }

    public void processSection() throws ServiceException {
        niveauDisable = false;
        specialiteDisable = true;
        semestreDisable = true;
        getListNiveau();
    }

    public void processNiveau() throws ServiceException {
        specialiteDisable = false;
        semestreDisable = true;
        getListSpecialite();
        getTypes();
    }

    public void processNiveau1() throws ServiceException {
        specialiteDisable = false;
        semestreDisable = true;
        getListSpecialite1();
    }

    public void processSpecialite() throws ServiceException {
        semestreDisable = false;
        getListSpecialite();
    }

    public void processSpecialite1() throws ServiceException {
        semestreDisable = false;
        getListSpecialite1();
    }

    public void processSemestre() throws ServiceException {
        getListAnneeAcademique();
    }

    public List<Semestre> getListSemestre() throws ServiceException {
        listSemestre = sem.findBySpecialite(specialiteId);
        return listSemestre;
    }

    public void setListSemestre(List<Semestre> listSemestre) {
        this.listSemestre = listSemestre;
    }

    public List<Niveau> getListNiveau() throws ServiceException {
        listNiveau = niv.findBySection(sectionId);
        return listNiveau;
    }

    public void setListNiveau(List<Niveau> listNiveau) {
        this.listNiveau = listNiveau;
    }

    public List<AnneeAcademique> getListAnneeAcademique() throws ServiceException {
        listAnneeAcademique = new LinkedList<>();
        Utilisateur utilisateur = uti.findByUsername(UserUtil.getUsername());
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
        listAnneeAcademique = anAc.findAll();
        return listAnneeAcademique;
    }

    public void setListAnneeAcademique(List<AnneeAcademique> listAnneeAcademique) {
        this.listAnneeAcademique = listAnneeAcademique;
    }

    public List<Section> getListSection() throws ServiceException {
        listSection = sec.findByDepartement(departementId);
        return listSection;
    }

    public void setListSection(List<Section> listSection) {
        this.listSection = listSection;
    }

    public List<Departement> getListDepartement() throws ServiceException {
        listDepartement = dep.findAll();
        return listDepartement;
    }

    public void setListDepartement(List<Departement> listDepartement) {
        this.listDepartement = listDepartement;
    }

    public List<Module> getListModule() throws ServiceException {
        listModule = mod.findAll();
        return listModule;
    }

    public void setListModule(List<Module> listModule) {
        this.listModule = listModule;
    }

    public boolean isSectionDisable() {
        return sectionDisable;
    }

    public void setSectionDisable(boolean sectionDisable) {
        this.sectionDisable = sectionDisable;
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

    public boolean isSemestreDisable() {
        return semestreDisable;
    }

    public void setSemestreDisable(boolean semestreDisable) {
        this.semestreDisable = semestreDisable;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public List<Module> getListModule1() {
        return listModule1;
    }

    public void setListModule1(List<Module> listModule1) {
        this.listModule1 = listModule1;
    }

    public List<Module> getListModule2() {
        return listModule2;
    }

    public void setListModule2(List<Module> listModule2) {
        this.listModule2 = listModule2;
    }

    public Pv getPv() {
        return pv;
    }

    public void setPv(Pv pv) {
        this.pv = pv;
    }

    public List<Etudiant> getEtudiants() {
        etudiants = new LinkedList<Etudiant>();
        try {
            etudiants = etu.findBySpecialiteAnneeForSyntheses(specialiteId, anneeAcademiqueid);
        } catch (ServiceException ex) {
        } catch (IllegalArgumentException ex) {
        }
        return etudiants;
    }

    public void setEtudiants(List<Etudiant> etudiants) {
        this.etudiants = etudiants;
    }

    public List<Module> getModules() {
        try {
            modules = mod.findBySemestreAnneeAcSpecialite(anneeAcademiqueid, semestreid, specialiteId);
            return modules;
        } catch (ServiceException ex) {
        } catch (IllegalArgumentException ex) {
        }
        return null;
    }

    public void filterNoteSem() throws ServiceException {
        token = true;
        etudiants = etu.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
    }

    public void filterNoteAn() throws ServiceException {
        etudiants = etu.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }

    public String getN(Long idEtudiant, Long idUE, int token) {
        String result = null;
        try {

            Note note = not.findByUEEtudiant(idEtudiant, idUE);

            switch (token) {
                case 1:
                    result = String.valueOf(note.getMoy() * 5);
                    break;
                case 2:
                    result = note.getGrade();
                    break;
                case 3:
                    result = String.valueOf(note.getCote());
                    break;
            }
            if (result.equals("null")) {
                result = null;
            }
            return result;
        } catch (NullPointerException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ServiceException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public List<Module> getModules1() throws ServiceException {
        try {
            Long semid = getSemestres().get(0).getId();
            modules1 = mod.findBySemestreAnneeAcSpecialite(anneeAcademiqueid, semid, specialiteId);
            return modules1;
        } catch (ServiceException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IndexOutOfBoundsException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }


        return null;
    }

    public List<Module> getModules2() {
        try {

            Long semid = getSemestres().get(1).getId();
            modules2 = mod.findBySemestreAnneeAcSpecialite(anneeAcademiqueid, semid, specialiteId);
            return modules2;
        } catch (ServiceException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IndexOutOfBoundsException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

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

    public List<Semestre> getSemestres() throws ServiceException {
        semestres = sem.findBySpecialite(specialiteId);
        Collections.sort(semestres);
        return semestres;
    }

    public List<Pv> getPvsemestre() throws JRException {

        try {
            pvsemestre = new ArrayList<Pv>();

            pvsemestre = syntheseSemestrielle(specialiteId, semestreid, anneeAcademiqueid);
        } catch (ServiceException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }

        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pvsemestre", null);
        //FacesContext.getCurrentInstance().getApplication().createValueBinding( "#{print}").setValue(FacesContext.getCurrentInstance(), null );
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().rremove("pvsemestre");
        return pvsemestre;
    }

    public List<Pv> getPvsannuel() {
        pvsannuel = new LinkedList<Pv>();
        try {
            pvsannuel = syntheseAnnuelle(specialiteId, anneeAcademiqueid);
        } catch (ServiceException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataAccessException ex) {
            Logger.getLogger(ImpresionNoteSemestreOuAnnuelleBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pvsannuel;
    }

    public void removeSessionAttributeAfterRender(PhaseEvent event) {
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().remove("pvsemestre");
        }
    }

    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();
    }

    public boolean isToken() {
        return token;
    }

    public void setToken(boolean token) {
        this.token = token;
    }

    public String getTypeReleve() {
        return typeReleve;
    }

    public void setTypeReleve(String typeReleve) {
        this.typeReleve = typeReleve;
    }

    public String getTypeSynthese() {
        return typeSynthese;
    }

    public void setTypeSynthese(String typeSynthese) {
        this.typeSynthese = typeSynthese;
    }

    private String formatIntitule(String s, int lenght) {
        int longueur = s.length();
        if (longueur <= lenght) {
            return s;
        } else {
            String result = "";
            int start = 0;
            int end = lenght;
            do {
                result += s.substring(start, end) + "<br/>";
                start += lenght;
                end = (end + lenght) <= longueur ? end + lenght : longueur;
            } while (end != longueur);
            return result;
        }
    }

    public ISInscription getInsc() {
        return insc;
    }

    public void setInsc(ISInscription insc) {
        this.insc = insc;
    }

    public String getOpAnn() {
        return opAnn;
    }

    public void setOpAnn(String opAnn) {
        this.opAnn = opAnn;
    }

    public void doPassage(ActionEvent actionEven) throws ServiceException {
        try {
            AnneeAcademique ac = anAc.next(anAc.findById(anneeAcademiqueid).getAnnee());
        } catch (IndexOutOfBoundsException in) {
            JsfUtil.addErrorMessage("veuillez configurer l'année suivante!");
            return;
        }
        List<SpecialiteEtudiant> specialiteEtudiants = speEt.findByDepartementAnnee(departementId, anneeAcademiqueid);
        for (SpecialiteEtudiant specialiteEtudiant : specialiteEtudiants) {
            //On elargue le tronc commun
            Specialite specia = specialiteEtudiant.getSpecialite();
            Long niveauId = specialiteEtudiant.getSpecialite().getNiveau().getId();
            List<Specialite> specialites = spe.findByNiveau(niveauId);
            if (specialites.size() > 1) {
                if (specia.getNom().equals("/")) {
                    continue;
                }
            }
            try {
                try { //le but est de mettre tous les champsici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteEtudiant.getSpecialite().getId(), anneeAcademiqueid);
                    if (spa.getAnnuel() == false) {
                        JsfUtil.addErrorMessage("calculez les syntheses annulles du niveau " + specialiteEtudiant.getSpecialite().getNiveau().getLevel() + " de la specialité " + specialiteEtudiant.getSpecialite().getNom());
                        return;
                    }
                } catch (NoResultException nr) {
                    JsfUtil.addErrorMessage("calculez les syntheses annulles du niveau " + specialiteEtudiant.getSpecialite().getNiveau().getLevel() + " de la specialité " + specialiteEtudiant.getSpecialite().getNom());
                    return;
                }
            } catch (IllegalArgumentException ex) {
            }


            String decision = specialiteEtudiant.getDecision();
            Specialite specialiteOld = specialiteEtudiant.getSpecialite();
            Specialite specialite = specialiteEtudiant.getSpecialite();
            Etudiant etudiant = specialiteEtudiant.getEtudiant();
            int level = specialite.getNiveau().getLevel();
            String annee = specialiteEtudiant.getAnneeAcademique().getAnnee();
            // System.out.println(etudiant.getNom()+" "+etudiant.getMatricule()+" "+decision);
            boolean finishCycle = false;
            if (level == 3 || level == 5) //il est en fin de cycle et on de fait rien
            {
                try {
                    if (decision.toLowerCase().startsWith("a")) { //on est a la fin du cycle
                        finishCycle = true;
                    } else if (decision.toLowerCase().startsWith("r")) {
                        specialite = spe.nextSpecialite(specialite.getId()); // on bascule sur le slash
                    } else { //on vide toutes ses notes
                        // not.blanchirEtudiantAnnee(specialiteEtudiant.getEtudiant().getMatricule(), specialiteEtudiant.getAnneeAcademique().getId(), specialite.getNiveau().getSection().getId());
                        continue;
                    }
                } catch (NullPointerException ex) { //on vide toutes les notes
                    not.blanchirEtudiantAnnee(specialiteEtudiant.getEtudiant().getMatricule(), specialiteEtudiant.getAnneeAcademique().getId(), specialite.getNiveau().getSection().getId());
                    continue;
                }
            }
            if (!finishCycle) {
                try {
                    if (decision.toLowerCase().startsWith("a")) { //on l'inscrit a la specialite suivante si admis
                        //on suppirme son nom en bas si c'est deja inscrit
                        AnneeAcademique nextAnnee = anAc.next(annee);
                        try {
                            SpecialiteEtudiant so = speEt.findByEtudiantSpecialiteAnnee(etudiant.getId(), nextAnnee.getId(), specialiteOld.getId());
                            speEt.delete(so);
                        } catch (NoResultException ex) {
                        }
                        specialite = spe.nextSpecialite(specialite.getId());
                    } else if (!decision.toLowerCase().startsWith("r")) { //on le falla au niveau sup et on le supprime
                        continue;
                        /* try {
                         Specialite specialit = spe.nextSpecialite(specialite.getId());
                         SpecialiteEtudiant s = speEt.findByEtudiantSpecialiteAnnee(etudiant.getId(), nextAnnee.getId(), specialit.getId());
                         speEt.delete(s);
                         } catch (NoResultException e) {
                         } */
                    }
                } catch (NullPointerException ex) {
                    continue;
                    /* AnneeAcademique nextAnnee = anAc.next(annee);
                     try {
                     Specialite specialit = spe.nextSpecialite(specialite.getId());
                     SpecialiteEtudiant s = speEt.findByEtudiantSpecialiteAnnee(etudiant.getId(), nextAnnee.getId(), specialit.getId());
                     speEt.delete(s);
                     } catch (NoResultException e) {
                     } */
                }
                AnneeAcademique nextAnnee = anAc.next(annee);
                try {
                    SpecialiteEtudiant s = speEt.findByEtudiantSpecialiteAnnee(etudiant.getId(), nextAnnee.getId(), specialite.getId());
                } catch (NoResultException e) {
                    SpecialiteEtudiant ss = new SpecialiteEtudiant();
                    ss.setAnneeAcademique(nextAnnee);
                    ss.setEtudiant(etudiant);
                    ss.setSpecialite(specialite);
                    speEt.create(ss);
                }
            }
        }
        AnneeAcademique aa = anAc.findById(anneeAcademiqueid);
        Departement depp = dep.findById(departementId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages pour le département " + depp + " et pour l'année " + aa);
        JsfUtil.addSuccessMessage(" Operation faite avec succes ");
    }

    public void doChevauche(ActionEvent actionEven) throws ServiceException {
        List<SpecialiteEtudiant> specialiteEtudiants = speEt.findByDepartementAnnee(departementId, anneeAcademiqueid);
        for (SpecialiteEtudiant specialiteEtudiant : specialiteEtudiants) {
            int level = specialiteEtudiant.getSpecialite().getNiveau().getLevel();
            if (level == 3 || level == 5 || level == 4) {
                continue;
            }
            try {
                try { //le but est de mettre tous les champsici a false
                    SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteEtudiant.getSpecialite().getId(), anneeAcademiqueid);
                    if (spa.getSemestriel1() == false) {
                        JsfUtil.addErrorMessage("calculez la synthese du premier semestre du niveau " + specialiteEtudiant.getSpecialite().getNiveau().getLevel() + " de la specialité " + specialiteEtudiant.getSpecialite().getNom());
                        return;
                    }
                } catch (NoResultException nr) {
                    JsfUtil.addErrorMessage("calculez la synthese du premier semestre du niveau " + specialiteEtudiant.getSpecialite().getNiveau().getLevel() + " de la specialité " + specialiteEtudiant.getSpecialite().getNom());
                    return;
                }
            } catch (IllegalArgumentException ex) {
            }
            Etudiant etudiant = specialiteEtudiant.getEtudiant();
            Specialite specialite = specialiteEtudiant.getSpecialite();
            Long niveauId = specialiteEtudiant.getSpecialite().getNiveau().getId();
            List<Specialite> specialites = spe.findByNiveau(niveauId);
            if (specialites.size() > 1) {
                if (specialite.getNom().equals("/")) {
                    continue;
                }
            }
            level = specialite.getNiveau().getLevel();
            AnneeAcademique curentYear = specialiteEtudiant.getAnneeAcademique();
            // System.out.println(etudiant.getNom()+" "+etudiant.getMatricule()+" "+decision);
            if (level == 3 || level == 5) //il est en fin de cycle et on de fait rien
            {
                continue;
            } else {
                try {
                    if (specialiteEtudiant.getChevauche() == 1) { //s'il chevauche
                        specialite = spe.nextSpecialite(specialite.getId()); // il passe au niveau superieur
                        try {
                            SpecialiteEtudiant s = speEt.findByEtudiantSpecialiteAnnee(etudiant.getId(), curentYear.getId(), specialite.getId());
                            /* s.setAnneeAcademique(curentYear);
                             s.setEtudiant(etudiant);
                             s.setSpecialite(specialite);
                             speEt.update(s); */

                        } catch (NoResultException e) {
                            SpecialiteEtudiant ss = new SpecialiteEtudiant();
                            ss.setAnneeAcademique(curentYear);
                            ss.setEtudiant(etudiant);
                            ss.setSpecialite(specialite);
                            speEt.create(ss);
                        }
                    } /* else { //on l retire au niveau sup 
                     specialite = spe.nextSpecialite(specialite.getId());
                     try {
                     SpecialiteEtudiant s = speEt.findByEtudiantSpecialiteAnnee(etudiant.getId(), curentYear.getId(), specialite.getId());
                     speEt.delete(s);
                     } catch (NoResultException e) {
                     }
                     } */
                } catch (NullPointerException ex) {
                    continue;
                }
            }
        }
        AnneeAcademique aa = anAc.findById(anneeAcademiqueid);
        Departement depp = dep.findById(departementId);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des chevauchements pour le département " + depp + " et pour l'année " + aa);
        JsfUtil.addSuccessMessage(" Operation faite avec succes ");
    }

    public ISSpecialiteEtudiant getSpeEt() {
        return speEt;
    }

    public void setSpeEt(ISSpecialiteEtudiant speEt) {
        this.speEt = speEt;
    }

    public ISEtudiantSection getEtudiantSection() {
        return etudiantSection;
    }

    public void setEtudiantSection(ISEtudiantSection etudiantSection) {
        this.etudiantSection = etudiantSection;
    }

    public Map<String, Object> getSynthType() {
        synthType = new LinkedHashMap<>();
        synthType.put("sans mise a jour", "sans");
        synthType.put("avec mise a jour", "avec");
        return synthType;
    }

    public String fomatRomain(int level) {
        if (level == 1) {
            return "LICENCE I";
        }
        if (level == 2) {
            return "LICENCE II";
        }
        if (level == 3) {
            return "LICENCE III";
        }
        if (level == 4) {
            return "MASTER I";
        }
        if (level == 5) {
            return "MASTER II";
        }
        return null;
    }

    public Map<String, String> getTypes() throws ServiceException {
        try {
            Niveau niveau = niv.findById(niveauId);
            if (niveau.getLevel() == 3 || niveau.getLevel() == 5) {
                types = new TreeMap<>();
                types.put("1-Normale", "N");
                types.put("2-Ratrappage", "R");
                types.put("3-Final", "F");
            } else {
                types = new HashMap<>();
                types.put("Final", "F");
            }
            return types;
        } catch (IllegalArgumentException ex) {
        }
        return new HashMap<String, String>();
    }

    public void setTypes(Map<String, String> types) {
        this.types = types;
    }

    public void doMoyenne(ActionEvent actionEven) throws ServiceException {
        SpecialiteAnnee ss = (SpecialiteAnnee) spa.clone();
        ss.setMoyenne(moyenne);
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "changement de moyenne de la specalite " + ss.getSpecialite() + " et pour l'année " + ss.getAnneeAcademique());

        ss.setId(null);
        specialiteAnnee.delete(spa);
        ss = specialiteAnnee.create(ss);
        spa = ss;

        //specialiteAnnee.update(spa);
        JsfUtil.addSuccessMessage("La moyenne a été modifiée");


    }

    public void doRecalcul(ActionEvent actionEven) throws ServiceException {
        if ((specialiteId == null) || (anneeAcademiqueid == null)) {
            JsfUtil.addErrorMessage("selectionnez tous les champs!");
            return;
        }
        try {
            SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
            SpecialiteAnnee ss = (SpecialiteAnnee) spa.clone();
            ss.setMoyenne(spa.getMoyenne());
            ss.setId(null);
            ss.setAnnuel(false);
            ss.setSemestriel1(false);
            ss.setSemestriel1N(false);
            ss.setSemestriel1R(false);
            ss.setSemestriel2(false);
            ss.setSemestriel2N(false);
            ss.setSemestriel2R(false);
            specialiteAnnee.delete(spa);
            ss = specialiteAnnee.create(ss);
            JsfUtil.addSuccessMessage("Le verou des mises à jour a été enlevé!");
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "enlevement du verou  la specalite " + ss.getSpecialite() + " et pour l'année " + ss.getAnneeAcademique());

        } catch (NoResultException nr) {
            JsfUtil.addErrorMessage("Cette synthèse n'a pas encore été calculée!");
        }

    }

    public Float getMoyenne() throws ServiceException {
        try {
            try {
                spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
            } catch (NoResultException nr) {
                spa = new SpecialiteAnnee();
                spa.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                spa.setSpecialite(spe.findById(specialiteId));
                spa = specialiteAnnee.create(spa);
            }
            moyenne = spa.getMoyenne();
            return moyenne;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public ISSpecialiteAnnee getSpecialiteAnnee() {
        return specialiteAnnee;
    }

    public void setSpecialiteAnnee(ISSpecialiteAnnee specialiteAnnee) {
        this.specialiteAnnee = specialiteAnnee;
    }

    public void activePandeliberation(ActionEvent actionEven) throws ServiceException {
        Niveau nivo = niv.findById(niveauId);
        if (nivo.getLevel() < 4) {
            JsfUtil.addErrorMessage("les moyennes sont uniquement applicables en master");
            return;
        }
        statusPannoDeliberation = true;
    }

    public boolean isStatusPannoDeliberation() {
        return statusPannoDeliberation;
    }

    public void setStatusPannoDeliberation(boolean statusPannoDeliberation) {
        this.statusPannoDeliberation = statusPannoDeliberation;
    }

    public ISUtilisateur getUti() {
        return uti;
    }

    public void setUti(ISUtilisateur uti) {
        this.uti = uti;
    }

    public Map<String, Long> getNiveaux() throws ServiceException {
        niveaux = NivUtil.getLevel(getListNiveau());
        return niveaux;
    }
}
