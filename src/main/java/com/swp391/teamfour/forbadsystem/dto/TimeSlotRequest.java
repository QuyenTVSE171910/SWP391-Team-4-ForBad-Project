package com.swp391.teamfour.forbadsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotRequest {

    private String slotId;

    private String slotName;

    @JsonFormat(pattern = ("HH:mm"))
    private LocalTime startTime;

    @JsonFormat(pattern = ("HH:mm"))
    private LocalTime endTime;

    private float price;

    private String userId;

    private String yardId;
}
