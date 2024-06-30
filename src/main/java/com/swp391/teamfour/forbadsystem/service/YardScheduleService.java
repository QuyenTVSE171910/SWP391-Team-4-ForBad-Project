package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;

import java.util.List;

public interface YardScheduleService {

    void addTimeSlotToYard(String yardId, String slotId);

    void deleteSlotFromYard(String yardId, String timeSlotId);

    List<TimeSlotRequest> getAllSlotByYardId(String yardId);
}

