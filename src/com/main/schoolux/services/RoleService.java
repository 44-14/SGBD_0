package com.main.schoolux.services;


import com.main.schoolux.services.factorisation.ServiceImpl;
import com.persistence.entities.RoleEntity;
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

    // 1 paramètre => pour les autres opérations qui ne passent pas par  l'instanciation d'un EntityFinderImpl
    public RoleService(EntityManager em) {super(em);}



    // SETTER for protected em from superclass
    public void setEm(EntityManager em) {super.em = em;}










    // sert à vérifier si une entrée existe déjà en db avant de la créer
    @Override
    public boolean alreadyExist(RoleEntity myRole ) {

        return (selectRoleByLabelOrNull(myRole.getLabel()) != null);
    }




    // sélectionner un role via l'id
    @Override
    public RoleEntity selectOneByIdOrNull(int id) {

        LOG.debug("Select a role by the id : "+id);

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

            try {

                Query query = em.createNamedQuery("Role.selectAll", RoleEntity.class);
                List <RoleEntity> myRoleList = query.getResultList();

                LOG.debug("List <" + RoleEntity.class.getSimpleName() + ">  size: " + myRoleList.size());
                LOG.debug("Select all roles from database: Ok");
                return myRoleList;
            } catch (NoResultException e) {
                LOG.debug("The query found no role list to return", e);
                return null;
            }

        }






    // lire un role via le label name
    public RoleEntity selectRoleByLabelOrNull(String label) throws IllegalArgumentException {
        LOG.debug("Select a role by the label : " + label);

        if (label != null && !label.isEmpty()) {

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








}
