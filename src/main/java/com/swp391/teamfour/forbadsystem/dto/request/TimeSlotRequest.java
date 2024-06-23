package com.swp391.teamfour.forbadsystem.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TimeSlotRequest {

    private String slotId;

    @NotBlank(message = "Name must not be empty.")
    private String slotName;

    @JsonFormat(pattern = ("HH:mm"))
    private LocalTime startTime;

    @JsonFormat(pattern = ("HH:mm"))
    private LocalTime endTime;

    @NotNull(message = "Price per hour cannot be null")
    @Positive(message = "Price per hour must be greater than zero")
    private float price;

    private String userId;

    public static TimeSlotRequest build(TimeSlot timeSlot) {
        return new TimeSlotRequest(timeSlot.getSlotId(), timeSlot.getSlotName(), timeSlot.getStartTime(),
                timeSlot.getEndTime(), timeSlot.getPrice(), timeSlot.getUser().getUserId());
    }
}
