package com.main.schoolux.services;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class ServiceImpl<T> implements Service<T> {


    private final static Logger LOG = Logger.getLogger(ServiceImpl.class);
    protected EntityManager em;



    public ServiceImpl(EntityManager em) {
        this.em = em;
    }





    public abstract boolean alreadyExist(T t);

    public abstract T selectOneByIdOrNull(int id);

    public abstract Collection<T> selectAllOrNull();


    // ATTENTION
    // les transactions doivent etre generées dans le controlleur + gérer le rollback
    // C est aussi le controlleur qui fournit l'entity manager en argument lors de l'appel du service
    public void insert(T t) {
        LOG.debug("Insert " + t.toString());
        em.getTransaction().begin();
        em.persist(t);
        em.getTransaction().commit();
    }

    public void update(T t) {
        LOG.debug("Update :" + t.toString());
        em.getTransaction().begin();
        em.merge(t);
        em.getTransaction().commit();
    }

    public void delete(T t) {
        LOG.debug("Delete :" + t.toString());
        em.getTransaction().begin();
        em.remove(t);
        em.getTransaction().commit();
    }
}
