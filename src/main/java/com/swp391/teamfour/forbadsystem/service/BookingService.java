package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.BookingDetailsRequest;
import com.swp391.teamfour.forbadsystem.dto.response.BookingDto;

import java.util.List;

public interface BookingService {

    BookingDto booking(String bookingType, List<BookingDetailsRequest> bookingDetailsRequests);

    void success(BookingDto bookingRequest);
}
