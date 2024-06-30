package com.swp391.teamfour.forbadsystem.dto.response;

import com.swp391.teamfour.forbadsystem.model.FlexibleBooking;
import com.swp391.teamfour.forbadsystem.model.YardSchedule;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsResponse {

    private LocalDate date;

    private YardSchedule yardSchedule;

    private FlexibleBooking flexibleBooking;

}
