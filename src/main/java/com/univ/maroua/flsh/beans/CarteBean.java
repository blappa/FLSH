package com.univ.maroua.flsh.beans;

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
import com.univ.maroua.flsh.entities.Semestre;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.projection.Attestation;
import com.univ.maroua.flsh.projection.Pv;
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
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author lappa
 */
@ManagedBean(name="carteBean")
@SessionScoped
@ViewScoped
public class CarteBean implements Serializable {

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
    private ISSpecialiteEtudiant spetud;
     @ManagedProperty(value = "#{ISEtudiantSection}")
    private ISEtudiantSection etudiantSection;
   
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
    private String typeOperation;
    private String opAnn;
    private List<Reussite> reussites;
    List<Attestation> attestations = new ArrayList<Attestation>();
    private String matricule;
   private Logger logger = Logger.getLogger(this.getClass().getName());
     private  Map<String, Long> niveaux;

   
    public CarteBean() {
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

    public void init(String nom) throws JRException, ServiceException {
        
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(generic(1));
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "carte" + File.separator + nom + ".jasper", new HashMap(), beanCollectionDataSource);
    }

    public void PDF(ActionEvent actionEvent) throws JRException, IOException, ServiceException {
        init("carte");
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    List<Attestation> generic(int token) throws ServiceException {
        int nive = niv.findById(niveauId).getLevel();
        Float mgp = 0.0F;
        int credits = 0;
        
        etudiants = new LinkedList<Etudiant>();
        attestations = new ArrayList<Attestation>();
        Specialite ss = spe.findById(specialiteId);
        logger.info("#utilisateur="+UserUtil.getUsername()+"#action="+"edition des cartes d'etudiants de la specialite "+ss+"section"+sectionId);
        reportName = "carte" + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niv.findById(niveauId).getLevel();
        reportName = reportName.replaceAll(" ", "");

        if (token == 0) {
            try {

                etudiant = new Etudiant();
                etudiant = etu.findByMatricule(matricule);
                reportName = "carte" + etudiant.getNom();
                reportName = reportName.replaceAll(" ", "");

            } catch (javax.persistence.NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Matricule Etudiant Invalide!", ""));
            }
//            credits = etudiant.getCredit5() + etudiant.getCredit6();
            if (credits != 60) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "L'etudiant " + etudiant.getNom() + " n'a pas tout capitalise", ""));
                return null;
            }
            etudiants.add(etudiant);
        } else {
            etudiants = etu.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
            AnneeAcademique aa = anAc.findById(anneeAcademiqueid);
            if (!etudiants.isEmpty()) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "édition des cartes d'étudiants de la speciaite " + specialite + " de l'année " + aa);
        }
        }
        int i = 0;
        Specialite specialite = spe.findById(specialiteId);
        Semestre semestre2 = sem.findBySpecialite(specialiteId).get(1);
        for (Etudiant etudiant1 : etudiants) {
            //System.out.println("++++++"+etudiant1.getMoy5()+" "+ etudiant1.getMoy6()+" "+etudiant1.getNom());
            Attestation attestation = new Attestation();
            attestation.setNomPrenom(etudiant1.getNom());
            attestation.setMatricule(etudiant1.getMatricule());

            attestation.setDateNais(etudiant1.getDateNais());
            try {
                String lieu = etudiant1.getLieuNais().toLowerCase().trim(); //pout mettre la premiere lettre en Majuscules et le reste en miniscules
                attestation.setLieuNais(lieu);
            } catch (NullPointerException ex) {
            }
            attestation.setSpecialite(specialite.getNom());
            attestation.setFiliere(specialite.getNiveau().getSection().getNom());
            attestation.setSession(anAc.findById(anneeAcademiqueid).getAnnee());
            attestations.add(attestation);
            i++;
        }
        JsfUtil.addSuccessMessage("en tout " + i + " cartes trouvees");
        return attestations;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
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
    }

    public void processSpecialite() throws ServiceException {
        semestreDisable = false;
        getListSpecialite();
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
        } catch (ServiceException ex) {
        }
        return result;
    }

    public List<Module> getModules1() throws ServiceException {
        try {
            Long semid = getSemestres().get(0).getId();
            modules1 = mod.findBySemestreAnneeAcSpecialite(anneeAcademiqueid, semid, specialiteId);
            return modules1;
        } catch (ServiceException ex) {
       } catch (IndexOutOfBoundsException ex) {
        }


        return null;
    }

    public List<Module> getModules2() {
        try {

            Long semid = getSemestres().get(1).getId();
            modules2 = mod.findBySemestreAnneeAcSpecialite(anneeAcademiqueid, semid, specialiteId);
            return modules2;
        } catch (ServiceException ex) {
        } catch (IndexOutOfBoundsException ex) {
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

    public ISSpecialiteEtudiant getSpetud() {
        return spetud;
    }

    public void setSpetud(ISSpecialiteEtudiant spetud) {
        this.spetud = spetud;
    }

    public ISEtudiantSection getEtudiantSection() {
        return etudiantSection;
    }

    public void setEtudiantSection(ISEtudiantSection etudiantSection) {
        this.etudiantSection = etudiantSection;
    }
    
      public Map<String, Long> getNiveaux() throws ServiceException {
        niveaux= NivUtil.getLevel(getListNiveau());
        return niveaux;
    }
    
}
