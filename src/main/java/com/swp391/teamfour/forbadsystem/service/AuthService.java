package com.swp391.teamfour.forbadsystem.service;


import com.swp391.teamfour.forbadsystem.dto.JwtResponse;
import com.swp391.teamfour.forbadsystem.dto.SigninRequest;
import com.swp391.teamfour.forbadsystem.dto.SignupRequest;
import com.swp391.teamfour.forbadsystem.dto.UserInfor;
import com.swp391.teamfour.forbadsystem.model.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Map;


public interface AuthService {
    public UserInfor authenticateUser(SigninRequest signinRequest);

    public void registerUser(SignupRequest signUpRequest);

    public String getGoogleAuthUrl();

    public UserInfor handleGoogleCallBack(String code);

    public Map<String, String> buildAccessTokenRequest(String code);

    public JwtResponse getJwtToken(UserInfor user);




}
