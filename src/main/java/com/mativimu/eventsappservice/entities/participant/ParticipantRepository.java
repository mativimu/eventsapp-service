package com.mativimu.eventsappservice.entities.participant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository
            extends JpaRepository<Participant, Long> {
    
    @Query("SELECT p FROM Participant p WHERE p.event.id = :id")
    public List<Participant> findEventParticipants(@Param("id") Long eventId);

}
