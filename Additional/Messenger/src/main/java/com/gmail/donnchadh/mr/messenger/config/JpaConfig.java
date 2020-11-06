package com.gmail.donnchadh.mr.messenger.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaConfig {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("com.gmail.donnchadh.mr.messenger");
        }

        return entityManagerFactory;
    }

}
