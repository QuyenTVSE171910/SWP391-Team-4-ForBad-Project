package com.swp391.teamfour.forbadsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class IdGenerator {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String generateCourtId(String prefix) {
        Long id = jdbcTemplate.queryForObject("SELECT id FROM id_sequence FOR UPDATE", Long.class);
        jdbcTemplate.update("UPDATE id_sequence SET id = id + 1 WHERE id = ?", id);

        return prefix + String.format("%07d", id);
    }
}
