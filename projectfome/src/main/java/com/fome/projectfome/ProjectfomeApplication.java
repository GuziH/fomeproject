package com.fome.projectfome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ProjectfomeApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(ProjectfomeApplication.class, args);

	}

}
