package com.ninjacart.sample.id;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service
public class NinjaIdGenerator implements IdentifierGenerator, ApplicationContextAware {

    // We cannot autowire this as it will be instantiated by hibernate not by Spring.
    private static TimeBasedIdGeneratorService timeBasedIdGeneratorService;

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        return NinjaIdGenerator.timeBasedIdGeneratorService.getUniqueId();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    		NinjaIdGenerator.timeBasedIdGeneratorService = applicationContext.getBean(TimeBasedIdGeneratorService.class);
    }
}
