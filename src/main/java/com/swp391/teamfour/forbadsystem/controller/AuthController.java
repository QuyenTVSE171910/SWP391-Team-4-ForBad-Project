package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.JwtResponse;
import com.swp391.teamfour.forbadsystem.dto.MessageResponse;
import com.swp391.teamfour.forbadsystem.dto.SigninRequest;
import com.swp391.teamfour.forbadsystem.dto.SignupRequest;
import com.swp391.teamfour.forbadsystem.jwt.JwtTokenProvider;
import com.swp391.teamfour.forbadsystem.model.Role;
import com.swp391.teamfour.forbadsystem.model.RoleEnum;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.RoleRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.CustomUserDetails;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/forbad/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.app.jwtExpirationsMs}")
    Long expirationTime;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }
        User user = userRepository.findByEmail(signinRequest.getEmailOrPhoneNumber())
                .orElseGet(() -> userRepository.findByPhoneNumber(signinRequest.getEmailOrPhoneNumber())
                        .orElseThrow(() -> new RuntimeException("Error: User not found.")));

        String loginIdentifier = user.getEmail().equals(signinRequest.getEmailOrPhoneNumber())
                ? user.getEmail()
                : user.getPhoneNumber();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginIdentifier, signinRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String jwt = jwtTokenProvider.generateJwtToken(userDetails);
            return ResponseEntity.ok(new JwtResponse(jwt, expirationTime));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Error: Invalid username or password.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult
                    .getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());

            return ResponseEntity.badRequest().body(errors);
        }
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Phone number is already in use!"));
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setPasswordHash(encoder.encode(signUpRequest.getPassword()));
        user.setFullName(signUpRequest.getFullName());

        if (signUpRequest.getProfileAvatar() != null) {
            user.setProfileAvatar(signUpRequest.getProfileAvatar());
        }

        List<String> userRole = signUpRequest.getRoles();

        // Nếu không có role thì mặc định là khách hàng
        if (userRole == null) {
            Role role = roleRepository.findByRoleName(RoleEnum.ROLE_CUSTOMER.getRole())
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            user.setRole(role);
        } else {
            userRole.forEach(role -> {
                        switch (role) {
                            case "admin":
                                Role adminRole = roleRepository.findByRoleName(RoleEnum.ROLE_ADMIN.getRole())
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                user.setRole(adminRole);
                                break;
                            case "manager":
                                Role managerRole = roleRepository.findByRoleName(RoleEnum.ROLE_MANAGER.getRole())
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                user.setRole(managerRole);
                                break;
                            case "customer":
                                Role customerRole = roleRepository.findByRoleName(RoleEnum.ROLE_CUSTOMER.getRole())
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                user.setRole(customerRole);
                                break;
                            default:
                                Role staffRole = roleRepository.findByRoleName(RoleEnum.ROLE_STAFF.getRole())
                                        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                                user.setRole(staffRole);
                        }
                    }
            );
        }

        if (signUpRequest.getManagerId() != null) {
            User manager = userRepository.findById(signUpRequest.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Error: Manager not found."));
            user.setManager(manager);
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
