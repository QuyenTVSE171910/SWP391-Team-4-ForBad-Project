package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyAuthority('admin', 'manager', 'customer', 'staff')")
    public ResponseEntity<?> getUserInfor() {
        UserInfor userInfor = userService.getUserInfor();
        return ResponseEntity.ok(userInfor);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> deleteUser(@RequestParam String userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body(new MessageResponse("Đã xóa người dùng thành công."));
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('admin', 'manager', 'customer', 'staff')")
    public ResponseEntity<?> upadateUser(@RequestBody User user) {
        UserInfor userInfor = userService.updateUser(user);
        return ResponseEntity.ok(userInfor);
    }

    @GetMapping("/all-staff")
    @PreAuthorize("hasAuthority('manager')")
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok().body(userService.getAllStaff());
    }
}
