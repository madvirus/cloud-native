package complaints;

import lombok.Data;

@Data
public class ComplaintFiledEvent {
    private String id;
    private String company;
    private String complaint;
}
