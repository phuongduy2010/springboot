package com.nhuy.orderservice.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    @Bean(name = "transactionManager")
    @Primary
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean(name = "jpaTransactionTemplate")
    public TransactionTemplate transactionTemplate(@Qualifier("transactionManager")
                                                   PlatformTransactionManager jpaTxManager) {
        return new TransactionTemplate(jpaTxManager);
    }

}
