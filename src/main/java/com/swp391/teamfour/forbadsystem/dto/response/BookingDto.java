package com.swp391.teamfour.forbadsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp391.teamfour.forbadsystem.model.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private String bookingId;

    private UserInfor customer;

    private BookingTypeEnum bookingType;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime bookingDate;

    private float totalPrice;

    private StatusEnum statusEnum;

    private Collection<BookingDetailsResponse> bookingDetails;

}
