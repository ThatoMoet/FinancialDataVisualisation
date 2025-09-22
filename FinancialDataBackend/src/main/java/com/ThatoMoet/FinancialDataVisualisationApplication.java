package com.ThatoMoet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ThatoMoet.repository")
public class FinancialDataVisualisationApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinancialDataVisualisationApplication.class, args);
	}

}
