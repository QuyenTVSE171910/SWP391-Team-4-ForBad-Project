package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.service.YardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/yard-schedule")
public class YardScheduleController {

    @Autowired
    private YardScheduleService yardScheduleService;

    @PostMapping("/{yardId}/timeSlot/{timeSlotId}")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> addTimeSlotToYard(@PathVariable String yardId, @PathVariable String timeSlotId) {
        yardScheduleService.addTimeSlotToYard(yardId, timeSlotId);
        return ResponseEntity.ok().body(new MessageResponse("Đã thêm slot " + timeSlotId + " vào sân " + yardId + " thành công."));
    }

    @GetMapping("/getAllByYardId/{yardId}")
    public ResponseEntity<?> getAllByYardId(@PathVariable String yardId) {
        return ResponseEntity.ok(yardScheduleService.getAllSlotByYardId(yardId));
    }

    @DeleteMapping("{yardId}/deleteSlotFromYard/{timeSlotId}")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> deleteSlotFromYard(@PathVariable String yardId, @PathVariable String timeSlotId) {
        yardScheduleService.deleteSlotFromYard(yardId, timeSlotId);
        return ResponseEntity.ok(new MessageResponse("Đã xóa time slot ra khỏi danh sách time slot của sân."));
    }
}
