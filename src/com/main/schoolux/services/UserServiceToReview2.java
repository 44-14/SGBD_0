package com.main.schoolux.services;

import org.apache.log4j.Logger;


import javax.persistence.EntityManager;
import com.persistence.entityFinderImplementation.EntityFinder;
import com.persistence.entityFinderImplementation.EntityFinderImpl;

import com.persistence.entities.UserEntity;

import java.util.List;


public class UserServiceToReview2 {


    /////////////
    // LOGGER
    private final static Logger LOG = Logger.getLogger(UserServiceToReview2.class);


    ////////////
    // CHAMPS
    // est peuplé via le constructeur à 1 paramètre
    protected EntityManager em;


    ///////////////////
    // CONSTRUCTEURS //
    // 0 paramètre => dans le cas des read ou find qui utilisent la classe EntityFinderImpl qui instancie elle-même son EntityManager
    public UserServiceToReview2() {
    }

    // 1 paramètre => dans le cas des create - update - delete , car c'est le controller qui instancie l'EntityManager em avant de le passer en argument au service
    public UserServiceToReview2(EntityManager em) {
        this.em = em;
    }



    ////////////////////
    // METHODES
    ////////////////////

    // retourne 1 user
    public UserEntity selectOneUserById(int id) {

        // on place la référence de l'objet de type EntityFinderImpl dans un pointeur de type EntityFinder
        // qui est une interface implémentée par EntityFinderImpl => methodes factorisées
        EntityFinder<UserEntity> myEntityFinder = new EntityFinderImpl<UserEntity>();

        // on instancie un objet de type PermissionEntity dont les valeurs seront remplacées
        // au sein de la méthode findOne lors de l'instruction ** t = (T)em.find(ec, id); dans la classe EntityFinderImpl **  où t est myPermission
        UserEntity myUser = new UserEntity();

        return myEntityFinder.findOne(myUser, id);
        // les find = read


    }




    // liste tous les users
    public List<UserEntity> selectAllUsers() {

        EntityFinder<UserEntity> myEntityFinder = new EntityFinderImpl<UserEntity>();

        // Instanciation nécessaire d'un objet de type UserEntity pour le passer en argument de la méthode findByNamedQuery
        // qui récupère le type de l'objet via l'instruction ** Class<? extends Object> ec = t.getClass(); ** où t est myUser
        UserEntity myUser = new UserEntity();

        return myEntityFinder.findByNamedQuery("User.selectAll", myUser, null);


    }

}




    /*

public class MediaService {

    private static Logger log = Logger.getLogger(MediaService.class);




    }
    public void updateMedia(EntityManager em, MediaEntity media) {
        log.info("begin - updateMediasEntityervice");

        em.createNamedQuery("Media.updateById");
        // em.persist(Media);
        em.merge(media);
        log.info("end - updateMediaService");
    }

    public void deleteMedia(EntityManager em, MediaEntity media) {
        log.info("deleteMediasEntityService - begin");
        em.createNamedQuery("Media.updateById");
        em.merge(media);
        log.info("deleteMediasEntityervice - end");
    }

    public MediaEntity getReadMediaById() {
        EntityFinder finder = new EntityFinderImpl<EventEntity>();
        MediaEntity media = new MediaEntity();
        media = (MediaEntity) finder.findOne(media, 1);

        return media;
    }
}
*/




// READ ALL
/*
    public List<UserEntity> findAll()
    {

        TypedQuery<UserEntity> query = em.createNamedQuery("User.NamedQuery.findAll",UserEntity.class);
        if(param != null) {

            setParameters(query, param);
        }

        LOG.debug("List " + t + " size: " + listT.size());
        LOG.debug("Named query " + namedQuery + " find from database: Ok");

        return query.getResultList();
    }
}

 */















