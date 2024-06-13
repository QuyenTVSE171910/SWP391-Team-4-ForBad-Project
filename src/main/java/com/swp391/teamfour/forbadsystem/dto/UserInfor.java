package com.swp391.teamfour.forbadsystem.dto;

import com.swp391.teamfour.forbadsystem.service.serviceimp.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserInfor {
    private Long userId;
    private String email;
    private String phoneNumber;
    private String fullName;
    private String profileAvatar;
    private String role;
    private Long managerId;

    public static UserInfor build(CustomUserDetails userDetails) {
        return new UserInfor(userDetails.getUserId(), userDetails.getEmail(), userDetails.getPhoneNumber(),
                userDetails.getFullName(), userDetails.getProfileAvatar(), userDetails.getRole().toString(), userDetails.getManagerId());
    }
}
