package com.swp391.teamfour.forbadsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Name;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtRequest {
    private String courtId;
    private String courtName;
    private String address;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private String userId;
}
