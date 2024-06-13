package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.CourtRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface CourtService {

    List<Court> getAll();

    List<Court> getAllOfOwner(String userId);

    CourtRequest addCourt(CourtRequest newCourt);

    void deleteCourt(String courtId);

    CourtRequest updateCourt(CourtRequest courtRequest);
}
