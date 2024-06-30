package com.swp391.teamfour.forbadsystem.repository;

import com.swp391.teamfour.forbadsystem.model.BookingDetails;
import com.swp391.teamfour.forbadsystem.model.StatusEnum;
import com.swp391.teamfour.forbadsystem.model.YardSchedule;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {
    List<BookingDetails> getAllByDateAndStatus(LocalDate date, StatusEnum status);
}
