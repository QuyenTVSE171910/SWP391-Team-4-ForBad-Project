package com.swp391.teamfour.forbadsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @Column(name = "slot_id")
    private String slotId;

    @Column(name = "slot_name")
    private String slotName;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    @Column(name = "price")
    private float price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public TimeSlot(String slotName, LocalTime startTime, LocalTime endTime, float price) {
        this.slotName = slotName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }
}
