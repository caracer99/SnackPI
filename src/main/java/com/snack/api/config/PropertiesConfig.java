package com.snack.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PropertiesConfig {
	
	@Profile("dev")
	@Configuration
	public static class PropertiesDevConfig {
						
		@Bean
		public static PropertySourcesPlaceholderConfigurer properties() {
			PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
			
			log.info("Dev Server Start");
			
			conf.setLocations(
				new ClassPathResource("properties/dev/db.properties"),
				new ClassPathResource("properties/dev/info.properties")
			);
			
			return conf;
		}
		
	}
	
	@Profile("real")
	@Configuration
	public static class PropertiesRealConfig {
			
		@Bean
		public static PropertySourcesPlaceholderConfigurer properties() {
			PropertySourcesPlaceholderConfigurer conf = new PropertySourcesPlaceholderConfigurer();
			
			log.info("Real Server Start");
			
			conf.setLocations(
				new ClassPathResource("properties/real/db.properties"),
				new ClassPathResource("properties/real/info.properties")
			);
			
			return conf;
		}
		
	}

	
	
}