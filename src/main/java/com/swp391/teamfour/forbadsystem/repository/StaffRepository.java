package com.swp391.teamfour.forbadsystem.repository;

import com.swp391.teamfour.forbadsystem.dto.StaffInfo;
import com.swp391.teamfour.forbadsystem.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {


    @Query("SELECT new com.swp391.teamfour.forbadsystem.dto.StaffInfo(a.userId, a.fullName, a.email, a.phoneNumber, a.profileAvatar, a.role) " +
            "FROM Staff a " +
            "WHERE a.manager.userId = ?1 and a.role.roleId=4")
    List<StaffInfo> findAllStaffByManagerId(String managerId);

    Optional<Staff> findById(String userId);

    void deleteById(String userId);

    Optional<Staff> findByEmail(String email);

    Optional<Staff> findByPhoneNumber(String phoneNumber);

    Boolean existsByPhoneNumber(String phoneNumber);

    Boolean existsByEmail(String email);

}


