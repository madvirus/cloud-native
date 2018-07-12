package demo.account;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.Date;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String accountNumber;
    private boolean defaultAccount;

    private Date createAt;
    private OffsetDateTime lastModified;

    protected Account() {
    }

    public Account(String username, String accountNumber) {
        this.username = username;
        this.accountNumber = accountNumber;
        this.createAt = new Date();
        this.lastModified = OffsetDateTime.now();
    }

    public Account(Long id, String username, String accountNumber) {
        this.id = id;
        this.username = username;
        this.accountNumber = accountNumber;
        this.createAt = new Date();
        this.lastModified = OffsetDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public boolean isDefaultAccount() {
        return defaultAccount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public OffsetDateTime getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", defaultAccount=" + defaultAccount +
                ", createAt=" + createAt +
                ", lastModified=" + lastModified +
                '}';
    }
}
