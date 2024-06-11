package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.YardRequest;
import com.swp391.teamfour.forbadsystem.model.Yard;
import com.swp391.teamfour.forbadsystem.service.YardService;
import com.swp391.teamfour.forbadsystem.service.serviceimp.YardServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/yard")
public class YardController {

    private final YardService yardService;

    @Autowired
    public YardController(YardService yardService) {
        this.yardService = yardService;
    }

    @GetMapping("/findAllYard")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> findAllYardByCourt(@RequestBody YardRequest yardRequest) {
        List<Yard> timeSlotList = yardService.getAllYardByCourtId(yardRequest.getCourtId());
        if (timeSlotList == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tìm thấy tất cả các slot");
        }
        return ResponseEntity.ok(timeSlotList);
    }

    @PostMapping("/createyard")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> createYard(@RequestBody YardRequest yardRequest) {
        Yard yardCreate = yardService.createYard(yardRequest);
        if (yardCreate == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tạo sân!!!");
        }
        return ResponseEntity.ok(yardCreate);
    }

    @PutMapping("/updateyard")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> updateYard(@RequestBody YardRequest yardRequest) {
        return ResponseEntity.ok(yardService.updateYard(yardRequest));
    }

    @GetMapping("/findyard")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> findYardById(@RequestBody Map<String, String> yardIdJSON) {
        String yardId = yardIdJSON.get("yardId");
        Yard yard = yardService.findYardById(yardId);
        if (yard == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tìm thấy sân " + yardId);
        }
        return ResponseEntity.ok(yard);
    }

    @DeleteMapping("/deleteyard")
    @PreAuthorize("hasAnyAuthority('manager')")
    public ResponseEntity<?> deleteYard(@RequestBody Map<String, String> yardIdJSON) {
        String yardId = yardIdJSON.get("yardId");
        Yard yard = yardService.findYardById(yardId);
        if (yard == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể tìm thấy sân " + yardId);
        }
        yardService.deleteYardById(yard.getYardId());
        return ResponseEntity.ok().body("Đã xóa thành công sân");
    }
}
