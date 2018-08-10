package complaints.query;

import complaints.CommentAddedEvent;
import complaints.ComplaintClosedEvent;
import complaints.ComplaintFiledEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class ComplaintEventProcessor {
    private final ComplaintQueryObjectRepository complaints;
    private final CommentQueryObjectRepository comments;

    public ComplaintEventProcessor(ComplaintQueryObjectRepository complaints, CommentQueryObjectRepository comments) {
        this.complaints = complaints;
        this.comments = comments;
    }

    @EventHandler
    public void on(ComplaintFiledEvent cfe) {
        log.info("EventHandler: ComplaintFiledEvent: {}", cfe);
        ComplaintQueryObject complaint = new ComplaintQueryObject(cfe.getId(),
                cfe.getComplaint(),
                cfe.getCompany(),
                false);
        complaints.save(complaint);
    }

    @EventHandler
    public void on(CommentAddedEvent cae) {
        log.info("EventHandler: CommentAddedEvent: {}", cae);
        CommentQueryObject comment = new CommentQueryObject(cae.getComplaintId(), cae.getCommentId(), cae.getComment(), cae.getUser(), cae.getWhen());
        comments.save(comment);
    }

    @EventHandler
    public void on(ComplaintClosedEvent cce) {
        log.info("EventHandler: ComplaintClosedEvent: {}", cce);
        Optional<ComplaintQueryObject> complaintOpt = complaints.findById(cce.getComplaintId());
        complaintOpt.ifPresent(complaint -> {
            log.info("close complaint: {}", complaint.getId());
            complaint.setClosed(true);
            complaints.save(complaint);
        });
    }
}
