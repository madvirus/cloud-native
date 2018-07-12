package demo.account;

import org.springframework.data.repository.Repository;

import java.util.List;

public interface AccountRepository extends Repository<Account, Long> {
    List<Account> findAccountsByUsername(String username);

    Account findById(Long id);
}
