package com.swp391.teamfour.forbadsystem.dto.request;

import com.swp391.teamfour.forbadsystem.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "Email must not be empty.")
    @Size(max = 100)
    @Email
    private String email;

    @NotBlank(message = "Password length must be from 8 to 120.")
    @Size(min = 8, max = 120)
    private String password;

    @NotBlank(message = "Fullname must not be empty.")
    @Size(max = 100)
    private String fullName;

    private String role;

    private String managerId;
}
