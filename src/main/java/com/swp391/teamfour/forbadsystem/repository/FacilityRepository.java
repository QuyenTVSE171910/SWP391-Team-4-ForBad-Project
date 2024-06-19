package com.swp391.teamfour.forbadsystem.repository;

import com.swp391.teamfour.forbadsystem.model.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Long> {
    boolean existsByFacilityName(String facilityName);
}
