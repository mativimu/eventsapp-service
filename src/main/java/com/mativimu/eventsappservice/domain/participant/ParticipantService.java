package com.mativimu.eventsappservice.domain.participant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mativimu.eventsappservice.domain.event.Event;
import com.mativimu.eventsappservice.domain.event.EventService;
import com.mativimu.eventsappservice.domain.user.User;
import com.mativimu.eventsappservice.domain.user.UserService;

@Service
public class ParticipantService {
    //use repository layer for feed this services and make it independent form other services (that is the next commit)
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

    public Participant getParticipantById(Long participantId) {
        boolean exists = participantRepository.existsById(participantId);
        if(!exists) {
            throw new IllegalStateException("Participant with id " + participantId + " not found");
        }
        return participantRepository.findById(participantId).get();
    }

    public List<User> getUsersByEvent(Long eventId) {
        return participantRepository.findEventParticipants(eventId);
    }

    public List<Event> getEventsByUserIdAndStatus(Long userId, String status){
        return participantRepository.findUserEventsByIdAndStatus(userId, status);
    }

    public void addParticipant(String participantStatus, String attendanceProved, Long userId, Long eventId) {
        boolean exists = participantRepository.existsById(userId + eventId);
        if(exists) {
            throw new IllegalStateException("Participant already exists");
        }
        participantRepository.save(
            new Participant(
                participantStatus, userService.getUserById(userId), eventService.getEventById(eventId)
            )
        );
    }

    @Transactional
    public void updateAttendanceProved(Long userId, Long eventID, String attendanceProved) {
        Long participantID = userId + eventID;
        boolean exists = participantRepository.existsById(participantID);
        if(!exists) {
            throw new IllegalStateException("Participant with id " + participantID + " not found");
        }
        participantRepository.findById(participantID).get()
            .setAttendanceProved(attendanceProved);
    }

    public void deleteParticipant(Long participantID) throws DataAccessException{
        boolean exists = participantRepository.existsById(participantID);
        if(!exists) {
            throw new IllegalStateException("Participant with id " + participantID + " not found");
        }
        participantRepository.deleteById(participantID);
    }
}
