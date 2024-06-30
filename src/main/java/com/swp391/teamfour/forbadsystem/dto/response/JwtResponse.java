package com.swp391.teamfour.forbadsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String userId;
    private String fullName;
    private String imageUrl;
    private String email;
    private Set roles;
    private String accessToken;
    private Long expirationTime;
}
