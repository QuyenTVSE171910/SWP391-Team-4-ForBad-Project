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
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonIgnore
    private Court court;


    @OneToMany(mappedBy = "yard", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonIgnore
    private List<Feedback> feedbacks;

    @ManyToMany
    @JoinTable(name = "yard_schedule", joinColumns = @JoinColumn(name = "yard_id"),
            inverseJoinColumns = @JoinColumn(name = "slot_id"))
    @EqualsAndHashCode.Exclude // không sử dụng trường này trong equals và hashcode
    @ToString.Exclude // Khoonhg sử dụng trong toString()
    @JsonIgnore
    private Collection<TimeSlot> timeSlots;

    public Yard(String yardName) {
        this.yardName = yardName;
    }
}
