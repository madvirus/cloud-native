package demo;

import com.fasterxml.jackson.annotation.JsonFormat;
import demo.account.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebRandomPortTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void accountsApi() throws InterruptedException {
        ParameterizedTypeReference<List<AccountData>> type = new ParameterizedTypeReference<List<AccountData>>() {};
        ResponseEntity<List<AccountData>> resp = restTemplate.exchange("/fake/accounts", HttpMethod.GET, null, type);
        assertThat(resp.getBody()).hasSize(1);

        logger.info("first Account : {}", resp.getBody().get(0));
        logger.info("local server port = {}", port);

        //sleep(60000);
    }

    public static class AccountData {
        private Long id;

        private String username;
        private String accountNumber;
        private boolean defaultAccount;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private Date createAt;
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
        private OffsetDateTime lastModified;

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
            return "AccountData{" +
                    "id=" + id +
                    ", username='" + username + '\'' +
                    ", accountNumber='" + accountNumber + '\'' +
                    ", defaultAccount=" + defaultAccount +
                    ", createAt=" + createAt +
                    ", lastModified=" + lastModified +
                    '}';
        }
    }
}
