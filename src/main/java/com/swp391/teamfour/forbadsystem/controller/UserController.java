package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.UserInfor;
import com.swp391.teamfour.forbadsystem.service.serviceimp.CustomUserDetails;
import com.swp391.teamfour.forbadsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager') or hasAuthority('customer') or hasAuthority('staff')")
    public ResponseEntity<UserInfor> getUserInfor() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserInfor userInfor = new UserInfor(userDetails.getUserId(), userDetails.getEmail(), userDetails.getPhoneNumber(),
                    userDetails.getFullName(), userDetails.getProfileAvatar(),
                    userDetails.getRole().toString() , userDetails.getManagerId());
            return ResponseEntity.ok(userInfor);
        } catch (Exception ex) {
            throw new RuntimeException("Error internal server.");
        }
    }



}
