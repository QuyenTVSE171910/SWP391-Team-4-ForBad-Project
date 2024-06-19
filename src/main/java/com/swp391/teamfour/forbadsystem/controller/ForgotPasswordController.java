package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.dto.request.ResetPasswordRequest;
import com.swp391.teamfour.forbadsystem.service.ForgotPasswordService;
import com.swp391.teamfour.forbadsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/email-verify")
    public ResponseEntity<?> processForgotPassword(@RequestBody Map<String, String> emailJson) {
        String email = emailJson.get("email");
        forgotPasswordService.processForgotPassword(email);
        return ResponseEntity.ok().body(new MessageResponse("Mã xác thực đã được gửi về email."));
    }


    @PostMapping("/verify-token")
    public ResponseEntity<?> validTokenForUser(@RequestParam("token") String token) {
        Map<String, String> userId = forgotPasswordService.validPasswordResetToken(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token không hợp lệ hoặc đã hết hạn."));
        }
        return ResponseEntity.ok(userId);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        if (!userService.resetPassword(resetPasswordRequest.getUserId(), encoder.encode(resetPasswordRequest.getNewPassword()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Đổi mật khẩu không thành công."));
        }
        return ResponseEntity.ok().body(new MessageResponse("Đổi mật khẩu thành công ! Vui lòng đăng nhập lại."));
    }
}