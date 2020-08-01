package com.main.schoolux.servlets;

import com.JPAutil.JPAutil;
import com.entities.DocumentEntity;
import com.entities.EventEntity;
import com.entityFinder.EntityFinderImpl;
import com.main.schoolux.form.EventForm;
import com.main.schoolux.services.EventService;
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

@WebServlet(name = "UpdateEvent", urlPatterns = "/updateEvents")
public class UpdateEvent extends HttpServlet {
    private static Logger log = Logger.getLogger(ReadUser.class);

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("doPost UpdateEvent servlet - begin");
        EventService eventServ = new EventService();
        EntityManager em = JPAutil.createEntityManager("PersistUnit_schoolUX");
        /* Préparation de l'objet formulaire */
        EventForm form = new EventForm();

        EntityTransaction et = null;


        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        EventEntity event = form.updateEvent( request );

        try {
            log.info("try begin");
            et = em.getTransaction();
            log.info("transaction begin");
            et.begin();
            log.info("creation begin");
            eventServ.updateEvent(em, event);
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
        log.info("doPost end - updateEvent servlet");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EventService eventService = new EventService();
        EntityFinderImpl efi = new EntityFinderImpl();

        //test OK pour ces 2 lignes
        EventEntity event = eventService.getReadEventById();
        request.getSession().setAttribute("monEvent", event);

        DocumentEntity document = new DocumentEntity();

        RequestDispatcher rd=request.getRequestDispatcher("/WEB-INF/updateEvents.jsp");
        rd.forward(request, response);
    }
}
