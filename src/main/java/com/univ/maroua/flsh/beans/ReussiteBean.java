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
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Note;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteAnnee;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.projection.Reussite;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISEtudiantSection;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISNote;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteAnnee;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author koossery
 */
@ManagedBean(name = "reussiteBean")
@SessionScoped
public class ReussiteBean {

    /**
     * Creates a new instance of ReussiteBean
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
    @ManagedProperty(value = "#{ISEtudiantSection}")
    private ISEtudiantSection etudiantSection;
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant iSSpecialiteEtudiant;
    @ManagedProperty(value = "#{ISSPecialiteAnnee}")
    private ISSpecialiteAnnee specialiteAnnee;
    int niveauLevel;
    private List<Departement> listDepartement = new ArrayList<Departement>();
    private List<Section> listSection = new ArrayList<Section>();
    private List<Matiere> listMatiere = new ArrayList<Matiere>();
    private List<Specialite> listSpecialite = new ArrayList<Specialite>();
    private List<AnneeAcademique> listAnneeAcademique = new ArrayList<AnneeAcademique>();
    private List<Etudiant> etudiants = new ArrayList<Etudiant>();
    private Etudiant etudiant = new Etudiant();
    private Long departementId;
    private Long sectionId;
    private Long niveauId;
    private Long specialiteId;
    private Long anneeAcademiqueid;
    private String matricule;
    private String date;
    private int t = 0;
    private boolean sectionDisable = true;
    private boolean specialiteDisable = true;
    private JasperPrint jasperPrint;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String path = servletContext.getRealPath("") + File.separator + "print";
    private List<Reussite> reussites = new LinkedList<Reussite>();
//    private Reussite reussite = new Reussite();
    private Section session = new Section();
    private String reportName;
    private String typeReussite;
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());

    public ReussiteBean() {
    }

    public void init(String nom) throws JRException, ServiceException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(generic());
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

    public void xlsx(ActionEvent actionEven) throws JRException, IOException, ServiceException {

        init("reussite");
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment; filename=" + reportName + ".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        export(xlsxExporter, servletOutputStream);
    }

    public void PDF() throws JRException, IOException, ServiceException { //remplace xlx pour imprimer en pdf

        try {
            try { //le but est de mettre tous les champsici a false
                SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
                if (spa.getAnnuel() == false) {
                    JsfUtil.addErrorMessage("vous devez d'abord calculer les syntheses annuelles de cette specialité");
                    return;
                }
            } catch (NoResultException nr) {
                    JsfUtil.addErrorMessage("vous devez d'abord calculer les syntheses annuelles de cette specialité");
                    return;
             }
        } catch (IllegalArgumentException ex) {
        }


        init("reussite");
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename=" + reportName + ".pdf");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
        FacesContext.getCurrentInstance().responseComplete();
    }

    public List<Departement> getListDepartement() throws ServiceException {
        listDepartement = dep.findAll();
        return listDepartement;
    }

    public void filter(ActionEvent event) throws ServiceException {
        reussites = generic();
    }

    public List<Reussite> getReussites() {
        return reussites;
    }

    public void setReussites(List<Reussite> reussites) {
        this.reussites = reussites;
    }

    List<Reussite> generic() throws ServiceException {
        Float mgp = 0.0F;
        int credits = 0;
        etudiants = new LinkedList<Etudiant>();
        reussites = new ArrayList<Reussite>();
        Specialite ss = spe.findById(specialiteId);
        reportName = "reussite" + typeReussite + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niv.findById(niveauId).getLevel();
        reportName = reportName.replaceAll(" ", "");
        List<SpecialiteEtudiant> specialiteEtudiants = iSSpecialiteEtudiant.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
        for (SpecialiteEtudiant specialiteEtudiant : specialiteEtudiants) {
            try {
                if (specialiteEtudiant.getDecision().toLowerCase().startsWith("a")) {
                    etudiants.add(specialiteEtudiant.getEtudiant());
                }
            } catch (NullPointerException ex) {
            }
        }
        int i = 0;
        int token = 0;
        Specialite specialite = spe.findById(specialiteId);
        AnneeAcademique aa=anAc.findById(anneeAcademiqueid);
        for (Etudiant etudiant1 : etudiants) {
            EtudiantSection ets = etudiantSection.findByEtudiantSection(etudiant1.getId(), specialite.getNiveau().getSection().getId());
            if (typeReussite.equals("N")) {
                if (niveauLevel == 3) {
                    if ((ets.getSession5() == 1) && (ets.getSession6() == 1)) {
                        token = 1;
                    }
                } else {
                    if ((ets.getSession9() == 1) && (ets.getSession10() == 1)) {
                        token = 1;
                    }
                }
            } else {
            
                if (niveauLevel == 3) {
                    if ((ets.getSession5() == 0) || (ets.getSession6() == 0)) {
                        token = 1;
                    }
                } else {
                    if ((ets.getSession9() == 0) || (ets.getSession10() == 0)) {
                        token = 1;
                    }
                }
            }

            if (token == 1) {
                token = 0;
                if (niveauLevel == 3) {
                    mgp = (ets.getMoy5() + ets.getMoy6()) / 2;
                } else {
                    mgp = (ets.getMoy9() + ets.getMoy10()) / 2;
                }

                Reussite reussite = new Reussite();
                reussite.setMoyenne(mgp);
                reussite.setNomPrenom(etudiant1.getNom());
                reussite.setMatricule(etudiant1.getMatricule());
                if (typeReussite.equals("N")) {
                    reussite.setSession("Session normale");
                } else {
                    reussite.setSession("Session de rattrapage");
                }
                reussite.setAnnee("(Année académique " + anAc.findById(anneeAcademiqueid).getAnnee() + ")");
                reussite.setDepartement(dep.findById(departementId).getNom());
                reussite.setSection(sec.findById(sectionId).getNom());
                reussite.setSpecialite(specialite.getNom());
                reussite.setSexe(etudiant1.getSexe());
                Note noteTest = new Note();
                noteTest.setMoy(mgp);
                noteTest = not.calculMoyenne(noteTest);
                reussite.setMgp(noteTest.getCote());
                reussite.setMention(not.mention(noteTest.getCote()));
                reussite.setGrade(noteTest.getGrade());
                reussites.add(reussite);
                i++;
            }

        }
         if (typeReussite.equals("N")) {
                 logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des proces verbaux de reussite de session normale de la specialite "+specialite+" pour l'annee "+aa);
         }else{
                 logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des proces verbaux de reussite de session de ratrapage de la specialite "+specialite+" pour l'annee "+aa);
             
         }
        JsfUtil.addSuccessMessage("en tout " + i + " reussites trouvees");
        if (reussites.size() > 1) {
            Collections.sort(reussites);
            int k = 1;
            for (Reussite r : reussites) {
                r.setNo(k + "");
                k++;
            }
        }
        return reussites;
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

    public List<Section> getListSection() throws ServiceException {
        listSection = sec.findByDepartement(departementId);
        return listSection;
    }

    public List<Specialite> getListSpecialite() throws ServiceException {
        List<Niveau> niveaus = niv.findBySection(sectionId);
        for (Niveau niveau : niveaus) {
            if (niveau.getLevel() == niveauLevel) {
                niveauId = niveau.getId();
            }
        }
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public List<Matiere> getListMatiere() {
        return listMatiere;
    }

    public void setListMatiere(List<Matiere> listMatiere) {
        this.listMatiere = listMatiere;
    }

    public Section getSession() {
        return session;
    }

    public void setSession(Section session) {
        this.session = session;
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

    public String getTypeReussite() {
        return typeReussite;
    }

    public void setTypeReussite(String typeReussite) {
        this.typeReussite = typeReussite;
    }

    public ISEtudiantSection getEtudiantSection() {
        return etudiantSection;
    }

    public void setEtudiantSection(ISEtudiantSection etudiantSection) {
        this.etudiantSection = etudiantSection;
    }

    public ISSpecialiteEtudiant getiSSpecialiteEtudiant() {
        return iSSpecialiteEtudiant;
    }

    public void setiSSpecialiteEtudiant(ISSpecialiteEtudiant iSSpecialiteEtudiant) {
        this.iSSpecialiteEtudiant = iSSpecialiteEtudiant;
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
}
