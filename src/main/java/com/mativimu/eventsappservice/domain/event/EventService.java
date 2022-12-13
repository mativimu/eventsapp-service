package com.mativimu.eventsappservice.domain.event;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.hash.Hashing;


@Service
public class EventService {

    private final EventRepository eventRepository;

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

    public List<Event> getEventsByCode(String eventCode) {
        return eventRepository.findEventByCode(eventCode);
    }

    public Event getEventByFingerprint(String fingerprint) {
        return eventRepository.findEventByFingerprint(fingerprint).get(0);
    }

    public void addEvent(String eventCode, String eventName,
                            String eventType, Date eventDate) {
        Event event = new Event(eventCode, eventName, eventType, eventDate);
        List<Event> events = eventRepository.findEventByFingerprint(event.getFingerprint());
        if(!events.isEmpty()) {
            throw new IllegalStateException("Event already exists");
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
    public void updateEvent(Long eventId, String eventCode, String eventName,
                            String eventType, Date eventDate) {
        String fingerprint = Hashing.sha256()
            .hashString(eventCode + eventName + eventType + eventDate, StandardCharsets.UTF_8).toString();
        List<Event> events = eventRepository.findEventByFingerprint(fingerprint);
        if(!events.isEmpty()){
            throw new IllegalStateException("Event already exists");
        }
        Event event = eventRepository.findById(eventId).get();
        event.setEventCode(eventCode);
        event.setEventName(eventName);
        event.setEventType(eventType);
        event.setEventDate(eventDate);
    }
    
}