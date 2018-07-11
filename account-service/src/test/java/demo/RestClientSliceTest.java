package demo;

import demo.user.User;
import demo.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@RunWith(SpringRunner.class)
@RestClientTest( {UserService.class} ) // UserService 테스트 대상
@AutoConfigureWebClient(registerRestTemplate=true)
public class RestClientSliceTest {
    @Value("${user-service.host:user-service}")
    private String serviceHost;

    @Autowired
    private UserService userService;

    @Autowired
    private MockRestServiceServer server;

    @Test
    public void getAuthenticatedUser() {
        String jsonBody = "{\"id\": 1, \"username\": \"name\", \"firstName\": \"first\", \"lastName\": \"last\"}";
        server.expect(requestTo("http://" + serviceHost + "/uaa/v1/me"))
                .andRespond(withSuccess(jsonBody, MediaType.APPLICATION_JSON));
        User user = userService.getAuthenticatedUser();
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getUsername()).isEqualTo("name");
    }
}
