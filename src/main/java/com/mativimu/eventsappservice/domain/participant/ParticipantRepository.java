package com.mativimu.eventsappservice.domain.participant;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mativimu.eventsappservice.domain.event.Event;
import com.mativimu.eventsappservice.domain.user.User;


public interface ParticipantRepository
            extends JpaRepository<Participant, Long> {
    
    @Query("SELECT p.user FROM Participant p WHERE p.event.id = :id")
    public List<User> findEventParticipants(@Param("id") Long eventId);

    @Query("SELECT p.event FROM Participant p WHERE p.user.id = :id AND p.participantStatus = :status")
    public List<Event> findUserEventsByIdAndStatus(@Param("id") Long userId, @Param("status") String status);
}
