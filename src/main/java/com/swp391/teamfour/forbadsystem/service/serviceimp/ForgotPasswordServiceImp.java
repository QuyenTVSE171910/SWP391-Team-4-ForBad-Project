package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.model.PasswordResetToken;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.PasswordResetTokenRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.EmailService;
import com.swp391.teamfour.forbadsystem.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class ForgotPasswordServiceImp implements ForgotPasswordService {


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public PasswordResetToken processForgotPassword(String email) {
        try {
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại."));

            Random random = new Random();
            String token = String.valueOf(100000 + random.nextInt(900000));
            PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(24));
            passwordResetTokenRepository.save(passwordResetToken);
            emailService.sendPasswordResetMail(user.getEmail(), user.getFullName(), token);
            return passwordResetToken;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Map<String, String> validPasswordResetToken(String token) {
        try {
            PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
            if (passwordResetToken == null || passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
                return null;
            }
            Map<String, String> userId = new HashMap<>();
            userId.put("userId", passwordResetToken.getUser().getUserId());
            return userId;
        } catch (Exception ex) {
            throw ex;
        }
    }
}
