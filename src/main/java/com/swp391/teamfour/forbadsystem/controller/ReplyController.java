package com.swp391.teamfour.forbadsystem.controller;

import com.swp391.teamfour.forbadsystem.dto.request.ReplyRequest;
import com.swp391.teamfour.forbadsystem.model.Reply;
import com.swp391.teamfour.forbadsystem.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @GetMapping("/all/{feedbackId}")
    @PreAuthorize("hasAnyAuthority('customer', 'staff')")
    public ResponseEntity<?> getAllReplyOfFeedback(@PathVariable Long feedbackId) {
        return ResponseEntity.ok(replyService.getAllReplyOfFeedback(feedbackId));
    }

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('customer', 'staff')")
    public ResponseEntity<?> replyFeedback(@RequestBody ReplyRequest replyRequest) {
        return ResponseEntity.ok(replyService.replyFeedback(replyRequest));
    }

    @DeleteMapping("/delete/{replyId}")
    @PreAuthorize("hasAnyAuthority('customer', 'staff')")
    public ResponseEntity<?> deleteReply(@PathVariable Long replyId) {
        replyService.deleteReply(replyId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('customer', 'staff')")
    public ResponseEntity<?> updateReply(@RequestBody Reply reply) {
        return ResponseEntity.ok(replyService.updateReply(reply));
    }
}
