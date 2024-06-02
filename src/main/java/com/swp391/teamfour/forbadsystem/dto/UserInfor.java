package com.swp391.teamfour.forbadsystem.dto;

import com.swp391.teamfour.forbadsystem.model.Role;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
}
