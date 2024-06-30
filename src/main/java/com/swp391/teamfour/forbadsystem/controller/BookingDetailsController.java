package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.service.BookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/booking-details")
public class BookingDetailsController {

    @Autowired
    private BookingDetailsService bookingDetailsService;

    @PostMapping("/booked-slots/{yardId}")
    public ResponseEntity<?> getBookedSlots(@PathVariable String yardId, @RequestBody List<String> dateStrings) {
        return ResponseEntity.ok(bookingDetailsService.getBookedSlots(dateStrings, yardId));
    }
}
