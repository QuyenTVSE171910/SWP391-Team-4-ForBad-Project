package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.request.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.exception.AuthenticationExceptionHandler;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.service.TimeSlotService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/time-slot")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    @Autowired
    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    @GetMapping("/findAllSlot")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> findAllSlotByUser(){
        return ResponseEntity.ok(timeSlotService.findAllSlotByUserId());
    }

    @PostMapping("/createSlot")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> createSlot(@Valid @RequestBody TimeSlotRequest timeSlotRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AuthenticationExceptionHandler.getValidationErrors(bindingResult));
        }
        return ResponseEntity.ok(timeSlotService.createSlot(timeSlotRequest));
    }

    @PutMapping("/updateSlot")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> updateSlot(@Valid @RequestBody TimeSlotRequest timeSlotRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AuthenticationExceptionHandler.getValidationErrors(bindingResult));
        }
        return ResponseEntity.ok(timeSlotService.updateSlot(timeSlotRequest));
    }

    @GetMapping("/findSlot/{slotId}")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> getSlotById(@PathVariable String slotId) {
        return ResponseEntity.ok().body(timeSlotService.findSlotById(slotId));
    }

    @DeleteMapping("/deleteSlot/{slotId}")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> deleteSlot(@PathVariable String slotId) {
        timeSlotService.deteleSlotById(slotId);
        return ResponseEntity.ok().body(new MessageResponse("Đã xóa thành công slot!!!"));
    }


}
