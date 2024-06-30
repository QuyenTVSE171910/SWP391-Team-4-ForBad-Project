package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.getReferenceById(userDetails.getUserId());
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserInfor updateUser(User user) {
        try {
            User existingUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new RuntimeException("Error: User not found."));

            existingUser.setEmail(user.getEmail());
            existingUser.setFullName(user.getFullName());
            existingUser.setProfileAvatar(user.getProfileAvatar());
            existingUser.setRoles(user.getRoles());

            userRepository.save(existingUser);
            return new UserInfor(existingUser.getUserId(), existingUser.getEmail(), existingUser.getFullName(), existingUser.getProfileAvatar(),
                    existingUser.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()), existingUser.getManager().getUserId());
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteUser(String userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
        } else throw new RuntimeException("Người dùng không tồn tại.");
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(emailOrPhoneNumber)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email or phone number: " + emailOrPhoneNumber));
        return CustomUserDetails.build(user);
    }

    @Override
    public UserInfor getUserInfor() {
        try {
            User currentUser = getCurrentUser();
            String managerId =  (currentUser.getManager() != null ? currentUser.getManager().getUserId() : null);

            UserInfor userInfor = new UserInfor(currentUser.getUserId(), currentUser.getEmail(),
                    currentUser.getFullName(), currentUser.getProfileAvatar(),
                    currentUser.getRoles().stream().map(r -> r.getRoleName()).collect(Collectors.toSet()), managerId);
            return userInfor;
        } catch (Exception ex) {
            throw new RuntimeException("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    }

    @Override
    public boolean resetPassword(String userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));
        if (user != null) {
            user.setPasswordHash(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<UserInfor> getAllStaff() {
        try {
            User owner = getCurrentUser();
            List<User> staffs = userRepository.getAllByManager(owner);

            if (!staffs.isEmpty()) {
                return staffs.stream()
                            .map(staff -> UserInfor.build(CustomUserDetails.build(staff))).collect(Collectors.toList());
            } else {
                throw new RuntimeException("Danh sách nhân viên trống.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
