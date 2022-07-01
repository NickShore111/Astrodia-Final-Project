package org.nicholasshore.astrodia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class AstrodiaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AstrodiaApplication.class, args);
	}

}
