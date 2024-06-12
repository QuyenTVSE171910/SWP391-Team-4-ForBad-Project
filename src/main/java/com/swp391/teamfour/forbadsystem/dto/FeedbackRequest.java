package com.swp391.teamfour.forbadsystem.dto;

import com.swp391.teamfour.forbadsystem.model.Feedback;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackRequest {

    private double rate;
    private String comment;
    private String yardId;

    public static FeedbackRequest build(Feedback feedback) {
        return new FeedbackRequest(feedback.getRate(), feedback.getComment(), feedback.getYard().getYardId());
    }
}
