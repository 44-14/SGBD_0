package com.main.schoolux.servlets;


import com.JPAutil.JPAutil;
import com.entities.DocumentEntity;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreateDocument", urlPatterns = "/createDocuments")
public class CreateDocument extends HttpServlet {
    private static Logger log = Logger.getLogger(ReadUser.class);

    private EntityManager em;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
log.info("doPost CreateDocuments servlet - begin");
        DocumentService docServ = new DocumentService();
        EntityManager em = JPAutil.createEntityManager("schoolux");
        /* Préparation de l'objet formulaire */
        DocumentForm form = new DocumentForm();
        HttpSession session = request.getSession();
        String error = null;

        EntityTransaction et = null;

        //////////////////////////

            /////////////////////////
            /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
            DocumentEntity doc = form.createDoc(request);

            error = (String) session.getAttribute("error");
            log.debug("affichage d'un message d'erreur en serfvlet " + error);

            if(error != null){
                response.sendRedirect("createDocuments");
            }else {
                try {
                    log.info("try begin");
                    et = em.getTransaction();
                    log.info("transaction begin");
                    et.begin();
                    log.info("creation begin");
                    docServ.createDocument(em, doc);
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
                    // em.close();
                    log.debug("Close em : Ok");
                }
            }

}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
log.info("debut du doGet createDoc");
        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/JSP/createDocuments.jsp");
        rd.forward(request, response);
log.info("end doget createDoc");
    }
}
