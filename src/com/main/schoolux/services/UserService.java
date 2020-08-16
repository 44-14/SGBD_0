package com.main.schoolux.services;

import com.main.schoolux.exceptions.PropertyException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;


import com.persistence.entities.UserEntity;

import com.persistence.entityFinderImplementation.EntityFinder;
import com.persistence.entityFinderImplementation.EntityFinderImpl ;

public class UserService {

    private static Logger LOG = Logger.getLogger(UserService.class);


    public UserEntity createUser(EntityManager em, UserEntity myUser) {
        LOG.info("begin -  createUser() in UserService");
        em.persist(myUser);
        LOG.info("end - createUser() in UserService");
        return myUser;

    }


    public void updateUser(EntityManager em, UserEntity myUser) {
        LOG.info("begin -  updateUser() in UserService");

        UserEntity mirrorUser = em.find(UserEntity.class, myUser.getId());
        LOG.info(mirrorUser.toString());


        /*em.createNamedQuery("Media.updateById");*/
        // em.persist(Media);
        //em.merge(media);
        LOG.info("end - updateUser() in UserService");
    }
}



        /*
        public static void updateUtilisateur(Utilisateur user) throws PropertyException{
            prepareEntityManager();

            Utilisateur newUser = entitymanager.find(Utilisateur.class, user.getId());
            newUser = user;
            entitymanager.merge(newUser);
            closeResources();
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