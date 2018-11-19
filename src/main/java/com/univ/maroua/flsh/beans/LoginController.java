package com.univ.maroua.flsh.beans;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.beans.util.JsfUtil;
import com.univ.maroua.flsh.entities.Autorisation;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.univ.maroua.flsh.entities.Utilisateur;
import com.univ.maroua.flsh.service.ISAutorisation;
import com.univ.maroua.flsh.service.ISUtilisateur;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@ManagedBean(name = "log")
@SessionScoped
@Controller
public class LoginController implements Serializable {

    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    public String path = servletContext.getRealPath("") + File.separator + "template" + File.separator;
    ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
    private ISAutorisation aut = (ISAutorisation) ctx.getBean("ISAutorisation");
    private ISUtilisateur uti = (ISUtilisateur) ctx.getBean("ISUtilisateur");
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass().getName());
    private static String nom;
    private String name;
    private String pwdmd5;
    private String pwd;
    private boolean test = true;
    private Autorisation autorisation;
    private Utilisateur utilisateur;
    private String licence1;
    private String licence2;
    private String codeIPMac;
    private String mac1;
    private String adressIp;
    private String username;

    public LoginController() {
        autorisation = new Autorisation();
        utilisateur = new Utilisateur();
    }

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView printWelcome(ModelMap model) throws DataAccessException, ParseException {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            name = user.getUsername();
            username = user.getUsername();
            pwd = user.getPassword();
            Utilisateur utilisateur = uti.findByUsername(name);
            nom = utilisateur.getName();
            model.addAttribute("username", nom);
            List<Autorisation> cc = aut.findByAll();
            System.out.println("PWD Original : " + pwd + " -- " + pwdmd5 + " PWD Local: " + utilisateur.getPassword());
            for (Autorisation au : cc) {
                if (utilisateur.getAutorisation().getId().equals(au.getId())) {
                    logger.info("#utilisateur=" + utilisateur.getUsername() + "#action=" + "connexion avec le niveau d'accès " + convertRole(utilisateur.getAutorisation().getAuthority()));
                    String lc1 = readFile(path + "lcs.txt");
                    String lc2 = getLicenceLocal();
                    System.out.println("\nLicence Original : " + lc1 + " Licence Local: " + lc2);
                    if (lc1 != null) {
                        if (lc1.compareTo(lc2) == 0) {
                            return new ModelAndView("/pages/welcome");
                        } else {
                            //return new ModelAndView("licence"); //normalement licence
                            //cette ligne est juste pour test
                            return new ModelAndView("/pages/welcome");
                        }
                    } else {
                        //return new ModelAndView("licence"); //normalement licence
                        //cette ligne est juste pour test
                        return new ModelAndView("/pages/welcome");
                    }
                }
            }
        } catch (ClassCastException ex) {
            return loginerror(model);

        } catch (java.lang.NullPointerException ex) {
            return loginerror(model);
        } catch (ServiceException ex) {
            return loginerror(model);
        } catch (IOException ex) {
            return loginerror(model);
        }
        return loginerror(model);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView doPage() {
        ModelAndView model = new ModelAndView();
        try {
            String lc1 = readFile(path + "lcs.txt");
            String lc2 = getLicenceLocal();
            if (lc1 != null) {
                if (lc1.compareTo(lc2) == 0) {
                    model.setViewName("index");
                    return model;
                } else {
                    model.setViewName("licence");
                    return model;
                }
            } else {
                model.setViewName("licence");
                return model;
            }
        } catch (IOException ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erreur lors de l'identification", ""));
        }
        model.setViewName("index");
        return model;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelMap model) {
        return new ModelAndView("index");
    }

    @RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
    public ModelAndView loginerror(ModelMap model) {
        model.addAttribute("error", "true");
        return new ModelAndView("index");

    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(ModelMap model) throws ServiceException {
        return new ModelAndView("index");

    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public ModelAndView accessDenied(ModelMap model) throws ServiceException {
        logout(model);
        return new ModelAndView("403");

    }

    public String printWelcome() throws ServiceException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        utilisateur = uti.findByUsername(user.getUsername());
        name = utilisateur.getName();
        return name;
    }

    public boolean infor() {
        try {
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Collection<GrantedAuthority> granted = ((User) obj).getAuthorities();
            Object[] granted1 = granted.toArray();
            String authority = ((GrantedAuthority) granted1[0]).getAuthority();
            if (authority.contains("ROLE_INFOR")) {
                test = true;
                return true;
            }
            return false;
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean stat() {
        try {
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Collection<GrantedAuthority> granted = ((User) obj).getAuthorities();
            Object[] granted1 = granted.toArray();
            String authority = ((GrantedAuthority) granted1[0]).getAuthority();
            if (authority.contains("ROLE_SCOLAR")) {
                test = false;
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean dep() {
        try {
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Collection<GrantedAuthority> granted = ((User) obj).getAuthorities();
            Object[] granted1 = granted.toArray();
            String authority = ((GrantedAuthority) granted1[0]).getAuthority();
            if (authority.contains("ROLE_DEP")) {
                test = false;
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    public boolean doyen() {
        try {
            Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Collection<GrantedAuthority> granted = ((User) obj).getAuthorities();
            Object[] granted1 = granted.toArray();
            String authority = ((GrantedAuthority) granted1[0]).getAuthority();
            if (authority.contains("ROLE_DOYEN")) {
                test = true;
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public String doLicence() throws IOException {
/*
        try {

            mac1 = getMacAddress();
            if (mac1 != null) {
                codeIPMac = "LLM2015ISS-" + mac1;
                licence2 = getEncodedMD5(codeIPMac);

                System.out.println(licence1 + " et " + licence2);

                if (licence1.equals(licence2)) {
                    writeFile(licence1, path + "lcs.txt");
                    JsfUtil.addSuccessMessage("Votre Licence Est Valide. Veuillez saisir vos cordonnées de connexion.");
                    return "index";
                } else {
                    JsfUtil.addErrorMessage("Licence Incorret");
                }

            } else {
                JsfUtil.addErrorMessage("Address Doesn't Exist");
            }
        } catch (NoSuchAlgorithmException ex) {
            JsfUtil.addErrorMessage("Erreur Cryptage MD5");
        }
        return null;
        */
                return "index";
    }

    public String getLicenceLocal() throws IOException {
        try {

            mac1 = getMacAddress();
            StringBuilder sb = new StringBuilder();
            codeIPMac = getEncodedMD5("LLM2015ISS-" + mac1);

            return codeIPMac;
        } catch (NoSuchAlgorithmException ex) {
        }
        return null;
    }

    public static String getEncodedMD5(String key) throws NoSuchAlgorithmException {
        byte[] uniqueKey = key.getBytes();
        byte[] hash = null;
        hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }

    public ISUtilisateur getAdministration() {
        return uti;
    }

    public void setAdministration(ISUtilisateur uti) {
        this.uti = uti;
    }

    public static String getNom() {
        return nom;
    }

    public static void setNom(String nom) {
        LoginController.nom = nom;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public ISAutorisation getAut() {
        return aut;
    }

    public void setAut(ISAutorisation aut) {
        this.aut = aut;
    }

    public ISUtilisateur getUti() {
        return uti;
    }

    public void setUti(ISUtilisateur uti) {
        this.uti = uti;
    }

    public Autorisation getAutorisation() {
        return autorisation;
    }

    public void setAutorisation(Autorisation autorisation) {
        this.autorisation = autorisation;
    }

    public boolean isTest() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
    }

    public String getLicence1() {
        return licence1;
    }

    public void setLicence1(String licence1) {
        this.licence1 = licence1;
    }

    public String getLicence2() {
        return licence2;
    }

    public void setLicence2(String licence2) {
        this.licence2 = licence2;
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getCodeIPMac() {
        return codeIPMac;
    }

    public void setCodeIPMac(String codeIPMac) {
        this.codeIPMac = codeIPMac;
    }

    public String getAdressIp() {
        return adressIp;
    }

    public void setAdressIp(String adressIp) {
        this.adressIp = adressIp;
    }

    private static void writeFile(String fileContent, String filePathOutput) {
        try {

            // BufferedWriter a besoin d'un FileWriter, les 2 vont ensemble, on
            // donne comme argument le nom du fichier et false signifie qu'on
            // écrase le contenu du fichier et qu'on ne fait pas d'append sur le
            // contenu.
            FileWriter fileWriter = new FileWriter(filePathOutput, false);

            // Le BufferedWriter output auquel on donne comme argument le
            // fileWriter écrase le contenu déjà présent dans le fichier.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // On écrit dans le fichier ou plutot dans le bufferedWriter qui
            // sert de tampon(stream)
            bufferedWriter.write(fileContent);

            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println("Fichier créé");
        } catch (IOException ioe) {
            System.err.println("Erreur levée de type IOException au niveau de la méthode " + "writeFile(...) : ");
            ioe.printStackTrace();
        }
    }// End writeFile(...)

    public static String readFile(String filePathInput) {

        Scanner scanner = null;
        String line = null;
        StringBuffer str = new StringBuffer();
        try {
            scanner = new Scanner(new File(filePathInput));

            // On boucle sur chaque champ detecté
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();

                if (line != null) {
                    str.append(line + "\r\n");
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Erreur levée de type FileNotFoundException"
                    + " au niveau de la méthode " + "readFile(...) : ");
            e.printStackTrace();
        }
        return line;
    }// End readFile(...)

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

    public String getPwdmd5() {
        return pwdmd5;
    }

    public void setPwdmd5(String pwdmd5) {
        this.pwdmd5 = pwdmd5;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMacAddress() {
        String line = null;
        String command = File.separator + "sbin" + File.separator + "ifconfig"; //commande pour linux et le mac
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            command = "ipconfig /all";
        }
        Pattern p = Pattern.compile("([a-fA-F0-9]{1,2}(-|:)){5}[a-fA-F0-9]{1,2}");
        try {
            Process pa = Runtime.getRuntime().exec(command);
            pa.waitFor();
            BufferedReader reader = new BufferedReader(new InputStreamReader(pa.getInputStream()));
            Matcher m;
            while ((line = reader.readLine()) != null) {
                m = p.matcher(line);
                if (!m.find()) {
                    continue;
                }
                line = m.group();
                break;
            }
        } catch (Exception e) {
        }

        return line;

    }

    public ApplicationContext getCtx() {
        return ctx;
    }

    public void setCtx(ApplicationContext ctx) {
        this.ctx = ctx;
    }

    public String convertRole(String role) throws ServiceException {
        String result = "";
        switch (role) {
            case "ROLE_DOYEN":
                result = "administrateur";
                break;
            case "ROLE_INFOR":
                result = "informaticien";
                break;
            case "ROLE_SCOLAR":
                result = "scolarité";
                break;
            case "ROLE_DEP":
                result = "departement";
                break;
        }
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
