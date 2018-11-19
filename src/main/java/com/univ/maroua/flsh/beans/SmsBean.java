package com.univ.maroua.flsh.beans;

import com.douwe.generic.dao.DataAccessException;
import com.javacodegeeks.kannel.api.SMSManager;
import com.univ.maroua.flsh.beans.util.JsfUtil;
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
import com.univ.maroua.flsh.entities.Sms;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.projection.Pv;
import com.univ.maroua.flsh.projection.Template;
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
import com.univ.maroua.flsh.service.ISSms;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteAnnee;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.faces.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import javax.persistence.NoResultException;
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
import org.primefaces.model.StreamedContent;

/**
 *
 * @author lappa
 */
@ManagedBean(name = "smsBean")
@SessionScoped
@ViewScoped
public class SmsBean implements Serializable {

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
    @ManagedProperty(value = "#{ISSms}")
    private ISSms issms;
    private Semestre semestre;
    private Etudiant etudiant;
    private List<Etudiant> listEtudiant;
    private Sms selected;
    private List<Matiere> listMat;
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
    private String reportName;
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
    private int typeSemestre;
    private String matricule;
    private List<Sms> items;
    private static String HOST = "localhost";
    private static String USER_NAME = "syge";
    private static String PASSWORD = "syge";
    private static String PORT = "13131";
    private static String SENDER_NUMBER = "696951577";
    private static String SIGNATURE = "\n" + "*****powered by SYGE*****";
    private int growl;

    public SmsBean() {
        items = new LinkedList<>();
        this.listMat = new ArrayList<Matiere>();
        token = false;
        section = new Section();
        selected = new Sms();
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

    public Sms getSelected() {
        return selected;
    }

    public void setSelected(Sms selected) {
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

    public List<Section> getListSection() throws ServiceException {
        listSection = sec.findAll();
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

    public void doSend(ActionEvent actionEven) throws ServiceException {
        Calendar todayCal = Calendar.getInstance();
        Calendar matiereCal = Calendar.getInstance();
        List<Section> sections = sec.findByDepartement(departementId);
        // Send SMS to multiple recipients
        List<String> recipientsGroupA = null;
        SMSManager smsManager = SMSManager.getInstance();
        // We can change the prefetch size of the background worker thread
        smsManager.setMessagesPrefetchSize(30);
        // We can change the send SMS rate
        smsManager.setMessagesSendRate(65);
        Departement dd = dep.findById(departementId);
        AnneeAcademique aa = anAc.findById(anneeAcademiqueid);
        for (Section sect : sections) {
            recipientsGroupA = new ArrayList<>();
            switch (typeSemestre) {
                case 1:
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 1);
                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 1, etudiant1.getId());
                            //on envoie les notes uniquement aux solvables                  
                            if (!insc.estEligible(1, anneeAcademiqueid, etudiant1.getMatricule(), sect.getId())) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                }
                                continue;
                            }
                            //maintenant qu'il est solvables envoyons lui toutes ses notes 
                            //ici on cherche les notes dont la date de publication de depasse pas 1 mois et on s'assure que c'est la session normale
                            List<Note> notes = not.findByEtudiantSection(etudiant1.getMatricule(), sect.getId());
                            List<Note> toSend = new LinkedList<>();
                            //n vera les histoires  de date apres et ler  token pour dire qu'il a deja recu la note
                            for (Note note1 : notes) {
                                try {
                                    matiereCal.setTime(note1.getMatiere().getDate());
                                    todayCal.add(Calendar.MONTH, +2);

                                    if (matiereCal.compareTo(todayCal) > 0) {
                                        continue;
                                    }
                                } catch (NullPointerException ex) {
                                    continue;
                                }
                                if (note1.getRattrapage() == null) {
                                    if (!note1.getIsSmsSend() == true) {
                                        toSend.add(note1);
                                    }
                                }
                            }
                            for (Note note1 : toSend) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                }

                            }
                        }
                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<PV>  les notes de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);

                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "envoi par sms des notes session normale semestre 1 de la section  :" + sect + " pour l'annee " + aa);
                    JsfUtil.addSuccessMessage(" Operation faite avec succes pour la section " + sect.getSigle());
                    break;
                case 2:
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 1);

                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 1, etudiant1.getId());
                            //on envoie les notes uniquement aux solvables                  
                            if (!insc.estEligible(1, anneeAcademiqueid, etudiant1.getMatricule(), sect.getId())) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                }
                                continue;
                            }
                            //maintenant qu'il est solvables envoyons lui toutes ses notes 
                            //ici on cherche les notes dont la date de publication de depasse pas 1 mois et on s'assure que c'est la session normale
                            List<Note> notes = not.findByEtudiantSection(etudiant1.getMatricule(), sect.getId());
                            List<Note> toSend = new LinkedList<>();
                            //n vera les histoires  de date apres et ler  token pour dire qu'il a deja recu la note
                            for (Note note1 : notes) {
                                try {
                                    matiereCal.setTime(note1.getMatiere().getDate());
                                    todayCal.add(Calendar.MONTH, +2);

                                    if (matiereCal.compareTo(todayCal) > 0) {
                                        continue;
                                    }
                                } catch (NullPointerException ex) {
                                    continue;
                                }
                                if (note1.getRattrapage() != null) {
                                    if (!note1.getIsSmsSend() == true) {
                                        toSend.add(note1);
                                    }
                                }
                            }
                            for (Note note1 : toSend) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                }

                            }
                        }
                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<PV>  les notes de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);

                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "envoi par sms des notes ratrappage semestre 1 de la section  :" + sect + " pour l'annee " + aa);
                    JsfUtil.addSuccessMessage(" Operation faite avec succes pour la section " + sect.getSigle());
                    break;
                case 3:
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 2);
                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 2, etudiant1.getId());
                            //on envoie les notes uniquement aux solvables                  
                            if (!insc.estEligible(2, anneeAcademiqueid, etudiant1.getMatricule(), sect.getId())) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                }
                                continue;
                            }
                            //maintenant qu'il est solvables envoyons lui toutes ses notes 
                            //ici on cherche les notes dont la date de publication de depasse pas 1 mois et on s'assure que c'est la session normale
                            List<Note> notes = not.findByEtudiantSection(etudiant1.getMatricule(), sect.getId());
                            List<Note> toSend = new LinkedList<>();
                            //n vera les histoires  de date apres et ler  token pour dire qu'il a deja recu la note
                            for (Note note1 : notes) {
                                try {
                                    matiereCal.setTime(note1.getMatiere().getDate());
                                    todayCal.add(Calendar.MONTH, +2);

                                    if (matiereCal.compareTo(todayCal) > 0) {
                                        continue;
                                    }
                                } catch (NullPointerException ex) {
                                    continue;
                                }
                                if (note1.getRattrapage() == null) {
                                    if (!note1.getIsSmsSend() == true) {
                                        toSend.add(note1);
                                    }
                                }
                            }
                            for (Note note1 : toSend) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                }

                            }
                        }
                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<PV>  les notes de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);

                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "envoi par sms des notes session normale semestre 2 de la section  :" + sect + " pour l'annee " + aa);
                    JsfUtil.addSuccessMessage(" Operation faite avec succes pour la section " + sect.getSigle());
                    break;
                case 4:
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 2);
                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 2, etudiant1.getId());
                            //on envoie les notes uniquement aux solvables                  
                            if (!insc.estEligible(2, anneeAcademiqueid, etudiant1.getMatricule(), sect.getId())) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                }
                                continue;
                            }
                            //maintenant qu'il est solvables envoyons lui toutes ses notes 
                            //ici on cherche les notes dont la date de publication de depasse pas 1 mois et on s'assure que c'est la session normale
                            List<Note> notes = not.findByEtudiantSection(etudiant1.getMatricule(), sect.getId());
                            List<Note> toSend = new LinkedList<>();
                            //n vera les histoires  de date apres et ler  token pour dire qu'il a deja recu la note
                            for (Note note1 : notes) {
                                try {
                                    matiereCal.setTime(note1.getMatiere().getDate());
                                    todayCal.add(Calendar.MONTH, +2);

                                    if (matiereCal.compareTo(todayCal) > 0) {
                                        continue;
                                    }
                                } catch (NullPointerException ex) {
                                    continue;
                                }
                                if (note1.getRattrapage() != null) {
                                    if (!note1.getIsSmsSend() == true) {
                                        toSend.add(note1);
                                    }
                                }
                            }
                            for (Note note1 : toSend) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                        String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                        text += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text, SMSManager.MESSAGE_PRIORITY_1);
                                        note1.setIsSmsSend(true);
                                        Note note = (Note) note1.clone();
                                        not.delete(note1);
                                        note.setId(null);
                                        not.create(note);
                                    }
                                }

                            }
                        }
                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<PV>  les notes de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);

                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "envoi par sms des notes ratrappage semestre 2 de la section  :" + sect + " pour l'annee " + aa);
                    JsfUtil.addSuccessMessage(" Operation faite avec succes pour la section " + sect.getSigle());
                    break;
                case 5:
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 1);
                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0

                        for (Etudiant etudiant1 : etudiants) {
                            //on cherche les specialites qu'on fait les etudiants pour les envoyer leurs syntheses
                            List<Specialite> specialites = spe.findByEtudiantAnnee(etudiant1.getId(), anneeAcademiqueid);
                            sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 1, etudiant1.getId());
                            for (Specialite specialite1 : specialites) {
                                try {
                                    SpecialiteEtudiant spee = speEt.findByEtudiantSpecialiteAnnee(etudiant1.getId(), anneeAcademiqueid, specialite1.getId());
                                    if (spee.getSynth1() != null) {
                                        if (sms.getPortee().equals("1")) {
                                            if (etudiant1.getOrange() != null) {
                                                //si le texte deborde les 255 caracteres usuels on le deconpe en plusieurs
                                                String text = "<SYNTH1> " + spee.getSynth1();
                                                text += SIGNATURE;
                                                int splitInter = 255;
                                                int start = 0;
                                                int end = splitInter;

                                                if (text.length() > splitInter) {
                                                    while (start < text.length()) {
                                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text.substring(start, end), SMSManager.MESSAGE_PRIORITY_1);
                                                        start += splitInter;
                                                        end += splitInter;
                                                        if (end > text.length()) {
                                                            end = text.length();
                                                        }
                                                    }
                                                } else {
                                                    smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);

                                                }
                                            }
                                        } else {
                                            if (etudiant1.getInternationnal() != null) {
                                                String text = "<SYNTH1> " + spee.getSynth1();
                                                text += SIGNATURE;
                                                int splitInter = 255;
                                                int start = 0;
                                                int end = splitInter;

                                                if (text.length() > splitInter) {
                                                    while (start < text.length()) {
                                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text.substring(start, end), SMSManager.MESSAGE_PRIORITY_1);
                                                        start += splitInter;
                                                        end += splitInter;
                                                        if (end > text.length()) {
                                                            end = text.length();
                                                        }
                                                    }
                                                } else {
                                                    smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text, SMSManager.MESSAGE_PRIORITY_1);
                                                }
                                            }
                                        }
                                    }
                                } catch (NoResultException nr) {
                                }
                            }

                        }
                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<SYTH1>  les synthese de semestre 1 de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);

                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "envoi par sms des synthèse semestre 1 de la section  :" + sect + " pour l'annee " + aa);
                    JsfUtil.addSuccessMessage(" Operation faite avec succes pour la section " + sect.getSigle());
                    break;
                case 6:
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 2);
                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0

                        for (Etudiant etudiant1 : etudiants) {
                            //on cherche les specialites qu'on fait les etudiants pour les envoyer leurs syntheses
                            List<Specialite> specialites = spe.findByEtudiantAnnee(etudiant1.getId(), anneeAcademiqueid);
                            sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 2, etudiant1.getId());
                            for (Specialite specialite1 : specialites) {
                                try {
                                    SpecialiteEtudiant spee = speEt.findByEtudiantSpecialiteAnnee(etudiant1.getId(), anneeAcademiqueid, specialite1.getId());
                                    if (spee.getSynth2() != null) {
                                        if (sms.getPortee().equals("1")) {
                                            if (etudiant1.getOrange() != null) {
                                                String text = "<SYNTH2> " + spee.getSynth2();
                                                text += SIGNATURE;
                                                int splitInter = 255;
                                                int start = 0;
                                                int end = splitInter;

                                                if (text.length() > splitInter) {
                                                    while (start < text.length()) {
                                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text.substring(start, end), SMSManager.MESSAGE_PRIORITY_1);
                                                        start += splitInter;
                                                        end += splitInter;
                                                        if (end > text.length()) {
                                                            end = text.length();
                                                        }
                                                    }
                                                } else {
                                                    smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);

                                                }
                                            }
                                        } else {
                                            if (etudiant1.getInternationnal() != null) {
                                                String text = "<SYNTH2> " + spee.getSynth2();
                                                text += SIGNATURE;
                                                int splitInter = 255;
                                                int start = 0;
                                                int end = splitInter;

                                                if (text.length() > splitInter) {
                                                    while (start < text.length()) {
                                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text.substring(start, end), SMSManager.MESSAGE_PRIORITY_1);
                                                        start += splitInter;
                                                        end += splitInter;
                                                        if (end > text.length()) {
                                                            end = text.length();
                                                        }
                                                    }
                                                } else {
                                                    smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text, SMSManager.MESSAGE_PRIORITY_1);
                                                }
                                            }
                                        }
                                    }
                                } catch (NoResultException nr) {
                                }
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<SYTH2>  les synthese de semestre2 de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);

                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "envoi par sms des synthèse semestre 2 de la section  :" + sect + " pour l'annee " + aa);

                    JsfUtil.addSuccessMessage(" Operation faite avec succes pour la section " + sect.getSigle());
                    break;
                case 7:
                    //on cherche les deux types et on les melange
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 1);
                    List<Etudiant> etudiants2 = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 2);
                    for (Etudiant etudiant1 : etudiants2) {
                        etudiants.add(etudiant1);
                    }
                    Set<Etudiant> hs = new HashSet<>(); //pour vider les doublons
                    hs.addAll(etudiants);
                    etudiants.clear();
                    etudiants.addAll(hs);
                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0

                        for (Etudiant etudiant1 : etudiants) {
                            //on cherche les specialites qu'on fait les etudiants pour les envoyer leurs syntheses
                            List<Specialite> specialites = spe.findByEtudiantAnnee(etudiant1.getId(), anneeAcademiqueid);
                            try {
                                sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 1, etudiant1.getId());
                            } catch (NoResultException nr) {
                                sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 2, etudiant1.getId());
                            }
                            for (Specialite specialite1 : specialites) {
                                try {
                                    SpecialiteEtudiant spee = speEt.findByEtudiantSpecialiteAnnee(etudiant1.getId(), anneeAcademiqueid, specialite1.getId());
                                    if (spee.getAnn() != null) {
                                        if (sms.getPortee().equals("1")) {
                                            if (etudiant1.getOrange() != null) {
                                                String text = "<AN> " + spee.getAnn();
                                                text += SIGNATURE;
                                                int splitInter = 255;
                                                int start = 0;
                                                int end = splitInter;

                                                if (text.length() > splitInter) {
                                                    while (start < text.length()) {
                                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text.substring(start, end), SMSManager.MESSAGE_PRIORITY_1);
                                                        start += splitInter;
                                                        end += splitInter;
                                                        if (end > text.length()) {
                                                            end = text.length();
                                                        }
                                                    }
                                                } else {
                                                    smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);

                                                }
                                            }
                                        } else {
                                            if (etudiant1.getInternationnal() != null) {
                                                String text = "<AN> " + spee.getAnn();
                                                text += SIGNATURE;
                                                int splitInter = 255;
                                                int start = 0;
                                                int end = splitInter;

                                                if (text.length() > splitInter) {
                                                    while (start < text.length()) {
                                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text.substring(start, end), SMSManager.MESSAGE_PRIORITY_1);
                                                        start += splitInter;
                                                        end += splitInter;
                                                        if (end > text.length()) {
                                                            end = text.length();
                                                        }
                                                    }
                                                } else {
                                                    smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), text, SMSManager.MESSAGE_PRIORITY_1);

                                                }
                                            }
                                        }
                                    }
                                } catch (NoResultException nr) {
                                }
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<AN>  les synthese annuelles de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);

                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "envoi par sms des synthèse annuelles de la section  :" + sect + " pour l'annee " + aa);
                    JsfUtil.addSuccessMessage(" Operation faite avec succes pour la section " + sect.getSigle());
                    break;
            }
        }
    }

    public void doSend1(ActionEvent actionEven) throws ServiceException {
        List<Section> sections = sec.findByDepartement(departementId);
        // Send SMS to multiple recipients
        List<String> recipientsGroupA = null;
        SMSManager smsManager = SMSManager.getInstance();
        // We can change the prefetch size of the background worker thread
        smsManager.setMessagesPrefetchSize(30);
        // We can change the send SMS rate
        smsManager.setMessagesSendRate(65);
        for (Section sect : sections) {
            recipientsGroupA = new ArrayList<>();
            switch (typeSemestre) {
                case 1:
                    etudiants = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 1);
                    //on cherche ceux qui ont souscrit pour les deux semestres
                    List<Etudiant> etuds = etu.findByAnneeSectionType(anneeAcademiqueid, sect.getId(), 3);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    try {
                        Sms sms = new Sms();
                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            try {
                                sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 1, etudiant1.getId());
                            } catch (NoResultException ex) {
                                sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sect.getId(), 1, etudiant1.getId());
                            }
                            //on envoie les notes uniquement aux solvables                  
                            if (!insc.estEligible(1, anneeAcademiqueid, etudiant1.getMatricule(), sect.getId())) {
                                if (sms.getPortee().equals("1")) {
                                    if (etudiant1.getOrange() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                } else {
                                    if (etudiant1.getInternationnal() != null) {
                                        String message = "<PV>  SYGE ne peut pas vous cmmniquer vos notes car vous etes insolvable!";
                                        message += SIGNATURE;
                                        smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getInternationnal(), message, SMSManager.MESSAGE_PRIORITY_1);
                                    }
                                }
                                continue;
                            }
                            //maintenant qu'il est solvables envoyons lui toutes ses notes 
                            //ici on cherche les notes dont la date de publication de depasse pas 1 mois et on s'assure que c'est la session normale
                            List<Note> notes = not.findByEtudiantSection(etudiant1.getMatricule(), sect.getId());
                            List<Note> toSend = new LinkedList<>();
                            //n vera les histoires  de date apres et ler  token pour dire qu'il a deja recu la note
                            for (Note note1 : notes) {
                                if (note1.getRattrapage() == null) {
                                    if (!note1.getIsSmsSend() == true) {
                                        toSend.add(note1);
                                    }
                                }
                            }
                            if (etudiant1.getOrange() != null) {
                                for (Note note1 : toSend) {
                                    String codeMatiere = (note1.getMatiere().getCode() == null) ? note1.getMatiere().getModule().getTargetCode() : note1.getMatiere().getCode();
                                    String text = "<PV> " + codeMatiere + " " + note1.getMatiere().getIntitule() + " TPE=" + note1.getTpe() + " TD=" + note1.getTd() + " CC=" + note1.getCc() + " EX.=" + note1.getExamen() + " MOY.=" + note1.getMoy() + " GRADE.=" + note1.getGrade() + " DECIS.=" + note1.getDecision();
                                    text += SIGNATURE;
                                    smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), text, SMSManager.MESSAGE_PRIORITY_1);
                                    note1.setIsSmsSend(true);
                                    Note note = (Note) note1.clone();
                                    not.delete(note1);
                                    note.setId(null);
                                    not.create(note);
                                }


                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("693442158");
                        //recipientsGroupA.add("admin2number");
                        //recipientsGroupA.add("admin3number");
                        String message = "<PV>  les notes de la section " + sect.getSigle() + " ont ete envoyees";
                        message += SIGNATURE;
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, message, SMSManager.MESSAGE_PRIORITY_7);



                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages");
                    JsfUtil.addSuccessMessage(" Operation faite avec succes ");
                    break;
                case 2:
                    etudiants = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 1);
                    //on cherche ceux qui ont souscrit pour les deux semestres
                    etuds = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 3);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    try {

                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            if (etudiant1.getOrange() != null) {
                                smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), "les notes", SMSManager.MESSAGE_PRIORITY_0);
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("admin1number");
                        recipientsGroupA.add("admin2number");
                        recipientsGroupA.add("admin3number");
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, "the_message", SMSManager.MESSAGE_PRIORITY_1);



                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages");
                    JsfUtil.addSuccessMessage(" Operation faite avec succes ");
                    break;
                case 3:
                    etudiants = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 2);
                    //on cherche ceux qui ont souscrit pour les deux semestres
                    etuds = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 3);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    try {

                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            if (etudiant1.getOrange() != null) {
                                smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), "les notes", SMSManager.MESSAGE_PRIORITY_0);
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("admin1number");
                        recipientsGroupA.add("admin2number");
                        recipientsGroupA.add("admin3number");
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, "the_message", SMSManager.MESSAGE_PRIORITY_1);



                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages");
                    JsfUtil.addSuccessMessage(" Operation faite avec succes ");
                    break;
                case 4:
                    etudiants = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 2);
                    //on cherche ceux qui ont souscrit pour les deux semestres
                    etuds = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 3);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    try {

                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            if (etudiant1.getOrange() != null) {
                                smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), "les notes", SMSManager.MESSAGE_PRIORITY_0);
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("admin1number");
                        recipientsGroupA.add("admin2number");
                        recipientsGroupA.add("admin3number");
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, "the_message", SMSManager.MESSAGE_PRIORITY_1);



                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages");
                    JsfUtil.addSuccessMessage(" Operation faite avec succes ");
                    break;
                case 5:
                    etudiants = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 1);
                    //on cherche ceux qui ont souscrit pour les deux semestres
                    etuds = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 3);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    try {

                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            if (etudiant1.getOrange() != null) {
                                smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), "les notes", SMSManager.MESSAGE_PRIORITY_0);
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("admin1number");
                        recipientsGroupA.add("admin2number");
                        recipientsGroupA.add("admin3number");
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, "the_message", SMSManager.MESSAGE_PRIORITY_1);



                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages");
                    JsfUtil.addSuccessMessage(" Operation faite avec succes ");
                    break;
                case 6:
                    etudiants = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 2);
                    //on cherche ceux qui ont souscrit pour les deux semestres
                    etuds = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 3);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    try {

                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            if (etudiant1.getOrange() != null) {
                                smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), "les notes", SMSManager.MESSAGE_PRIORITY_0);
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("admin1number");
                        recipientsGroupA.add("admin2number");
                        recipientsGroupA.add("admin3number");
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, "the_message", SMSManager.MESSAGE_PRIORITY_1);



                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages");
                    JsfUtil.addSuccessMessage(" Operation faite avec succes ");
                    break;
                case 7:
                    //voir comment use les hash maps pour degager les doublons
                    etudiants = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 1);
                    //on cherche ceux qui ont souscrit pour les deux semestres
                    etuds = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 3);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    //on ajoute aussi pour le semestre 2
                    etuds = etu.findByAnneeDepartementType(anneeAcademiqueid, departementId, 2);
                    for (Etudiant etudiant1 : etuds) {
                        etudiants.add(etudiant1);
                    }
                    try {

                        //pour tous le etudiants on recupere les notes et on les envoie avec la priorite 0
                        for (Etudiant etudiant1 : etudiants) {
                            if (etudiant1.getOrange() != null) {
                                smsManager.sendSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, etudiant1.getOrange(), "les notes", SMSManager.MESSAGE_PRIORITY_0);
                            }
                        }

                        //apres l'administrateur doit confirmer si les messages sont effectivement arrivés
                        recipientsGroupA.add("admin1number");
                        recipientsGroupA.add("admin2number");
                        recipientsGroupA.add("admin3number");
                        smsManager.sendBulkSMS(HOST, PORT, USER_NAME, PASSWORD, SENDER_NUMBER, recipientsGroupA, "the_message", SMSManager.MESSAGE_PRIORITY_1);



                    } catch (Exception ex) {
                        JsfUtil.addErrorMessage("erreur d'envoi. le modem est t'il bien configuré ?");
                        return;
                    }
                    logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "validation des passages");
                    JsfUtil.addSuccessMessage(" Operation faite avec succes ");
                    break;
            }
        }
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
                types = new HashMap<String, String>();
                types.put("Normale", "N");
                types.put("Ratrappage", "R");
                types.put("Final", "F");
            } else {
                types = new HashMap<String, String>();
                types.put("Normale", "N"); //normalement c'est final mais on laisse d'abord comme ca
                types.put("Ratrappage", "R");
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

    public void setMoyenne(Float moyenne) {
        this.moyenne = moyenne;
    }

    public ISSpecialiteAnnee getSpecialiteAnnee() {
        return specialiteAnnee;
    }

    public void setSpecialiteAnnee(ISSpecialiteAnnee specialiteAnnee) {
        this.specialiteAnnee = specialiteAnnee;
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

    public int getTypeSemestre() {
        return typeSemestre;
    }

    public void setTypeSemestre(int typeSemestre) {
        this.typeSemestre = typeSemestre;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public ISSms getIssms() {
        return issms;
    }

    public void setIssms(ISSms issms) {
        this.issms = issms;
    }

    public void saveNew(ActionEvent event) {
        Etudiant etudiant1 = null;
        try {
            try {
                etudiant1 = etu.findByMatricule(etudiant.getMatricule());
            } catch (NoResultException nr) {
                JsfUtil.addErrorMessage("Le matricule " + etudiant1.getMatricule() + " n'est pas reconnu par le système!");
                return;
            }

            List<Section> sections = sec.findByEtudiantAnnee(etudiant1.getId(), anneeAcademiqueid);
            boolean found = false;
            for (Section section1 : sections) {
                if (section1.getId().equals(sectionId)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                JsfUtil.addErrorMessage("L'etudiant " + etudiant1.getNom() + " n'est pas inscrit dans cette section a cette annee!");
                return;
            }

            etudiant1.setOrange(etudiant.getOrange());
            etudiant1.setMtn(etudiant.getMtn());
            etudiant1.setNextel(etudiant.getNextel());
            etudiant1.setInternationnal(etudiant.getInternationnal());
            etu.update(etudiant1);

            Sms ss = new Sms();
            ss.setEtudiant(etudiant1);
            ss.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
            ss.setSection(sec.findById(sectionId));
            ss.setType(typeSemestre);

            issms.create(ss);
            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation pour les sms du l'etudiant :  " + selected);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Enregistrement avec succés", ""));
        } catch (ServiceException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enregistrement impossible", ""));
        }
    }

    public void update(ActionEvent event) {
        //nothing for the moment
    }

    public List<Sms> getItems() throws ServiceException {
        items = issms.findAll();
        return items;
    }

    public void delete(ActionEvent event) throws ServiceException {
        issms.delete(selected);
    }

    public void xls(ActionEvent actionEven) throws JRException, IOException, ServiceException, DataAccessException {
        JasperPrint jasperPrint;
        List<Template> tps = new LinkedList<>();
        Template tp = new Template();
        String comment = tp.getComment();
        comment += "\n" + "1 : pour les sms locaux ";
        comment += "\n" + "2 : pour les sms internationaux ";
        tp.setComment(comment);
        tps.add(tp);

        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String path = servletContext.getRealPath("") + File.separator + "print";
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "template" + File.separator + "sms.jasper", new HashMap(), new JRBeanCollectionDataSource(tps));

        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setHeader("content-disposition", "attachment; filename=" + "sms.xls");
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
                String matricule = "";
                String orange = "";
                String mtn = "";
                String nexttel = "";
                String inter = "";
                String type = "";
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cell.getColumnIndex()) {
                        case 0:
                            matricule = cell.getStringCellValue();
                            break;
                        case 1:
                            orange = cell.getStringCellValue();
                            break;
                        case 2:
                            mtn = cell.getStringCellValue();
                            break;
                        case 3:
                            nexttel = cell.getStringCellValue();
                            break;
                        case 4:
                            inter = cell.getStringCellValue();
                            break;
                        case 5:
                            type = cell.getStringCellValue();
                            break;
                    }

                }
                if ((!matricule.equals("Matricule")) || (!orange.equals("No. Orange"))
                        || (!mtn.equals("No. Mtn")) || (!nexttel.equals("No. Nexttel"))
                        || (!inter.equals("No. Inter.")) || (!type.equals("Type"))) {
                    JsfUtil.addErrorMessage("le fichier n'est pas bien structure");
                    return;
                }
                continue;
            }

            String matricule = null;
            String orange = null;
            String mtn = null;
            String nexttel = null;
            String inter = null;
            String type = null;
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getColumnIndex()) {
                    case 0:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            matricule = cell.getStringCellValue();
                        }
                        break;
                    case 1:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            orange = cell.getStringCellValue().trim();
                        }
                        break;
                    case 2:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            mtn = cell.getStringCellValue();
                        }
                        break;
                    case 3:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            nexttel = cell.getStringCellValue();
                        }
                        break;
                    case 4:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            inter = cell.getStringCellValue();
                        }
                        break;
                    case 5:
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (cell.getStringCellValue() != null) {
                            type = cell.getStringCellValue();
                        }
                        break;
                }

            }
            if (matricule != null) {
                if (!matricule.isEmpty()) {
                    if (type.isEmpty()) {
                        JsfUtil.addErrorMessage("ligne" + (row.getRowNum() + 1) + ": preicser le type d'envoi: nationnal ou internationnal");
                        return;
                    }
                    Etudiant e = null;
                    matricule = matricule.trim();
                    try {
                        e = etu.findByMatricule(matricule);
                        if (orange != null) {
                            e.setOrange(orange);
                        }
                        if (mtn != null) {
                            e.setMtn(mtn);
                        }
                        if (nexttel != null) {
                            e.setNextel(nexttel);
                        }
                        if (inter != null) {
                            e.setInternationnal(inter);
                        }
                        etu.update(e);
                    } catch (NoResultException ex) {
                        JsfUtil.addErrorMessage("Le matricule " + matricule + " n'est pas reconnu!");
                        return;
                    }


                    List<Section> sections = sec.findByEtudiantAnnee(e.getId(), anneeAcademiqueid);
                    boolean found = false;
                    for (Section section1 : sections) {
                        if (section1.getId().equals(sectionId)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        JsfUtil.addErrorMessage("L'etudiant " + e.getNom() + " n'est pas inscrit dans cette section a cette annee !");
                        return;
                    }
                    try {
                        Sms sms = issms.findByAnneeSectionTypeEtudiant(anneeAcademiqueid, sectionId, typeSemestre, e.getId());
                    } catch (NoResultException ex) {
                        Sms ss = new Sms();
                        ss.setEtudiant(e);
                        ss.setAnneeAcademique(anAc.findById(anneeAcademiqueid));
                        ss.setSection(sec.findById(sectionId));
                        ss.setType(typeSemestre);
                        ss.setPortee(type);
                        issms.create(ss);
                        i++;
                        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "creation pour sms l'etudiant:  " + e);
                    }
                }
            }

        }
        logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "importation des etudiants");
        JsfUtil.addSuccessMessage("En tout " + i + " etudiants(s) importe(s)");
    }
}
