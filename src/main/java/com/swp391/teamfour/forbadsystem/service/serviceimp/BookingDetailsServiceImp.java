package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.BookingDetails;
import com.swp391.teamfour.forbadsystem.model.StatusEnum;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.YardSchedule;
import com.swp391.teamfour.forbadsystem.repository.BookingDetailsRepository;
import com.swp391.teamfour.forbadsystem.service.BookingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BookingDetailsServiceImp implements BookingDetailsService {

    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;

    @Override
    public Map<LocalDate, List<TimeSlotRequest>> getBookedSlots(List<String> dateStrings, String yardId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        List<LocalDate> dates = dateStrings.stream()
                .map(date -> LocalDate.parse(date, formatter))
                .collect(Collectors.toList());

        Map<LocalDate, List<TimeSlotRequest>> bookedSlotsResponse = new HashMap<>();

        dates.forEach(localDate -> {
            List<BookingDetails> bookingDetailsList = bookingDetailsRepository.getAllByDateAndStatus(localDate, StatusEnum.WAITING_FOR_CHECK_IN);
            List<TimeSlotRequest> bookedSlots = bookingDetailsList.stream()
                    .map(bookingDetails -> bookingDetails.getYardSchedule())
                    .filter(yardSchedule -> yardSchedule.getYard().getYardId().equals(yardId))
                    .map(yardSchedule -> TimeSlotRequest.build(yardSchedule.getSlot()))
                    .collect(Collectors.toList());
            bookedSlotsResponse.put(localDate, bookedSlots);
        });

        return bookedSlotsResponse;
    }
}
