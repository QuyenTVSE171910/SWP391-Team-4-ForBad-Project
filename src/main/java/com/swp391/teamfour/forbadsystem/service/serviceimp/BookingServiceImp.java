package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.BookingDetailsRequest;
import com.swp391.teamfour.forbadsystem.dto.response.BookingDetailsResponse;
import com.swp391.teamfour.forbadsystem.dto.response.BookingDto;
import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.model.*;
import com.swp391.teamfour.forbadsystem.repository.*;
import com.swp391.teamfour.forbadsystem.service.BookingService;
import com.swp391.teamfour.forbadsystem.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BookingServiceImp implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private YardScheduleRepository yardScheduleRepository;

    @Autowired
    private YardRepository yardRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdGenerator idGenerator;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.getReferenceById(userDetails.getUserId());
    }

    @Override
    public BookingDto booking(String bookingType, List<BookingDetailsRequest> bookingDetailsRequests) {
        try {
            User customer = getCurrentUser();

            BookingDto booking = new BookingDto();

            List<BookingDetailsResponse> bookingDetailsList = new ArrayList<>();
            AtomicReference<Float> totalPrice = new AtomicReference<>((float) 0);

            booking.setBookingId(idGenerator.generateCourtId("BK"));
            booking.setCustomer(UserInfor.build(customer));
            booking.setBookingType(BookingTypeEnum.valueOf(bookingType));
            booking.setBookingDate(LocalDateTime.now());

            bookingDetailsRequests.stream().forEach(bookingDetailsRequest -> {

                Yard yard = yardRepository.findById(bookingDetailsRequest.getYardId())
                        .orElseThrow(() -> new RuntimeException("Sân không tồn tại."));
                TimeSlot slot = timeSlotRepository.findById(bookingDetailsRequest.getSlotId())
                        .orElseThrow(() -> new RuntimeException("Slot không tồn tại."));

                YardSchedule yardSchedule = yardScheduleRepository.findByYardAndSlot(yard, slot)
                        .orElseThrow(() -> new RuntimeException("YardSchedule không tồn tại."));

                BookingDetailsResponse bookingDetailsResponse = new BookingDetailsResponse();
                bookingDetailsResponse.setDate(bookingDetailsRequest.getDate());
                bookingDetailsResponse.setYardSchedule(yardSchedule);

                totalPrice.updateAndGet(v -> v + yardSchedule.getSlot().getPrice());
                bookingDetailsList.add(bookingDetailsResponse);
            });

            booking.setTotalPrice(totalPrice.get());
            booking.setBookingDetails(bookingDetailsList);
            return booking;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void success(BookingDto bookingRequest) {
            try {
                User customer = getCurrentUser();
                Booking booking = new Booking(bookingRequest.getBookingId(), customer,
                        bookingRequest.getBookingType(), bookingRequest.getBookingDate(), bookingRequest.getTotalPrice(),
                        StatusEnum.PENDING);
                List<BookingDetails> bookingDetailsList = new ArrayList<>();

                bookingRequest.getBookingDetails().stream().forEach(bookingDetailsResponse -> {
                    BookingDetails bookingDetails = new BookingDetails();
                    bookingDetails.setDate(bookingDetailsResponse.getDate());
                    bookingDetails.setYardSchedule(bookingDetailsResponse.getYardSchedule());
                    bookingDetails.setStatus(StatusEnum.WAITING_FOR_CHECK_IN);
                    bookingDetails.setBooking(booking);
                    bookingDetailsList.add(bookingDetails);
                });
                booking.setBookingDetails(bookingDetailsList);
                bookingRepository.save(booking);
            } catch (Exception ex) {
                throw ex;
            }
    }
}
