package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.RoleSelectionRequest;
import com.swp391.teamfour.forbadsystem.dto.request.SigninRequest;
import com.swp391.teamfour.forbadsystem.dto.request.SignupRequest;
import com.swp391.teamfour.forbadsystem.dto.response.JwtResponse;
import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.jwt.JwtTokenProvider;
import com.swp391.teamfour.forbadsystem.model.Role;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.RoleRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.AuthService;
import com.swp391.teamfour.forbadsystem.utils.IdGenerator;
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

import java.util.*;
import java.util.stream.Collectors;

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
    public JwtResponse authenticateUser(SigninRequest signinRequest) {
        try {
            if (!userRepository.existsByEmail(signinRequest.getEmail()))
                throw new BadCredentialsException("Email not exist !");

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

            Set roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());

            String jwtToken = jwtTokenProvider.generateJwtToken(userDetails);

            return new JwtResponse(userDetails.getUserId(), userDetails.getFullName(), userDetails.getProfileAvatar(), userDetails.getEmail(),
                    roles, jwtToken, expirationTime);
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Error: Invalid username or password.");
        }
    }

    @Override
    public void registerUser(SignupRequest signUpRequest) {
        try {

            User user = new User();
            user.setUserId(idGenerator.generateCourtId("U"));
            user.setEmail(signUpRequest.getEmail());
            user.setPasswordHash(encoder.encode(signUpRequest.getPassword()));
            user.setFullName(signUpRequest.getFullName());

            user.setProfileAvatar("https://firebasestorage.googleapis.com/v0/b/forbad-43f1e.appspot.com/o/" +
                    "anonymous_person.jpg?alt=media&token=a5bb8c3c-bfc3-4fd4-912e-30b6fbd46391");

            List<Role> roles = new ArrayList<>();
            Role role;
            if (signUpRequest.getRole() != null) {
                role = roleRepository.findByRoleName(signUpRequest.getRole())
                        .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            } else {
                role = roleRepository.findByRoleName("customer")
                        .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            }

            roles.add(role);
            user.setRoles(roles);

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
    public JwtResponse handleGoogleCallBack(Map<String, String> codeJson) {
        try {
            String code = codeJson.get("code");
            // Gửi yêu cầu lên Google để lấy access token
            Map<String, String> accessTokenResponse = restTemplate.postForObject(accessTokenUrl, buildAccessTokenRequest(code), Map.class);

            // Sử dụng access token để lấy thông tin người dùng từ Google API
            Map<String, String> userInfo = restTemplate.getForObject(userInforUrl + "?access_token=" + accessTokenResponse.get("access_token"), Map.class);

            User user = new User();

            if (userInfo.get("email") != null) {
                if (!userRepository.existsByEmail(userInfo.get("email"))) {
                    user.setUserId(idGenerator.generateCourtId("U"));
                    user.setEmail(userInfo.get("email"));
                    user.setFullName(userInfo.get("family_name") + " " + userInfo.get("given_name"));
                    user.setPasswordHash(encoder.encode(String.valueOf(new Random())));

                    List<Role> roles = new ArrayList<>();
                    Role role = roleRepository.findByRoleName("customer")
                            .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    roles.add(role);
                    user.setRoles(roles);

                } else {
                    user = userRepository.findByEmail(userInfo.get("email"))
                            .orElseThrow(() -> new RuntimeException("Error: User not found."));
                }
                user.setProfileAvatar(userInfo.get("picture"));
                userRepository.save(user);
            } else {
                throw new RuntimeException("Error: Email is not provided by Google");
            }

            CustomUserDetails userDetails = CustomUserDetails.build(user);

            Set roles = userDetails.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());

            String jwtToken = jwtTokenProvider.generateJwtToken(userDetails);

            return new JwtResponse(userDetails.getUserId(), userDetails.getFullName(), userDetails.getProfileAvatar(),
                    userDetails.getEmail(), roles, jwtToken, expirationTime);
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

    @Override
    public UserInfor updateRole(RoleSelectionRequest roleSelectionRequest) {
        try {
            User user = userRepository.findById(roleSelectionRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại trong hệ thống."));

            Role role = roleRepository.findByRoleName(roleSelectionRequest.getRole())
                    .orElseThrow(() -> new RuntimeException("Role không tồn tại."));

            user.getRoles().add(role);
            userRepository.save(user);
            return UserInfor.build(CustomUserDetails.build(user));
        } catch (Exception ex) {
            throw ex;
        }
    }
}
