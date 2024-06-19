package com.swp391.teamfour.forbadsystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp391.teamfour.forbadsystem.model.Court;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtRequest {
    private String courtId;

    @NotBlank(message = "Must not be empty.")
    private String courtName;

    @NotBlank(message = "Must not be empty.")
    private String address;

    @JsonFormat(pattern = "HH:mm")
    @NotBlank(message = "Must not be empty.")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    @NotBlank(message = "Must not be empty.")
    private LocalTime closeTime;

    private MultipartFile image;

    @NotBlank(message = "Must not be empty.")
    private String userId;

}
