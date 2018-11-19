/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.univ.maroua.flsh.test;

import com.douwe.generic.dao.DataAccessException;
import com.douwe.generic.dao.impl.GenericDao;
import com.univ.maroua.flsh.dao.IEtudiantdao;
import com.univ.maroua.flsh.dao.Impl.IEtudiantdaoImpl;
import com.univ.maroua.flsh.entities.Departement;
import com.univ.maroua.flsh.entities.Etudiant;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author lateu
 */
public class TestDao {
        
    private static EntityManagerFactory emf;
    private static EntityManager em ;
    private static IEtudiantdao etudiantdao;
    
    public static void main(String[] args) throws DataAccessException {
        // Contexte de persistance
        emf = Persistence.createEntityManagerFactory("FLSH");
        // on récupère un EntityManager à partir de l'EntityManagerFactory précédent
        em= emf.createEntityManager();
        etudiantdao=new IEtudiantdaoImpl();
         ((GenericDao)etudiantdao).setManager(em);
     
// début transaction
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        
        // enregistrememnt d'une filiere
//        Specialite specialite =new Specialite();
//        specialite.setCycle1(1);
//        specialite.setNom("maintenance informatique");
//        Departement da = departementDao.findByAbr("INFOTEL");
//        da.setNom("informatique et telecommunications");
//        da.setAbreviation("INFOTEL");
//         Etablissement ea = new Etablissement();
//        ea.setNom("institut superieur du sahel");
//        ea.setSigle("ISS");
//        da.setEtablissement(ea);
       
       
       
        
        //rechercher un departement a travers son abbreviation
//          try {
//            Departement departement=departementDao.findByAbr("TRAMARH");
//                    System.out.println("le departement TRAMARH a pour caracteristiques \n "+departement);
//        } catch (DataAccessException ex) {
//            System.out.println("attention erreur"+ex.getMessage());
//        }
//          
//          // rechercher un departement a travers son nom
//        try {
//            Departement departement=departementDao.findByNom("informatique et telecommunications");
//                    System.out.println("le departement informatique et telecomunications a pour caracteristiques \n "+departement);
//        } catch (DataAccessException ex) {
//            System.out.println("attention erreur"+ex.getMessage());
//        }
//        
//        // retrouver les departements d'un etablissement
//        try {
//            List<Departement> departements=departementDao.findByEtablissement("ISS");
//                    System.out.println("les departmenets de l'ISS sont");
//                    for (Departement dep:departementDao.findByEtablissement("ISS")) {
//                System.out.println(dep);
//            }
//        } catch (DataAccessException ex) {
//            System.out.println("attention erreur"+ex.getMessage());
//        }
//        
         System.out.println(etudiantdao.haveDoneSpecialiteAnnee(4494L, 100L, 2L));
     
// fin transaction
        tx.commit();
// fin EntityManager
        em.close();
// fin EntityMangerFactory
        emf.close();


    }
    
}
