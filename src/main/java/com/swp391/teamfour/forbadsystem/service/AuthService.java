package com.swp391.teamfour.forbadsystem.service;


import com.swp391.teamfour.forbadsystem.dto.request.RoleSelectionRequest;
import com.swp391.teamfour.forbadsystem.dto.request.SigninRequest;
import com.swp391.teamfour.forbadsystem.dto.request.SignupRequest;
import com.swp391.teamfour.forbadsystem.dto.response.JwtResponse;
import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;

import java.util.Map;


public interface AuthService {
    JwtResponse authenticateUser(SigninRequest signinRequest);

    void registerUser(SignupRequest signUpRequest);

    String getGoogleAuthUrl();

    JwtResponse handleGoogleCallBack(Map<String, String> codeJson);

    Map<String, String> buildAccessTokenRequest(String code);

    UserInfor updateRole(RoleSelectionRequest roleSelectionRequest);

}
