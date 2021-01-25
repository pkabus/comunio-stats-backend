package pkabus.comuniostatsbackend;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class PersistenceTestConfiguration {

	private TestDbProperties testProperties;

	public PersistenceTestConfiguration(final TestDbProperties testProperties) {
		this.testProperties = testProperties;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(testProperties.getDriver());
		dataSource.setUrl(testProperties.getUrl());
		dataSource.setUsername(testProperties.getUsername());
		dataSource.setPassword(testProperties.getPassword());
		return dataSource;
	}
}
