package com.main.schoolux.servlets;



import com.persistence.JPAutil.JPAutil;
import com.persistence.entities.EventEntity;
import com.main.schoolux.form.EventForm;
import com.main.schoolux.services.EventService;
import org.apache.log4j.Logger;


import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CreateEvent",urlPatterns = "/createEvents")
public class CreateEvent extends HttpServlet {
    private static Logger log = Logger.getLogger(ReadUser.class);
    private EntityManager em;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("begin - doPost createEvent");
        EventService eventServ = new EventService();
        log.debug("appel de l'entityManager via JPAutil");
       EntityManager em = JPAutil.createEntityManager("PersistUnit_schoolUX");
        log.debug("appel effectué");
        /* Préparation de l'objet formulaire */
        EventForm eventForm = new EventForm();
        HttpSession session = request.getSession();


        EntityTransaction et = null;

        /* Appel au traitement et à la validation de la requête, et récupération du bean en résultant */
        EventEntity event = eventForm.createEvent(request);

            try {
                log.info("try begin");
                et = em.getTransaction();
                log.info("transaction begin");
                et.begin();
                log.info("creation begin");
                eventServ.createEvents(em,event);
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

        log.info("end - doPost createEvent");
        this.getServletContext().getRequestDispatcher("/WEB-INF/createEvents.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("begin doGet createEvent");
        this.getServletContext().getRequestDispatcher("/WEB-INF/createEvents.jsp").forward(request, response);
        log.info("doget createEvent - end");
    }
}
