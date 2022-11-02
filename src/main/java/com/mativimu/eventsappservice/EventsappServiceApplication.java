package com.mativimu.eventsappservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mativimu.eventsappservice.entities.user.User;
import com.mativimu.eventsappservice.entities.user.UserService;

@SpringBootApplication
public class EventsappServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsappServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveUser(new User("Matías Vigo Muñoz", "mativimu", "mativimu@gmail.com", "1234", "Student"));	
			userService.saveUser(new User("Sebastian Vigo Muñoz", "sebavimu", "sebavimu@gmail.com", "1234", "Student"));	
			userService.saveUser(new User("David Romero", "davit", "d.romero@gmail.com", "sandevistan", "edgerunner"));	
		};
	}

}
