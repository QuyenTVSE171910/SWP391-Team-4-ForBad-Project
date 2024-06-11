package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<?> findAllSlotByUser(@RequestBody TimeSlotRequest timeSlotRequest){
        List<TimeSlot> timeSlotList = timeSlotService.findAllSlotByUserId(timeSlotRequest.getUserId());
        if (timeSlotList == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tìm thấy tất cả các slot");
        }
        return ResponseEntity.ok(timeSlotList);
    }

    @PostMapping("/createSlot")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> createSlot(@RequestBody TimeSlotRequest timeSlotRequest) {
        TimeSlot createSlot = timeSlotService.createSlot(timeSlotRequest);
        if (createSlot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(" Không Thể Tạo Slot");
        }
        return ResponseEntity.ok(createSlot);
    }

    @PutMapping("/updateSlot")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> updateSlot(@RequestBody TimeSlotRequest timeSlotRequest) {
        return ResponseEntity.ok(timeSlotService.updateSlot(timeSlotRequest));
    }

    @GetMapping("/findSlot")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> getSlotById(@RequestBody Map<String, String> slotIdJSON) {
        String slotId = slotIdJSON.get("slotId");
        TimeSlot timeSlot = timeSlotService.findSlotById(slotId);
        if (timeSlot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Slot không tồn tại");
        }
        return ResponseEntity.ok().body("Tìm Thấy slot " + timeSlot.getSlotId());
    }

    @DeleteMapping("/deleteSlot")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> deleteSlot(@RequestBody Map<String, String> slotIdJSON) {
        String slotId = slotIdJSON.get("slotId");
        TimeSlot timeSlot = timeSlotService.findSlotById(slotId);
        if (timeSlot == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Slot không tồn tại");
        }
        timeSlotService.deteleSlotById(timeSlot.getSlotId());
        return ResponseEntity.ok().body("Đã xóa thành công slot!!!");
    }
}
