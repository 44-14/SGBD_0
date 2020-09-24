package com.main.schoolux.services;


import com.persistence.entities.RoleEntity;
import com.persistence.entities.UserEntity;
import com.persistence.entityFinderImplementation.EntityFinder;
import com.persistence.entityFinderImplementation.EntityFinderImpl;
import org.apache.log4j.Logger;


import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
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


    // SETTER for protected em from superclass
    public void setEm(EntityManager em) {super.em = em;}


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

        LOG.debug("Select 1 user by the id : " + id);

        // find = read = select

        // Method 1 :
        // on place la référence de l'objet de type EntityFinderImpl dans un pointeur de type EntityFinder
        // qui est une interface implémentée par EntityFinderImpl => methodes factorisées
        EntityFinder<UserEntity> myEntityFinder = new EntityFinderImpl<UserEntity>();

        // on instancie un objet de type PermissionEntity dont les valeurs seront remplacées
        // au sein de la méthode findOne lors de l'instruction ** t = (T)em.find(ec, id); dans la classe EntityFinderImpl **  où t est myPermission
        UserEntity myUser = new UserEntity();

        return myEntityFinder.findOne(myUser, id);


        // Method 2 :

        //return em.find(UserEntity.class, id);
    }







        // lire tous les users
        @Override
        public List<UserEntity> selectAllOrNull() {

        /*
        // Method 1 mais qui détache la collection de l'entityManager vu que findByNamedQuery contient l'instruction em.close
            LOG.debug("Select all users ");

            EntityFinder<UserEntity> myEntityFinder = new EntityFinderImpl<UserEntity>();

            // Instanciation nécessaire d'un objet de type UserEntity pour le passer en argument de la méthode findByNamedQuery
            // qui récupère le type de l'objet via l'instruction ==>  Class<? extends Object> ec = t.getClass();   où t est myUser
            UserEntity myUser = new UserEntity();

            return myEntityFinder.findByNamedQuery("User.selectAll", myUser, null);



            LOG.debug("Select all Roles ");

            EntityFinder<RoleEntity> myEntityFinder = new EntityFinderImpl<RoleEntity>();

            // Instanciation nécessaire d'un objet de type RoleEntity pour le passer en argument de la méthode findByNamedQuery
            // qui récupère le type de l'objet via l'instruction ==>  Class<? extends Object> ec = t.getClass();   où t est myRole
            RoleEntity myRole = new RoleEntity();

            return myEntityFinder.findByNamedQuery("Role.selectAll", myRole, null);

         */

            // Cette méthode et la méthode 2 dans les selectOneByIdOrNull vont permettre de tjrs passer un em dans le constructeur du service, et de tjrs utiliser le meme em
            try {
                //List<UserEntity> myUserList = em.createNamedQuery("User.selectAll", UserEntity.class)
                //.getResultList();
                Query query = em.createNamedQuery("User.selectAll", UserEntity.class);
                List<UserEntity> myUserList = query.getResultList();

                LOG.debug("List " + UserEntity.class.getSimpleName() + " size: " + myUserList.size());
                LOG.debug("Find all roles from database: Ok");
                return myUserList;
            } catch (NoResultException e) {
                LOG.debug("The query found no userlist to return", e);
                return null;
            }
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



    public void deleteLogically (UserEntity myUser) {
        myUser.setActive(false);
        this.update(myUser);
    }

}
