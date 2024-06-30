package com.swp391.teamfour.forbadsystem.dto.request;

import com.swp391.teamfour.forbadsystem.model.FlexibleBooking;
import com.swp391.teamfour.forbadsystem.model.YardSchedule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsRequest {

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate date;

    private String yardId;

    private String slotId;

//    private FlexibleBooking flexibleBooking;
}
