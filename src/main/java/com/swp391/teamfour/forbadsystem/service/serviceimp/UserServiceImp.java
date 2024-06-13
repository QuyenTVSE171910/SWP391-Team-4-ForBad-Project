package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.UserInfor;
import com.swp391.teamfour.forbadsystem.model.PasswordResetToken;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.PasswordResetTokenRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.EmailService;
import com.swp391.teamfour.forbadsystem.service.UserService;
import com.swp391.teamfour.forbadsystem.service.serviceimp.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByEmail(String mail) {
        User user = userRepository.findByEmail(mail)
                .orElseThrow(() -> new BadCredentialsException("Người dùng không tồn tại trong hệ thống."));
        return user;
    }

    @Override
    public User findByUserId(Long id) {
        return null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public UserInfor updateUser(User user) {
        User existingUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("Error: User not found."));

        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setFullName(user.getFullName());
        existingUser.setProfileAvatar(user.getProfileAvatar());
        existingUser.setRole(user.getRole());

        userRepository.save(existingUser);
        return new UserInfor(existingUser.getUserId(), existingUser.getEmail(), existingUser.getPhoneNumber(), existingUser.getFullName(), existingUser.getProfileAvatar(),
                existingUser.getRole().toString(), existingUser.getManager().getUserId());
    }

    @Override
    public void deleteUser(User user) {
        userRepository.deleteById(user.getUserId());
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) throws UsernameNotFoundException {
            User user = userRepository.findByEmail(emailOrPhoneNumber)
                    .orElseGet(() -> userRepository.findByPhoneNumber(emailOrPhoneNumber)
                            .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email or phone number: " + emailOrPhoneNumber)));
            return CustomUserDetails.build(user);
    }

    @Override
    public UserInfor getUserInfor(String emailOrPhoneNumber) {
        User user = userRepository.findByEmail(emailOrPhoneNumber)
                .orElseGet(() -> userRepository.findByPhoneNumber(emailOrPhoneNumber)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email or phone number: " + emailOrPhoneNumber)));
        CustomUserDetails userDetails = CustomUserDetails.build(user);

        return UserInfor.build(userDetails);
    }

    @Override
    public boolean resetPassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        if (user != null) {
            user.setPasswordHash(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
