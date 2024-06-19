package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.request.RoleSelectionRequest;
import com.swp391.teamfour.forbadsystem.dto.request.SigninRequest;
import com.swp391.teamfour.forbadsystem.dto.request.SignupRequest;
import com.swp391.teamfour.forbadsystem.dto.response.GoogleAuthResponse;
import com.swp391.teamfour.forbadsystem.dto.response.JwtResponse;
import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.exception.AuthenticationExceptionHandler;
import com.swp391.teamfour.forbadsystem.service.AuthService;
import com.swp391.teamfour.forbadsystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AuthenticationExceptionHandler.getValidationErrors(bindingResult));
        }
        return ResponseEntity.ok(authService.authenticateUser(signinRequest));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(AuthenticationExceptionHandler.getValidationErrors(bindingResult));
        }

        if (userService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        authService.registerUser(signUpRequest);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleLogin() {
        return ResponseEntity.ok(new GoogleAuthResponse(authService.getGoogleAuthUrl()));
    }

    @PostMapping("/google/callback")
    public ResponseEntity<?> googleCallback(@RequestBody Map<String, String> codeJson) {

        JwtResponse jwtResponse = authService.handleGoogleCallBack(codeJson);

        return ResponseEntity.ok().body(jwtResponse);
    }

    @PutMapping("/update-role")
    public ResponseEntity<?> updateRole(@RequestBody RoleSelectionRequest roleSelectionRequest) {
        return ResponseEntity.ok().body(authService.updateRole(roleSelectionRequest));
    }
}
