package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.YardSchedule;
import org.aspectj.weaver.patterns.ConcreteCflowPointcut;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface BookingDetailsService {

    Map<LocalDate, List<TimeSlotRequest>> getBookedSlots(List<String> dateStrings, String yardId);
}
