/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.beans;

import com.douwe.generic.dao.DataAccessException;
import com.univ.maroua.flsh.entities.Matiere;
import com.univ.maroua.flsh.projection.LoggerModel;
import com.univ.maroua.flsh.projection.Releve;
import com.univ.maroua.flsh.service.Impl.ServiceException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author richard@lateu
 */
@ManagedBean(name = "loggerBean")
@RequestScoped
@ViewScoped
public class LoggerBean {

    private List<LoggerModel> loggerModels;
    private LoggerModel loggerModel;
    JasperPrint jasperPrint;
    private ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
    private String path = servletContext.getRealPath("") + File.separator + "print";
    private String loggerPath = servletContext.getRealPath("");

    public List<LoggerModel> getLoggerModels() {

        return loggerModels;
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

    public void init() throws JRException, DataAccessException, FileNotFoundException, IOException {
        List<LoggerModel> list = new ArrayList<>();
        LoggerModel logm = new LoggerModel();
        loggerPath = loggerPath.replaceAll("webapps", "-");
        StringTokenizer tok = new StringTokenizer(loggerPath, "-");
        loggerPath = tok.nextToken();
        loggerPath += "bin" + File.separator + "readme.log";
        File f ;
            FileReader fr;
            BufferedReader br ;
        try {
            f = new File(loggerPath);
            fr = new FileReader(f);
            br = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            //on cree d'abord
            FileWriter fileWriter = new FileWriter(loggerPath, false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.flush();
            bufferedWriter.close();
            
            f = new File(loggerPath);
            fr = new FileReader(f);
            br = new BufferedReader(fr);         
        }
        try {

            String ligne;
            String jour,heure;
            while ((ligne = br.readLine()) != null) {
                System.out.println("==================ligne" + ligne);
                String date, UserUtil, action;
                date = ligne.substring(0, 19);
                jour=date.substring(0, 10).trim();
                heure=date.substring(10, 19).trim();
                ligne = ligne.replace(date, "");
                int n = ligne.indexOf("#");
                String tmp = ligne.substring(0, n + 12);
                ligne = ligne.replace(tmp, "");
                UserUtil = ligne.substring(1, ligne.indexOf("#"));
                ligne = ligne.replace(UserUtil, "");
                action = ligne.substring(9);
                logm.setJourop(jour);
                logm.setHeureop(heure);
                logm.setUtilisateur(UserUtil);
                logm.setTache(action);
                list.add(logm);
                logm = new LoggerModel();
                System.out.println("=======" + date + " ========" + UserUtil + " ========" + action + " ==========");
                //ligne=br.readLine();
            }
            br.close();
            fr.close();
        } catch (IOException exception) {
            System.out.println("===============erreur lors de la lecture" + exception.getMessage());

        }


        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(list);
        String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/print/logger/logger2.jasper");
        jasperPrint = JasperFillManager.fillReport(path, new HashMap(), beanCollectionDataSource);
    }

    public void XLS() throws JRException, IOException, DataAccessException {
        init();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("content-disposition", "attachment;filename=logger.xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter xlsxExporter = new JRXlsxExporter();

        export(xlsxExporter, servletOutputStream);

    }

//     public void PDF() throws JRException, IOException, DataAccessException {
//        init();
//        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
//        httpServletResponse.addHeader("content-disposition", "attachment;filename=logger.pdf");
//        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
//        JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
//    }
    public void setLoggerModels(List<LoggerModel> loggerModels) {
        this.loggerModels = loggerModels;
    }
}
