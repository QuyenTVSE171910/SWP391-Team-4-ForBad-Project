package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flexible_booking")
public class FlexibleBooking {

    @Id
    @Column(name = "flexible_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long flexibleId;

    @Column(name = "available_hours")
    private int availableHours;

    @Column(name = "used_hours")
    private int usedHours;

    @Column(name = "expiration_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime expirationDate;
}
