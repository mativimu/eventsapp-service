package com.mativimu.eventsappservice;

import java.text.SimpleDateFormat;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mativimu.eventsappservice.domain.event.Event;
import com.mativimu.eventsappservice.domain.event.EventRepository;
import com.mativimu.eventsappservice.domain.participant.Participant;
import com.mativimu.eventsappservice.domain.participant.ParticipantRepository;
import com.mativimu.eventsappservice.domain.user.User;
import com.mativimu.eventsappservice.domain.user.UserRepository;

@SpringBootApplication
public class EventsappServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventsappServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner run(UserRepository userRepository, EventRepository eventRepository, ParticipantRepository participantRepository) {
		return args -> {
			eventRepository.save(
				new Event(
					"PGY4121",
					"Programación de Aplicaciones Móviles",
					"Lección",
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("08-11-2022 19:00:00")
				)
			);
			eventRepository.save(
				new Event(
					"GPI0101",
					"Gestión de Personas",
					"Lección",
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("09-11-2022 20:30:00")
				)
			);
			eventRepository.save(
				new Event(
					"PGY4121",
					"Programación de Aplicaciones Móviles",
					"Lección",
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("10-11-2022 19:00:00")
				)
			);
			eventRepository.save(
				new Event(
					"OSY5111",
					"Sistemas Operativos",
					"Lección",
					new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse("12-11-2022 20:30:00")
				)
			);
			userRepository.save(
				new User(
				"mativimu",
				"mativimu@gmail.com",
				"d7f3fa33e69a29d562d36f95b5a990a6bf4db44ceb2dc70b7bb0750f552b45dc",
				"Unknown",
				"Estudiante"
				)
			);
			participantRepository.save(
				new Participant(
					"owner",
					userRepository.findById(1L).get(),
					eventRepository.findById(1L).get())
			);
		};
	}
}
