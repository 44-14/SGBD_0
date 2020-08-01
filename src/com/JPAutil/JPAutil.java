package com.JPAutil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public final class JPAutil {

    private JPAutil() {}

    private static EntityManagerFactory emf;

    public static EntityManager createEntityManager(final String persistUnit) {
        if (emf == null) {

            emf = Persistence.createEntityManagerFactory(persistUnit);
        }
        return emf.createEntityManager();
    }
}