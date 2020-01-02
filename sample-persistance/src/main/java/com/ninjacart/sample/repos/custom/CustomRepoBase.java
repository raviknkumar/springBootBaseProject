package com.ninjacart.sample.repos.custom;

import org.hibernate.Session;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

@Transactional(value = "coreTransactionManager",readOnly = true)
public class CustomRepoBase<E> {

    @PersistenceContext(name = "coreTransactionManager")
    private EntityManager entityManager;


    private Class<E> getPersistenceClass(){
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<E> type = (Class<E>) superClass.getActualTypeArguments()[0];
        return type;
    }

    protected Session currentSession() {
        return entityManager.unwrap(Session.class);
    }




}
