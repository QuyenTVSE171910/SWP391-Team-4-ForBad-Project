package com.swp391.teamfour.forbadsystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private MultipartFile imageUrl;

    private String userId;

}
