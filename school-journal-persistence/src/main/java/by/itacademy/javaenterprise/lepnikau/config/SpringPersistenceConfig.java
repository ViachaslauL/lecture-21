package by.itacademy.javaenterprise.lepnikau.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Objects;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:db.properties")
@ComponentScan(basePackages = "by.itacademy.javaenterprise.lepnikau.dao")
public class SpringPersistenceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public BasicDataSource basicDataSource() {

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(environment.getProperty("db.driver.class"));
        dataSource.setUrl(environment.getProperty("db.url"));
        dataSource.setUsername(environment.getProperty("db.user"));
        dataSource.setPassword(environment.getProperty("db.password"));
        dataSource.setInitialSize(Integer.parseInt(
                Objects.requireNonNull(environment.getProperty("db.initial.size"))
        ));
        dataSource.setMaxTotal(Integer.parseInt(
                Objects.requireNonNull(environment.getProperty("db.pool.size.max"))
        ));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
                new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(basicDataSource());
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        entityManagerFactoryBean.setPackagesToScan("by.itacademy.javaenterprise.lepnikau.entity");
        entityManagerFactoryBean.setJpaProperties(hibernateProperties());

        return entityManagerFactoryBean;
    }

    @Bean
    public JpaTransactionManager jpaTransactionManager() {
        return new JpaTransactionManager(
                entityManagerFactory().getNativeEntityManagerFactory());
    }

    @Bean
    public TransactionTemplate transactionTemplate() {
        TransactionTemplate template = new TransactionTemplate();
        template.setTransactionManager(jpaTransactionManager());
        template.setTimeout(120);
        return template;
    }

    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }
}
