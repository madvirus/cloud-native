package complaints.query;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "complaint")
public class ComplaintQueryObject {

    @Id
    private String id;
    private String complaint;
    private String company;
    private boolean closed;

    public ComplaintQueryObject(String id, String complaint, String company, boolean closed) {
        this.id = id;
        this.complaint = complaint;
        this.company = company;
        this.closed = closed;
    }

    protected ComplaintQueryObject() {

    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public String getId() {
        return id;
    }

    public String getComplaint() {
        return complaint;
    }

    public String getCompany() {
        return company;
    }

    public boolean isClosed() {
        return closed;
    }
}
