package com.swp391.teamfour.forbadsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordResponse {
    private UserInfor userInfor;
    private String newPassword;
}
