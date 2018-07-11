package demo;

import demo.account.Account;
import demo.account.AccountController;
import demo.account.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class WebMvcSliceTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AccountService service;

    @Test
    public void name() throws Exception {
        given(service.getUserAccounts()).willReturn(Arrays.asList(new Account(1L, "이름", "계좌")));

        mvc.perform(get("/fake/accounts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("이름"));

    }
}
