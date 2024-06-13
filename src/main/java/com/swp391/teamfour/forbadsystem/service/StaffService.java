package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.StaffInfo;
import com.swp391.teamfour.forbadsystem.model.Staff;
import org.springframework.security.core.userdetails.UserDetailsService;


import java.util.List;


public interface StaffService extends UserDetailsService {
    List<StaffInfo> getAllStaff(String mangerID);

    Staff findByEmail(String mail);

    boolean existsByEmail(String email);

    boolean existsByPhoneNumber(String phoneNumber);

    //StaffInfo getStaffInfor(String emailOrPhoneNumber);

    Staff updateStaff(StaffInfo staffInfo);

    Staff addStaff(StaffInfo staff);

    void deleteStaff(String userId);


}

