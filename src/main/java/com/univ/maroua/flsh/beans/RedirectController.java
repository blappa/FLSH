package com.univ.maroua.flsh.beans;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RedirectController {

    public RedirectController() {
    }

   @RequestMapping(value = "/a0", method = RequestMethod.GET)
    public String p0(ModelMap model) {
        return "licence";
    }

    @RequestMapping(value = "/a1", method = RequestMethod.GET)
    public String p1(ModelMap model) {
        return "pages/etudiants/index";
    }

    @RequestMapping(value = "/a2", method = RequestMethod.GET)
    public String p2(ModelMap model) {
        return "pages/matieres/index";
    }

    @RequestMapping(value = "/a3", method = RequestMethod.GET)
    public String p3(ModelMap model) {
        return "pages/notes/index";
    }

    @RequestMapping(value = "/a4", method = RequestMethod.GET)
    public String p4(ModelMap model) {
        return "pages/importations/index";
    }

    @RequestMapping(value = "/a5", method = RequestMethod.GET)
    public String p5(ModelMap model) {
        return "pages/exportations/index";
    }

    @RequestMapping(value = "/a7", method = RequestMethod.GET)
    public String p6(ModelMap model) {
        return "pages/modules/index";
    }

    @RequestMapping(value = "/a9", method = RequestMethod.GET)
    public String p8(ModelMap model) {
        return "pages/etudiants/etudiant/index";
    }

    
    
    @RequestMapping(value = "/a10", method = RequestMethod.GET)
    public String p9(ModelMap model) {
        return "pages/etudiants/profil/index";
    }

    @RequestMapping(value = "/a11", method = RequestMethod.GET)
    public String p10(ModelMap model) {
        return "pages/etudiants/blanchir/index";
    }

    @RequestMapping(value = "/a12", method = RequestMethod.GET)
    public String p11(ModelMap model) {
        return "pages/exportations/etudiants/preinscrit/index";
    }

    @RequestMapping(value = "/a13", method = RequestMethod.GET)
    public String p12(ModelMap model) {
        return "pages/exportations/etudiants/carte/index";
    }

    @RequestMapping(value = "/a14", method = RequestMethod.GET)
    public String p13(ModelMap model) {
        return "pages/exportations/etudiants/certificat/index";
    }

    @RequestMapping(value = "/a15", method = RequestMethod.GET)
    public String p14(ModelMap model) {
        return "pages/exportations/etudiants/quitus/index";
    }

    @RequestMapping(value = "/a16", method = RequestMethod.GET)
    public String p15(ModelMap model) {
        return "pages/etudiants/inscription/index";
    }

    @RequestMapping(value = "/a17", method = RequestMethod.GET)
    public String p16(ModelMap model) {
        return "pages/etudiants/suggestion/index";
    }

    @RequestMapping(value = "/a18", method = RequestMethod.GET)
    public String p17(ModelMap model) {
        return "pages/exportations/notes/releves/global/index";
    }

    @RequestMapping(value = "/a19", method = RequestMethod.GET)
    public String p18(ModelMap model) {
        return "pages/exportations/notes/releves/personnel/index";
    }

    @RequestMapping(value = "/a20", method = RequestMethod.GET)
    public String p19(ModelMap model) {
        return "pages/exportations/notes/attestation/global/index";
    }

    @RequestMapping(value = "/a21", method = RequestMethod.GET)
    public String p20(ModelMap model) {
        return "pages/exportations/notes/attestation/personnel/index";
    }

    @RequestMapping(value = "/a22", method = RequestMethod.GET)
    public String p22(ModelMap model) {
        return "pages/exportations/matieres/pdf/index";
    }
    
    @RequestMapping(value = "/a222", method = RequestMethod.GET)
    public String p222(ModelMap model) {
        return "pages/exportations/matieres/excel/index";
    }
    
    @RequestMapping(value = "/a223", method = RequestMethod.GET)
    public String p223(ModelMap model) {
        return "pages/notes/requette/index";
    }

    @RequestMapping(value = "/a23", method = RequestMethod.GET)
    public String p23(ModelMap model) {
        return "pages/importations/notes/specialite/index";
    }

    @RequestMapping(value = "/a24", method = RequestMethod.GET)
    public String p25(ModelMap model) {
        return "pages/importations/importation/notes/complet/index";
    }

    @RequestMapping(value = "/a25", method = RequestMethod.GET)
    public String p26(ModelMap model) {
        return "pages/importations/notes/matiere/index";
    }

    @RequestMapping(value = "/a26", method = RequestMethod.GET)
    public String p27(ModelMap model) {
        return "pages/importations/etudiants/inscrit/index";
    }

    @RequestMapping(value = "/a27", method = RequestMethod.GET)
    public String p28(ModelMap model) {
        return "pages/importations/etudiants/payes/index";
    }

    @RequestMapping(value = "/a28", method = RequestMethod.GET)
    public String p29(ModelMap model) {
        return "pages/importations/matiere/index";
    }

    @RequestMapping(value = "/a29", method = RequestMethod.GET)
    public String p30(ModelMap model) {
        return "pages/importations/etudiants/update/index";
    }

    @RequestMapping(value = "/a30", method = RequestMethod.GET)
    public String p31(ModelMap model) {
        return "pages/importations/etudiants/preinscrit/index";
    }

    @RequestMapping(value = "/a31", method = RequestMethod.GET)
    public String p32(ModelMap model) {
        return "pages/importations/importation/etudiants/update/index";
    }

    @RequestMapping(value = "/a32", method = RequestMethod.GET)
    public String p33(ModelMap model) {
        return "pages/modules/matiere/index";
    }

    @RequestMapping(value = "/a33", method = RequestMethod.GET)
    public String p34(ModelMap model) {
        return "pages/matieres/matiereOptionnnelle/index";
    }

    @RequestMapping(value = "/a34", method = RequestMethod.GET)
    public String p35(ModelMap model) {
        return "pages/modules/matiere/module/index";
    }

    @RequestMapping(value = "/a35", method = RequestMethod.GET)
    public String p36(ModelMap model) {
        return "pages/modules/departement/index";
    }

    @RequestMapping(value = "/a36", method = RequestMethod.GET)
    public String p37(ModelMap model) {
        return "pages/modules/section/index";
    }

    @RequestMapping(value = "/a37", method = RequestMethod.GET)
    public String p38(ModelMap model) {
        return "pages/modules/niveau/index";
    }

    @RequestMapping(value = "/a38", method = RequestMethod.GET)
    public String p39(ModelMap model) {
        return "pages/modules/specialite/index";
    }

    @RequestMapping(value = "/a39", method = RequestMethod.GET)
    public String p40(ModelMap model) {
        return "pages/modules/specialite/special/index";
    }

    @RequestMapping(value = "/a40", method = RequestMethod.GET)
    public String p41(ModelMap model) {
        return "pages/modules/specialiteEtudiant/index";
    }

    @RequestMapping(value = "/a41", method = RequestMethod.GET)
    public String p42(ModelMap model) {
        return "pages/importations/etudiants/spchange/index";
    }

    @RequestMapping(value = "/a42", method = RequestMethod.GET)
    public String p43(ModelMap model) {
        return "pages/modules/anneeAcademique/index";
    }

    @RequestMapping(value = "/a43", method = RequestMethod.GET)
    public String p44(ModelMap model) {
        return "pages/modules/semestre/index";
    }

    @RequestMapping(value = "/a44", method = RequestMethod.GET)
    public String p45(ModelMap model) {
        return "pages/modules/utilisateur/index";
    }

    @RequestMapping(value = "/a45", method = RequestMethod.GET)
    public String p46(ModelMap model) {
        return "pages/notes/note/index";
    }

    @RequestMapping(value = "/a46", method = RequestMethod.GET)
    public String p47(ModelMap model) {
        return "pages/notes/note/all/index";
    }

    @RequestMapping(value = "/a47", method = RequestMethod.GET)
    public String p48(ModelMap model) {
        return "pages/notes/reussite/index";
    }

    @RequestMapping(value = "/a48", method = RequestMethod.GET)
    public String p49(ModelMap model) {
        return "pages/notes/admis/index";
    }

    @RequestMapping(value = "/a49", method = RequestMethod.GET)
    public String p50(ModelMap model) {
        return "pages/notes/chevauche/index";
    }

    @RequestMapping(value = "/a50", method = RequestMethod.GET)
    public String p51(ModelMap model) {
        return "pages/notes/semestre/index";
    }

    @RequestMapping(value = "/a51", method = RequestMethod.GET)
    public String p52(ModelMap model) {
        return "pages/notes/annuelle/index";
    }

    @RequestMapping(value = "/a52", method = RequestMethod.GET)
    public String p59(ModelMap model) {
        return "pages/statistiques/diagramme/index";
    }

    @RequestMapping(value = "/a53", method = RequestMethod.GET)
    public String p60(ModelMap model) {
        return "pages/statistiques/rapport/index";
    }
    
    @RequestMapping(value = "/a54", method = RequestMethod.GET)
    public String p54(ModelMap model) {
        return "pages/statistiques/index";
    }

    @RequestMapping(value = "/a57", method = RequestMethod.GET)
    public String p56(ModelMap model) {
        return "pages/etudiants/deleteEtudiant/index";
    }

    @RequestMapping(value = "/a8", method = RequestMethod.GET)
    public String p58(ModelMap model) {
        return "pages/aide/index";
    }
    
    @RequestMapping(value = "/a61", method = RequestMethod.GET)
    public String p61(ModelMap model) {
        return "pages/changePwd/changePwd";
    }
    
    @RequestMapping(value = "/a62", method = RequestMethod.GET)
    public String p62(ModelMap model) {
        return "pages/notes/synthese/annuelle/index";
    }
    
    @RequestMapping(value = "/a63", method = RequestMethod.GET)
    public String p63(ModelMap model) {
        return "pages/notes/synthese/semestrielle/index";
    }
    
    @RequestMapping(value = "/a64", method = RequestMethod.GET)
    public String p64(ModelMap model) {
        return "pages/notes/valider/index";
    }
    
    @RequestMapping(value = "/a65", method = RequestMethod.GET)
    public String p65(ModelMap model) {
        return "pages/notes/synthese/index";
    }   
    
    @RequestMapping(value = "/a66", method = RequestMethod.GET)
    public String p66(ModelMap model) {
        return "/pages/modules/logger/logger";
    } 
    
     @RequestMapping(value = "/a67", method = RequestMethod.GET)
    public String p67(ModelMap model) {
        return "/pages/langue/langue";
    }
     
      @RequestMapping(value = "/a68", method = RequestMethod.GET)
    public String p68(ModelMap model) {
        return "/pages/sms/index";
    }
}