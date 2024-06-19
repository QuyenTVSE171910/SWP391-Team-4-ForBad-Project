package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.Yard;

import java.util.List;

public interface TimeSlotService {

    List<TimeSlot> findAllSlotByUserId();

    TimeSlot createSlot(TimeSlotRequest timeSlotRequest);

    TimeSlot updateSlot(TimeSlotRequest timeSlotRequest);

    TimeSlot findSlotById(String slotId);

    void deteleSlotById(String slotId);


}
