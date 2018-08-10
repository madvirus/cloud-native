package complaints.query;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "complaint_comment")
public class CommentQueryObject {
    @Id
    private String commentId;
    private String complaintId;
    @Column(name = "comment_message")
    private String comment;
    private String user;
    @Column(name = "when_date")
    private Date when;

    protected CommentQueryObject() {
    }

    public CommentQueryObject(String complaintId, String commentId, String comment, String user, Date when) {
        this.complaintId = complaintId;
        this.commentId = commentId;
        this.comment = comment;
        this.user = user;
        this.when = when;
    }
}
