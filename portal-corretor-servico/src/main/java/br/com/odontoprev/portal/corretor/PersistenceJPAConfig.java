package br.com.odontoprev.portal.corretor;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class PersistenceJPAConfig{
 
   @Bean
   public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
      LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
      //em.setDataSource(dataSource());
//      em.setPackagesToScan(new String[] { "org.baeldung.persistence.model" });
      em.setPackagesToScan(new String[] { "br.com.odontoprev.portal.corretor.model" });
 
      JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
      em.setJpaVendorAdapter(vendorAdapter);
      em.setJpaProperties(additionalProperties());
 
      return em;
   }
 
   @Bean
   public DataSource dataSource(){
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
//      dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
      dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
      dataSource.setUrl("jdbc:oracle:thin://brdtcx7-scan.odontoprev.com.br:1521/DCMS");
      dataSource.setUsername( "corret" );
      dataSource.setPassword( "corret" );
      return dataSource;
   }
 
   @Bean
   public PlatformTransactionManager transactionManager(EntityManagerFactory emf){
       JpaTransactionManager transactionManager = new JpaTransactionManager();
       transactionManager.setEntityManagerFactory(emf);
 
       return transactionManager;
   }
 
   @Bean
   public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
       return new PersistenceExceptionTranslationPostProcessor();
   }
 
   Properties additionalProperties() {
       Properties properties = new Properties();
//       properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
//       properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
       properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        
       return properties;
   }
}