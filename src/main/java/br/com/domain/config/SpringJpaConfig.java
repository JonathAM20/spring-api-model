package br.com.domain.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class SpringJpaConfig {

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();

//        POSTGRESQL CONFIGURATION
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://127.0.0.1:5432/spring-api-model");
//        MYSQL CONFIGURATION
//        ds.setDriverClassName("com.mysql.jdbc.Driver");
//        ds.setUrl("jdbc:mysql://serverName:3306/instanceName?createDatabaseIfNotExist=true");
//        SQL SERVER CONFIGURATION
//        ds.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//        ds.setUrl("jdbc:sqlserver://serverName;databaseName=instanceName");
//        ORACLE CONFIGURATION
//        ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
//        ds.setUrl("jdbc:oracle:thin:@192.168.3.152:49161:XE");
        ds.setUsername("postgres");
        ds.setPassword("postgres");

        return ds;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource());
        factory.setPackagesToScan("br.com.domain");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        factory.setJpaProperties(jpaProperties());
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public JpaTransactionManager transactionManager(){
        JpaTransactionManager tx = new JpaTransactionManager();
        tx.setEntityManagerFactory(entityManagerFactory());
        tx.setJpaDialect(new HibernateJpaDialect());
        return tx;
    }

    private Properties jpaProperties(){
        Properties props = new Properties();
//        POSTGRESQL CONFIGURATION
        props.setProperty("hibernate.dialect","org.hibernate.dialect.PostgreSQLDialect");
//        MYSQL CONFIGURATION
//        props.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
//        SQL SERVER CONFIGURATION
//        props.setProperty("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
//        ORACLE CONFIGURATION
//        props.setProperty("hibernate.dialect","org.hibernate.dialect.OracleDialect");
        props.setProperty("hibernate.show_sql","true");
        props.setProperty("hibernate.format_sql","true");
        props.setProperty("hibernate.hbm2ddl.auto","update");
        return props;
    }
}