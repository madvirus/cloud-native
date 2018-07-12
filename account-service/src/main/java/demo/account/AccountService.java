package demo.account;

import demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserService userService;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public List<Account> getUserAccounts() {
        return Optional.ofNullable(userService.getAuthenticatedUser())
                .map(u -> accountRepository.findAccountsByUsername(u.getUsername()))
                .orElse(Collections.emptyList());
    }
}
