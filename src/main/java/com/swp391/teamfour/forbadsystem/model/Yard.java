package com.swp391.teamfour.forbadsystem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public Yard(String yardName) {
        this.yardName = yardName;
    }
}
