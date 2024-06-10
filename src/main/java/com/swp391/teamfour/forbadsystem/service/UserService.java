package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.UserInfor;
import com.swp391.teamfour.forbadsystem.model.PasswordResetToken;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

public interface UserService extends UserDetailsService {
    User findByEmail(String mail);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    UserInfor updateUser(User user);

    UserInfor getUserInfor(String emailOrPhoneNumber);

    void deleteUser(User user);

    boolean resetPassword(String userId, String newPassword);
}
