package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.FeedbackRequest;
import com.swp391.teamfour.forbadsystem.model.Feedback;

import java.util.List;

public interface FeedbackService {

    List<Feedback> getAllFeedbackOfYard(String yardId);

    FeedbackRequest giveFeedback(FeedbackRequest feedbackRequest);

    FeedbackRequest updateFeedback(Feedback feedback);

    void deleteFeedback(Long feedbackId);

//    ReplyRequest replyFeedback(ReplyRequest replyRequest);
}
