package com.swp391.teamfour.forbadsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "yard_schedule",
        uniqueConstraints = @UniqueConstraint(columnNames = {"yard_id", "slot_id"}),
        indexes = @Index(name = "idx_yard_schedule_slot_id_yard_id", columnList = "slot_id, yard_id")
)
public class YardSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "yard_id", nullable = false)
    private Yard yard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "slot_id", nullable = false)
    private TimeSlot slot;
}
