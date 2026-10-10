package com.example.agriculture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {
	
	 @Bean
	 public OpenAPI agricultureFarmOpenAPI() 
	 {
		 return new OpenAPI()
				 .info(new Info()
						 .title("Agriculture Farm Management API")
						 .version("1.0")
						 .description("REST API for managing farmers, farms, fields, crops," + "activities, expenses and harvests.")
						 .contact(new Contact()
								 .name("Sheela Reddy")
								 .email("sheelareddy.000@gmail.com")));
						 				 
	 }

}
