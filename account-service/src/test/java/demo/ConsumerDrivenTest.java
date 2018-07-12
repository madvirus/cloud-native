package demo;

import demo.user.User;
import demo.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@TestPropertySource(properties = "user-service.host=localhost")
@AutoConfigureStubRunner(ids = {"user-service:user-service:+:stubs:8888"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class ConsumerDrivenTest {

    @Autowired
    private UserService userService;

    @Test
    public void getAuthenticatedUser() {
        User user = userService.getAuthenticatedUser();
        assertThat(user.getId()).isNotNegative();
        assertThat(user.getUsername()).matches("[A-Za-z0-9]+");
    }
}
