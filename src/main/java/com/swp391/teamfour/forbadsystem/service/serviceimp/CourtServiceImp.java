package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.CourtRequest;
import com.swp391.teamfour.forbadsystem.dto.response.CourtResponse;
import com.swp391.teamfour.forbadsystem.dto.response.UserInfor;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.CourtRepository;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import com.swp391.teamfour.forbadsystem.service.CourtService;
import com.swp391.teamfour.forbadsystem.service.FirebaseService;
import com.swp391.teamfour.forbadsystem.utils.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourtServiceImp implements CourtService {
    @Autowired
    private CourtRepository courtRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private FirebaseService firebaseService;

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        return userRepository.getReferenceById(userDetails.getUserId());
    }

    @Override
    public List<Court> getAll() {
        try {
            if (courtRepository.findAll().isEmpty()) throw new RuntimeException("Danh sách sân trống.");

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

            if (owner.getCourts().isEmpty()) throw new RuntimeException("Danh sách sân trống.");

            return owner.getCourts().stream().toList();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public CourtResponse addCourt(CourtRequest newCourt) throws IOException {
        try {
            Court court = new Court(newCourt.getCourtName(), newCourt.getAddress(),
                    newCourt.getOpenTime(), newCourt.getCloseTime(), 0);

            if (newCourt.getCourtId() == null) court.setCourtId(idGenerator.generateCourtId("C"));

            if (newCourt.getImage() != null) {
                court.setImageUrl(firebaseService.uploadFile(newCourt.getImage()));
            }

            court.setImageUrl("https://firebasestorage.googleapis.com/v0/b/forbad-43f1e.appspot.com/o/" +
                              "default_court_background.jpg?alt=media&token=c79a6780-2d25-4753-8d30-6a849128edf8");

            User owner = userRepository.findById(newCourt.getUserId())
                    .orElseThrow(() -> new RuntimeException("Chủ sân không tồn tại trong hệ thống."));
            court.setUser(owner);

            owner.getCourts().add(court);
            userRepository.save(owner);

            return CourtResponse.build(court);
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
    public CourtResponse updateCourt(CourtRequest courtRequest) throws IOException {
        try {
            Court existingCourt = courtRepository.findById(courtRequest.getCourtId())
                    .orElseThrow(() -> new RuntimeException("Không tồn tại cơ sở này trong hệ thống."));
            existingCourt.setCourtName(courtRequest.getCourtName());
            existingCourt.setAddress(courtRequest.getAddress());
            existingCourt.setOpenTime(courtRequest.getOpenTime());
            existingCourt.setCloseTime(courtRequest.getCloseTime());
            existingCourt.setImageUrl(firebaseService.uploadFile(courtRequest.getImage()));
            courtRepository.save(existingCourt);
            return CourtResponse.build(existingCourt);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void addStaffToCourt(String courtId, String staffId) {
        try {
            Court existingCourt = courtRepository.findById(courtId)
                    .orElseThrow(() -> new RuntimeException("Cơ sở không tồn tại trong hệ thống."));

            User existingStaff = userRepository.findById(staffId)
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại trong hệ thống."));

            User manager = getCurrentUser();

            if (!manager.getStaffs().contains(existingStaff))
                throw new RuntimeException("Không có nhân viên này trong danh sách nhân viên của chủ sân.");

            if (!existingCourt.getStaffs().contains(existingStaff)) {
                existingCourt.getStaffs().add(existingStaff);
                existingStaff.getWorkplaces().add(existingCourt);
                courtRepository.save(existingCourt);
                userRepository.save(existingStaff);
            } else {
                throw new RuntimeException("Nhân viên này đã có trong danh sách nhân viên của cơ sở.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public List<UserInfor> getAllStaffByCourtId(String courtId) {
        try {
            Court existingCourt = courtRepository.findById(courtId)
                    .orElseThrow(() -> new RuntimeException("Cơ sở này không tồn tại trong hệ thống"));

            List<User> staffs = existingCourt.getStaffs().stream().toList();

            if (!staffs.isEmpty()) {
                return staffs.stream()
                        .map(staff -> UserInfor.build(CustomUserDetails.build(staff))).collect(Collectors.toList());
            } else {
                throw new RuntimeException("Danh sách nhân viên trống.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteStaffFromCourt(String courtId, String staffId) {
        try {
            Court existingCourt = courtRepository.findById(courtId)
                    .orElseThrow(() -> new RuntimeException("Cơ sở không tồn tại trong hệ thống."));

            User existingStaff = userRepository.findById(staffId)
                    .orElseThrow(() -> new RuntimeException("Nhân viên không tồn tại trong hệ thống."));

            if (!existingCourt.getStaffs().contains(existingStaff)) throw new RuntimeException("Nhân viên không tồn tại trong cơ sở này.");

            existingCourt.getStaffs().remove(existingStaff);
            existingStaff.getWorkplaces().remove(existingCourt);

            courtRepository.save(existingCourt);
            userRepository.save(existingStaff);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
