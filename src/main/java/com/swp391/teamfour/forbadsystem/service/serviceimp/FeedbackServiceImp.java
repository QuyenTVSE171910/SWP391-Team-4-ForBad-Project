package com.swp391.teamfour.forbadsystem.service.serviceimp;

import com.swp391.teamfour.forbadsystem.dto.FeedbackRequest;
import com.swp391.teamfour.forbadsystem.dto.ReplyRequest;
import com.swp391.teamfour.forbadsystem.model.Court;
import com.swp391.teamfour.forbadsystem.model.Feedback;
import com.swp391.teamfour.forbadsystem.model.Reply;
import com.swp391.teamfour.forbadsystem.model.Yard;
import com.swp391.teamfour.forbadsystem.repository.CourtRepository;
import com.swp391.teamfour.forbadsystem.repository.FeedbackRepository;
import com.swp391.teamfour.forbadsystem.repository.YardRepository;
import com.swp391.teamfour.forbadsystem.service.FeedbackService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImp implements FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private YardRepository yardRepository;

    @Autowired
    private CourtRepository courtRepository;

    @Override
    public List<Feedback> getAllFeedbackOfYard(String yardId) {
        try {
            if (yardId == null) throw new RuntimeException("YardId trống.");
            Yard yard = yardRepository.findByYardId(yardId);

            if (yard == null) throw new RuntimeException("Không tồn tại sân này trong hệ thống.");

            return yard.getFeedbacks();
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public FeedbackRequest giveFeedback(FeedbackRequest feedbackRequest) {
        try {
            Feedback feedback = new Feedback();
            Yard yard = yardRepository.findById(feedbackRequest.getYardId())
                            .orElseThrow(() -> new RuntimeException("Sân này không tồn tại trong hệ thống."));

            feedback.setRate(feedbackRequest.getRate());
            feedback.setComment(feedbackRequest.getComment());
            feedback.setYard(yard);
            yard.getFeedbacks().add(feedback);
            feedbackRepository.save(feedback);

            Court court = yard.getCourt();

            // Tính rate trung bình mới cho Court
            double newRate = (court.getRate() == 0) ? feedback.getRate() : (court.getRate() + feedback.getRate()) / 2;

            // Cập nhật rate mới cho Court
            court.setRate(newRate);

            // Lưu Court sau khi cập nhật rate
            courtRepository.save(court);
            return FeedbackRequest.build(feedback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public FeedbackRequest updateFeedback(Feedback feedback) {
        try {
            Feedback existingFeedback = feedbackRepository.findById(feedback.getFeedbackId())
                    .orElseThrow(() ->new RuntimeException("Feedback này không tồn tại trong hệ thống."));

            existingFeedback.setRate(feedback.getRate());
            existingFeedback.setComment(feedback.getComment());
            feedbackRepository.save(existingFeedback);

            return FeedbackRequest.build(existingFeedback);
        } catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void deleteFeedback(Long feedbackId) {
        try {
            if (!feedbackRepository.existsById(feedbackId)) throw new RuntimeException("Feedback không tồn tại trong hệ thống.");
            feedbackRepository.deleteById(feedbackId);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
