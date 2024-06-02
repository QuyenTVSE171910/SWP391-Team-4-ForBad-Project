package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.UserInfor;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("forbad/member")
public class UserController {

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('manager') or hasAuthority('customer') or hasAuthority('staff')")
    public ResponseEntity<UserInfor> getUserInfor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserInfor userInfor = new UserInfor(userDetails.getUserId(), userDetails.getEmail(), userDetails.getPhoneNumber(),
                                            userDetails.getFullName(), userDetails.getProfileAvatar(),
                                            userDetails.getAuthorities().stream().collect(Collectors.toList()).get(0).toString() , userDetails.getManagerId());
        return ResponseEntity.ok(userInfor);
    }
}
