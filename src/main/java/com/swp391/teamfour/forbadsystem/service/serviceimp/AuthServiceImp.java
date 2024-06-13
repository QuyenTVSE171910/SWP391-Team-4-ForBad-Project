package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.*;
import com.swp391.teamfour.forbadsystem.jwt.JwtTokenProvider;
import com.swp391.teamfour.forbadsystem.model.Role;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.RoleRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.AuthService;
import com.swp391.teamfour.forbadsystem.service.IdGenerator;
import com.swp391.teamfour.forbadsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Random;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    RoleRepository roleRepository;

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

    @Value("${jwt.app.jwtExpirationsMs}")
    private Long expirationTime;


    @Override
    public UserInfor authenticateUser(SigninRequest signinRequest) {
        try {
            UserInfor userInfor = userService.getUserInfor(signinRequest.getEmailOrPhoneNumber());

            String loginIdentifier = userInfor.getEmail().equals(signinRequest.getEmailOrPhoneNumber())
                    ? userInfor.getEmail()
                    : userInfor.getPhoneNumber();

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginIdentifier, signinRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            return new UserInfor(userDetails.getUserId(), userDetails.getEmail(), userDetails.getPhoneNumber(), userDetails.getFullName(),
                    userDetails.getProfileAvatar(), userDetails.getRole().toString(), userDetails.getManagerId());
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Error: Invalid username or password.");
        }
    }

    @Override
    public JwtResponse getJwtToken(RoleSelectionRequest roleSelectionRequest) {
        try {
            User existingUser = userRepository.findById(roleSelectionRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("Error: User not found."));

            if (roleSelectionRequest.getRole() != null) {
                Role role = roleRepository.findByRoleName(roleSelectionRequest.getRole())
                        .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                existingUser.setRole(role);
            }

            userRepository.save(existingUser);

            CustomUserDetails userDetails = CustomUserDetails.build(existingUser);

            return new JwtResponse(jwtTokenProvider.generateJwtToken(userDetails), expirationTime);
        } catch (RuntimeException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public void registerUser(SignupRequest signUpRequest) {
        try {
            User user = new User();
            user.setUserId(idGenerator.generateCourtId("U"));
            user.setEmail(signUpRequest.getEmail());
            user.setPhoneNumber(signUpRequest.getPhoneNumber());
            user.setPasswordHash(encoder.encode(signUpRequest.getPassword()));
            user.setFullName(signUpRequest.getFullName());

            if (signUpRequest.getProfileAvatar() != null) {
                user.setProfileAvatar(signUpRequest.getProfileAvatar());
            }

            user.setProfileAvatar(signUpRequest.getProfileAvatar());

            if (signUpRequest.getManagerId() != null) {
                User manager = userRepository.findById(signUpRequest.getManagerId())
                        .orElseThrow(() -> new RuntimeException("Error: Manager not found."));
                user.setManager(manager);
            }

            userRepository.save(user);
        } catch (Exception ex) {
            throw new RuntimeException("Error: Registration failed");
        }
    }

    @Override
    public String getGoogleAuthUrl() {
        return "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=" + clientId +
                "&response_type=code" +
                "&scope=email%20profile" +
                "&redirect_uri=" + redirectUri +
                "&state=google-login" +
                "&access_type=offline" +
                "&prompt=consent";
    }

    @Override
    public UserInfor handleGoogleCallBack(String code) {
        try {
            // Gửi yêu cầu lên Google để lấy access token
            Map<String, String> accessTokenResponse = restTemplate.postForObject(accessTokenUrl, buildAccessTokenRequest(code), Map.class);

            // Sử dụng access token để lấy thông tin người dùng từ Google API
            Map<String, String> userInfo = restTemplate.getForObject(userInforUrl + "?access_token=" + accessTokenResponse.get("access_token"), Map.class);

            User user = new User();

            if (userInfo.get("email") != null) {
                if (!userRepository.existsByEmail(userInfo.get("email"))) {
                    user.setEmail(userInfo.get("email"));
                    user.setFullName(userInfo.get("family_name") + " " +  userInfo.get("given_name"));
                    user.setPasswordHash(encoder.encode(String.valueOf(new Random())));
                    user.setProfileAvatar(userInfo.get("picture"));
                    userRepository.save(user);
                } else {
                    user = userRepository.findByEmail(userInfo.get("email"))
                            .orElseThrow(() -> new RuntimeException("Error: User not found."));
                    user.setProfileAvatar(userInfo.get("picture"));
                    userRepository.save(user);
                }
            } else {
                throw new RuntimeException("Error: Email is not provided by Google");
            }

            CustomUserDetails userDetails = CustomUserDetails.build(user);

            return new UserInfor(userDetails.getUserId(), userDetails.getEmail(), userDetails.getPhoneNumber(), userDetails.getFullName(),
                    userDetails.getProfileAvatar(), userDetails.getRole().toString(), userDetails.getManagerId());
        } catch (Exception ex) {
            throw new RuntimeException("Error: Error occured while processing.");
        }
    }

    @Override
    public Map<String, String> buildAccessTokenRequest(String code) {
        // Xây dựng request để lấy access token từ code
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
