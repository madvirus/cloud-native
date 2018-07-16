package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CustomerInit {
    private CustomerRepository repo;

    @Autowired
    public CustomerInit(CustomerRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    public void init() {
        repo.save(new Customer("범균", "최"));
        repo.save(new Customer("마이", "오"));
    }
}
