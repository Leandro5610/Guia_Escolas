package br.senai.sp.escolaguide.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.senai.sp.escolaguide.interceptor.AppInterceptor;

@Configuration
public class AppConfig implements WebMvcConfigurer {
	@Autowired
	private AppInterceptor interceptor;
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor);
	}
	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3307/escolaguide");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		return dataSource;
	}
	
		//configura o Hibernate (ORM - Mapeamento Obejeto Relacioanal)
		@Bean
		public JpaVendorAdapter jpaVendorAdapter() {
			HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
			adapter.setDatabase(Database.MYSQL);
			adapter.setDatabasePlatform("org.hibernate.dialect.MySQL8Dialect");
			adapter.setShowSql(true);
			adapter.setPrepareConnection(true);
			adapter.setGenerateDdl(true);
			return adapter;
		}
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/**");
		}
		
		
		
}
