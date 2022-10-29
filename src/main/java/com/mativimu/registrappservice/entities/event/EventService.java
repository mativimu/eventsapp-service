package com.mativimu.registrappservice.entity.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    
    private final EventRepository eventRepo;

    @Autowired
    public EventService(EventRepository eventRepo) {
        this.eventRepo = eventRepo;
    }
    
}
