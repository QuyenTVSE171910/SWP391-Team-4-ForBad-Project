package com.swp391.teamfour.forbadsystem.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.swp391.teamfour.forbadsystem.model.Court;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourtResponse {

    private String courtId;

    private String courtName;

    private String address;

    private LocalTime openTime;

    private LocalTime closeTime;

    private String image;

    private String userId;

    public static CourtResponse build(Court court) {
        return new CourtResponse(court.getCourtId(), court.getCourtName(), court.getAddress(), court.getOpenTime(),
                court.getCloseTime(), court.getImageUrl(), court.getUser().getUserId());
    }
}
