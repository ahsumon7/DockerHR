package com.example.hr;

import com.example.hr.service.EmployeeXmlService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HrApplication {

	public static void main(String[] args) {
		SpringApplication.run(HrApplication.class, args);
	}

	@Bean
	public CommandLineRunner xmlRunner(EmployeeXmlService xmlService) {
		return args -> {
			xmlService.importFromXmlClasspath();  // Load employees.xml from resources folder
		};
	}



}
