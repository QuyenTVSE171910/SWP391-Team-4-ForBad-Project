package com.Court.CRUD.Responsitory;

import com.Court.CRUD.DTO.CourtDTO;
import com.Court.CRUD.Model.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourtResponsitory extends JpaRepository<Court, String> {
    @Query("SELECT MAX(CAST(SUBSTRING(court.court_id, 2) AS int)) FROM Court court")
    Integer findMaxCourtId();
}
