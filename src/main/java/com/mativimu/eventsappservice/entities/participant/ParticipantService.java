package com.mativimu.eventsappservice.entities.participant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.mativimu.eventsappservice.entities.event.Event;
import com.mativimu.eventsappservice.entities.event.EventService;
import com.mativimu.eventsappservice.entities.user.UserService;

@Service
public class ParticipantService {
    
    private final ParticipantRepository participantRepository;
    private final UserService userService;
    private final EventService eventService;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, 
                                UserService userService, EventService eventService) {
        this.participantRepository = participantRepository;
        this.userService = userService;
        this.eventService = eventService;
    }

    public Participant getParticipantById(Long participantId) throws DataAccessException {
        boolean exists = participantRepository.existsById(participantId);
        if(!exists) {
            throw new IllegalStateException("Participant with id " + participantId + " not found");
        }
        return participantRepository.findById(participantId).get();
    }

    public List<Participant> getParticipantsByEvent(Event event) throws DataAccessException {
        return participantRepository.findEventParticipants(event.getEventId());
    }

    public void addParticipant(String participantStatus, String attendanceProved, Long userId, Long eventID) {
        boolean exists = participantRepository.existsById(userId + eventID);
        if(exists) {
            throw new IllegalStateException("Participant already exists");
        }
        participantRepository.save(
            new Participant(
                participantStatus, userService.getUserById(userId), eventService.getEventById(eventID)
            )
        );
    }

    public void updateAttendanceProved(Long userId, Long eventID, String attendanceProved) throws DataAccessException {
        Long participantID = userId + eventID;
        boolean exists = participantRepository.existsById(participantID);
        if(!exists) {
            throw new IllegalStateException("Participant with id " + participantID + " not found");
        }
        participantRepository.findById(participantID).get()
            .setAttendanceProved(attendanceProved);
    }

    public void deleteParticpant(Long participantID) throws DataAccessException{
        boolean exists = participantRepository.existsById(participantID);
        if(!exists) {
            throw new IllegalStateException("Participant with id " + participantID + " not found");
        }
        participantRepository.deleteById(participantID);
    }
}
