package com.mativimu.eventsappservice.domain.event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.Hashing;
import com.mativimu.eventsappservice.domain.participant.ParticipantService;
import com.mativimu.eventsappservice.security.TokenUtils;
import com.mativimu.eventsappservice.utils.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;
    private final ParticipantService participantService;

    @Autowired
    public EventController(EventService eventService, ParticipantService participantService) {
        this.eventService = eventService;
        this.participantService = participantService;
    }

    @GetMapping("/all/{token}")
    public ResponseEntity<List<Event>> getEvents(@PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        return 
            ResponseEntity.ok().body(eventService.getEvents());        
    }

    @GetMapping("/{code}/{token}")
    public ResponseEntity<List<Event>> getEventsByCode(
                @PathVariable("token") String token, @PathVariable("code") String email) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        return 
            ResponseEntity.ok().body(eventService.getEventsByCode(email));
    }

    @GetMapping("/id/{id}/{token}")
    public ResponseEntity<Event> getEventById(
                @PathVariable("token") String token, @PathVariable("id") String id) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        Long userId = Long.parseLong(id);
        return 
            ResponseEntity.ok().body(eventService.getEventById((userId)));
    }

    @GetMapping("/subscribed/user/{id}/{token}")
    public ResponseEntity<List<Event>> getSubscribedEvents(
                @PathVariable("token") String token, @PathVariable("id") String id) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        List<Event> userEventsIds = participantService
            .getEventsByUserIdAndStatus(Long.parseLong(id), "guest");
        List<Event> subscribedEvents = new ArrayList<>();
        userEventsIds.stream()
        .forEach( (event) -> { 
            subscribedEvents.add(eventService.getEventById(event.getEventId()));
        });
        return 
            ResponseEntity.ok().body(subscribedEvents);
    }

    @GetMapping("/created/user/id/{id}/{token}")
    public ResponseEntity<List<Event>> getCreatedEvents(
                @PathVariable("token") String token, @PathVariable("id") String id) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        List<Event> userEventsIds = participantService
            .getEventsByUserIdAndStatus(Long.parseLong(id), "owner");
        List<Event> createdEvents = new ArrayList<>();
        userEventsIds.stream()
        .forEach( (event) -> { 
            createdEvents.add(eventService.getEventById(event.getEventId()));
        });
        return 
            ResponseEntity.ok().body(createdEvents);
    }

    @PostMapping("/add/owner/{user_id}/{token}")
    public ResponseEntity<Message> addEvent(
                @PathVariable("token") String token, @RequestBody Event event, @PathVariable("user_id") String userId) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        log.info("DateTime format extracted from json request: " + event.getEventDate());
        String hashingSource = event.getEventCode() + event.getEventName() + event.getEventType() + event.getEventDate();
        String fingerprint = Hashing.sha256()
            .hashString(hashingSource , StandardCharsets.UTF_8).toString();
        eventService
            .addEvent(
                event.getEventCode(),event.getEventName(), event.getEventType(),event.getEventDate()
            );
        Event justAddedEvent = eventService.getEventByFingerprint(fingerprint);
        participantService
            .addParticipant(
                "owner", "false", Long.parseLong(userId),justAddedEvent.getEventId()
            );
        return 
            ResponseEntity.ok().body(new Message("Event Created"));
    }

    @DeleteMapping("/remove/{id}/{token}")
    public ResponseEntity<Message> deleteUser(@PathVariable("token") String token, @PathVariable("id") String id) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        Long userId = Long.parseLong(id);
        eventService.deleteEvent(userId);
        return 
            ResponseEntity.ok().body(new Message("Event Deleted"));
    }

    @PutMapping("/set/{token}")
    public ResponseEntity<Message> updateEvent(@RequestBody Event event, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        eventService.updateEvent(
            event.getEventId(),
            event.getEventCode(),
            event.getEventName(),
            event.getEventType(),
            event.getEventDate()
        );
        return 
            ResponseEntity.ok().body(new Message("Event Updated"));
    }

}