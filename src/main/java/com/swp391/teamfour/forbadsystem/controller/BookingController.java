package com.swp391.teamfour.forbadsystem.controller;
import com.swp391.teamfour.forbadsystem.dto.request.BookingDetailsRequest;
import com.swp391.teamfour.forbadsystem.dto.response.BookingDto;
import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/{bookingType}")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<?> booking(@PathVariable String bookingType, @RequestBody List<BookingDetailsRequest> bookingDetailsList) {
        return ResponseEntity.ok(bookingService.booking(bookingType, bookingDetailsList));
    }

    @PostMapping("/success")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<?> success(@RequestBody BookingDto bookingRequest) {
        bookingService.success(bookingRequest);
        return ResponseEntity.ok().body(new MessageResponse("Đã đặt lịch thành công."));
    }
}
