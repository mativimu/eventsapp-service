package com.mativimu.eventsappservice.domain.participant;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mativimu.eventsappservice.domain.user.User;
import com.mativimu.eventsappservice.domain.user.UserService;
import com.mativimu.eventsappservice.security.TokenUtils;
import com.mativimu.eventsappservice.utils.Message;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.ResponseEntity;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/participants")
public class ParticipantController  {

    private final ParticipantService participantService;
    private final UserService userService;

    @Autowired
    public ParticipantController(ParticipantService participantService, UserService userService) {
        this.participantService = participantService;
        this.userService = userService;
    }

    @GetMapping("/from-event/{id}/{token}")
    public ResponseEntity<List<User>> getEventParticipants(
                @PathVariable("id") String id,@PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        List<User> eventUsersIds = participantService
            .getUsersByEvent(Long.parseLong(id));
        List<User> eventParticipants = new ArrayList<>();
        eventUsersIds.stream()
            .forEach( (user) -> { 
            eventParticipants.add(userService.getUserById(user.getUserId()));
        });
        return ResponseEntity.ok().body(eventParticipants);
    }

    @PostMapping("/add/id/{user_id}/status/{status}/event/{event_id}/proof/{proof}/{token}")
    public ResponseEntity<Message> addParticipant(
            @PathVariable("id") String userId, @PathVariable("status") String status,
            @PathVariable("event_id") String eventId, @PathVariable("proof") String proof, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        participantService.addParticipant(status, proof, Long.parseLong(userId), Long.parseLong(eventId));
        return ResponseEntity.ok().body(new Message("User added to database"));
    }

    @DeleteMapping("/id/{id}/remove/{token}")
    public ResponseEntity<Message> deleteParticipant(
                        @PathVariable("id") String id, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        participantService.deleteParticipant(Long.parseLong(id));
        return ResponseEntity.ok().body(new Message("User added to database"));
    }

    @PutMapping("/id/{user_id}/event/{event_id}/set/proof/{token}")
    public ResponseEntity<Message> updateAttendanceProved(@PathVariable("user_id") String userId,
                        @PathVariable("event_id") String eventId, @PathVariable("token") String token) {
        boolean isValid = TokenUtils.verifyToken(token);
        if(!isValid){
            throw new InvalidParameterException("Invalid Token");
        }
        participantService
            .updateAttendanceProved(Long.parseLong(userId), Long.parseLong(eventId), "true");            
        return ResponseEntity.ok().body(new Message("User added to database"));
    }

}