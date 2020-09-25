package com.main.schoolux.services;


import com.persistence.entities.PermissionEntity;
import com.persistence.entities.RoleEntity;
import com.persistence.entityFinderImplementation.EntityFinder;
import com.persistence.entityFinderImplementation.EntityFinderImpl;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


public class RoleService extends ServiceImpl<RoleEntity> {

    private final static Logger LOG = Logger.getLogger(RoleService.class);

    // methode insert - update - delete définies dans la classe parent


    ///////////////////
    // CONSTRUCTEURS //

    // 0 paramètre => dans le cas des read/find/select qui utilisent la classe EntityFinderImpl qui instancie elle-même son EntityManager
    //public RoleService() {
    //}
        /* En fait il ne faut pas utiliser les classes EntityFinderImpl.class parce que les méthodes dedans génèrent et closent leurs em d'elles-mêmes, ce qui fait
    qu'elles ne peuvent servir que pour faire des select. Si on veut faire un select d'une entité via .findOne pour ensuite la supprimer via .remove, ça ne fonctionnera pas
    car l'em a été close au sein de .findOne et l'entité récupérée n'est donc plus attached au context vu qu'il est close
     */


    // 1 paramètre => pour les autres opérations qui ne passent pas par  l'instanciation d'un EntityFinderImpl
    public RoleService(EntityManager em) {super(em);}



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




    /* Pas de champ isActive dans la table role donc les suppressions sont effectives
    @Override
    public void deleteLogically (RoleEntity myRole) {
        myRole.isActive = false ;
        this.update(myRole);
    }
     */






    // sert à vérifier si une entrée existe déjà en db avant de la créer
    @Override
    public boolean alreadyExist(RoleEntity myRole ) {
        // label et abbreviation uniques keys  => passé la méthode qui check en argument de celle-ci ?
        // ou on verifie ici les retours de chaque methode selectRoleByLabelOrNull / selectRoleByAbbreviationOrNull
        // chaque fois que c different de null, on ecrit un msg d erreur et on retourne la liste des msg plutot qu un boolean
        // dans le controlleur, on check si la liste des msg est supérieure à 0, si oui erreur => retour au formulaire
        return (selectRoleByLabelOrNull(myRole.getLabel()) != null);
    }




    // lire 1 Role via l'id
    @Override
    public RoleEntity selectOneByIdOrNull(int id) {

        LOG.debug("Select 1 Role by the id : "+id);

        // find = read = select

        // Method 1 :
        // on place la référence de l'objet de type EntityFinderImpl dans un pointeur de type EntityFinder
        // qui est une interface implémentée par EntityFinderImpl => methodes factorisées
        //EntityFinder<RoleEntity> myEntityFinder = new EntityFinderImpl<RoleEntity>();

        // on instancie un objet de type RoleEntity dont les valeurs seront remplacées
        // au sein de la méthode findOne lors de l'instruction ** t = (T)em.find(ec, id); dans la classe EntityFinderImpl **  où t est myPermission
        RoleEntity myRole = new RoleEntity();

        //return myEntityFinder.findOne(myRole,id);


        // Method 2 :

        return em.find(RoleEntity.class, id);
    }




        // lire tous les Roles
        @Override
        public List<RoleEntity> selectAllOrNull() {

        /*
        // Method 1 mais qui détache la collection de l'entityManager vu que findByNamedQuery contient l'instruction em.close

            LOG.debug("Select all Roles ");

            EntityFinder<RoleEntity> myEntityFinder = new EntityFinderImpl<RoleEntity>();

            // Instanciation nécessaire d'un objet de type RoleEntity pour le passer en argument de la méthode findByNamedQuery
            // qui récupère le type de l'objet via l'instruction ==>  Class<? extends Object> ec = t.getClass();   où t est myRole
            RoleEntity myRole = new RoleEntity();

            return myEntityFinder.findByNamedQuery("Role.selectAll", myRole, null);

         */

        // Cette méthode et la méthode 2 dans les selectOneByIdOrNull vont permettre de tjrs passer un em dans le constructeur du service, et de tjrs utiliser le meme em
            try {
                //List<PermissionEntity> myPermissionList = em.createNamedQuery("Permission.selectAll", PermissionEntity.class)
                //.getResultList();
                Query query = em.createNamedQuery("Role.selectAll", RoleEntity.class);
                List <RoleEntity> myRoleList = query.getResultList();

                LOG.debug("List " + RoleEntity.class.getSimpleName() + " size: " + myRoleList.size());
                LOG.debug("Find all roles from database: Ok");
                return myRoleList;
            } catch (NoResultException e) {
                LOG.debug("The query found no role list to return", e);
                return null;
            }

        }










    // lire 1 Role via le label name
    public RoleEntity selectRoleByLabelOrNull(String label) throws IllegalArgumentException {
        LOG.debug("Select 1 Role by the label : " + label);

        if (label != null && !label.isEmpty()) {

            // fonctionne pas car findByNamedQuery retourne une liste => on peut garder ça et check si la taille est supérieure à 0, ou seconde méthode avec createNamedQuery
            //EntityFinder<RoleEntity> myEntityFinder = new EntityFinderImpl<RoleEntity>();
            //RoleEntity myRole = new RoleEntity();
            //return myEntityFinder.findByNamedQuery("Role.selectByLabel", myRole, null);

            try {

                return em.createNamedQuery("Role.selectOneByLabel",RoleEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.debug("The query found no role to return", e);
                return null;
            }
        } else {

            throw new IllegalArgumentException("Label is empty or null");
        }
    }


    /* Pas de champ isActive dans la table role donc les suppressions sont effectives
    @Override
    public void deleteLogically (RoleEntity myRole) {
        myRole.isActive = false ;
        this.update(myRole);
    }
     */





}
