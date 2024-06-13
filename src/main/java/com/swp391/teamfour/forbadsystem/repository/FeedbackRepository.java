package com.swp391.teamfour.forbadsystem.repository;

import com.swp391.teamfour.forbadsystem.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}
