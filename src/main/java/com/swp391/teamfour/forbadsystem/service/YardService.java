package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.YardRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.Yard;

import java.util.List;

public interface YardService {

    List<Yard> getAllYardByCourtId(String courtId);

    Yard createYard(YardRequest yardRequest);

    Yard updateYard(YardRequest yardRequest);

    Yard findYardById(String yardId);

    void deleteYardById(String yardId);
}
