package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.TimeSlotRequest;
import com.swp391.teamfour.forbadsystem.model.TimeSlot;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.TimeSlotRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.utils.IdGenerator;
import com.swp391.teamfour.forbadsystem.service.TimeSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Duration;
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

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.getReferenceById(userDetails.getUserId());
    }

    @Override
    public List<TimeSlot> findAllSlotByUserId() {

        User owner = getCurrentUser();

        if (timeSlotRepository.findAllByUser(owner).isEmpty())
            throw new RuntimeException("Không có timeslot nào được tạo.");
        return timeSlotRepository.findAllByUser(owner);
    }

    @Override
    public TimeSlot createSlot(TimeSlotRequest timeSlotRequest) {
        try {
            TimeSlot timeSlot = new TimeSlot();

            User owner = getCurrentUser();

            // Kiểm tra xem owner đã có TimeSlot với tên giống với TimeSlotRequest chưa
            boolean slotExists = owner.getTimeSlots().stream()
                    .anyMatch(slot -> slot.getSlotName().equals(timeSlotRequest.getSlotName()));

            if (slotExists) throw new RuntimeException("Slot với tên này đã có trong danh sách.");

            if (timeSlotRequest.getSlotId() == null) {
                timeSlot.setSlotId(idGenerator.generateCourtId("TL"));
            }

            timeSlot.setSlotName(timeSlotRequest.getSlotName());
            timeSlot.setStartTime(timeSlotRequest.getStartTime());
            timeSlot.setEndTime(timeSlotRequest.getEndTime());
            timeSlot.setUser(owner);

            long timeSlotHour = Duration.between(timeSlot.getStartTime(), timeSlot.getEndTime()).toHours();
            timeSlot.setPrice(timeSlotHour * timeSlotRequest.getPricePerHour());

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

            User owner = getCurrentUser();

            // Kiểm tra xem owner đã có TimeSlot với tên giống với TimeSlotRequest nhưng khác với existingTimeSlot
            boolean slotExists = owner.getTimeSlots().stream()
                    .anyMatch(slot -> !slot.getSlotId().equals(existingTimeSlot.getSlotId())
                            && slot.getSlotName().equals(timeSlotRequest.getSlotName()));

            if (slotExists) throw new RuntimeException("Slot với tên này đã có trong danh sách.");

            existingTimeSlot.setStartTime(timeSlotRequest.getStartTime());
            existingTimeSlot.setEndTime(timeSlotRequest.getEndTime());

            long timeSlotHour = Duration.between(existingTimeSlot.getStartTime(), existingTimeSlot.getEndTime()).toHours();
            existingTimeSlot.setPrice(timeSlotHour * timeSlotRequest.getPricePerHour());

            timeSlotRepository.save(existingTimeSlot);
            return existingTimeSlot;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public TimeSlot findSlotById(String slotId) {
        TimeSlot timeSlot = timeSlotRepository.findById(slotId)
                .orElseThrow(() -> new RuntimeException("Timeslot này không tồn tại trong hệ thống."));

        return timeSlot;
    }

    @Override
    public void deteleSlotById(String slotId) {
        try {
            if (!timeSlotRepository.existsById(slotId))
                throw new RuntimeException("Timeslot không tồn tại trong hệ thống.");
            timeSlotRepository.deleteById(slotId);
        } catch (Exception ex) {
            throw ex;
        }
    }


}
