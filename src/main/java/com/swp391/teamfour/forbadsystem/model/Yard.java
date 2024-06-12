package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

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
    @JsonIgnore
    private Court court;

    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private List<Feedback> feedbacks;

    public Yard(String yardName) {
        this.yardName = yardName;
    }
}
