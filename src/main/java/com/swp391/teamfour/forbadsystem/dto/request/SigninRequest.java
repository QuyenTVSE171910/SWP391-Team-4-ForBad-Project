package com.swp391.teamfour.forbadsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SigninRequest {

    @NotBlank(message = "Must not be empty.")
    private String email;

    @NotBlank(message = "Password length must be from 8 to 120.")
    @Size(min = 8, max = 120)
    private String password;
}
