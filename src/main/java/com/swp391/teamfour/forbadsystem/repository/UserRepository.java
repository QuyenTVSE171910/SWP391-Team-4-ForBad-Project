package com.swp391.teamfour.forbadsystem.repository;

import com.swp391.teamfour.forbadsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findById(String userId);

    void deleteById(String userId);

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);

    List<User> getAllByManager(User manager);
}
