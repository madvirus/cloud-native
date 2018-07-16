package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/v1/customers")
public class CustomerRestController {
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerRestController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @RequestMapping(method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.HEAD, HttpMethod.OPTIONS, HttpMethod.PUT, HttpMethod.DELETE)
                .build();
    }

    @GetMapping
    ResponseEntity<Collection<Customer>> getCollection() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/{id}")
    ResponseEntity<Customer> get(@PathVariable("id") Long id) {
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<Customer> post(@RequestBody Customer c) {
        Customer customer = customerRepository.save(new Customer(c.getFiratName(), c.getLastName()));
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri).body(customer);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return customerRepository.findById(id)
                .map(c -> {
                    customerRepository.delete(c);
                    return ResponseEntity.noContent().build();
                })
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    ResponseEntity<?> head(@PathVariable("id") Long id) {
        return customerRepository.findById(id)
                .map(c -> ResponseEntity.noContent().build())
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> put(@PathVariable("id") Long id, @RequestBody Customer c) {
        return customerRepository.findById(id)
                .map(existing -> {
                    Customer customer = customerRepository.save(new Customer(existing.getId(), existing.getFiratName(), existing.getLastName()));
                    URI selfUri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfUri).body(customer);
                })
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

}
