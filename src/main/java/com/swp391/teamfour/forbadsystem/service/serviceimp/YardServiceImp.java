package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.YardRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.model.Yard;
import com.swp391.teamfour.forbadsystem.repository.CourtRepository;
import com.swp391.teamfour.forbadsystem.repository.YardRepository;
import com.swp391.teamfour.forbadsystem.service.IdGenerator;
import com.swp391.teamfour.forbadsystem.service.YardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YardServiceImp implements YardService {

    private final YardRepository yardRepository;

    private final CourtRepository courtRepository;

    private final IdGenerator idGenerator;

    @Autowired
    public YardServiceImp(YardRepository yardRepository, CourtRepository courtRepository, IdGenerator idGenerator) {
        this.yardRepository = yardRepository;
        this.courtRepository = courtRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<Yard> getAllYardByCourtId(String courtId) {
        Court court = courtRepository.findById(courtId).orElseThrow(() -> new RuntimeException("Sân không tồn tại trong hệ thống."));
        return yardRepository.findYardByCourt(court);
    }

    @Override
    public Yard createYard(YardRequest yardRequest) {
        try {
            Yard yard = new Yard(yardRequest.getYardName());
            if (yardRequest.getYardId() == null) {
                yard.setYardId(idGenerator.generateCourtId("Y"));
            }
            Court court = courtRepository.findById(yardRequest.getCourtId())
                    .orElseThrow(() -> new RuntimeException("Cơ sở không tồn tại trong hệ thống."));
            yard.setCourt(court);
            yardRepository.save(yard);
            return yard;
        } catch (Exception ex) {
            throw ex;
        }
    }
    @Override
    public Yard updateYard(YardRequest yardRequest) {
            try {
                Yard existingYard = yardRepository.findById(yardRequest.getYardId())
                        .orElseThrow(() -> new RuntimeException("Sân không tồn tại cơ sở này trong hệ thống."));
                existingYard.setYardName(yardRequest.getYardName());
                yardRepository.save(existingYard);
                return existingYard;
            } catch (Exception ex) {
                throw ex;
            }
    }

    @Override
    public Yard findYardById(String yardId) {
        Yard yard = yardRepository.findByYardId(yardId);
        if (yard == null){
            return null;
        }
        return yard;
    }

    @Override
    public void deleteYardById(String yardId) {
        try {
            if (yardRepository.findByYardId(yardId) != null) {
                yardRepository.deleteById(yardId);
            } else {
                throw new RuntimeException("Cơ sở không tồn tại trong hệ thống.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
