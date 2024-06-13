package com.swp391.teamfour.forbadsystem.repository;

import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, String> {

    List<TimeSlot> findAllByUser(User user);

    TimeSlot findBySlotId(String slotId);

}
