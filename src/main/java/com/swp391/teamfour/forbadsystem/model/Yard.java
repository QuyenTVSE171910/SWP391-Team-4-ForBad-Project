package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Court court;


    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<Feedback> feedbacks;
}
