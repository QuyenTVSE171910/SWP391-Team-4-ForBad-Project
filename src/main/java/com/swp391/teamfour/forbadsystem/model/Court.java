package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp391.teamfour.forbadsystem.service.IdGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="court")
public class Court {
    @Id
    @Column(name = "court_id")
    private String courtId;

    @Column(name = "court_name", nullable = false)
    private String courtName;

    @Column(nullable = false)
    private String address;

    @Column(name = "open_time", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private int rate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Court(String courtName, String address, LocalTime openTime, LocalTime closeTime, int rate) {
        this.courtName = courtName;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.rate = rate;
    }
}
