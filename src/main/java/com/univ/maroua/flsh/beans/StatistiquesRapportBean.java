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
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.entities.Module;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.Statistique;
import com.univ.maroua.flsh.projection.DelStat;
import com.univ.maroua.flsh.projection.PvMatiere;
import com.univ.maroua.flsh.projection.PvMatiereModel;
import com.univ.maroua.flsh.projection.Stats;
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
import com.univ.maroua.flsh.service.ISStatistique;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.primefaces.event.SelectEvent;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author william mekomou
 * <williammekomou@yahoo.com>
 */
@ManagedBean(name="statistiquesRapportBean")
@RequestScoped
public class StatistiquesRapportBean implements Serializable {

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
    @ManagedProperty(value = "#{ISStatistique}")
    private ISStatistique stat;
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
    private Float noteDeliberation = 0f;
    private PvMatiere target = new PvMatiere();
    private String sessionDel;
    private String typeImpression;
    private String typeSup;
    private PvMatiere[] selectedPvs;
    private PvMatiereModel pvMatiereModel;
    HashMap hm = new HashMap();
   private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
  
    public StatistiquesRapportBean() {
        section = new Section();
        selected = new Note();
        anneeAcademique = new AnneeAcademique();
        matiere = new Matiere();
        etudiant = new Etudiant();
        departement = new Departement();
        niveau = new Niveau();
        specialite = new Specialite();
        pvMatiere = new PvMatiere();
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
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"creation de la note :"+selected+"de la matiere:  "+matiere.getIntitule()+"pour l'etudiant: "+etudiant.getMatricule()+"pour l'année academique: "+anneeAcademique);
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
            logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"suppression de la note :"+selected);
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
              logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"mise à jour de la note de l'etudiant :"+etudiant.getMatricule()+"pour la matiere : "+matiere.getIntitule()+"pour l'anneé academique"+anneeAcademique);
          
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Mise à jour avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mise à jour impossible", ""));
        }
    }

    public void init(String nom) throws JRException, ServiceException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(listPvMatiere);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "statistique" + File.separator + nom + ".jasper", new HashMap(), beanCollectionDataSource);
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

    public void xlsxRapport(ActionEvent actionEven) throws JRException, IOException, ServiceException {
        moduleName = "statistique" + typeImpression;
        List<Departement> departements = dep.findAll();
        List<Stats> stats = new LinkedList();
        Niveau niveau = new Niveau();
        for (Departement departement1 : departements) {
            List<Section> sections = sec.findByDepartement(departement1.getId());
            for (Section section1 : sections) {
                List<Niveau> niveaus = niv.findBySection(section1.getId());
                for (Niveau niveau1 : niveaus) {
                    String niveauLevel = niveau1.getLevel() + "";
                    if (niveauLevel.equals(typeImpression)) {
                        niveau = niveau1;
                        break;
                    }
                }
                List<Specialite> specialites = spe.findByNiveau(niveau.getId());
                for (int i = 0; i < specialites.size(); i++) {
                    Specialite specialite1 = specialites.get(i);
                    if (specialites.size() > 1 && i == 0) {
                        continue;
                    }
                    try {
                        String percent = "%";
                        Statistique statistique = stat.findBySpecialiteAnnee(specialite1.getId(), anneeAcademiqueid);
                        Stats s = new Stats();
                        s.setAnnee(anAc.findById(anneeAcademiqueid).getAnnee());
                        s.setDep(departement1.getNom());
                        s.setNiveau(niveau.getLevel() + "");
                        s.setSection(section1.getNom());
                        s.setSpec(specialite1.getNom());
                        s.setNbreEtAd(statistique.getAdmis() + "");
                        s.setNbreEtCom(statistique.getInscrits() + "");
                        s.setNbreEtSol(statistique.getReglementaires() + "");
                        s.setPercentAd(String.format("%.2f", (statistique.getAdmis() / (float) statistique.getReglementaires() * 100)) + percent);
                        stats.add(s);
                    } catch (NoResultException ex) {
                    }
                }
            }
        }
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(stats);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "statistique" + File.separator + "rapport" + ".jasper", new HashMap(), beanCollectionDataSource);

        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment; filename=" + moduleName + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        export(xlsxExporter, servletOutputStream);
    }

  
    public void activePandeliberation(ActionEvent actionEven) {
        statusPannoDeliberation = true;
    }

    public void doDeliberation(ActionEvent actionEven) throws ServiceException {
        DelStat del = new DelStat();
        for (Note note1 : items) {
            if (sessionDel.equals("SN")) {
                try {
                    not.calculDeliberation(note1, noteDeliberation, 1, del);
                    logger.info("deliberation en session normale  par l'utilisateur: "+UserUtil.getUsername());
                } catch (NullPointerException e) {
                }
            } else if (sessionDel.equals("SR")) {
                try {
                    not.calculDeliberation(note1, noteDeliberation, 2, del);
                    logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"deliberation en session rattrapage");
                } catch (NullPointerException e) {
                }
            }
        }
        if (sessionDel.equals("SN")) {
            JsfUtil.addSuccessMessage("en tout " + del.getSession() + " notes deliberees");
        } else if (sessionDel.equals("SR")) {
            JsfUtil.addSuccessMessage("en tout " + del.getRat() + " notes deliberees");
        }
    }

    public void prepare() {
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
            modules = mod.findByAnneeAcSpecialite(anneeAcademiqueid, specialiteId);
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

    public Float getNoteDeliberation() {
        return noteDeliberation;
    }

    public void setNoteDeliberation(Float noteDeliberation) {
        this.noteDeliberation = noteDeliberation;
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

   

    public void setPvMatiereModel(PvMatiereModel pvMatiereModel) {
        this.pvMatiereModel = pvMatiereModel;
    }

    public void check(SelectEvent event) {
    }

    public void doDelete(ActionEvent actionEven) {

        JsfUtil.addSuccessMessage("tu as selectinne");
        System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww");

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

    public ISStatistique getStat() {
        return stat;
    }

    public void setStat(ISStatistique stat) {
        this.stat = stat;
    }
}
