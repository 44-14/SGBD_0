package com.main.schoolux.services;


import com.main.schoolux.services.factorisation.ServiceImpl;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EntityFinder;
import com.persistence.entityFinderImplementation.EntityFinderImpl;
import org.apache.log4j.Logger;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


public class UserService extends ServiceImpl<UserEntity> {

    private final static Logger LOG = Logger.getLogger(UserService.class);

    // methode insert - update - delete - insertAndFlush définies dans la classe parent


    ///////////////////
    // CONSTRUCTEURS //

    public UserService(EntityManager em) {
        super(em);
    }


    // SETTER for protected em from superclass
    public void setEm(EntityManager em) {super.em = em;}




    public void deleteLogically (UserEntity myUser) {
        myUser.setActive(false);
        this.update(myUser);
    }

    // sert à vérifier si une entrée existe déjà en db avant de la créer
    // amélioration : rendre générique (object toCheck, string uniqueConstraint)
    public boolean alreadyExist(UserEntity myUser ) {
        return (selectUserByUsernameOrNull(myUser.getUsername()) != null);
    }



    // lire un user via l'id
    @Override
    public UserEntity selectOneByIdOrNull(int id) {

        LOG.debug("Select an user by the id : " + id);

        return em.find(UserEntity.class, id);
    }





    // lire tous les users
    @Override
    public List<UserEntity> selectAllOrNull() {

       try {
                Query query = em.createNamedQuery("User.selectAll", UserEntity.class);
                List<UserEntity> myUserList = query.getResultList();

                LOG.debug("List <" + UserEntity.class.getSimpleName() + " size: " + myUserList.size());
                LOG.debug("Selected all roles users database ");
                return myUserList;
            } catch (NoResultException e) {
                LOG.debug("The query found no user list to return", e);
                return null;
            }
        }







    // lire 1 user via l'username
    public UserEntity selectUserByUsernameOrNull(String username) throws IllegalArgumentException {
        LOG.debug("Select an user by the username : " + username);

        if (MyStringUtil.hasContent(username)) {

            try {

                return em.createNamedQuery("User.selectOneByUsername",UserEntity.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.debug("The query found no user to return", e);
                return null;
            }
        } else {

            throw new IllegalArgumentException("Username is empty or null");
        }
    }


}
