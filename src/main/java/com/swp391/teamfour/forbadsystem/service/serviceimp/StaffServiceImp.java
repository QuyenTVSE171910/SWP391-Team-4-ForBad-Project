package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.StaffInfo;
import com.swp391.teamfour.forbadsystem.model.Staff;
import com.swp391.teamfour.forbadsystem.repository.StaffRepository;
import com.swp391.teamfour.forbadsystem.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffServiceImp implements StaffService {

    @Autowired
    private StaffRepository staffRepo;

    @Override
    public List<StaffInfo> getAllStaff(String mangerID) {
        return staffRepo.findAllStaffByManagerId(mangerID);
    }

    @Override
    public Staff findByEmail(String mail) {
        Staff staff = staffRepo
                .findByEmail(mail)
                .orElseThrow(()->new BadCredentialsException("Staff không tồn tại trong hệ thống."));
        return staff;
    }

    @Override
    public boolean existsByEmail(String email) {
        return staffRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        return staffRepo.existsByPhoneNumber(phoneNumber);
    }

    /*@Override
    public StaffInfo getStaffInfor(String emailOrPhoneNumber) {
        Staff staff = staffRepo.findByEmail(emailOrPhoneNumber)
                .orElseGet(() -> staffRepo.findByPhoneNumber(emailOrPhoneNumber)
                        .orElseThrow(() -> new UsernameNotFoundException("Staff Not Found with email or phone number: " + emailOrPhoneNumber)));
        CustomStaffDetails staffDetails = CustomStaffDetails.build(staff);
        return StaffInfo.build(staffDetails);
    }*/

    @Override
    public Staff updateStaff(StaffInfo staffinfo) {
        Staff existStaff = staffRepo.findById(staffinfo.getUserId()).orElseThrow(() -> new RuntimeException("Error: User not found."));

        existStaff.setEmail(staffinfo.getEmail());
        existStaff.setPhoneNumber(staffinfo.getPhoneNumber());
        existStaff.setFullName(staffinfo.getFullName());
        existStaff.setProfileAvatar(staffinfo.getProfileAvatar());
        existStaff.setRole(staffinfo.getRole());

        staffRepo.save(existStaff);
        return existStaff;
    }

    @Override
    public Staff addStaff(StaffInfo newstaff) {
        Staff staff;
        try {
            staff = Staff.builder()
                    .email(newstaff.getEmail())
                    .fullName(newstaff.getFullName())
                    .profileAvatar(newstaff.getProfileAvatar())
                    .role(newstaff.getRole())
                    .manager(newstaff.getManager())
                    .passwordHash(newstaff.getPasswordHash())
                    .phoneNumber(newstaff.getPhoneNumber())
                    .userId(newstaff.getUserId())
                    .build();
            staffRepo.save(staff);
            return staff;
        }
        catch (Exception ex) {
            throw ex;
        }
    }


    @Override
    public void deleteStaff(String userId) {
        try {
            if (staffRepo.existsById(userId)) {
                staffRepo.deleteById(userId);
            } else {
                throw new RuntimeException("Staff không tồn tại trong hệ thống.");
            }
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) throws UsernameNotFoundException {
        Staff staff = staffRepo.findByEmail(emailOrPhoneNumber)
                .orElseGet(() -> staffRepo.findByPhoneNumber(emailOrPhoneNumber)
                        .orElseThrow(() -> new UsernameNotFoundException("Staff Not Found with email or phone number: " + emailOrPhoneNumber)));
        CustomStaffDetails staffDetails = CustomStaffDetails.build(staff);
        return CustomStaffDetails.build(staff);
    }
}
