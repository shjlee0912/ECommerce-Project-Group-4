package com.hcl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class ProductManagerApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ProductManagerApplication.class, args);
	}
}
