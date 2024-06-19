package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.FacilityRequest;
import com.swp391.teamfour.forbadsystem.model.Facility;

import java.util.List;

public interface FacilityService {
    List<FacilityRequest> getAllFacility();

    FacilityRequest addFacility(FacilityRequest facilityRequest);

    FacilityRequest updateFacility(FacilityRequest facilityRequest);

    void deleteFacility(Long facilityId);
}
