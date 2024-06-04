package com.swp391.teamfour.forbadsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "userAccount", uniqueConstraints = {@UniqueConstraint(columnNames = "email"),
                                           @UniqueConstraint(columnNames = "phoneNumber")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 13)
    private String phoneNumber;

    @Column(length = 120, nullable = false)
    private String passwordHash;

    @Column(length = 100, nullable = false)
    private String fullName;

    @Column(length = 255)
    private String profileAvatar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "managerId", referencedColumnName = "userId")
    private User manager;


}
