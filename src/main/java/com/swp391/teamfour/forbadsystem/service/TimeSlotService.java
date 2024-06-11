package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.User;

import java.util.List;

public interface TimeSlotService {

    public List<TimeSlot> findAllSlotByUserId(String userId);

    public TimeSlot createSlot(TimeSlotRequest timeSlotRequest);

    public TimeSlot updateSlot(TimeSlotRequest timeSlotRequest);

    public TimeSlot findSlotById(String slotId);

    public void deteleSlotById(String slotId);
}
