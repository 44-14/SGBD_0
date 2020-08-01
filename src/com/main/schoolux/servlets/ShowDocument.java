package com.main.schoolux.servlets;


import com.JPAutil.JPAutil;
import com.entities.DocumentEntity;
import com.main.schoolux.form.DocumentForm;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ShowDocument", urlPatterns = "/showDocuments")
public class ShowDocument extends HttpServlet {

    EntityManager em = JPAutil.createEntityManager("projetecole");
    public List<DocumentEntity> document;
    private static Logger log = Logger.getLogger(ReadUser.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //recuperer les choix (update ou delete) ainsi que l'id du bouton caché
        DocumentForm documentForm = new DocumentForm();
        documentForm.updateOrDeleteDocument(request);

        log.info("updateOrDELETE result: "+ documentForm.updateOrDeleteDocument(request));
        if ("updateDoc".equals(documentForm.updateOrDeleteDocument(request))){
            log.debug("ici, on est dans le if updateDoc dans ");

            //request.getRequestDispatcher("/WEB-INF/updateDocuments.jsp").forward(request, response);
            response.sendRedirect("updateDocuments");

           // this.getServletContext().getRequestDispatcher("/WEB-INF/updateDocuments.jsp").forward(request, response);
        } else if("deleteDoc".equals(documentForm.updateOrDeleteDocument(request))){
            log.debug("ici, on est dans le if deleteDoc");
            response.sendRedirect("deleteDocuments");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManager em = JPAutil.createEntityManager("PersistUnit_schoolUX");

       // DocumentService documentService = new DocumentService();

       // request.setAttribute( "documents", document );

        //lister les documents de la DB actif
        Query query = em.createNamedQuery("Document.findAllActive");
        List<DocumentEntity> list = query.getResultList();
        //JE RECUPERE BIEN MA LISTE DE DOCUMENTS, reste à l'envoyer sur la jsp
        //request.setAttribute("documents", list);
        request.setAttribute("listDoc", list);
        log.info("Complete query size: {}"+list);



        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/showDocuments.jsp");
        rd.forward(request, response);

    }
}
