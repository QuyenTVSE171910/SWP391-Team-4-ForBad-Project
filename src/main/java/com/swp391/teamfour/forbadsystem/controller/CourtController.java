package com.swp391.teamfour.forbadsystem.controller;


import com.swp391.teamfour.forbadsystem.dto.CourtRequest;
import com.swp391.teamfour.forbadsystem.dto.MessageResponse;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/court")
public class CourtController {

    @Autowired
    private CourtService courtService;

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(courtService.getAll());
    }

    @GetMapping("/all-of-owner")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> getAllCourtOfOwner(@RequestParam String userId) {
        return ResponseEntity.ok(courtService.getAllOfOwner(userId));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ResponseEntity<?> addCourt(@RequestBody CourtRequest newCourt) {
        courtService.addCourt(newCourt);
        return ResponseEntity.ok().body(new MessageResponse("Thêm cơ sở mới thành công."));
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ResponseEntity<?> deleteCourt(@RequestParam String courtId) {
        courtService.deleteCourt(courtId);
        return ResponseEntity.ok().body(new MessageResponse("Xóa cơ sở thành công."));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin', 'manager')")
    public ResponseEntity<?> updateCourt(@RequestBody CourtRequest courtRequest) {
        return ResponseEntity.ok(courtService.updateCourt(courtRequest));
    }

}
