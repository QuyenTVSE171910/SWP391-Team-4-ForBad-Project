package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.FacilityRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.Facility;
import com.swp391.teamfour.forbadsystem.repository.FacilityRepository;
import com.swp391.teamfour.forbadsystem.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacilityServiceImp implements FacilityService {

    @Autowired
    private FacilityRepository facilityRepository;

    @Override
    public List<FacilityRequest> getAllFacility() {
        if (facilityRepository.findAll().isEmpty()) throw new RuntimeException("Danh sách tiện ích trống.");
        return facilityRepository.findAll().stream().map(facility -> FacilityRequest.build(facility)).collect(Collectors.toList());
    }

    @Override
    public FacilityRequest addFacility(FacilityRequest facilityRequest) {
        try {
            if (facilityRepository.existsByFacilityName(facilityRequest.getFacilityName())) throw new RuntimeException("Tiện ích này đã có trong hệ thống.");

            Facility facility = new Facility();
            facility.setFacilityName(facilityRequest.getFacilityName());

            facilityRepository.save(facility);

            return FacilityRequest.build(facility);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public FacilityRequest updateFacility(FacilityRequest facilityRequest) {
        try {
            Facility existingFacility = facilityRepository.findById(facilityRequest.getFacilityId())
                    .orElseThrow(() -> new RuntimeException("Tiện ích này không tồn tại trong hệ thống."));

            if (facilityRepository.existsByFacilityName(facilityRequest.getFacilityName()))
                throw new RuntimeException("Tiện ích này đã tồn tại trong hệ thống.");

            existingFacility.setFacilityName(facilityRequest.getFacilityName());
            facilityRepository.save(existingFacility);

            return FacilityRequest.build(existingFacility);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteFacility(Long facilityId) {
        try {
            if (!facilityRepository.existsById(facilityId)) throw new RuntimeException("Tiện ích này không tồn tại trong hệ thống.");

            facilityRepository.deleteById(facilityId);
        } catch (Exception ex) {
            throw ex;
        }
    }


}
