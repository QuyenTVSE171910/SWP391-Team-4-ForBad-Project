package com.swp391.teamfour.forbadsystem.repository;

import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.Yard;
import com.swp391.teamfour.forbadsystem.model.YardSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface YardScheduleRepository extends JpaRepository<YardSchedule, Long> {
    boolean existsByYardAndSlot(Yard yard, TimeSlot slot);

    Optional<YardSchedule> findByYardAndSlot(Yard yard, TimeSlot slot);

    List<YardSchedule> findAllByYard(Yard yard);
}
