package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.request.FacilityRequest;
import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.exception.AuthenticationExceptionHandler;
import com.swp391.teamfour.forbadsystem.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/facility")
public class FacilityController {

    @Autowired
    private FacilityService facilityService;

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ResponseEntity<?> getAllFacility() {
        return ResponseEntity.ok().body(facilityService.getAllFacility());
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> addFacility(@RequestBody FacilityRequest facilityRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AuthenticationExceptionHandler.getValidationErrors(bindingResult));
        }
        return ResponseEntity.ok().body(facilityService.addFacility(facilityRequest));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> updateFacility(@RequestBody FacilityRequest facilityRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AuthenticationExceptionHandler.getValidationErrors(bindingResult));
        }
        return ResponseEntity.ok().body(facilityService.updateFacility(facilityRequest));
    }

    @DeleteMapping("/delete/{facilityId}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteFacility(@PathVariable Long facilityId) {
        facilityService.deleteFacility(facilityId);
        return ResponseEntity.ok(new MessageResponse("Đã xóa tiện ích thành công."));
    }

}
