package com.example.modyo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactivefeign.spring.config.EnableReactiveFeignClients;

@SpringBootApplication
@EnableReactiveFeignClients
public class ModyoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModyoApplication.class, args);
	}

}
