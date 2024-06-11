package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.model.PasswordResetToken;
import com.swp391.teamfour.forbadsystem.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface ForgotPasswordService {
    PasswordResetToken processForgotPassword(String email);

    Map<String, String> validPasswordResetToken(String token);
}
