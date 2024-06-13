package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.TimeSlotRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.IdGenerator;
import com.swp391.teamfour.forbadsystem.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeSlotServiceImp implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    private final UserRepository userRepository;

    private final IdGenerator idGenerator;

    @Autowired
    public TimeSlotServiceImp(TimeSlotRepository timeSlotRepository, UserRepository userRepository, IdGenerator idGenerator) {
        this.timeSlotRepository = timeSlotRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
    }


    @Override
    public List<TimeSlot> findAllSlotByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("Chủ sân không tồn tại trong hệ thống."));
        return timeSlotRepository.findAllByUser(user);
    }

    @Override
    public TimeSlot createSlot(TimeSlotRequest timeSlotRequest) {
        try {
            TimeSlot timeSlot = new TimeSlot(timeSlotRequest.getSlotName(), timeSlotRequest.getStartTime(), timeSlotRequest.getEndTime(), timeSlotRequest.getPrice());
            if (timeSlotRequest.getSlotId() == null) {
                timeSlot.setSlotId(idGenerator.generateCourtId("TL"));
            }
            User user = userRepository.findById(timeSlotRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("Chủ sân không tồn tại trong hệ thống."));
            timeSlot.setUser(user);
            timeSlotRepository.save(timeSlot);
            return timeSlot;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public TimeSlot updateSlot(TimeSlotRequest timeSlotRequest) {
        try {
            TimeSlot existingTimeSlot = timeSlotRepository.findById(timeSlotRequest.getSlotId())
                    .orElseThrow(() -> new RuntimeException("Không tồn tại cơ sở này trong hệ thống."));
            existingTimeSlot.setSlotName(timeSlotRequest.getSlotName());
            existingTimeSlot.setStartTime(timeSlotRequest.getStartTime());
            existingTimeSlot.setEndTime(timeSlotRequest.getEndTime());
            existingTimeSlot.setPrice(timeSlotRequest.getPrice());
            timeSlotRepository.save(existingTimeSlot);
            return existingTimeSlot;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public TimeSlot findSlotById(String slotId) {
        TimeSlot timeSlot = timeSlotRepository.findBySlotId(slotId);
        if (timeSlot == null) {
            return null;
        }
        return timeSlot;
    }

    @Override
    public void deteleSlotById(String slotId) {
        try {
            if (timeSlotRepository.findBySlotId(slotId) != null) {
                timeSlotRepository.deleteById(slotId);
            } else {
                throw new RuntimeException("Cơ sở không tồn tại trong hệ thống.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
