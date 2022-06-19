package com.perscholas.astrodia;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AstrodiaApplication extends SpringBootServletInitializer {
	@Override // Used to load custom Error.html file
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AstrodiaApplication.class);
	}

	@Bean // Fixes issue with mapping List<Flights> in RestController return
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	public static void main(String[] args) {
		SpringApplication.run(AstrodiaApplication.class, args);
	}

}
