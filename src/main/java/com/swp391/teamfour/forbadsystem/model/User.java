package com.swp391.teamfour.forbadsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account", uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
                                           @UniqueConstraint(columnNames = "phone_number")})
public class User {

    @Id()
    @Column(name = "user_id")
    private String userId;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 13, name = "phone_number")
    private String phoneNumber;

    @Column(length = 120, name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(length = 100, name = "full_name", nullable = false)
    private String fullName;

    @Column(length = 255, name = "profile_avatar")
    private String profileAvatar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "user_id")
    private User manager;


}
