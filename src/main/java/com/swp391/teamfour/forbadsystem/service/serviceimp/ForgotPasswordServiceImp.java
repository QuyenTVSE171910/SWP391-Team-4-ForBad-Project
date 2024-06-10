package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.model.PasswordResetToken;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.PasswordResetTokenRepository;
import com.swp391.teamfour.forbadsystem.service.EmailService;
import com.swp391.teamfour.forbadsystem.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class ForgotPasswordServiceImp implements ForgotPasswordService {


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public PasswordResetToken createTokenResetPassword(User user) {
        Random random = new Random();
        String token = String.valueOf(100000 + random.nextInt(900000));
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(24));
        passwordResetTokenRepository.save(passwordResetToken);
        emailService.sendPasswordResetMail(user.getEmail(), user.getFullName(), token);
        return passwordResetToken;
    }

    @Override
    public String validPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return null;
        }
        return passwordResetToken.getUser().getUserId();
    }
}
