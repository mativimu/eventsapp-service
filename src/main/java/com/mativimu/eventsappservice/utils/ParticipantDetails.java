package com.mativimu.eventsappservice.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantDetails {
    private Long userId;
    private String userFullName;
    private String userEmail;
    private String participantStatus;
    private String attendanceProved;
}
