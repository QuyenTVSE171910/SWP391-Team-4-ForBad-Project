package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.CourtRequest;
import com.swp391.teamfour.forbadsystem.dto.request.FacilityRequest;
import com.swp391.teamfour.forbadsystem.dto.response.CourtResponse;
import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.model.Court;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface CourtService {

    List<Court> getAll();

    List<Court> getAllOfOwner();

     CourtResponse addCourt(CourtRequest newCourt) throws IOException;

    void deleteCourt(String courtId);

    CourtResponse updateCourt(CourtRequest courtRequest) throws IOException;

    void addStaffToCourt(String courtId, String staffId);

    List<UserInfor> getAllStaffByCourtId(String courtId);

    void deleteStaffFromCourt(String courtId, String staffId);

    void addFacilityToCourt(String courtId, Long facilityId);

    void deleteFacilityFromCourt(String courtId, Long facilityId);

    List<FacilityRequest> getAllFacilityByCourtId(String courtId);
}
