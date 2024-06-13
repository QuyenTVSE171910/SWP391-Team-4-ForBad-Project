package com.Court.CRUD.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="court")

public class Court {
    @Id
    private String court_id;
    private String court_name;
    private String address;
    private LocalDateTime open_time;
    private LocalDateTime close_time;
    private int rate;

    public String getCourt_id() {
        return court_id;
    }

    public void setCourtId(String court_id) {
        this.court_id = court_id;
    }

}
