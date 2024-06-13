package com.Court.CRUD.Service;

import com.Court.CRUD.DTO.CourtDTO;
import com.Court.CRUD.Model.Court;

import java.util.List;

public interface CourtService {
    List<CourtDTO> findAllCourts();

    Court save(Court court);

    CourtDTO findCourtByID(String CourtID);

    void updateCourt(CourtDTO court);

    void delete(String courtId);
}
