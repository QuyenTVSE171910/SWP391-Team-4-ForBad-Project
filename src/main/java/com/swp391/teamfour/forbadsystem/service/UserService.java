package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.model.User;
import com.swp391.teamfour.forbadsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String emailOrPhoneNumber) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(emailOrPhoneNumber)
                .orElseGet(() -> userRepository.findByPhoneNumber(emailOrPhoneNumber)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with email or phone number: " + emailOrPhoneNumber)));
        return CustomUserDetails.build(user);
    }
}
