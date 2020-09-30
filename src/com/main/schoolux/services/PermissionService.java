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

    // 1 paramètre => pour les autres opérations qui ne passent pas par  l'instanciation d'un EntityFinderImpl
    public PermissionService(EntityManager em) {
        super(em);
    }


    // SETTER for protected em from superclass
    public void setEm(EntityManager em) {super.em = em;}








    /**
     * sert à vérifier si une entrée existe déjà en db avant de la créer
     * @param myPermission
     * @return
     */
    // amélioration : rendre générique (object toCheck, string uniqueConstraint)
    public boolean alreadyExist(PermissionEntity myPermission ) {
        return (selectPermissionByLabelOrNull(myPermission.getLabel()) != null);
    }


    /**
     * sélectionner 1 permission via l'id
     * @param id
     * @return la permissionEntity ou null
     */
    @Override
    public PermissionEntity selectOneByIdOrNull(int id) {

        LOG.debug("Select a permission by the id : "+id);

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


    /**
     * lire toutes les permissions
      */
    @Override
    public List<PermissionEntity> selectAllOrNull() {

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


    /**
     * lire une permission via le label name
     * @param label
     * @return
     * @throws IllegalArgumentException
     */
    public PermissionEntity selectPermissionByLabelOrNull(String label) throws IllegalArgumentException {
        LOG.debug("Select a permission by the label : " + label);

        if (MyStringUtil.hasContent(label)){

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
