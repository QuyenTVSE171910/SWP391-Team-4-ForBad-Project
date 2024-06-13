package com.swp391.teamfour.forbadsystem.dto;

import com.swp391.teamfour.forbadsystem.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private Long expirationTime;
}
