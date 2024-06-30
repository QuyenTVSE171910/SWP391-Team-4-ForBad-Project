package com.swp391.teamfour.forbadsystem.dto.response;

import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.service.serviceimp.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class UserInfor {
    private String userId;
    private String email;
    private String fullName;
    private String profileAvatar;
    private Set role;
    private String managerId;

    public static UserInfor build(CustomUserDetails userDetails) {
        return new UserInfor(userDetails.getUserId(), userDetails.getEmail(),
                userDetails.getFullName(), userDetails.getProfileAvatar(), userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority()).collect(Collectors.toSet()), userDetails.getManagerId());
    }

    public static UserInfor build(User user) {
        String managerId = (user.getManager() != null) ? user.getManager().getUserId() : null;
        return new UserInfor(user.getUserId(), user.getEmail(),
                user.getFullName(), user.getProfileAvatar(), user.getRoles()
                .stream().collect(Collectors.toSet()), managerId);
    }
}
