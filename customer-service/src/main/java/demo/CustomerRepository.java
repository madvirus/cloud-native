package demo;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CustomerRepository {
    private AtomicLong ids = new AtomicLong(0);

    private Map<Long, Customer> data = new TreeMap<>();

    public Collection<Customer> findAll() {
        return data.values();
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(data.get(id));
    }

    public Customer save(Customer c) {
        long nextId = ids.incrementAndGet();
        c.setId(nextId);
        data.put(c.getId(), c);
        return c;
    }

    public void delete(Customer c) {
        data.remove(c.getId());
    }
}
