package com.leverx.learn.blogme.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Viktar on 27.05.2020
 */
@Configuration
@EnableJpaRepositories("com.leverx.learn.blogme.repository")
@EnableTransactionManagement
@ComponentScan("com.leverx.learn.blogme")
@PropertySource("classpath:db.properties")
public class DatabaseConfig {


    private static final String ENTITY_PACKAGE = "com.leverx.learn.blogme.entity";
    private static final String HIBERNATE_PROPERTIES = "hibernate.properties";


    private Environment env;

    @Autowired
    public DatabaseConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(ENTITY_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());

        return em;
    }

    private Properties getHibernateProperties() {
        Properties properties = new Properties();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(HIBERNATE_PROPERTIES);
        try {
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new IllegalArgumentException("There is no hibernate.properties in classpath");
        }
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(env.getRequiredProperty("db.url"));
        ds.setDriverClassName(env.getRequiredProperty("db.driver"));
        ds.setUsername(env.getRequiredProperty("db.username"));
        ds.setPassword(env.getRequiredProperty("db.password"));


        return ds;
    }

//    @Value("${db.driver}")
//    private String DATABASE_DRIVER;
//    @Value("${db.url}")
//    private String DATABASE_URL;
//    @Value("${db.username}")
//    private String DATABASE_USERNAME;
//    @Value("${db.password}")
//    private String DATABASE_PASSWORD;
//
//    private static final String ENTITY_PACKAGE = "com.leverx.learn.blogme.entity";
//    private static final String HIBERNATE_PROPERTIES = "hibernate.properties";
//
//    @Autowired
//    private Environment env;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(getDataSource());
//        em.setPackagesToScan(ENTITY_PACKAGE);
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//        em.setJpaProperties(getHibernateProperties());
//        return em;
//    }
//
//    @Bean
//    public DataSource getDataSource() {
//        BasicDataSource ds = new BasicDataSource();
//        ds.setUrl(DATABASE_URL);
//        ds.setDriverClassName(DATABASE_DRIVER);
//        ds.setUsername(DATABASE_USERNAME);
//        ds.setPassword(DATABASE_PASSWORD);
//
//        return ds;
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(getDataSource());
//        sessionFactory.setPackagesToScan("com.leverx.learn.blogme");
//        sessionFactory.setHibernateProperties(getHibernateProperties());
//
//        return sessionFactory;
//    }
//
////    @Bean
////    public PlatformTransactionManager transactionManager() {
////        JpaTransactionManager transactionManager = new JpaTransactionManager();
////        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
////        return transactionManager;
////    }
//
//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        sessionFactory = sessionFactory().getObject();
//        txManager.setSessionFactory(sessionFactory);
//
//        return txManager;
//    }
//
//    private Properties getHibernateProperties() {
//        Properties properties = new Properties();
//        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(HIBERNATE_PROPERTIES);
//        try {
//            properties.load(inputStream);
//            return properties;
//        } catch (IOException e) {
//            throw new IllegalArgumentException("There is no hibernate.properties in classpath");
//        }
//    }
}
