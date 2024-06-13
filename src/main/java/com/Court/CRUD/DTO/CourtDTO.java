package com.Court.CRUD.DTO;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Builder

public class CourtDTO {
    @Id
    private String court_id;
    private String court_name;
    private String address;
    private LocalDateTime open_time;
    private LocalDateTime close_time;
    private int rate;
}
