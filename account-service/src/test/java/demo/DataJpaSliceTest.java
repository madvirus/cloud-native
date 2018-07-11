package demo;

import demo.account.Account;
import demo.account.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DataJpaSliceTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findOne() {
        Account entity = new Account("username", "account");
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.clear();

        Account sel = accountRepository.findById(entity.getId());
        assertThat(sel).isNotNull();

        logger.info("entity == sel = {}", (entity == sel));
        logger.info("findById({}): {}", entity.getId(), sel);
    }
}
