package com.ninjacart.sample.config;



import com.netflix.config.DynamicPropertyFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
		basePackages = "com.ninjacart.sample.repos",
		entityManagerFactoryRef = "coreEntityManager",
		transactionManagerRef = "coreTransactionManager"
)
@Slf4j
public class PersistenceConfiguration {

	@Value("${persistence.user:#{null}}") private String username;
	@Value("${persistence.password:#{null}}") private String password;
	@Value("${persistence.databaseName:#{null}}") private String database;
	@Value("${persistence.serverName:#{null}}") private String server;
	@Value("${persistence.portNumber:#{null}}") private Integer port;
	@Value("${persistence.maximumPoolSize:#{null}}") private Integer maxPoolSize;
	@Value("${persistence.connectionTimeout:#{null}}") private Long connectionTimeout;
	@Value("${persistence.jdbcUrl:#{null}}") private String jdbcUrl;


	private static final String SHOW_SQL = "hibernate.show_sql";
	private static final String FORMAT_SQL = "hibernate.format_sql";

	private Boolean getShowSql() {
		return DynamicPropertyFactory.getInstance().getBooleanProperty(SHOW_SQL, false).getValue();
	}

	private Boolean getFormatSql() {
		return DynamicPropertyFactory.getInstance().getBooleanProperty(FORMAT_SQL, false).getValue();
	}

	@Primary
	@Bean
	public javax.sql.DataSource ds1DataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.addDataSourceProperty("databaseName",database);
		ds.addDataSourceProperty("serverName", server);
		ds.addDataSourceProperty("portNumber", port);
		ds.setJdbcUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		ds.setConnectionTimeout(connectionTimeout);
		ds.setMaximumPoolSize(maxPoolSize);
		return ds;
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean coreEntityManager() {
		LocalContainerEntityManagerFactoryBean em
				= new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(ds1DataSource());
		em.setPackagesToScan(
				"com.ninjacart.sample.entity");
		HibernateJpaVendorAdapter vendorAdapter
				= new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		HashMap<String, Object> properties = new HashMap<>();
		properties.put(SHOW_SQL,getShowSql());
		properties.put(FORMAT_SQL,getFormatSql());
		em.setJpaPropertyMap(properties);
		return em;
	}


	@Primary
	@Bean
	public PlatformTransactionManager coreTransactionManager() {
		JpaTransactionManager transactionManager
				= new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(
				coreEntityManager().getObject());
		return transactionManager;
	}


	@PostConstruct
	public void migrateFlyway() {
		Flyway flyway = new Flyway();
		flyway.setDataSource(ds1DataSource());
		flyway.repair();
		flyway.migrate();
	}
}