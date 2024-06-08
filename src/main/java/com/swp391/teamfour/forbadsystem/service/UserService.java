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
    public User findByEmail(String mail);

    public User findByUserId(Long id);

    public boolean existsByEmail(String email);

    public boolean existsByPhoneNumber(String phoneNumber);

    public UserInfor updateUser(User user);

    public UserInfor getUserInfor(String emailOrPhoneNumber);

    public void deleteUser(User user);

    public boolean resetPassword(Long userId, String newPassword);
}
