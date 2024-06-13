package com.Court.CRUD.Service.Implement;

import com.Court.CRUD.Controller.GenerateString;
import com.Court.CRUD.DTO.CourtDTO;
import com.Court.CRUD.Model.Court;
import com.Court.CRUD.Responsitory.CourtResponsitory;
import com.Court.CRUD.Service.CourtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourtServiceImp implements CourtService {
    private CourtResponsitory courtResp;

    @Override
    public void updateCourt(CourtDTO courtDTO) {
        Court court = mapToCourt(courtDTO);
        courtResp.save(court);
    }

    @Override
    public void delete(String courtId) {
        courtResp.deleteById(courtId);
    }

    private Court mapToCourt(CourtDTO court) {
        Court courtDTO = Court.builder()
                .court_id(court.getCourt_id())
                .court_name(court.getCourt_name())
                .address(court.getAddress())
                .open_time(court.getOpen_time())
                .close_time(court.getClose_time())
                .rate(court.getRate())
                .build();
        return courtDTO;
    }


    @Autowired
    public CourtServiceImp (CourtResponsitory courtResp){
        this.courtResp = courtResp;
    }

    @Override
    public List<CourtDTO> findAllCourts(){
        List<Court> courts = courtResp.findAll();
        return courts.stream().map(this::mapToCourtDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Court save(Court court) {
        Integer currentMaxNumber = courtResp.findMaxCourtId();
        if (currentMaxNumber == null) {
            currentMaxNumber = 0;
        }
        String nextCode = GenerateString.generateNextCode(currentMaxNumber);
        court.setCourtId(nextCode);
        return courtResp.save(court);
    }

    @Override
    public CourtDTO findCourtByID(String CourtID) {
        Court court = courtResp.findById(CourtID).get();
        return mapToCourtDTO(court);
    }


    private CourtDTO mapToCourtDTO(Court court){
        CourtDTO courtDTO = CourtDTO.builder()
                .court_id(court.getCourt_id())
                .court_name(court.getCourt_name())
                .address(court.getAddress())
                .open_time(court.getOpen_time())
                .close_time(court.getClose_time())
                .rate(court.getRate())
                .build();
        return courtDTO;
    }
}
