package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean existsByEmail(String email);

    UserInfor updateUser(User user);

    UserInfor getUserInfor();

    List<UserInfor> getAllStaff();

    void deleteUser(String userId);

    boolean resetPassword(String userId, String newPassword);
}
