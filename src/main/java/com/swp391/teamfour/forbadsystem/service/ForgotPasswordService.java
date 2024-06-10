package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.model.PasswordResetToken;
import com.swp391.teamfour.forbadsystem.model.User;
import org.springframework.stereotype.Service;

public interface ForgotPasswordService {
    PasswordResetToken createTokenResetPassword(User user);

    String validPasswordResetToken(String token);
}
