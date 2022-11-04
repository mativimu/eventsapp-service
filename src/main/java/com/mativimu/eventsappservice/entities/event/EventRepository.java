package com.mativimu.eventsappservice.entities.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository 
            extends JpaRepository<Event, Long> {
    
    @Query("SELECT e FROM Event e WHERE e.eventCode = :code")
    public Event findEventByCode(@Param("code") String code);
}