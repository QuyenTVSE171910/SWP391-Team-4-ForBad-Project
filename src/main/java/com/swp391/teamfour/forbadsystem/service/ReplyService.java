package com.swp391.teamfour.forbadsystem.service;

import com.swp391.teamfour.forbadsystem.dto.request.ReplyRequest;
import com.swp391.teamfour.forbadsystem.model.Reply;

import java.util.List;

public interface ReplyService {
    List<Reply> getAllReplyOfFeedback(Long feedbackId);

    ReplyRequest replyFeedback(ReplyRequest replyRequest);

    void deleteReply(Long replyId);

    ReplyRequest updateReply(Reply reply);
}
