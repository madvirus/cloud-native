package complaints.command;

import complaints.*;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.util.Assert;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Slf4j
@Aggregate
public class ComplaintAggregate {

    @AggregateIdentifier
    private String complaintId;
    private boolean closed;

    public ComplaintAggregate() {
    }

    @CommandHandler
    public ComplaintAggregate(FileComplaintCommand c) {
        log.info("handle FileComplaintCommand: {}", c);
        apply(new ComplaintFiledEvent(c.getId(), c.getCompany(), c.getDescription()));
    }

    @CommandHandler
    public void resolveComplaint(CloseComplaintCommand ccc) {
        log.info("handle CloseComplaintCommand: {}", ccc);
        if (!this.closed) {
            apply(new ComplaintClosedEvent(complaintId));
        }
    }

    @CommandHandler
    public void addComment(AddCommentCommand c) {
        log.info("handle AddCommentCommand: {}", c);
        Assert.isTrue(!this.closed, "Closed Complaint");
        apply(new CommentAddedEvent(c.getComplaintId(), c.getCommentId(), c.getComment(), c.getUser(), c.getWhen()));
    }

    @EventSourcingHandler
    protected void on(ComplaintFiledEvent cfe) {
        log.info("handle Sourcing Event: ComplaintFiledEvent: {}", cfe);
        this.complaintId = cfe.getId();
        this.closed = false;
    }

    @EventSourcingHandler
    protected void on(ComplaintClosedEvent cfe) {
        log.info("handle Sourcing Event: ComplaintClosedEvent: {}", cfe);
        this.closed = true;
    }
}
