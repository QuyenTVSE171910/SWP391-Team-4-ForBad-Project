package com.swp391.teamfour.forbadsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp391.teamfour.forbadsystem.model.Court;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse {

    private String courtId;

    private String courtName;

    private String address;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime openTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime closeTime;

    private String image;

    private String userId;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime beginDate;

    public static CourtResponse build(Court court) {
        return new CourtResponse(court.getCourtId(), court.getCourtName(), court.getAddress(), court.getOpenTime(),
                court.getCloseTime(), court.getImageUrl(), court.getUser().getUserId(), court.getBeginDate());
    }
}
