package com.main.schoolux.form;


import com.persistence.entities.EventEntity;
import com.persistence.entityFinder.EntityFinderImpl;
import com.main.schoolux.servlets.ReadUser;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;


public class EventForm {
    private static Logger log = Logger.getLogger(ReadUser.class);

    public EventEntity createEvent(HttpServletRequest request){
        log.info("createEventForm - begin");
        EventEntity event = new EventEntity();
        HttpSession session = request.getSession();
        String error = null;


        SimpleDateFormat sdf = new SimpleDateFormat();
        log.info("begin-request");
        //request
        String name = request.getParameter("name");
        Date beginDate = Date.valueOf(request.getParameter("beginDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        log.debug("end OF DATE and begin of hours");
        String beginHour = request.getParameter("beginHour");
        String endHour = request.getParameter("endHour");
        log.debug("end of hour ");
        String descritption = request.getParameter("description");
        String url = request.getParameter("urlAttachment");

        log.info("beginDate " + beginDate);
        log.info("beginHour :"+beginHour);
        log.info("endHour : "+endHour);

        beginHour = beginHour + ":00";
        log.info("beginHour aprs concatenattion" + beginHour);
        endHour = endHour + ":00";


        Time beginHours = Time.valueOf(beginHour);
        log.info("beginHour aprs cst en time" + beginHour);
        Time endHours = Time.valueOf(endHour);
        log.info("set event");

        //verificate before set
        if(name != null && beginDate != null && endDate != null && beginHours != null && endHour != null && descritption != null){
            event.setName(name);
            event.setBeginDate(beginDate);
            event.setEndDate(endDate);
            event.setBeginHour(beginHours);
            event.setEndHour(endHours);
            event.setDescription(descritption);
            event.setAttachmentUrl(url);
        } else{
          error = "Veuillez remplir tous les champs demandés";
          session.setAttribute("error", error);
        }


        log.info("createEvent - end");
        return event;

    }

    public EventEntity updateEvent(HttpServletRequest request){
        log.info("updateEventForm - begin");
        EventEntity event = new EventEntity();
        EntityFinderImpl efi = new EntityFinderImpl();
//je veux l'objet event entier ou l'id de l'objet est 1
        event = (EventEntity) efi.findOne(event, 1);

        String name = request.getParameter("updateNameEvent");
        log.debug("nom event modif : " + name);
        event.setName(name);
        String id = request.getParameter("idEventUpdate");
        log.info("updateEventForm - end");
        return event;
    }
    public EventEntity deleteLogic(HttpServletRequest request){
        log.debug("deleteLogicForm begin");

        EventEntity Event = new EventEntity();
        EntityFinderImpl efi = new EntityFinderImpl();
        int error = 0;

        //recuperate of the id for delete a Event
        //int id = Integer.parseInt(request.getParameter("idEventDelete"));
        log.debug("l'id recupere depuis notre page ShowEvent dans le delete est : "+ 1);
        Event = (EventEntity) efi.findOne(Event, 1);
        //request

        Event.setName("ANNULE");
        log.info("nom: "+Event.getName());

        log.info("deleteEvent - end");
        return Event;
    }
/*
    public Event changeActif(HttpServletRequest request){
        Event Event = new Event();
        //Event.setValide();
    }
*/

    /**
     Methodes for Events
     */

    public String updateOrDeleteEvent(HttpServletRequest request){
        HttpSession session = request.getSession();
        int idEvent = 0;

        String type;
        String format;
        //request
        String update = request.getParameter("updateEvent");
        String delete = request.getParameter("deleteEvent");

        //condition
        if(update != null){
            log.info("choix update: "+ update);
            //recuperate idEventUptade from ShowEvent.jsp
            idEvent = Integer.parseInt(request.getParameter("idEventUpdate"));
            //put idEventUpdate in a session for use later in updateEvents
            session.setAttribute("idEventUpdate", idEvent);

            //recuperate the id is OK, now, go recuperate the other(label...)
            String labelEvent = request.getParameter("labelEventUpdate");
            log.debug("labelEventUpdate dans le form "+labelEvent );
            session.setAttribute("labelEventUpdate", labelEvent);
            type = request.getParameter("typeEventUpdate");
            session.setAttribute("typeEventUpdate", type);
        }else if (delete != null){
            log.info("choix delete: "+ delete);
            idEvent = Integer.parseInt(request.getParameter("idEventDelete"));
            session.setAttribute("idEventDelete", idEvent);
        }


        String choice = null;
        // log.info("choix update: "+ update);
        // log.info("choix delete: "+ delete);
        log.info("id du Event : "+ idEvent);


        if (delete != null){
            choice = delete;

            //on doit envoyer le delete a la servlet
        }else if (update != null){
            choice = update;
            //on doit envoyer a la servlet update
        }

        return choice;
    }

    public String yesOrNo(HttpServletRequest request){
        String choice = null;
        String radio = request.getParameter("choiceDelete");
        // String no = request.getParameter("no");
        log.info("string radio: "+radio);
        //log.info("string no: "+no);

        if(radio.equals("yes") && radio != null){
            choice = "yes";
        } else if ( radio.equals("no") && radio != null){
            choice = "no";
        }

        return choice;

    }
}
