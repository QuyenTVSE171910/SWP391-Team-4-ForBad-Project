package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.*;
import com.swp391.teamfour.forbadsystem.jwt.JwtTokenProvider;
import com.swp391.teamfour.forbadsystem.model.Role;
import com.swp391.teamfour.forbadsystem.model.RoleEnum;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.RoleRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.CustomUserDetails;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/forbad/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    RestTemplate restTemplate;

    @Value("${jwt.app.jwtExpirationsMs}")
    private Long expirationTime;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.google.token-uri}")
    private String accessTokenUrl;

    @Value("${spring.security.oauth2.client.provider.google.user-info-uri}")
    private String userInforUrl;

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

            Cookie jwtCookie = new Cookie("token", jwt);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setSecure(true); // Ensure cookie is only sent over HTTPS
            jwtCookie.setPath("/");

            // Set cookie in response
            HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            response.addCookie(jwtCookie);

            return ResponseEntity.ok().build();
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

        if (signUpRequest != null) {
            user.setProfileAvatar(signUpRequest.getProfileAvatar());
        }

        if (signUpRequest.getManagerId() != null) {
            User manager = userRepository.findById(signUpRequest.getManagerId())
                    .orElseThrow(() -> new RuntimeException("Error: Manager not found."));
            user.setManager(manager);
        }

        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @GetMapping("/google")
    public ResponseEntity<?> googleLogin() {
         return ResponseEntity.ok(new GoogleAuthResponse(getGoogleAuthUrl()));
    }

    private String getGoogleAuthUrl() {
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&scope=email%20profile" +
                "&redirect_uri=" + redirectUri +
                "&state=google-login" +
                "&access_type=offline" +
                "&prompt=consent";
        return googleAuthUrl;
    }

    @GetMapping("/google/callback")
    public void googleCallback(@RequestParam("code") String code) throws IOException {

        // Gửi yêu cầu lên Google để lấy access token
        Map<String, String> accessTokenResponse = restTemplate.postForObject(accessTokenUrl, buildAccessTokenRequest(code), Map.class);

        // Sử dụng access token để lấy thông tin người dùng từ Google API
        Map<String, String> userInfo = restTemplate.getForObject(userInforUrl + "?access_token=" + accessTokenResponse.get("access_token"), Map.class);

        User user = new User();

       if (!userRepository.existsByEmail(userInfo.get("email"))) {
           user.setEmail(userInfo.get("email"));
           user.setFullName(userInfo.get("family_name") + " " +  userInfo.get("given_name"));
           user.setPasswordHash(encoder.encode(String.valueOf(new Random())));
           user.setProfileAvatar(userInfo.get("picture"));
           userRepository.save(user);
       } else {
           user = userRepository.findByEmail(userInfo.get("email"))
                   .orElseThrow(() -> new RuntimeException("Error: User not found."));
       }

       CustomUserDetails userDetails = CustomUserDetails.build(user);

       String jwt = jwtTokenProvider.generateJwtToken(userDetails);


        Cookie jwtCookie = new Cookie("token", jwt);
        jwtCookie.setHttpOnly(true);
        jwtCookie.setSecure(true); // Ensure cookie is only sent over HTTPS
        jwtCookie.setPath("/");

        // Set cookie in response
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        response.addCookie(jwtCookie);

        response.sendRedirect("http://localhost:3002/home");
    }

    private Map<String, String> buildAccessTokenRequest(String code) {
        // Xây dựng request để lấy access token từ code
        // Đây là một ví dụ đơn giản, bạn cần tuân thủ đúng cú pháp của Google
        Map<String, String> requestBody = Map.of(
                "code", code,
                "client_id", clientId,
                "client_secret", clientSecret,
                "redirect_uri", redirectUri,
                "grant_type", "authorization_code"
        );
        return requestBody;
    }
}
