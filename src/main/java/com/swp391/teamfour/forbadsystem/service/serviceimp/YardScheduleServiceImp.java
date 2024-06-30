package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.Yard;
import com.swp391.teamfour.forbadsystem.model.YardSchedule;
import com.swp391.teamfour.forbadsystem.repository.TimeSlotRepository;
import com.swp391.teamfour.forbadsystem.repository.YardRepository;
import com.swp391.teamfour.forbadsystem.repository.YardScheduleRepository;
import com.swp391.teamfour.forbadsystem.service.TimeSlotService;
import com.swp391.teamfour.forbadsystem.service.YardScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YardScheduleServiceImp implements YardScheduleService {

    @Autowired
    private YardRepository yardRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @Autowired
    private YardScheduleRepository yardScheduleRepository;

    @Override
    public void addTimeSlotToYard(String yardId, String slotId) {
        try {
            Yard yard = yardRepository.findById(yardId)
                    .orElseThrow(() -> new RuntimeException("Sân này không tồn tại trong hệ thống."));

            TimeSlot slot = timeSlotRepository.findById(slotId)
                    .orElseThrow(() -> new RuntimeException("Time slot này không tồn tại trong hệ thống."));

            YardSchedule yardSchedule = new YardSchedule();

            if (yardScheduleRepository.existsByYardAndSlot(yard, slot)) throw new RuntimeException("Slot này đã có trong sân.");

            yardSchedule.setYard(yard);
            yardSchedule.setSlot(slot);

            yardScheduleRepository.save(yardSchedule);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteSlotFromYard(String yardId, String timeSlotId) {
        Yard yard = yardRepository.findById(yardId)
                .orElseThrow(() -> new RuntimeException("Sân này không tồn tại trong hệ thống."));

        TimeSlot slot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new RuntimeException("Time slot này không tồn tại trong hệ thống."));

        YardSchedule existingYardSchedule = yardScheduleRepository.findByYardAndSlot(yard, slot)
        .orElseThrow(() -> new RuntimeException("Time slot này không tồn tại trong danh sách time slot của sân này."));

        yardScheduleRepository.delete(existingYardSchedule);
    }

    @Override
    public List<TimeSlotRequest> getAllSlotByYardId(String yardId) {
        try {
            Yard existingYard = yardRepository.findById(yardId)
                    .orElseThrow(() -> new RuntimeException("Sân này không có trong hệ thống."));
            List<YardSchedule> yardSchedules = yardScheduleRepository.findAllByYard(existingYard);
            return yardSchedules.stream().map(yardSchedule -> TimeSlotRequest.build(yardSchedule.getSlot())).collect(Collectors.toList());
        } catch (Exception ex) {
            throw ex;
        }
    }
}
