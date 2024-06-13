package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.swp391.teamfour.forbadsystem.model.Staff;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
public class CustomStaffDetails implements UserDetails {

    private String userId;
    private String email;
    private String phoneNumber;
    @JsonIgnore
    private String password;
    private String fullName;
    private String profileAvatar;
    private GrantedAuthority role;
    private String managerId;

    public static CustomStaffDetails build(Staff staff) {
        GrantedAuthority authority = (staff.getRole() != null) ? new SimpleGrantedAuthority(staff.getRole().getRoleName()) : new SimpleGrantedAuthority("temp");
        String managerId = (staff.getManager() != null) ? staff.getManager().getUserId() : null;

        return new CustomStaffDetails(
                staff.getUserId(),
                staff.getEmail(),
                staff.getPhoneNumber(),
                staff.getPasswordHash(),
                staff.getFullName(),
                staff.getProfileAvatar(),
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

