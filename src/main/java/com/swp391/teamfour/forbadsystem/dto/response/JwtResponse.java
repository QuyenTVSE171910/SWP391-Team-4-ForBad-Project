package com.swp391.teamfour.forbadsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String userId;
    private String fullName;
    private String imageUrl;
    private String role;
    private String accessToken;
    private Long expirationTime;
}
