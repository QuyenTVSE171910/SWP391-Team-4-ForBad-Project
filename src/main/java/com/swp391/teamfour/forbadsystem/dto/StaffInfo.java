package com.swp391.teamfour.forbadsystem.dto;

import com.swp391.teamfour.forbadsystem.model.Role;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.service.serviceimp.CustomStaffDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class StaffInfo {
    private String userId;
    private String email;
    private String phoneNumber;
    private String passwordHash;
    private String fullName;
    private String profileAvatar;
    private Role role;
    private User manager;


    public StaffInfo(String userId,
                     String fullName,
                     String email,
                     String phoneNumber,
                     String profileAvatar,
                     Role role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.profileAvatar = profileAvatar;
        this.role = role;
    }

    public StaffInfo(String userId, String email, String phoneNumber, String fullName, String profileAvatar, String string, String managerId) {
    }


    public static StaffInfo build(CustomStaffDetails staffDetails) {
        return new StaffInfo(staffDetails.getUserId(),
                staffDetails.getEmail(),
                staffDetails.getPhoneNumber(),
                staffDetails.getFullName(),
                staffDetails.getProfileAvatar(),
                staffDetails.getRole().toString(),
                staffDetails.getManagerId());
    }
}

