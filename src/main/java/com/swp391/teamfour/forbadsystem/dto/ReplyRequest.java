package com.swp391.teamfour.forbadsystem.dto;

import com.swp391.teamfour.forbadsystem.model.Reply;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyRequest {
    private String comment;
    private Long feedbackId;

    public static ReplyRequest build(Reply reply) {
        return new ReplyRequest(reply.getComment(), reply.getFeedback().getFeedbackId());
    }
}
