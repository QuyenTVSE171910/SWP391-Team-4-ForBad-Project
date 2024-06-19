package com.swp391.teamfour.forbadsystem.dto.request;

import com.swp391.teamfour.forbadsystem.model.Facility;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FacilityRequest {

    private Long facilityId;

    @NotBlank(message = "Must not be empty.")
    private String facilityName;

    public static FacilityRequest build(Facility facility) {
        return new FacilityRequest(facility.getFacilityId(), facility.getFacilityName());
    }
}
