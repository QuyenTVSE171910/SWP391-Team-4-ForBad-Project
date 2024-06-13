package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.MessageResponse;
import com.swp391.teamfour.forbadsystem.dto.StaffInfo;
import com.swp391.teamfour.forbadsystem.model.Staff;
import com.swp391.teamfour.forbadsystem.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/all/{manager_id}")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> getAll(@PathVariable("manager_id") String mangerId) {
        List<StaffInfo> staffs = staffService.getAllStaff(mangerId);
        return ResponseEntity.ok(staffs);
    }

    @PostMapping("/add-staff")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> addStaff(@RequestBody StaffInfo newstaff) {
        staffService.addStaff(newstaff);
        return ResponseEntity.ok().body(new MessageResponse("Thêm Staff mới thành công."));
    }

    @DeleteMapping("/delete-staff")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> deleteStaff(@RequestParam String userId) {
        staffService.deleteStaff(userId);
        return ResponseEntity.ok().body(new MessageResponse("Xóa Staff thành công."));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> updateCourt(@RequestBody StaffInfo staffInfo) {
        Staff staff = staffService.updateStaff(staffInfo);
        return ResponseEntity.ok(staff);
    }

}
