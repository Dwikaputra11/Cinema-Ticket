package com.spring.binar.challenge_5;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition
public class Challenge5Application {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(Challenge5Application.class, args);
	}

}
