package demo.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class AccountController {
    private AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts")
    public List<Account> accounts() {
        return accountService.getUserAccounts();
    }

    @GetMapping("/fake/accounts")
    public List<Account> fakeAccounts() {
        return Arrays.asList(new Account("이름", "계정번호"));
    }
}
