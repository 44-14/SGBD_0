package com.main.schoolux.services;


import com.main.schoolux.services.factorisation.ServiceImpl;
import com.main.schoolux.utilitaries.MyStringUtil;
import com.persistence.entities.PermissionEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;


public class PermissionService extends ServiceImpl<PermissionEntity> {

    private final static Logger LOG = Logger.getLogger(PermissionService.class);

    // methode insert - update - delete - insertAndFlush définies dans la classe parent


    ///////////////////
    // CONSTRUCTEURS //

    // 0 paramètre => dans le cas des select qui utilisent la classe EntityFinderImpl qui instancie elle-même son EntityManager (j utilise que pour selectAll
    //public PermissionService() {
    //}
    /* En fait il ne faut pas utiliser les classes EntityFinderImpl.class parce que les méthodes dedans génèrent et closent leurs em d'elles-mêmes, ce qui fait
    qu'elles ne peuvent servir que pour faire des select. Si on veut faire un select d'une entité via .findOne pour ensuite la supprimer via .remove, ça ne fonctionnera pas
    car l'em a été close au sein de .findOne et l'entité récupérée n'est donc plus attached au context vu qu'il est close
     */

    // 1 paramètre => pour les autres opérations qui ne passent pas par  l'instanciation d'un EntityFinderImpl
    public PermissionService(EntityManager em) {
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
    public void deleteLogically (PermissionEntity myPermission) {
        myPermission.isActive = false ;
        this.update(myPermission);
    }
     */




    // sert à vérifier si une entrée existe déjà en db avant de la créer
    // amélioration : rendre générique (object toCheck, string uniqueConstraint)
    public boolean alreadyExist(PermissionEntity myPermission ) {
        // label et abbreviation uniques keys  => passé la méthode qui check en argument de celle-ci ?
        // ou on verifie ici les retours de chaque methode selectPermissionByLabelOrNull / selectPermissionByAbbreviationOrNull
        // chaque fois que c different de null, on ecrit un msg d erreur et on retourne la liste des msg plutot qu un boolean
        // dans le controlleur, on check si la liste des msg est supérieure à 0, si oui erreur => retour au formulaire
        return (selectPermissionByLabelOrNull(myPermission.getLabel()) != null);
    }




    // sélectionner 1 permission via l'id
    @Override
    public PermissionEntity selectOneByIdOrNull(int id) {

        LOG.debug("Select a role by the id : "+id);

        // find = read = select

        // Method 1 :
        // on place la référence de l'objet de type EntityFinderImpl dans un pointeur de type EntityFinder
        // qui est une interface implémentée par EntityFinderImpl => methodes factorisées
        //EntityFinder<PermissionEntity> myEntityFinder = new EntityFinderImpl<PermissionEntity>();

        // on instancie un objet de type PermissionEntity dont les valeurs seront remplacées
        // au sein de la méthode findOne lors de l'instruction ** t = (T)em.find(ec, id); dans la classe EntityFinderImpl **  où t est myPermission
        //PermissionEntity myPermission = new PermissionEntity();

        //return myEntityFinder.findOne(myPermission,id);


        // Method 2 :

        return em.find(PermissionEntity.class, id);
    }






    // lire toutes les permissions
    @Override
    public List<PermissionEntity> selectAllOrNull() {

        /*
        // Method 1 mais qui détache la collection de l'entityManager vu que findByNamedQuery contient l'instruction em.close

            LOG.debug("Select all Permissions ");

            EntityFinder<PermissionEntity> myEntityFinder = new EntityFinderImpl<PermissionEntity>();

            // Instanciation nécessaire d'un objet de type PermissionEntity pour le passer en argument de la méthode findByNamedQuery
            // qui récupère le type de l'objet via l'instruction ==>  Class<? extends Object> ec = t.getClass();   où t est myPermission
            PermissionEntity myPermission = new PermissionEntity();

            return myEntityFinder.findByNamedQuery("Permission.selectAll", myPermission, null);

     */

        try {
            //List<PermissionEntity> myPermissionList = em.createNamedQuery("Permission.selectAll", PermissionEntity.class)
                //.getResultList();
            Query query = em.createNamedQuery("Permission.selectAll", PermissionEntity.class);
            List <PermissionEntity> myPermissionList = query.getResultList();

            LOG.debug("List " + PermissionEntity.class.getSimpleName() + " size: " + myPermissionList.size());
            LOG.debug("Selected all permissions from database ");
            return myPermissionList;
        } catch (NoResultException e) {
            LOG.debug("The query found no permission list to return", e);
            return null;
        }

    }



    // lire une permission via le label name
    public PermissionEntity selectPermissionByLabelOrNull(String label) throws IllegalArgumentException {
        LOG.debug("Select a permission by the label : " + label);

        //if (label != null && !label.isEmpty()) {
        if (MyStringUtil.hasContent(label)){

            // fonctionne pas car findByNamedQuery retourne une liste => on peut garder ça et check si la taille est supérieure à 0, ou seconde méthode avec createNamedQuery
            //EntityFinder<PermissionEntity> myEntityFinder = new EntityFinderImpl<PermissionEntity>();
            //PermissionEntity myPermission = new PermissionEntity();
            //return myEntityFinder.findByNamedQuery("Permission.selectByLabel", myPermission, null);

            try {

                return em.createNamedQuery("Permission.selectOneByLabel",PermissionEntity.class)
                        .setParameter("label", label)
                        .getSingleResult();
            } catch (NoResultException e) {
                LOG.debug("The query found no permission to return", e);
                return null;
            }
        } else {

            throw new IllegalArgumentException("Label is empty or null");
        }
    }




}
