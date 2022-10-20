package com.mativimu.registrappservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mativimu.registrappservice.user.UserService;
import com.mativimu.registrappservice.user.User;

@SpringBootApplication
public class RegistrappServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrappServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveUser(new User(null,"Matías Vigo Muñoz", "mativimu", "mativimu@gmail.com", "1234", "Student"));	
			userService.saveUser(new User(null,"Sebastian Vigo Muñoz", "sebavimu", "sebavimu@gmail.com", "1234", "Student"));	
			userService.saveUser(new User(null,"Javiera Vigo Muñoz", "javimu", "javimu@gmail.com", "1234", "Student"));	
		};
	}

}
