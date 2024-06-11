package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.CourtRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.CourtRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.CourtService;
import com.swp391.teamfour.forbadsystem.service.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CourtServiceImp implements CourtService {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Override
    public List<Court> getAll() {
        try {
            return courtRepository.findAll();
        } catch (Exception ex) {
            throw new RuntimeException("Có lỗi xảy ra. Vui lòng thử lại.");
        }
    }

    @Override
    public List<Court> getAllOfOwner(String userId) {
        try {
            User owner = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Chủ sân không tồn tại trong hệ thống."));
            return courtRepository.getAllByUser(owner);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Court addCourt(CourtRequest newCourt) {
        try {
            Court court = new Court(newCourt.getCourtName(), newCourt.getAddress(),
                    newCourt.getOpenTime(), newCourt.getCloseTime(), 0);
            if (newCourt.getCourtId() == null) {
                court.setCourtId(idGenerator.generateCourtId("C"));
            }
            User owner = userRepository.findById(newCourt.getUserId())
                    .orElseThrow(() -> new RuntimeException("Chủ sân không tồn tại trong hệ thống."));
            court.setUser(owner);
            owner.setCourts(Arrays.asList(court));
            userRepository.save(owner);
            return court;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteCourt(String courtId) {
        try {
            if (courtRepository.existsById(courtId)) {
                courtRepository.deleteById(courtId);
            } else {
                throw new RuntimeException("Cơ sở không tồn tại trong hệ thống.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public Court updateCourt(CourtRequest courtRequest) {
        try {
            Court existingCourt = courtRepository.findById(courtRequest.getCourtId())
                    .orElseThrow(() -> new RuntimeException("Không tồn tại cơ sở này trong hệ thống."));
            existingCourt.setCourtName(courtRequest.getCourtName());
            existingCourt.setAddress(courtRequest.getAddress());
            existingCourt.setOpenTime(courtRequest.getOpenTime());
            existingCourt.setCloseTime(courtRequest.getCloseTime());
            courtRepository.save(existingCourt);
            return existingCourt;
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void updateRate(int rate, String courtId) {
        try {
            Court existingCourt = courtRepository.findById(courtId)
                    .orElseThrow(() -> new RuntimeException("Không tồn tại cơ sở này trong hệ thống."));
            int existingRate = courtRepository.getRateByCourtId(courtId);
            int newRate = (existingRate + rate) / 2;
            existingCourt.setRate(newRate);
            courtRepository.save(existingCourt);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
