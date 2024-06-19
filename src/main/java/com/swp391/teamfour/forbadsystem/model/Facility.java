package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "facility")
public class Facility {

    @Id
    @Column(name = "facility_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @Column(name = "facility_name")
    private String facilityName;

    @ManyToMany
    @JoinTable(name = "court_facility", joinColumns = @JoinColumn(name = "facility_id"),
            inverseJoinColumns = @JoinColumn(name = "court_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Collection<Court> courts;
}
