package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="court")
@EntityListeners(AuditingEntityListener.class)
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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private User user;

    @Column(updatable = false)
    @CreatedDate
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime beginDate;

    @OneToMany(mappedBy = "court", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Yard> yards;

    @ManyToMany(mappedBy = "workplaces", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Collection<User> staffs;

    @ManyToMany(mappedBy = "courts")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private Collection<Facility> facilities;

    public Court(String courtName, String address, LocalTime openTime, LocalTime closeTime, int rate) {
        this.courtName = courtName;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.rate = rate;
    }
}
