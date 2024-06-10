package com.swp391.teamfour.forbadsystem.service;


import com.swp391.teamfour.forbadsystem.dto.*;
import com.swp391.teamfour.forbadsystem.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface AuthService {
    UserInfor authenticateUser(SigninRequest signinRequest);

    void registerUser(SignupRequest signUpRequest);

    String getGoogleAuthUrl();

    UserInfor handleGoogleCallBack(String code);

    Map<String, String> buildAccessTokenRequest(String code);

    JwtResponse getJwtToken(RoleSelectionRequest roleSelectionRequest);


}
