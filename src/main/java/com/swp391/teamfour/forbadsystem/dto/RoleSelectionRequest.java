package com.swp391.teamfour.forbadsystem.dto;

import jakarta.persistence.Access;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleSelectionRequest {
    private String userId;
    private String role;
}
