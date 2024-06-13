package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.MessageResponse;
import com.swp391.teamfour.forbadsystem.dto.ResetPasswordResponse;
import com.swp391.teamfour.forbadsystem.dto.UserInfor;
import com.swp391.teamfour.forbadsystem.model.PasswordResetToken;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.service.ForgotPasswordService;
import com.swp391.teamfour.forbadsystem.service.UserService;
import com.swp391.teamfour.forbadsystem.service.serviceimp.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("forbad/forgot-password")
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping("/email-verify")
    public ResponseEntity<?> processForgotPassword(@RequestBody Map<String, String> emailJson) {
        Map<String, String> email = emailJson;
        String mail = email.get("email");
        User user = userService.findByEmail(mail);
        if (user != null) {
            PasswordResetToken token = forgotPasswordService.createTokenResetPassword(user);
            return ResponseEntity.ok().body(new MessageResponse("Mã xác thực đã được gửi về email."));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Có lỗi xảy ra. Vui lòng thử lại"));
    }

    @PostMapping("/verify-token")
    public ResponseEntity<?> validTokenForUser(@RequestParam("token") String token) {
        PasswordResetToken passwordResetToken = forgotPasswordService.validPasswordResetToken(token);
        if (passwordResetToken == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("Token không hợp lệ hoặc đã hết hạn."));
        }
        CustomUserDetails userDetails = CustomUserDetails.build(passwordResetToken.getUser());
        UserInfor userInfor = UserInfor.build(userDetails);

        return ResponseEntity.ok(userInfor);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> processResetPassword(@RequestBody ResetPasswordResponse resetPasswordResponse) {
        if (!userService.resetPassword(resetPasswordResponse.getUserInfor().getUserId(), encoder.encode(resetPasswordResponse.getNewPassword()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Đổi mật khẩu không thành công."));
        }
        return ResponseEntity.ok().body(new MessageResponse("Đổi mật khẩu thành công ! Vui lòng đăng nhập lại."));
    }
}