package com.leverx.learn.blogme.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
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

    @Value("${db.driver}")
    private String DATABASE_DRIVER;
    @Value("${db.url}")
    private String DATABASE_URL;
    @Value("${db.username}")
    private String DATABASE_USERNAME;
    @Value("${db.password}")
    private String DATABASE_PASSWORD;

    private static final String ENTITY_PACKAGE = "com.leverx.learn.blogme.entity";
    private static final String HIBERNATE_PROPERTIES = "hibernate.properties";


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(ENTITY_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(getHibernateProperties());
        return em;
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUrl(DATABASE_URL);
        ds.setDriverClassName(DATABASE_DRIVER);
        ds.setUsername(DATABASE_USERNAME);
        ds.setPassword(DATABASE_PASSWORD);

        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
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
}
