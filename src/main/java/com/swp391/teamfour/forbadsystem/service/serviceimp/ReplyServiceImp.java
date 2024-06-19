package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.request.ReplyRequest;
import com.swp391.teamfour.forbadsystem.model.Feedback;
import com.swp391.teamfour.forbadsystem.model.Reply;
import com.swp391.teamfour.forbadsystem.repository.FeedbackRepository;
import com.swp391.teamfour.forbadsystem.repository.ReplyRepository;
import com.swp391.teamfour.forbadsystem.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReplyServiceImp implements ReplyService {

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Override
    public List<Reply> getAllReplyOfFeedback(Long feedbackId) {
        try {
            Feedback feedback = feedbackRepository.findById(feedbackId)
                    .orElseThrow(() -> new RuntimeException("Feedback này không tồn tại trong hệ thống."));

            if (feedback.getReplies().isEmpty()) throw new RuntimeException("Không có phản hồi nào.");

            return feedback.getReplies();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public ReplyRequest replyFeedback(ReplyRequest replyRequest) {
        try {
            Feedback feedback = feedbackRepository.findById(replyRequest.getFeedbackId())
                    .orElseThrow(() -> new RuntimeException("Feedback này không tồn tại trong hệ thống."));

            Reply reply = new Reply();
            reply.setComment(replyRequest.getComment());
            reply.setFeedback(feedback);
            feedback.getReplies().add(reply);
            feedbackRepository.save(feedback);

            return ReplyRequest.build(reply);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteReply(Long replyId) {
        try {
            if (!replyRepository.existsById(replyId)) throw new RuntimeException("Reply này không tồn tại trong hệ thống.");

            replyRepository.deleteById(replyId);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public ReplyRequest updateReply(Reply reply) {
        try {
            Reply existingReply = replyRepository.findById(reply.getReplyId())
                    .orElseThrow(() -> new RuntimeException("Reply này không tồn tại trong hệ thống."));

            existingReply.setComment(reply.getComment());
            replyRepository.save(existingReply);

            return ReplyRequest.build(existingReply);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
