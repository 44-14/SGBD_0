package com.main.schoolux.services;


import com.main.schoolux.services.factorisation.ServiceImpl;
import com.persistence.entities.RolePermissionEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


public class RolePermissionService extends ServiceImpl<RolePermissionEntity> {

    private final static Logger LOG = Logger.getLogger(RolePermissionService.class);

    // methode insert - update - delete définies dans la classe parent


    ///////////////////
    // CONSTRUCTEURS //

    // 0 paramètre => dans le cas des select qui utilisent la classe EntityFinderImpl qui instancie elle-même son EntityManager (j utilise que pour selectAll
    //public RolePermissionService() {
    //}
    /* En fait il ne faut pas utiliser les classes EntityFinderImpl.class parce que les méthodes dedans génèrent et closent leurs em d'elles-mêmes, ce qui fait
    qu'elles ne peuvent servir que pour faire des select. Si on veut faire un select d'une entité via .findOne pour ensuite la supprimer via .remove, ça ne fonctionnera pas
    car l'em a été close au sein de .findOne et l'entité récupérée n'est donc plus attached au context vu qu'il est close
     */

    // 1 paramètre => pour les autres opérations qui ne passent pas par  l'instanciation d'un EntityFinderImpl
    public RolePermissionService(EntityManager em) {
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




   /*Pas de champ isActive dans la table role donc les suppressions sont effectives
    @Override
    public void deleteLogically (RolePermissionEntity myRolePermission) {
        myRolePermission.isActive = false ;
        this.update(myRolePermission);
    }
     */

    // PAS POUR CE SERVICE-CI mais comme c'est dans l interface
    // sert à vérifier si une entrée existe déjà en db avant de la créer
    @Override
    public boolean alreadyExist(RolePermissionEntity myRolePermission ) {
        // label et abbreviation uniques keys  => passé la méthode qui check en argument de celle-ci ?
        // ou on verifie ici les retours de chaque methode selectRolePermissionByLabelOrNull / selectRolePermissionByAbbreviationOrNull
        // chaque fois que c different de null, on ecrit un msg d erreur et on retourne la liste des msg plutot qu un boolean
        // dans le controlleur, on check si la liste des msg est supérieure à 0, si oui erreur => retour au formulaire
        return false;
    }






    // lire 1 RolePermission via l'id
    @Override
    public RolePermissionEntity selectOneByIdOrNull(int id) {

        LOG.debug("Select 1 RolePermission by the id : "+id);

        // find = read = select

        // Method 1 :
        // on place la référence de l'objet de type EntityFinderImpl dans un pointeur de type EntityFinder
        // qui est une interface implémentée par EntityFinderImpl => methodes factorisées
        //EntityFinder<RolePermissionEntity> myEntityFinder = new EntityFinderImpl<RolePermissionEntity>();

        // on instancie un objet de type RolePermissionEntity dont les valeurs seront remplacées
        // au sein de la méthode findOne lors de l'instruction ** t = (T)em.find(ec, id); dans la classe EntityFinderImpl **  où t est myRolePermission
        //RolePermissionEntity myRolePermission = new RolePermissionEntity();

        //return myEntityFinder.findOne(myRolePermission,id);


        // Method 2 :

        return em.find(RolePermissionEntity.class, id);
    }






        // lire tous les RolePermission
        @Override
    public List<RolePermissionEntity> selectAllOrNull() {

        /*
        // Method 1 mais qui détache la collection de l'entityManager vu que findByNamedQuery contient l'instruction em.close

            LOG.debug("Select all RolePermission ");

            EntityFinder<RolePermissionEntity> myEntityFinder = new EntityFinderImpl<RolePermissionEntity>();

            // Instanciation nécessaire d'un objet de type RolePermissionEntity pour le passer en argument de la méthode findByNamedQuery
            // qui récupère le type de l'objet via l'instruction ==>  Class<? extends Object> ec = t.getClass();   où t est myRolePermission
            RolePermissionEntity myRolePermission = new RolePermissionEntity();

            return myEntityFinder.findByNamedQuery("RolePermission.selectAll", myRolePermission, null);

     */

        try {
            //List<RolePermissionEntity> myRolePermissionList = em.createNamedQuery("RolePermission.selectAll", RolePermissionEntity.class)
                //.getResultList();
            Query query = em.createNamedQuery("RolePermission.selectAll", RolePermissionEntity.class);
            List <RolePermissionEntity> myRolePermissionList = query.getResultList();

            LOG.debug("List " + RolePermissionEntity.class.getSimpleName() + " size: " + myRolePermissionList.size());
            LOG.debug("Find all RolePermissions from database: Ok");
            return myRolePermissionList;
        } catch (NoResultException e) {
            LOG.debug("The query found no RolePermission list to return", e);
            return null;
        }

    }


    /*
    Pas de champ isActive dans la table role donc les suppressions sont effectives
    @Override
    public void deleteLogically (RolePermissionEntity myRolePermission) {
        myRolePermission.isActive = false ;
        this.update(myRolePermission);
    }
     */









/*  PAS POUR CETTE ENTITY

    // lire 1 RolePermission via le label name
    public RolePermissionEntity selectRolePermissionByLabelOrNull(String label) throws IllegalArgumentException {
        LOG.debug("Select 1 RolePermission by the label : " + label);

        if (label != null && !label.isEmpty()) {

            // fonctionne pas car findByNamedQuery retourne une liste => on peut garder ça et check si la taille est supérieure à 0, ou seconde méthode avec createNamedQuery
            //EntityFinder<RolePermissionEntity> myEntityFinder = new EntityFinderImpl<RolePermissionEntity>();
            //RolePermissionEntity myRolePermission = new RolePermissionEntity();
            //return myEntityFinder.findByNamedQuery("RolePermission.selectByLabel", myRolePermission, null);

            try {

                return em.createNamedQuery("RolePermission.selectOneByLabel",PermissionEntity.class)
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
    */




}
