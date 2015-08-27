package br.com.s2.mercadorias.helper;

import java.util.Properties;
import java.util.logging.Logger;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.com.s2.mercadorias.dao.PropriedadesDao;
import br.com.s2.mercadorias.pojo.Propriedades;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableTransactionManagement
public class JpaUtils {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Bean()
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrincipal() throws Exception {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setPersistenceUnitName("unitPrincipal");
		em.setDataSource(dataSourcePrincipal());
		
		em.setPackagesToScan(new String[] { "br.com.s2.mercadorias.pojo" });
	
		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());
	
		return em;
	}
	
	@Bean
	public ComboPooledDataSource dataSourcePrincipal() throws Exception {
		Propriedades props = new PropriedadesDao().ler();
		if ( props.getPath() == null || props.getPath().isEmpty() ){
			logger.severe("Não foi possivel obter do arquivo de parametros o path de conexão da base de dados. Por favor, verifique!");
		}
			
		
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		dataSource.setDriverClass("com.mysql.jdbc.Driver");
		dataSource.setJdbcUrl(props.getPath());
		dataSource.setUser(props.getUsuario());
		dataSource.setPassword(props.getEnoc());
		dataSource.setMinPoolSize(5);
		dataSource.setAcquireIncrement(1);
		dataSource.setMaxPoolSize(500);
		dataSource.setAcquireRetryAttempts(30);
		dataSource.setCheckoutTimeout(30000);
		dataSource.setPreferredTestQuery("select 1");
		dataSource.setAcquireRetryAttempts(10);
		dataSource.setTestConnectionOnCheckin(true);
		
		return dataSource;
	}
	
	@Bean
	public PlatformTransactionManager transactionManagerPrincipal(
			EntityManagerFactory emf) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		
		return transactionManager;
	}
	
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
	
	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "validate");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		properties.setProperty("format_sql", "true");
		properties.setProperty("hibernate.show_sql", "true");
		return properties;
	}
}
