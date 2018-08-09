package complaints;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CommentAddedEvent {
    private final String complaintId;
    private final String commentId;
    private final String comment;
    private final String user;
    private final Date when;

}
