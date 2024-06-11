package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.YardRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.Yard;

import java.util.List;

public interface YardService {

    public List<Yard> getAllYardByCourtId(String courtId);
    public Yard createYard(YardRequest yardRequest);

    public Yard updateYard(YardRequest yardRequest);

    public Yard findYardById(String yardId);

    public void deleteYardById(String yardId);
}
