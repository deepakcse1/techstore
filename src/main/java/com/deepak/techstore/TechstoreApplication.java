package com.deepak.techstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TechstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechstoreApplication.class, args);
	}

}
