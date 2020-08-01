package com.main.schoolux.servlets;


import com.persistence.JPAutil.JPAutil;
import com.persistence.entities.DocumentEntity;
import com.persistence.entityFinder.EntityFinderImpl;
import com.main.schoolux.form.DocumentForm;
import com.main.schoolux.services.DocumentService;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UpdateDocument", urlPatterns = "/updateDocuments")
public class UpdateDocument extends HttpServlet {
    private static Logger log = Logger.getLogger(ReadUser.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("doPost CreateDocuments servlet - begin");
        DocumentService docServ = new DocumentService();
        EntityManager em = JPAutil.createEntityManager("PersistUnit_schoolUX");
        /* Préparation de l'objet formulaire */
        DocumentForm form = new DocumentForm();

        EntityTransaction et = null;


        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        DocumentEntity doc = form.updateDoc( request );

        try {
            log.info("try begin");
            et = em.getTransaction();
            log.info("transaction begin");
            et.begin();
            log.info("creation begin");
            docServ.updateDocument(em, doc);
            log.info("commit");
            et.commit();
            log.info("end try");
        } catch (Exception e) {
            log.info("debut du rollback");
            log.debug(e);
        }
        finally {
            if (et !=null && et.isActive()) {
                et.rollback();
            }
            // em.close();
            log.debug("Close em : Ok");
        }
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
       // Document docID = (Document) efi.findOne(document, 5);
        //request.getSession().setAttribute("docFindOne", docID);

        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/updateDocuments.jsp");
        rd.forward(request, response);
    }
}
