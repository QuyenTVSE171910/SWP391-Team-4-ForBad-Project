package com.swp391.teamfour.forbadsystem.controller;


import com.swp391.teamfour.forbadsystem.dto.request.CourtRequest;
import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.service.CourtService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/court")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(courtService.getAll());
    }

    @GetMapping("/courts-of-owner")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> getAllCourtOfOwner() {
        return ResponseEntity.ok(courtService.getAllOfOwner());
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addCourt(@Valid CourtRequest newCourt) throws IOException {
        return ResponseEntity.ok(courtService.addCourt(newCourt));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ResponseEntity<?> deleteCourt(@RequestParam String courtId) {
        courtService.deleteCourt(courtId);
        return ResponseEntity.ok().body(new MessageResponse("Xóa cơ sở thành công."));
    }

    @PutMapping(value = "/update", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ResponseEntity<?> updateCourt(@Valid CourtRequest courtRequest) throws IOException {
        return ResponseEntity.ok(courtService.updateCourt(courtRequest));
    }

    @PostMapping("{courtId}/add-staff/{staffId}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> addStaffToCourt(@PathVariable String courtId, @PathVariable String staffId) {
        courtService.addStaffToCourt(courtId, staffId);
        return ResponseEntity.ok(new MessageResponse("Đã thêm nhân viên vào cơ sở thành công"));
    }

    @GetMapping("/staffs-of-court/{courtId}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> getAllStaffByCourtId(@PathVariable String courtId) {
        return ResponseEntity.ok().body(courtService.getAllStaffByCourtId(courtId));
    }

    @DeleteMapping("{courtId}/deleteStaffFromCourt/{staffId}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteStaffFromCourt(@PathVariable String courtId, @PathVariable String staffId) {
        courtService.deleteStaffFromCourt(courtId, staffId);
        return ResponseEntity.ok(new MessageResponse("Đã xóa nhân viên ra khỏi danh sách nhân viên của sân"));
    }

    @PostMapping("{courtId}/addFacilityToCourt/{facilityId}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> addFacilityToCourt(@PathVariable String courtId, @PathVariable Long facilityId) {
        courtService.addFacilityToCourt(courtId, facilityId);
        return ResponseEntity.ok(new MessageResponse("Đã thêm tiện ích vào cơ sở thành công."));
    }

    @DeleteMapping("{courtId}/deleteFacilityFromCourt/{facilityId}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> deleteFacilityFromCourt(@PathVariable String courtId, @PathVariable Long facilityId) {
        courtService.deleteFacilityFromCourt(courtId, facilityId);
        return ResponseEntity.ok(new MessageResponse("Đã xóa tiện ích ra khỏi cơ sở."));
    }

    @GetMapping("/facilities-of-court/{courtId}")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> getAllFacilityByCourtId(@PathVariable String courtId) {
        return ResponseEntity.ok(courtService.getAllFacilityByCourtId(courtId));
    }

}
