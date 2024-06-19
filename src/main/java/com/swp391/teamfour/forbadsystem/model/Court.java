package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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

    private double rate;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonIgnore
    private List<Yard> yards;

    @ManyToMany(mappedBy = "workplaces", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonIgnore
    private Collection<User> staffs;


    @ManyToMany(mappedBy = "courts")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Collection<Service> services;

    public Court(String courtName, String address, LocalTime openTime, LocalTime closeTime, int rate) {
        this.courtName = courtName;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.rate = rate;
    }
}
