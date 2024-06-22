package com.secondaryif.server.config;

import jakarta.persistence.EntityManagerFactory;
import org.neo4j.driver.Driver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionManagerConfig {
    @Primary
    @Bean("transactionManager") // spring은 기본 transactionManager가 있다. 주로 쓰이는걸 transactionManager로 하자.
    public JpaTransactionManager h2TransactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
    @Bean("neo4jTransactionManager")
    public Neo4jTransactionManager neo4jTransactionManager(@Qualifier("neo4jDriver") Driver driver) {
        return new Neo4jTransactionManager(driver);
    }
    @Bean("chainedTransactionManager")
    public PlatformTransactionManager chainedTransactionManager(JpaTransactionManager h2TransactionManager,
                                                         Neo4jTransactionManager neo4jTransactionManager) {
        return new ChainedTransactionManager(h2TransactionManager, neo4jTransactionManager);
    }
}
