package com.univ.maroua.flsh.beans;

import com.univ.maroua.flsh.beans.util.NivUtil;
import com.univ.maroua.flsh.entities.AnneeAcademique;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import com.univ.maroua.flsh.entities.Inscription;
import com.univ.maroua.flsh.entities.Niveau;
import com.univ.maroua.flsh.entities.Section;
import com.univ.maroua.flsh.entities.Specialite;
import com.univ.maroua.flsh.entities.SpecialiteEtudiant;
import com.univ.maroua.flsh.service.ISAnneeAcademique;
import com.univ.maroua.flsh.service.ISDepartement;
import com.univ.maroua.flsh.service.ISEtudiant;
import com.univ.maroua.flsh.service.ISEtudiantSection;
import com.univ.maroua.flsh.service.ISInscription;
import com.univ.maroua.flsh.service.ISNiveau;
import com.univ.maroua.flsh.service.ISSection;
import com.univ.maroua.flsh.service.ISSpecialite;
import com.univ.maroua.flsh.service.ISSpecialiteEtudiant;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;
import org.primefaces.model.chart.PieChartModel;

/**
 *
 * @author william mekomou
 * <williammekomou@yahoo.com>
 */
@ManagedBean(name = "statistiquesBean")
@SessionScoped
@ViewScoped
public class StatistiquesBean implements Serializable {

    @ManagedProperty(value = "#{ISEtudiant}")
    private ISEtudiant etu;
    private List<Etudiant> items;
    @ManagedProperty(value = "#{ISInscription}")
    private ISInscription insc;
    private List<Inscription> inscriptions;
    @ManagedProperty(value = "#{ISAnneeAcademique}")
    private ISAnneeAcademique anAc;
    private AnneeAcademique anneeAcademiqueSelect;
    @ManagedProperty(value = "#{ISDepartement}")
    private ISDepartement dep;
    @ManagedProperty(value = "#{ISSection}")
    private ISSection sec;
    @ManagedProperty(value = "#{ISNiveau}")
    private ISNiveau niv;
    private List<Niveau> niveaux;
    @ManagedProperty(value = "#{ISSpecialte}")
    private ISSpecialite spe;
    @ManagedProperty(value = "#{ISSpecialteEtudiant}")
    private ISSpecialiteEtudiant sSpecialteEtudiant;
    @ManagedProperty(value = "#{ISEtudiantSection}")
    private ISEtudiantSection etudiantSection;
    private AnneeAcademique annee;
    private List<AnneeAcademique> listAnnee;
    private List<Specialite> specialites;
    private List<Specialite> specialites3;
    private List<Specialite> specialites5;
    private Specialite specialiteSelect;
    private List<SpecialiteEtudiant> specisEtudiants = new ArrayList<SpecialiteEtudiant>();
    private List<Section> sections;
    private List<Niveau> niveaus;
    private List<Departement> departements;
    private Departement departementSelect;
    private AnneeAcademique anneeAcademique;
    private List<AnneeAcademique> anneeAcademiques;
    private List<Departement> listDepartement;
    private List<Etudiant> listEtudiant;
    private Departement departement;
    private List<Etudiant> itemsNew;
    private HashSet<String> regions;
    private PieChartModel hommesFemmes;
    private PieChartModel regloPermierSem;
    private PieChartModel regloSecondSem;
    private PieChartModel regloFinAnnee;
    private CartesianChartModel repatitionParDepartement;
    private CartesianChartModel repartitionParBac;
    private CartesianChartModel repartitionParSpecialite;
    private PieChartModel proportionParSpecialite;
    private CartesianChartModel repatitionParSexeLicenceSpevialite;
    private CartesianChartModel repartitionParBacAnnee;
    private CartesianChartModel repartitionParBacDist;
    private CartesianChartModel repartitionParRegionDep;
    private Long anneeId;
    private boolean specialiteDisable = true;
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
    private Long levelId;
    //pour desactiver les combo boxes au lancement
    private boolean sectionDisable = true;
    private boolean niveauDisable = true;
    private boolean anneeDisable = true;
    private boolean moduleDisable = true;
    private boolean matiereDisable = true;
    private boolean barDisable = false;
    private boolean delibereDisable = true;
    private boolean statusPannoDeliberation = false;
    private int max = 2000;
    private int choix;
    private  Map<String, Long> niveauxAll;


    public StatistiquesBean() {
        departement = new Departement();
    }

//    public AnneeAcademique currentAcademiqueYear() throws ServiceException {
//        anneeAcademiques = anAc.findAll();
//        int i = anneeAcademiques.size();
//        return anneeAcademiques.get(i - 1);
//
//
//    }
    public void filter(ActionEvent event) throws ServiceException {
        hommesFemmes = getHommesFemmes();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter1(ActionEvent event) throws ServiceException {
        repartitionParSpecialite = getRepartitionParSpecialite();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter3(ActionEvent event) throws ServiceException {
        repatitionParDepartement = getRepatitionParDepartement();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter5(ActionEvent event) throws ServiceException {
        regloPermierSem = getRegloPermierSem();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter4(ActionEvent event) throws ServiceException {
        repatitionParSexeLicenceSpevialite = getRepatitionParSexeLicenceSpevialite();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter6(ActionEvent event) throws ServiceException {
        regloSecondSem = getRegloSecondSem();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter7(ActionEvent event) throws ServiceException {
        regloFinAnnee = getRegloFinAnnee();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter8(ActionEvent event) throws ServiceException {
        repartitionParBacDist = getRepartitionParBacDist();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter9(ActionEvent event) throws ServiceException {
        repartitionParRegionDep = getRepartitionParRegionDep();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public void filter10(ActionEvent event) throws ServiceException {
        repartitionParBacAnnee = getRepartitionParBacAnnee();
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Filtre réalisé avec succés", ""));
    }

    public PieChartModel getHommesFemmes() throws ServiceException {
        hommesFemmes = new PieChartModel();
        int nbrM = 0;
        int nbrF = 0;
        List<Etudiant> etudiants = etu.findByAnnee(anneeId);
        for (Etudiant etudiant : etudiants) {
            try {
                if (etudiant.getSexe().toLowerCase().trim().startsWith("m")) {
                    nbrM++;
                } else if (etudiant.getSexe().toLowerCase().trim().startsWith("f")) {
                    nbrF++;
                }
            } catch (NullPointerException ex) {
                continue;
            }
        }
        hommesFemmes.set("Hommes (" + nbrM + ")", nbrM);
        hommesFemmes.set("Femmes (" + nbrF + ")", nbrF);
        return hommesFemmes;
    }

    public PieChartModel getRegloPermierSem() throws ServiceException {
        regloPermierSem = new PieChartModel();
        int nombreEtudiant = 0;
        int nbinscritFirtSem = 0;
        items = etu.findByAnnee(anneeId);
        nombreEtudiant = items.size();
        for (Etudiant etudiant : items) {
            if (insc.aPayeByTypeAnnee(1, anneeId, etudiant.getMatricule(), null)) {
                nbinscritFirtSem++;
            }
        }

        int nonInscrit = nombreEtudiant - nbinscritFirtSem;
        regloPermierSem.set("Payes(" + nbinscritFirtSem + ")", nbinscritFirtSem);
        regloPermierSem.set("Non payes(" + nonInscrit + ")", nonInscrit);

        return regloPermierSem;
    }

    public PieChartModel getRegloSecondSem() throws ServiceException {
        regloSecondSem = new PieChartModel();
        int nombreEtudiant = 0;
        int nbinscritFirtSem = 0;
        items = etu.findByAnnee(anneeId);
        nombreEtudiant = items.size();
        for (Etudiant etudiant : items) {
            if (insc.aPayeByTypeAnnee(2, anneeId, etudiant.getMatricule(), null)) {
                nbinscritFirtSem++;
            }
        }
        nombreEtudiant = items.size();
        int nonInscrit = nombreEtudiant - nbinscritFirtSem;
        regloSecondSem.set("Payes(" + nbinscritFirtSem + ")", nbinscritFirtSem);
        regloSecondSem.set("Non Payes(" + nonInscrit + ")", nonInscrit);
        return regloSecondSem;
    }

    public PieChartModel getRegloFinAnnee() throws ServiceException {
        regloSecondSem = new PieChartModel();
        int nombreEtudiant = 0;
        int nbinscritFirtSem = 0;
        items = etu.findByAnnee(anneeId);
        nombreEtudiant = items.size();
        for (Etudiant etudiant : items) {
            if (insc.aPayeByTypeAnnee(1, anneeId, etudiant.getMatricule(), null) && insc.aPayeByTypeAnnee(2, anneeId, etudiant.getMatricule(), null)) {
                nbinscritFirtSem++;
            } else if (insc.aPayeByTypeAnnee(3, anneeId, etudiant.getMatricule(), null)) {
                nbinscritFirtSem++;
            }
        }
        nombreEtudiant = items.size();
        int nonInscrit = nombreEtudiant - nbinscritFirtSem;
        regloSecondSem.set("Payes(" + nbinscritFirtSem + ")", nbinscritFirtSem);
        regloSecondSem.set("Non Payes(" + nonInscrit + ")", nonInscrit);
        return regloSecondSem;
    }

    public CartesianChartModel getRepatitionParDepartement() throws ServiceException {
        repatitionParDepartement = new CartesianChartModel();
        ChartSeries bande = new ChartSeries();
        int count;
        bande.setLabel("département");
        try {
            String annee = anAc.findById(anneeId).getAnnee();
            departements = dep.findAll();
            max = 0;

            for (Departement departement : departements) {
                sections = new ArrayList<Section>();
                niveaux = new ArrayList<Niveau>();
                sections = sec.findByDepartement(departement.getId());
                count = 0;

                for (Section section : sections) {
                    niveaux = niv.findBySection(section.getId());
                    for (Niveau niveau : niveaux) {
                        specialites = spe.findByNiveau(niveau.getId());
                        for (Specialite specialite : specialites) {
                            items = etu.findBySpecialiteAnnee(specialite.getId(), anneeId);
                            for (Etudiant etudiant : items) {
                                if (choix == 1) {
                                    if (!insc.estEligible(1, anneeId, etudiant.getMatricule(), null)) {
                                        continue;
                                    }
                                } else if (choix == 2) {
                                    if (!insc.estEligible(2, anneeId, etudiant.getMatricule(), null)) {
                                        continue;
                                    }
                                }
                                count++;
                            }
                        }
                    }
                }
                bande = new ChartSeries();
                bande.setLabel(departement.getNom() + " (" + count + ")");

                max = ((max > count) ? max : count);
                bande.set(annee, count);
                repatitionParDepartement.addSeries(bande);
            }
            max += 10;
        } catch (IllegalArgumentException ex) {
            bande.set("année académique", 0);
            repatitionParDepartement.addSeries(bande);
        }

        return repatitionParDepartement;
    }

    public CartesianChartModel getRepartitionParSpecialite() throws ServiceException {

        repartitionParSpecialite = new CartesianChartModel();
        ChartSeries boys = new ChartSeries();
        boys.setLabel("Hommes");
        ChartSeries girls = new ChartSeries();
        girls.setLabel("Femmes");


        int m = 0, f = 0;
        try {

            specialiteSelect = spe.findById(specialiteId);

            anneeAcademiqueSelect = anAc.findById(anneeId);
            items = etu.findBySpecialiteAnnee(specialiteId, anneeId);

            for (Etudiant etudiant : items) {
                if (choix == 1) {
                    if (!insc.estEligible(1, anneeId, etudiant.getMatricule(), null)) {
                        continue;
                    }
                } else if (choix == 2) {
                    if (!insc.estEligible(2, anneeId, etudiant.getMatricule(), null)) {
                        continue;
                    }
                }
                try {
                    if (etudiant.getSexe().toLowerCase().trim().startsWith("m")) {
                        m++;
                    } else if (etudiant.getSexe().toLowerCase().trim().startsWith("f")) {
                        f++;
                    }
                } catch (NullPointerException ex) {
                    continue;
                }
            }
            max = ((m > f) ? m : f) + 10; //la taille max des diagrammes
            boys = new ChartSeries();
            boys.setLabel("Hommes (" + m + ")");
            girls = new ChartSeries();
            girls.setLabel("Femmes (" + f + ")");

            boys.set(specialiteSelect.getNom(), m);
            girls.set(specialiteSelect.getNom(), f);



        } catch (IllegalArgumentException e) {
            //pour initialiser
            boys.set("specialité", 0);
            girls.set("specialité", 0);
        }


        repartitionParSpecialite.addSeries(boys);
        repartitionParSpecialite.addSeries(girls);
        return repartitionParSpecialite;

    }

    public PieChartModel getProportionParSpecialite() throws ServiceException {
        proportionParSpecialite = new PieChartModel();
        int m = 0, f = 0;
        specialiteSelect = spe.findById(2L);
        anneeAcademiqueSelect = anAc.findById(2L);
        items = etu.findBySpecialiteAnnee(specialiteSelect.getId(), anneeAcademiqueSelect.getId());
        for (Etudiant etudiant : items) {
            if (countBySexe(etudiant, "Masculin") == true) {
                m++;
            } else if (countBySexe(etudiant, "Féminin")) {
                f++;
            }
        }
        proportionParSpecialite.set("Masculin", m);
        proportionParSpecialite.set("Féminin", f);


        return proportionParSpecialite;
    }

    public CartesianChartModel getRepatitionParSexeLicenceSpevialite() throws ServiceException {
        repatitionParSexeLicenceSpevialite = new CartesianChartModel();
        Specialite spec = null;

        int m = 0, f = 0;
        try {
            spec = spe.findById(specialiteId);
            List<SpecialiteEtudiant> spe = sSpecialteEtudiant.findBySpecialiteAnnee(specialiteId, anneeId);
            for (SpecialiteEtudiant specialiteEtudiant : spe) {
                try {
                    if (specialiteEtudiant.getDecision().toLowerCase().startsWith("a")) {
                        if (specialiteEtudiant.getEtudiant().getSexe().toLowerCase().startsWith("m")) {
                            m++;
                        } else {
                            f++;
                        }
                    }
                } catch (NullPointerException ex) {
                    continue;
                }
            }

            ChartSeries ch = new ChartSeries();
            ch.setLabel("Masculin(" + m + ")");
            ch.set(spec.getNom(), m);
            repatitionParSexeLicenceSpevialite.addSeries(ch);
            ChartSeries ch1 = new ChartSeries();
            ch1.setLabel("Feminin(" + f + ")");
            ch1.set(spec.getNom(), f);
            repatitionParSexeLicenceSpevialite.addSeries(ch1);

            max = ((m > f) ? m : f) + 10; //la taille max des diagrammes

        } catch (IllegalArgumentException ex) {
            ChartSeries ch = new ChartSeries();
            ch.setLabel("Masculin");
            ch.set("spécialité", 0);
            repatitionParSexeLicenceSpevialite.addSeries(ch);
            ChartSeries ch1 = new ChartSeries();
            ch1.setLabel("Feminin");
            ch1.set("spécialité", 0);
            repatitionParSexeLicenceSpevialite.addSeries(ch1);
        }


        return repatitionParSexeLicenceSpevialite;

    }

    public CartesianChartModel getRepartitionParBacAnnee() throws ServiceException {
        repartitionParBacAnnee = new CartesianChartModel();
        int m = 0, f = 0;
        specialiteSelect = spe.findById(2L);
        anneeAcademiqueSelect = anAc.findById(2L);
        items = etu.findBySpecialiteAnnee(specialiteSelect.getId(), anneeAcademiqueSelect.getId());
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Masculin");
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Feminin");
        HashSet<String> bacDist = AnneDoDistinct(items);

        for (String s : bacDist) {

            for (Etudiant etudiant : items) {
                try {
                    if ((countBySexe(etudiant, "Masculin") == true) && (etudiant.getAnneeObtDiplo().equals(s))) {
                        m++;
                    } else if ((countBySexe(etudiant, "Féminin")) && (etudiant.getAnneeObtDiplo().equals(s))) {
                        f++;
                    }
                } catch (NullPointerException ee) {
                    continue;
                }
            }
            series1.set(s, m);
            series2.set(s, f);

        }
        repartitionParBacAnnee.addSeries(series1);
        repartitionParBacAnnee.addSeries(series2);
        return repartitionParBacAnnee;
    }

    public CartesianChartModel getRepartitionParBacDist() throws ServiceException {
        repartitionParBacDist = new CartesianChartModel();
        int m = 0, f = 0;
        ChartSeries mas = new ChartSeries();
        mas.setLabel("Hommes");
        ChartSeries fem = new ChartSeries();
        fem.setLabel("Femmes");

        try {
            items = etu.findBySpecialiteAnnee(specialiteId, anneeId);
            HashSet<String> bacType = BacDistinct(items);
            mas = new ChartSeries();
            mas.setLabel("Hommes");
            fem = new ChartSeries();
            fem.setLabel("Femmes");
            if (bacType.isEmpty()) {
                mas.set("types de diplomes", 0);
                fem.set("types de diplomes", 0);
            }
            String dd = null;
            for (String s : bacType) {

                for (Etudiant etudiant : items) {
                    try {
                        String diplome = etudiant.getDiplomEntree().toLowerCase().replaceAll(" ", "");
                        if ((countBySexe(etudiant, "m")) && (diplome.equals(s))) {
                            m++;
                            dd = etudiant.getDiplomEntree();
                        } else if ((countBySexe(etudiant, "f")) && (diplome.equals(s))) {
                            f++;
                            dd = etudiant.getDiplomEntree();
                        }
                    } catch (NullPointerException ex) {
                        continue;
                    }
                }
                if (dd != null) {
                    max = ((m > f) ? m : f) + 10;
                    mas.set(dd, m);
                    fem.set(dd, f);
                }
            }
        } catch (IllegalArgumentException ill) {
            mas.set("types de diplomes", 0);
            fem.set("types de diplomes", 0);
        }

        repartitionParBacDist.addSeries(mas);
        repartitionParBacDist.addSeries(fem);
        return repartitionParBacDist;
    }

    public CartesianChartModel getRepartitionParRegionDep() throws ServiceException {
        repartitionParRegionDep = new CartesianChartModel();
        specialiteSelect = new Specialite();
        itemsNew = new ArrayList<Etudiant>();
        regions = new HashSet<String>();
        items = new ArrayList<Etudiant>();
        int m = 0, f = 0;
        LineChartSeries series1 = new LineChartSeries();
        series1.setLabel("Masculin");
        LineChartSeries series2 = new LineChartSeries();
        series2.setLabel("Feminin");

        try {
            items = etu.findBySpecialiteAnnee(specialiteId, anneeId);
            regions = RegionDistinct(items);
            series1 = new LineChartSeries();
            series1.setLabel("Masculin");
            series2 = new LineChartSeries();
            series2.setLabel("Feminin");
            if (regions.isEmpty()) {
                series1.set("region", 0);
                series2.set("region", 0);
            }

            max = 0;
            String rr = null;
            for (String s : regions) {
                for (Etudiant etudiant : items) {
                    try {
                        String region = etudiant.getRegion().toLowerCase().replaceAll(" ", "");
                        region = region.replaceAll("'", "");
                        if ((countBySexe(etudiant, "m")) && (region.equals(s))) {
                            rr = etudiant.getRegion().replaceAll("'", "").replaceAll(" ", "_");
                            m++;
                        } else if ((countBySexe(etudiant, "f")) && (region.equals(s))) {
                            f++;
                            rr = etudiant.getRegion().replaceAll("'", "").replaceAll(" ", "_");

                        }
                    } catch (NullPointerException ex) {
                    }
                }
                if (max < m) {
                    max = m;
                }
                if (max < f) {
                    max = f;
                }

                if (rr != null) {
                    series1.set(rr, m);
                    series2.set(rr, f);
                }

            }
            max += 10;
        } catch (IllegalArgumentException ill) {
            series1.set("region", 0);
            series2.set("region", 0);
        }
        repartitionParRegionDep.addSeries(series1);
        repartitionParRegionDep.addSeries(series2);

        return repartitionParRegionDep;
    }

    private boolean countBySexe(Etudiant e, String s) {
        try {
            if (e.getSexe().toLowerCase().startsWith(s)) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException ex) {
            return false; //il ne sera pas compte
        }
    }

    public HashSet<String> AnneDoDistinct(List<Etudiant> etudiants) {
        HashSet<String> hash = new HashSet<String>();
        for (Etudiant etudiant : etudiants) {
            hash.add(etudiant.getAnneeObtDiplo());
        }
        return hash;
    }

    public HashSet<String> BacDistinct(List<Etudiant> etudiants) {
        HashSet<String> hash = new HashSet<String>();
        for (Etudiant etudiant : etudiants) {
            try {
                String diplome = etudiant.getDiplomEntree().toLowerCase().replaceAll(" ", "");
                try {
                    Integer.parseInt(diplome);
                } catch (NumberFormatException ex) {
                    hash.add(diplome);
                }
            } catch (NullPointerException ex) {
            }
        }
        return hash;
    }

    public HashSet<String> RegionDistinct(List<Etudiant> etudiants) {
        HashSet<String> hash = new HashSet<String>();
        for (Etudiant etudiant : etudiants) {
            try {
                String region = etudiant.getRegion().toLowerCase().replace(" ", "");
                region = region.replaceAll("'", "");
                if (region.length() != 0 || region != null) {
                    try {
                        Integer.parseInt(region);
                    } catch (NumberFormatException ex) {
                        hash.add(region);
                    }
                }
            } catch (NullPointerException ex) {
            }
        }
        return hash;
    }

    public List<Etudiant> StudentDepYear(List<Etudiant> etudiants, Long yearID) throws ServiceException {
        List<Etudiant> currentList = new ArrayList<Etudiant>();
        for (Etudiant etudiant : etudiants) {
            specisEtudiants = sSpecialteEtudiant.findByMatriculeEtudiant(etudiant.getMatricule());

            for (SpecialiteEtudiant specialteEtudiant : specisEtudiants) {
                if (specialteEtudiant.getAnneeAcademique().getId() == yearID) {
                    currentList.add(etudiant);
                }
            }

            System.out.println("==========nbreSpecialite pour l'etudiant" + specisEtudiants.size());

        }
        return currentList;
    }

    public List<Departement> getListDepartement() throws ServiceException {
        listDepartement = dep.findAll();
        return listDepartement;
    }

    public void setListDepartement(List<Departement> listDepartement) {
        this.listDepartement = listDepartement;
    }

    public void setRepartitionParSpecialite(CartesianChartModel repartitionParSpecialite) {
        this.repartitionParSpecialite = repartitionParSpecialite;
    }

    public List<Etudiant> getItems() {
        return items;
    }

    public void setItems(List<Etudiant> items) {
        this.items = items;
    }

    public List<Inscription> getInscriptions() {
        return inscriptions;
    }

    public void setInscriptions(List<Inscription> inscriptions) {
        this.inscriptions = inscriptions;
    }

    public void setNiveaux(List<Niveau> niveaux) {
        this.niveaux = niveaux;
    }

    public List<Specialite> getSpecialites(Long departementId) throws ServiceException {
        List<Specialite> listSpecialites = new ArrayList<Specialite>();
        try {
            sections = sec.findByDepartement(departementId);
            for (Section section : sections) {
                niveaus = niv.findBySection(section.getId());
                for (Niveau niveau : niveaus) {
                    specialites = spe.findByNiveau(niveau.getId());
                    for (Specialite specialite : specialites) {
                        listSpecialites.add(specialite);
                    }
                }
            }
        } catch (NullPointerException ex) {
            listSpecialites = new ArrayList<Specialite>();
        }
        return listSpecialites;
    }

    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
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

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public List<Departement> getDepartements() {
        return departements;
    }

    public void setDepartements(List<Departement> departements) {
        this.departements = departements;
    }

    public AnneeAcademique getAnneeAcademique() {
        return anneeAcademique;
    }

    public void setAnneeAcademique(AnneeAcademique anneeAcademique) {
        this.anneeAcademique = anneeAcademique;
    }

    public List<AnneeAcademique> getAnneeAcademiques() {
        return anneeAcademiques;
    }

    public void setAnneeAcademiques(List<AnneeAcademique> anneeAcademiques) {
        this.anneeAcademiques = anneeAcademiques;
    }

    public AnneeAcademique getAnneeAcademiqueSelect() {
        return anneeAcademiqueSelect;
    }

    public void setAnneeAcademiqueSelect(AnneeAcademique anneeAcademiqueSelect) {
        this.anneeAcademiqueSelect = anneeAcademiqueSelect;
    }

    public Specialite getSpecialiteSelect() {
        return specialiteSelect;
    }

    public void setSpecialiteSelect(Specialite specialiteSelect) {
        this.specialiteSelect = specialiteSelect;
    }

    public void setRepartitionParBac(CartesianChartModel repartitionParBac) {
        this.repartitionParBac = repartitionParBac;
    }

    public ISEtudiant getEtu() {
        return etu;
    }

    public void setEtu(ISEtudiant etu) {
        this.etu = etu;
    }

    public void setInsc(ISInscription insc) {
        this.insc = insc;
    }

    public void setAnAc(ISAnneeAcademique anAc) {
        this.anAc = anAc;
    }

    public ISInscription getInsc() {
        return insc;
    }

    public ISAnneeAcademique getAnAc() {
        return anAc;
    }

    public ISDepartement getDep() {
        return dep;
    }

    public void setDep(ISDepartement dep) {
        this.dep = dep;
    }

    public ISSection getSec() {
        return sec;
    }

    public void setSec(ISSection sec) {
        this.sec = sec;
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

    public Departement getDepartementSelect() {
        return departementSelect;
    }

    public void setDepartementSelect(Departement departementSelect) {
        this.departementSelect = departementSelect;
    }

    public ISSpecialiteEtudiant getsSpecialteEtudiant() {
        return sSpecialteEtudiant;
    }

    public void setsSpecialteEtudiant(ISSpecialiteEtudiant sSpecialteEtudiant) {
        this.sSpecialteEtudiant = sSpecialteEtudiant;
    }

    public List<SpecialiteEtudiant> getSpecisEtudiants() {
        return specisEtudiants;
    }

    public void setSpecisEtudiants(List<SpecialiteEtudiant> specisEtudiants) {
        this.specisEtudiants = specisEtudiants;
    }

    public List<Etudiant> getItemsNew() {
        return itemsNew;
    }

    public HashSet<String> getRegions() {
        return regions;
    }

    public void setItemsNew(List<Etudiant> itemsNew) {
        this.itemsNew = itemsNew;
    }

    public void setRegions(HashSet<String> regions) {
        this.regions = regions;
    }

    public void setHommesFemmes(PieChartModel hommesFemmes) {
        this.hommesFemmes = hommesFemmes;
    }

    public void setRegloPermierSem(PieChartModel regloPermierSem) {
        this.regloPermierSem = regloPermierSem;
    }

    public void setRegloSecondSem(PieChartModel regloSecondSem) {
        this.regloSecondSem = regloSecondSem;
    }

    public void setRegloFinAnnee(PieChartModel regloFinAnnee) {
        this.regloFinAnnee = regloFinAnnee;
    }

    public void setRepatitionParDepartement(CartesianChartModel repatitionParDepartement) {
        this.repatitionParDepartement = repatitionParDepartement;
    }

    public void setProportionParSpecialite(PieChartModel proportionParSpecialite) {
        this.proportionParSpecialite = proportionParSpecialite;
    }

    public void setRepatitionParSexeLicenceSpevialite(CartesianChartModel repatitionParSexeLicenceSpevialite) {
        this.repatitionParSexeLicenceSpevialite = repatitionParSexeLicenceSpevialite;
    }

    public void setRepartitionParBacAnnee(CartesianChartModel repartitionParBacAnnee) {
        this.repartitionParBacAnnee = repartitionParBacAnnee;
    }

    public void setRepartitionParBacDist(CartesianChartModel repartitionParBacDist) {
        this.repartitionParBacDist = repartitionParBacDist;
    }

    public void setRepartitionParRegionDep(CartesianChartModel repartitionParRegionDep) {
        this.repartitionParRegionDep = repartitionParRegionDep;
    }

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Long getAnneeId() {
        return anneeId;
    }

    public void setAnneeId(Long anneeId) {
        this.anneeId = anneeId;
    }

    public List<Etudiant> getListEtudiant() {
        return listEtudiant;
    }

    public void setListEtudiant(List<Etudiant> listEtudiant) {
        this.listEtudiant = listEtudiant;
    }

    public boolean isSpecialiteDisable() {
        return specialiteDisable;
    }

    public void setSpecialiteDisable(boolean specialiteDisable) {
        this.specialiteDisable = specialiteDisable;
    }

    public void setNiveaus(List<Niveau> niveaus) {
        this.niveaus = niveaus;
    }

    public AnneeAcademique getAnnee() {
        return annee;
    }

    public void setAnnee(AnneeAcademique annee) {
        this.annee = annee;
    }

    public List<AnneeAcademique> getListAnnee() throws ServiceException {
        listAnnee = anAc.findAll();
        return listAnnee;
    }

    public void setListAnnee(List<AnneeAcademique> listAnnee) {
        this.listAnnee = listAnnee;
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

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
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

    public boolean isAnneeDisable() {
        return anneeDisable;
    }

    public void setAnneeDisable(boolean anneeDisable) {
        this.anneeDisable = anneeDisable;
    }

    public boolean isModuleDisable() {
        return moduleDisable;
    }

    public void setModuleDisable(boolean moduleDisable) {
        this.moduleDisable = moduleDisable;
    }

    public boolean isMatiereDisable() {
        return matiereDisable;
    }

    public void setMatiereDisable(boolean matiereDisable) {
        this.matiereDisable = matiereDisable;
    }

    public boolean isBarDisable() {
        return barDisable;
    }

    public void setBarDisable(boolean barDisable) {
        this.barDisable = barDisable;
    }

    public boolean isDelibereDisable() {
        return delibereDisable;
    }

    public void setDelibereDisable(boolean delibereDisable) {
        this.delibereDisable = delibereDisable;
    }

    public boolean isStatusPannoDeliberation() {
        return statusPannoDeliberation;
    }

    public void setStatusPannoDeliberation(boolean statusPannoDeliberation) {
        this.statusPannoDeliberation = statusPannoDeliberation;
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

    public void processSection3() throws ServiceException {
        specialiteDisable = false;
        getSpecialites3();
    }

    public void processSection5() throws ServiceException {
        specialiteDisable = false;

        getSpecialites5();
    }

    public void processNiveau() throws ServiceException {
        specialiteDisable = false;
        getSpecialites();
    }

    public List<Niveau> getNiveaus() throws ServiceException {

        try {

            niveaus = niv.findBySection(sectionId);
        } catch (NullPointerException ex) {

            niveaus = new ArrayList<Niveau>();
        }

        return niveaus;
    }

    public List<Specialite> getSpecialites() throws ServiceException {

        try {
            specialites = spe.findByNiveau((long) level);
        } catch (NullPointerException ex) {

            specialites = new ArrayList<Specialite>();
        }
        return specialites;
    }

    public List<Specialite> getSpecialites3() throws ServiceException {
        List<Niveau> ni = getNiveaus();
        for (Niveau niveau : ni) {
            if (niveau.getLevel() == 3) {
                levelId = niveau.getId();
                break;
            }
        }
        try {
            specialites3 = spe.findByNiveau((long) levelId);
        } catch (NullPointerException ex) {

            specialites3 = new ArrayList<Specialite>();
        }
        return specialites3;
    }

    public List<Specialite> getSpecialites5() throws ServiceException {
        List<Niveau> ni = getNiveaus();
        for (Niveau niveau : ni) {
            if (niveau.getLevel() == 5) {
                levelId = niveau.getId();
                break;
            }
        }

        try {
            specialites5 = spe.findByNiveau(levelId);
        } catch (NullPointerException ex) {

            specialites5 = new ArrayList<Specialite>();
        }
        return specialites5;
    }

    public ISEtudiantSection getEtudiantSection() {
        return etudiantSection;
    }

    public void setEtudiantSection(ISEtudiantSection etudiantSection) {
        this.etudiantSection = etudiantSection;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getChoix() {
        return choix;
    }

    public void setChoix(int choix) {
        this.choix = choix;
    }

    public Map<String, Long> getNiveauxAll() throws ServiceException {
         niveauxAll= NivUtil.getLevel(getNiveaus());
        return niveauxAll;
    }
    
    
}
