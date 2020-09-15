package com.main.schoolux.services;


import org.apache.log4j.Logger;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.persistence.entities.UserEntity;



/*

public class UserServiceToReview {

    private final static Logger LOG = Logger.getLogger(UserServiceToReview.class);

    // peuplé via le constructeur => c est le controlleur qui instancie l'EntityManager et qui le passe en paramètre dans l'appel du service
    protected EntityManager em;


    // constructeur
    public UserServiceToReview(EntityManager em)
    {
        this.em = em;
    }




    // READ ONE
    public UserEntity findOne(int id)
    {
        return em.find(UserEntity.class,id);
    }


    // READ ALL
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


/*

    public <K, V> List<T> findByNamedQuery(String namedQuery, T t, Map<K, V> param) {

        List<T> listT = new ArrayList<T>();
        Class<? extends Object> ec = t.getClass();


        // controlleur
        EntityManager em = EMF.getEM();
        try {
            Query query = em.createNamedQuery(namedQuery, ec);

            if(param != null) {

                setParameters(query, param);
            }
            listT = (List<T>) query.getResultList();

            log.debug("List " + t + " size: " + listT.size());
            log.debug("Named query " + namedQuery + " find from database: Ok");
        }
        finally {

            em.clear();
            em.close();
        }
        return listT;
    }

/*






package com.main.schoolux.services;

import com.main.schoolux.exceptions.PropertyException;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;


import com.persistence.entities.UserEntity;

import com.persistence.entityFinderImplementation.EntityFinder;
import com.persistence.entityFinderImplementation.EntityFinderImpl ;

public class UserServiceToReview {

    private static Logger LOG = Logger.getLogger(UserServiceToReview.class);


    public UserEntity createUser(EntityManager em, UserEntity myUser) {
        LOG.info("begin -  createUser() in UserServiceToReview");
        em.persist(myUser);
        LOG.info("end - createUser() in UserServiceToReview");
        return myUser;

    }


    public void updateUser(EntityManager em, UserEntity myUser) {
        LOG.info("begin -  updateUser() in UserServiceToReview");

        UserEntity mirrorUser = em.find(UserEntity.class, myUser.getId());
        LOG.info(mirrorUser.toString());


        /*em.createNamedQuery("Media.updateById");*/
        // em.persist(Media);
        //em.merge(media);
        //LOG.info("end - updateUser() in UserServiceToReview");
 /*   }
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