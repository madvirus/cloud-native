package demo;

import demo.user.UserController;
import demo.user.UserService;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;

public class UserServiceBase {

    @Before
    public void setUp() {
        RestAssuredMockMvc.standaloneSetup(new UserController(new UserService()));
    }
}
