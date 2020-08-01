package com.main.schoolux.services;

import com.entities.EventEntity;
import com.entityFinder.EntityFinder;
import com.entityFinder.EntityFinderImpl;
import com.main.schoolux.servlets.ReadUser;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

public class EventService {
    private static Logger log = Logger.getLogger(ReadUser.class);


    public void createEvents(EntityManager em, EventEntity event) {
        em.persist(event);
        em.merge(event);

    }

    public void updateEvent(EntityManager em, EventEntity event) {
        log.info("begin - updateEventEntityervice");

      //  em.createNamedQuery("Event.updateById");
        // em.persist(event);
        em.merge(event);
        log.info("end - updateEventService");
    }

    public void deleteEvent(EntityManager em, EventEntity event) {
        log.info("deleteEventEntityService - begin");
        //em.createNamedQuery("Event.updateById");
        em.merge(event);
        log.info("deleteEventEntityervice - end");
    }


    public EventEntity getReadEventById() {
        EntityFinder finder = new EntityFinderImpl<EventEntity>();
        EventEntity event = new EventEntity();
        event = (EventEntity) finder.findOne(event, 1);

        return event;
    }

    public List<EventEntity> readAllEventEntity() {
        EntityFinder finder = new EntityFinderImpl<EventEntity>();
        EventEntity event = new EventEntity();
        List<EventEntity> listEvent = finder.findByNamedQuery("Event.findAll", event, null);

        return listEvent;
    }
}