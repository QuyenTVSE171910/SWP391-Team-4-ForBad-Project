package com.swp391.teamfour.forbadsystem.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp391.teamfour.forbadsystem.model.Role;
import com.swp391.teamfour.forbadsystem.model.RoleEnum;
import com.swp391.teamfour.forbadsystem.model.User;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private Long userId;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String password;
    private String fullName;
    private String profileAvatar;
    private GrantedAuthority role;
    private Long managerId;

    public static CustomUserDetails build(User user) {
        GrantedAuthority authority = (user.getRole() != null) ? new SimpleGrantedAuthority(user.getRole().getRoleName()) : new SimpleGrantedAuthority(RoleEnum.ROLE_TEMP.getRole());
        Long managerId = (user.getManager() != null) ? user.getManager().getUserId() : null;

        return new CustomUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getPasswordHash(),
                user.getFullName(),
                user.getProfileAvatar(),
                authority,
                managerId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
