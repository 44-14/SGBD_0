package com.persistence.entityFinderImplementation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/** 
 * Class to get a connection to the database
 * 
 * @author Renaud DIANA
 */
public final class EMF {

    // la classe instancie un attribut membre de type EntityManagerFactory qui est static
    // avec egalement une methode getter statique donc pas besoin d'instancier la classe EMF


    /* Initialement :
	private static EntityManagerFactory emfInstance =
	        Persistence.createEntityManagerFactory("your_unit_persistence_name");

	 Pour projet schoolUX  => la persistence unit à passer en paramètre ci-dessous est dans le persistence.xml
     */
    private static EntityManagerFactory emfInstance =
            Persistence.createEntityManagerFactory("PersistUnit_schoolUX");


    //constructeur
    private EMF() {}

    //ne retourne pas un type EMF mais un type EntityManagerFactory
    public static EntityManagerFactory getEMF() {
        return emfInstance;
    }


    //  createEntityManager() est une methode de la classe EntityManagerFactory et pas de la classe EMF
    public static EntityManager getEM() { return emfInstance.createEntityManager(); }
 
 /*	Create EntityManager in others classes
  * EntityManager em = EMF.getEM();
  * try {
  *     // ... do stuff with em ...
  * } finally {
  *     em.close();
  * }
  */
}
