package complaints;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.TargetAggregateIdentifier;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCommentCommand {
    @TargetAggregateIdentifier
    private String complaintId;
    private String commentId;
    private String comment;
    private String user;
    private Date when;
}
