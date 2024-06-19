package com.swp391.teamfour.forbadsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GoogleTokenResponse {

    private String accessToken;
    private String idToken;
}
