package com.mativimu.eventsappservice.domain.event;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface EventRepository 
            extends JpaRepository<Event, Long> {
    
    @Query("SELECT e FROM Event e WHERE e.eventCode = :code")
    public List<Event> findEventByCode(@Param("code") String code);

    @Query("SELECT e FROM Event e WHERE e.fingerprint = :fingerprint")
    public List<Event> findEventByFingerprint(@Param("fingerprint") String fingerprint);
}
