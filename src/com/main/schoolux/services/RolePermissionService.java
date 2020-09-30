package com.main.schoolux.services;


import com.main.schoolux.services.factorisation.ServiceImpl;
import com.persistence.entities.RolePermissionEntity;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.ArrayList;
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





    // A faire ensuite
    // sert à vérifier si une entrée existe déjà en db avant de la créer
    @Override
    public boolean alreadyExist(RolePermissionEntity myRolePermission ) {
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



    // Lire une RolePermission via la clé composite idRole + idPErmission
    public RolePermissionEntity selectOneByCompositeOrNull(int idPermission, int idRole) {

        LOG.debug("Select 1 RolePermission by the composite  : Permission id = "+idPermission + " and Role id = "+idRole);


        try {
            //List<RolePermissionEntity> myRolePermissionList = em.createNamedQuery("RolePermission.selectAll", RolePermissionEntity.class)
            //.getResultList();
            Query query = em.createNamedQuery("RolePermission.selectOneByComposite", RolePermissionEntity.class);
            LOG.debug(" query.setParam");
            query.setParameter("idPermission", idPermission);
            query.setParameter("idRole",idRole);

            LOG.debug("getResult");
            RolePermissionEntity test = (RolePermissionEntity) query.getSingleResult();
            return (RolePermissionEntity) query.getSingleResult();
        } catch (NoResultException e) {
            LOG.debug("The query found no RolePermission to return with that composite key", e);
            return null;
        }

    }

    // Lire une RolePermission via la clé composite idRole + idPErmission
    public int deleteAllHavingIdRole(int idRole) {

        LOG.debug("Delete all RolePermission having the role id  = "+idRole);
        List <RolePermissionEntity> myListTest = this.selectAllHavingIdRole(idRole);
        try {
            Query query = em.createNamedQuery("RolePermission.deleteAllByIdRole", RolePermissionEntity.class);
            LOG.debug(" query.setParam");
            query.setParameter("idRole",idRole);

            query.executeUpdate();


            //update entity manager with changes
            List <RolePermissionEntity> myList = this.selectAllHavingIdRole(idRole);

            return 0;

        } catch (NoResultException e) {
            LOG.debug("The query couldnt delete the RolePermission records having the role id : "+idRole,e);
            throw e;
        }

    }





    // Lire une RolePermission via la clé composite idRole + idPErmission
    public List <RolePermissionEntity>  selectAllHavingIdRole(int idRole) {

        LOG.debug("Select all RolePermission having the role id  = "+idRole);

        try {

            Query query = em.createNamedQuery("RolePermission.selectAllByIdRole", RolePermissionEntity.class);
            LOG.debug(" query.setParam");
            query.setParameter("idRole",idRole);

            return  query.getResultList();


        } catch (NoResultException e) {
            LOG.debug("The query couldnt delete the RolePermission records having the role id : "+idRole,e);
            return null;
        }

    }






        // lire tous les RolePermission
        @Override
    public List<RolePermissionEntity> selectAllOrNull() {



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










}
