package demo.account;

import demo.user.User;
import demo.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class AccountServiceTest {
    @MockBean
    private UserService userService;
    @MockBean
    private AccountRepository accountRepository;

    private AccountService accountService;

    @Before
    public void before() {
        accountService = new AccountService(accountRepository, userService);
    }

    @Test
    public void getUserAccountsReturnsSingleAccount() {
        given(accountRepository.findAccountsByUsername("user"))
                .willReturn(Collections.singletonList(new Account("user", "123456789")));

        given(userService.getAuthenticatedUser()).willReturn(new User(0L, "user", "first", "last"));

        List<Account> actual = accountService.getUserAccounts();
        assertThat(actual).hasSize(1);
    }
}
