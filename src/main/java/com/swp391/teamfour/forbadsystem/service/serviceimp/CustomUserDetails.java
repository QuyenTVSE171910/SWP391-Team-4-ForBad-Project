package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp391.teamfour.forbadsystem.model.Role;
import com.swp391.teamfour.forbadsystem.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {

    private String userId;
    private String email;
    @JsonIgnore
    private String password;
    private String fullName;
    private String profileAvatar;
    private Collection<? extends GrantedAuthority> roles;
    private String managerId;

    public static CustomUserDetails build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().stream().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getRoleName())));
        String managerId = (user.getManager() != null) ? user.getManager().getUserId() : null;

        return new CustomUserDetails(
                user.getUserId(),
                user.getEmail(),
                user.getPasswordHash(),
                user.getFullName(),
                user.getProfileAvatar(),
                authorities,
                managerId);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
