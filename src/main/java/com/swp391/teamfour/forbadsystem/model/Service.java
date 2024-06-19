package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services")
public class Service {

    @Id
    @Column(name = "service_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(name = "service_name")
    private String serviceName;

    @ManyToMany
    @JoinTable(name = "court_service", joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "court_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Collection<Court> courts;
}
