package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.request.FeedbackRequest;
import com.swp391.teamfour.forbadsystem.dto.response.MessageResponse;
import com.swp391.teamfour.forbadsystem.model.Feedback;
import com.swp391.teamfour.forbadsystem.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('customer', 'staff')")
    public ResponseEntity<?> getAllFeedbackOfYard(@RequestParam String yardId) {
        List<Feedback> feedbacks = feedbackService.getAllFeedbackOfYard(yardId);
        return ResponseEntity.ok(feedbacks);
    }

    @PostMapping("/give")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<?> giveFeedback(@RequestBody FeedbackRequest feedbackRequest) {
        FeedbackRequest feedback = feedbackService.giveFeedback(feedbackRequest);
        return ResponseEntity.ok(feedback);
    }

    @PutMapping("/update")
    @PreAuthorize("hasAuthority('customer')")
    public ResponseEntity<?> updateFeedback(@RequestBody Feedback feedback) {
        FeedbackRequest newFeedback = feedbackService.updateFeedback(feedback);
        return ResponseEntity.ok(newFeedback);
    }

    @DeleteMapping("/delete/{feedbackId}")
    @PreAuthorize("hasAnyAuthority('customer', 'staff')")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long feedbackId) {
        feedbackService.deleteFeedback(feedbackId);
        return ResponseEntity.ok().body(new MessageResponse("Đã xóa feedback thành công."));
    }


}
