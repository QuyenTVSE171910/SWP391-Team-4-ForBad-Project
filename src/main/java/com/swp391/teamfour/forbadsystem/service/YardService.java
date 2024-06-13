package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.YardRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.Yard;

import java.util.List;

public interface YardService {

    List<Yard> getAllYardByCourtId(String courtId);

    Yard createYard(YardRequest yardRequest);

    Yard updateYard(YardRequest yardRequest);

    Yard findYardById(String yardId);

    void deleteYardById(String yardId);

    void addTimeSlotToYard(String yardId, String slotId);
}
