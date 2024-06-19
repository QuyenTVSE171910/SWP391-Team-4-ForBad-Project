package com.swp391.teamfour.forbadsystem.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class YardRequest {

    private String yardId;

    @NotBlank(message = "Name must not be empty.")
    private String yardName;

    @NotBlank(message = "CourtId must not be empty.")
    private String courtId;
}
