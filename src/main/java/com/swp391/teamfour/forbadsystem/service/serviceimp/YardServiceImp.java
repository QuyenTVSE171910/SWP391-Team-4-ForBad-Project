package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.YardRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.Yard;
import com.swp391.teamfour.forbadsystem.repository.CourtRepository;
import com.swp391.teamfour.forbadsystem.repository.TimeSlotRepository;
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

    private final TimeSlotRepository timeSlotRepository;

    private final IdGenerator idGenerator;

    @Autowired
    public YardServiceImp(YardRepository yardRepository, CourtRepository courtRepository, TimeSlotRepository timeSlotRepository, IdGenerator idGenerator) {
        this.yardRepository = yardRepository;
        this.courtRepository = courtRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.idGenerator = idGenerator;
    }

    @Override
    public List<Yard> getAllYardByCourtId(String courtId) {
        Court court = courtRepository.findById(courtId).orElseThrow(() -> new RuntimeException("Sân không tồn tại trong hệ thống."));
        return court.getYards().stream().toList();
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
            court.getYards().add(yard);
            courtRepository.save(court);
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
        if (yard == null) {
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

    @Override
    public void addTimeSlotToYard(String yardId, String slotId) {
        try {
            Yard yard = yardRepository.findById(yardId)
                    .orElseThrow(() -> new RuntimeException("Sân này không tồn tại trong hệ thống."));

            TimeSlot slot = timeSlotRepository.findById(slotId)
                    .orElseThrow(() -> new RuntimeException("Time slot này không tồn tại trong hệ thống."));

            yard.getTimeSlots().add(slot);
            slot.getYards().add(yard);

            yardRepository.save(yard);
            timeSlotRepository.save(slot);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
