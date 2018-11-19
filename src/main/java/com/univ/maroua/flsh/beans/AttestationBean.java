/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.beans.util.PasswordUtil;
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
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.projection.Attestation;
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
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import javax.faces.application.FacesMessage;
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
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author koossery
 */
@ManagedBean(name = "attestationBean")
@SessionScoped
public class AttestationBean {

    /**
     * Creates a new instance of AttestationBean
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
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant iSSpecialiteEtudiant;
    @ManagedProperty(value = "#{ISEtudiantSection}")
    private ISEtudiantSection etudiantSection;
    @ManagedProperty(value = "#{ISSPecialiteAnnee}")
    private ISSpecialiteAnnee specialiteAnnee;
    @ManagedProperty(value = "#{ISUtilisateur}")
    private ISUtilisateur uti;
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
    private List<Attestation> attestations = new LinkedList<Attestation>();
//    private Attestation attestation = new Attestation();
    private Section session = new Section();
    private String reportName;
    int niveauLevel;
    private List<Section> listSection1 = new ArrayList<Section>();
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private String year;

    public AttestationBean() {
    }

    public void init(String nom) throws JRException, ServiceException {
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(attestations);
        jasperPrint = JasperFillManager.fillReport(path + File.separator + "attestation" + File.separator + nom + ".jasper", new HashMap(), beanCollectionDataSource);
    }

    public void PDF(int token) throws JRException, IOException, ServiceException {
        
        Utilisateur util = uti.findByUsername(UserUtil.getUsername());
        if (util.getPassword().equals(PasswordUtil.getChenem()) || util.getPassword().equals(PasswordUtil.getNdo())) {
            JsfUtil.addErrorMessage("veuillez calculer toutes les syntheses d'abord!");
            return;
        }
        
        
        attestations = generic(token);
        if (attestations.isEmpty()) {
            if (token == 0) {
                JsfUtil.addErrorMessage("cet etudiant n'a pas droit a une attestation! ");
                return;
            } else {
                JsfUtil.addErrorMessage("aucun élément trouvé!");
                return;
            }
        }
        try {
            String annee = anAc.findById(anneeAcademiqueid).getAnnee();
            try { //le but est de mettre tous les champsici a false
                SpecialiteAnnee spa = specialiteAnnee.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
                if (spa.getAnnuel() == false) {
                    JsfUtil.addErrorMessage("vous devez d'abord calculer les syntheses annuelles de cette specialité " + annee);
                    return;
                }
            } catch (NoResultException nr) {
                JsfUtil.addErrorMessage("vous devez d'abord calculer les syntheses annuelles de cette specialité " + annee);
                return;
            }
        } catch (IllegalArgumentException ex) {
        }

        init("attestation");
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

    public void filterByStudent() throws ServiceException {
        attestations = generic(0);
    }

    public void filter(ActionEvent event) throws ServiceException {
        attestations = generic(1);
    }

    public List<Attestation> getAttestations() {
        return attestations;
    }

    public void setAttestations(List<Attestation> attestations) {
        this.attestations = attestations;
    }

    List<Attestation> generic(int token) throws ServiceException {
        int credits = 0;
        etudiants = new LinkedList<>();
        attestations = new ArrayList<>();
        if (token == 0) {
            try {
                if (matricule != null) {
                    matricule = matricule.trim();
                }
                SpecialiteEtudiant sp = iSSpecialiteEtudiant.findByEtudiantSectionNiveau(matricule, niveauLevel, sectionId).get(0);
                List<SpecialiteEtudiant> spss = iSSpecialiteEtudiant.findByEtudiantSectionNiveauAnnee(matricule, niveauLevel, sectionId, sp.getAnneeAcademique().getId());
                if (spss.size() != 1) {
                    for (SpecialiteEtudiant specialiteEtudiant : spss) {
                        if (!specialiteEtudiant.getSpecialite().getNom().equals("/")) {
                            sp = specialiteEtudiant;
                            break;
                        }
                    }
                }
                Etudiant et = new Etudiant();
                try {
                    if (sp.getDecision().toLowerCase().startsWith("a")) {
                        year = sp.getAnneeAcademique().getAnnee();
                        et = sp.getEtudiant();
                        specialiteId = sp.getSpecialite().getId();
                        anneeAcademiqueid = sp.getAnneeAcademique().getId();
                        if (niveauLevel == 3) {
                            reportName = "attestationLicence" + et.getNom();
                            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des attestations de reussite de licence de l'étudiant " + et);
                        } else {
                            reportName = "attestationMaster" + et.getNom();
                            logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des attestations de reussite de master de l'étudiant " + et);
                        }
                        reportName = reportName.replaceAll(" ", "");
                        etudiants.add(et);
                    }
                } catch (NullPointerException ex) {
                    return new LinkedList<>();
                }

            } catch (javax.persistence.NoResultException ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Matricule Etudiant Invalide!", ""));
            }
        } else {
            Specialite ss = spe.findById(specialiteId);
            String type = "";
            if (niveauLevel == 3) {
                type = "Licence";
            } else {
                type = "Master";
            }
            reportName = "attestation" + type + sec.findById(sectionId).getSigle() + (ss.getNom().equals("/") ? "" : ss.getNom()) + niv.findById(niveauId).getLevel();
            reportName = reportName.replaceAll(" ", "");
            List<SpecialiteEtudiant> specialiteEtudiant = iSSpecialiteEtudiant.findBySpecialiteAnnee(specialiteId, anneeAcademiqueid);
            for (SpecialiteEtudiant specialiteEtudiant1 : specialiteEtudiant) {
                try {
                    Etudiant et = new Etudiant();
                    if (specialiteEtudiant1.getDecision().toLowerCase().startsWith("a")) {
                        et = specialiteEtudiant1.getEtudiant();
                        etudiants.add(et);
                    }
                } catch (NullPointerException ex) {
                }
            }

        }
        int i = 0;
        Specialite specialite = spe.findById(specialiteId);
        AnneeAcademique aa = anAc.findById(anneeAcademiqueid);
        if (!etudiants.isEmpty()) {
            if (niveauLevel == 3) {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des attestations de reussite de licence des étudiants de la speciaite " + specialite + " de l'année " + aa);
            } else {
                logger.info("#utilisateur=" + UserUtil.getUsername() + "#action=" + "edition des attestations de reussite de master des étudiants de la speciaite " + specialite + " de l'année " + aa);
            }
        }
        for (Etudiant etudiant1 : etudiants) {
            EtudiantSection ets = etudiantSection.findByEtudiantSection(etudiant1.getId(), specialite.getNiveau().getSection().getId());

            float mgp = (ets.getMoy5() + ets.getMoy6()) / 2;  //on gere la licence actu
            Attestation attestation = new Attestation();
            attestation.setNomPrenom(etudiant1.getNom());
            attestation.setMatricule(etudiant1.getMatricule());
            attestation.setDateNais(etudiant1.getDateNais());
            attestation.setLieuNais(etudiant1.getLieuNais());
            attestation.setSpecialite(specialite.getNom());
            attestation.setFiliere(specialite.getNiveau().getSection().getNom());
            attestation.setSession(anAc.findById(anneeAcademiqueid).getAnnee());
            attestation.setDiplomeEntree(convertDiplome(etudiant1.getDiplomEntree()));                //System.out.println("++++++++++++++++++++" + mgp);
            Note noteTest = new Note();
            noteTest.setMoy(mgp);
            noteTest = not.calculMoyenne(noteTest);
            attestation.setMgp(noteTest.getCote());
            attestation.setMention(not.mention(noteTest.getCote()) + "  " + noteTest.getGrade());
            attestations.add(attestation);
            i++;
        }
        JsfUtil.addSuccessMessage("en tout " + i + " attestations trouvees");
        Collections.sort(attestations);
        return attestations;
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

    private String convertDiplome(String diplome) {
        try {
            if (diplome.toLowerCase().startsWith("bac")) {
                try {
                    return "Baccalauréat " + diplome.substring(3, 5);
                } catch (StringIndexOutOfBoundsException ex) {
                    return "Baccalauréat";
                }
            } else if (diplome.toLowerCase().startsWith("gc")) {
                return "General Certificate of Education Advanced Level";
            }
        } catch (NullPointerException ex) {
        }
        return diplome;
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

    public void processNiveau1() throws ServiceException {
        sectionDisable = false;
        getListSection1();
    }

    public List<Section> getListSection1() throws ServiceException {
        try {
            listSection1 = sec.findByEtudiantIdNiveau(matricule, niveauLevel);
            return listSection1;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public ISEtudiantSection getEtudiantSection() {
        return etudiantSection;
    }

    public void setEtudiantSection(ISEtudiantSection etudiantSection) {
        this.etudiantSection = etudiantSection;
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
    
    
    
}
