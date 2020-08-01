package com.main.schoolux.servlets;


import com.persistence.JPAutil.JPAutil;
import com.persistence.entities.DocumentEntity;
import com.persistence.entityFinder.EntityFinderImpl;
import com.main.schoolux.form.DocumentForm;
import com.main.schoolux.services.DocumentService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "deleteDocument",  urlPatterns = "/deleteDocuments")
public class deleteDocument extends HttpServlet {
    private static Logger log = Logger.getLogger(ReadUser.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        log.info("doPost deleteDocument servlet - begin");
        DocumentService docServ = new DocumentService();
        EntityManager em = JPAutil.createEntityManager("PersistUnit_schoolUX");
        /* Préparation de l'objet formulaire */
        DocumentForm form = new DocumentForm();

        EntityTransaction et = null;
        String choice = form.yesOrNo(request);
        log.debug("reponse du oui ou non : "+choice);
        DocumentEntity doc = form.deleteLogic(request);
        log.debug("after reponse du oui ou non ");
       // String choice = form.yesOrNo(request);
       if(choice != null) {
           log.debug("vegin - if yes servlet delete");

           try {
               log.info("try begin");
               et = em.getTransaction();
               log.info("transaction begin");
               et.begin();
               log.info("delete begin");
               docServ.deleteDocument(em, doc);
               log.info("commit");
               et.commit();
               log.info("end try");
           } catch (Exception e) {
               log.info("debut du rollback");
               log.debug(e);
           } finally {
               if (et != null && et.isActive()) {
                   et.rollback();
               }
           }
           //}else if ("no".equals(form.yesOrNo(request))){
           // response.sendRedirect("showDocuments");
       }
        //}

        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
       // Document doc = form.updateDoc( request );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DocumentService documentService = new DocumentService();
        EntityFinderImpl efi = new EntityFinderImpl();

        //test OK pour ces 2 lignes
        DocumentEntity doc = documentService.getReadDocById();
        request.getSession().setAttribute("monDocument", doc);

        DocumentEntity document = new DocumentEntity();
 /*  a test ici
        EntityManager em = util.JPAutil.createEntityManager("projetecole");
        document = (Document) em.createNamedQuery("Document.findByName");
        request.getSession().setAttribute("documentID", document);
*/
//ca fonctionne aussi ces 2 lignes
        DocumentEntity docID = (DocumentEntity) efi.findOne(document, 5);
        request.getSession().setAttribute("docFindOne", docID);

        this.getServletContext().getRequestDispatcher("/WEB-INF/deleteDocuments.jsp").forward(request, response);
    }
}
