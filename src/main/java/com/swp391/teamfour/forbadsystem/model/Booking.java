package com.swp391.teamfour.forbadsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @Column(name = "booking_id")
    private String bookingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private User customer;

    @Column(name = "booking_type")
    private BookingTypeEnum bookingType;

    @Column(name = "booking_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime bookingDate;

    @Column(name = "total_price")
    private float totalPrice;

    private StatusEnum status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "checked_in_by")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private User checkedInBy;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Collection<BookingDetails> bookingDetails;

    public Booking(String bookingId, User customer, BookingTypeEnum bookingType, LocalDateTime bookingDate,
                   float totalPrice, StatusEnum status) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.bookingType = bookingType;
        this.bookingDate = bookingDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }
}
