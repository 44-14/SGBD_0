package com.main.schoolux.services;

/*
import be.atc.dataAccess.entities.RoleEntity;
import be.atc.dataAccess.entities.SexeType;
import be.atc.dataAccess.entities.TeamEntity;
import be.atc.dataAccess.entities.WorkerEntity;
*/

import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EntityFinder;
import com.persistence.entityFinderImplementation.EntityFinderImpl;
import org.apache.log4j.Logger;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


public class UserService extends ServiceImpl<UserEntity> {

    private final static Logger LOG = Logger.getLogger(UserService.class);

    // methode insert - update - delete définies dans la classe parent


    ///////////////////
    // CONSTRUCTEURS //

    // 0 paramètre => dans le cas des read/find/select qui utilisent la classe EntityFinderImpl qui instancie elle-même son EntityManager
    public UserService() {
    }


    // 1 paramètre => pour les autres opérations qui ne passent pas par  l'instanciation d'un EntityFinderImpl
    public UserService(EntityManager em) {
        super(em);
    }



    /*
    public WorkerEntity create(String firstName,
                               String lastName,
                               LocalDate birthdate,
                               String username,
                               String password,
                               SexeType sexeType,
                               RoleEntity role,
                               TeamEntity team)
    {
        WorkerEntity newWorker = new WorkerEntity();
        newWorker.setFirstName(firstName);
        newWorker.setLastName(lastName);
        newWorker.setBirthdate(birthdate);
        newWorker.setUsername(username);
        newWorker.setPassword(password);
        newWorker.setDeleted(false);
        newWorker.setSexe(sexeType);
        newWorker.setRole(role);
        newWorker.setTeam(team);
        return newWorker;
    }
     */


    @Override
    public boolean alreadyExist(UserEntity myUser ) {
        return (selectUserByUsernameOrNull(myUser.getUsername()) != null);
    }




    // lire 1 user via l'id
    @Override
    public UserEntity selectOneByIdOrNull(int id) {

        LOG.debug("Select 1 user by the id : "+id);

        // find = read = select

        // Method 1 :
        // on place la référence de l'objet de type EntityFinderImpl dans un pointeur de type EntityFinder
        // qui est une interface implémentée par EntityFinderImpl => methodes factorisées
        //EntityFinder<PermissionEntity> myEntityFinder = new EntityFinderImpl<PermissionEntity>();

        // on instancie un objet de type PermissionEntity dont les valeurs seront remplacées
        // au sein de la méthode findOne lors de l'instruction ** t = (T)em.find(ec, id); dans la classe EntityFinderImpl **  où t est myPermission
        //UserEntity myuser = new UserEntity();

        //return myEntityFinder.findOne(myUser,id);


        // Method 2 :

        return em.find(UserEntity.class, id);
    }






        // lire tous les users
        @Override
    public List<UserEntity> selectAllOrNull() {

            LOG.debug("Select all users ");

            EntityFinder<UserEntity> myEntityFinder = new EntityFinderImpl<UserEntity>();

            // Instanciation nécessaire d'un objet de type UserEntity pour le passer en argument de la méthode findByNamedQuery
            // qui récupère le type de l'objet via l'instruction ==>  Class<? extends Object> ec = t.getClass();   où t est myUser
            UserEntity myUser = new UserEntity();

            return myEntityFinder.findByNamedQuery("User.selectAll", myUser, null);


    }








    // lire 1 user via l'username
    public UserEntity selectUserByUsernameOrNull(String username) throws IllegalArgumentException {
        LOG.debug("Select 1 user by the username : " + username);

        if (username != null && !username.isEmpty()) {

            // fonctionne pas car findByNamedQuery retourne une liste => on peut garder ça et check si la taille est supérieure à 0, ou seconde méthode avec createNamedQuery
            //EntityFinder<UserEntity> myEntityFinder = new EntityFinderImpl<UserEntity>();
            //UserEntity myUser = new UserEntity();
            //return myEntityFinder.findByNamedQuery("User.selectByUsername", myUser, null);

            try {
                LOG.debug("ici");
                return em.createNamedQuery("User.selectOneByUsername",UserEntity.class)
                        .setParameter("username", username)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.debug("The query found no user to return", e);
                return null;
            }
        } else {
            LOG.debug("ou là");
            throw new IllegalArgumentException("Username is empty or null");
        }
    }


}
