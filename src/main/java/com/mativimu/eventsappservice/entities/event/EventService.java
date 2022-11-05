package com.mativimu.eventsappservice.entities.event;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvents() {
        return eventRepository.findAll();
    }

    public Event getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
            .orElseThrow(() -> 
                new IllegalStateException("Event with id "+ eventId + " not found"));
        return event;
    }

    public List<Event> getEventByCode(String eventCode) {
        List<Event> events = eventRepository.findEventByCode(eventCode);
        if(events.size() == 0) {
            throw new IllegalStateException("There is no events with " + eventCode + "code");
        }
        return events;
    }

    public void addEvent(Event event) throws DataAccessException {
        boolean exists = eventRepository.existsById(event.getEventId());
        if(!exists) {
            throw new IllegalStateException("Event with id " + event.getEventId() + " not found");
        }
        eventRepository.save(event);
    }
    
    public void deleteEvent(Long eventId) {
        boolean exists = eventRepository.existsById(eventId);
        if(!exists) {
            throw new IllegalStateException("Event with id " + eventId + " not found");
        }
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public void updateEvent(Long eventId, String eventCode, 
                            String eventName,String eventType, Date eventDate) throws DataAccessException {

        boolean exists = eventRepository.existsById(eventId);
        if(!exists){
            throw new IllegalStateException("Event with id " + eventId + " not found");
        }
        Event event = eventRepository.findById(eventId).get();
        event.setEventCode(eventCode);
        event.setEventName(eventName);
        event.setEventType(eventType);
        event.setEventDate(eventDate);
    }
    
}