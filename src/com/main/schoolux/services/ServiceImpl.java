package com.main.schoolux.services;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.Collection;

public abstract class ServiceImpl<T> implements Service<T> {


    private final static Logger LOG = Logger.getLogger(ServiceImpl.class);
    protected EntityManager em;


    public ServiceImpl(){}

    public ServiceImpl(EntityManager em) {
        this.em = em;
    }






    public abstract boolean alreadyExist(T t);

    public abstract T selectOneByIdOrNull(int id);

    public abstract Collection<T> selectAllOrNull();

    public void insert(T t) {
        LOG.debug("Insert " + t.toString());
        em.persist(t);

    }

    public void insertAndFlush(T t) {
        LOG.debug("Insert and flush " + t.toString());
        em.persist(t);
        em.flush();


    }

    public void update(T t) {
        LOG.debug("Update :" + t.toString());
        em.merge(t);

    }

    public void delete(T t) {
        LOG.debug("Delete :" + t.toString());
        em.remove(t);

    }

    /*
    public abstract void deleteLogically (T t); // modifiera le boolean field isActive d'un objet et call la méthode update (T t) qui fait le merge()

    On met cette methode dans l'interface courante dans le cas où toutes les tables ont un champ isActive afin de simuler une suppression logique sans supprimer réellement le record
    Si des tables ne l'ont pas, les services liées à ces tables seront obligé de définir la méthode qui ne servira jamais vu que tous les services implémentent l'interface courante

     */




    // ATTENTION : LES 3 METHODES CI-DESSOUS ETAIENT INUTILISABLES
    // la transaction doit être generées dans le controlleur + gérer le rollback car une seule transaction peut être composée d'appels à plusieurs methods insert update etc
    // La transaction est donc crée en dehors du Service, et englobe les appels aux methodes des services
    // Hors ici, la transaction serait totalement interne à une méthode d'un service ==> mauvais
    // C est aussi le controlleur qui fournit l'entity manager en argument à la création du service

    /*
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
     */

}
