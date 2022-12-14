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
				"Matías Vigo",
				"Estudiante"
				)
			);
			userRepository.save(
				new User(
				"javiera",
				"j.vigo@gmail.com",
				"a8d28817470031b8c39fd613f6aa0e8a8a9862f0b3484667389f2982f320173a",
				"Javiera Vigo",
				"Estudiante"
				)
			);
			userRepository.save(
				new User(
				"Sebastian",
				"s.vigo@gmail.com",
				"b8c51b703aaab0f018a3f553b8f621cefaa6aac1cac1233f29cc73b4564916ad",
				"Sebastian Vigo",
				"Estudiante"
				)
			);
			userRepository.save(
				new User(
				"Veronica",
				"v.munoz@gmail.com",
				"497183e7172c561938f75858f0c9a2afc0dfc82a9cf9c986394e3395770e0ed3",
				"Verónica Muñoz",
				"Estudiante"
				)
			);
			userRepository.save(
				new User(
				"Reinaldo",
				"r.vigo@gmail.com",
				"22e25ed1b8483dbffa8f48c92d30a5ae0c9b78a2e46f06aee2fe0ff4a6f65407",
				"Reinaldo Vigo",
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
