package com.swp391.teamfour.forbadsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "yard")
public class Yard {

    @Id
    @Column(name = "yard_id")
    private String yardId;

    @Column(name = "yard_name")
    private String yardName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "court_id", nullable = false)
    private Court court;

    @ManyToMany
    @JoinTable(name = "yard_schedule", joinColumns = @JoinColumn(name = "yard_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id"))
    private Collection<TimeSlot> timeSlots;

    public Yard(String yardName) {
        this.yardName = yardName;
    }
}
