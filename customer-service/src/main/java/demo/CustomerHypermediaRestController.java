package demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/v2", produces = "application/hal+json")
public class CustomerHypermediaRestController {
    private CustomerRepository customerRepository;
    private CustomerResourceAssembler customerResourceAssembler;

    @Autowired
    public CustomerHypermediaRestController(
            CustomerRepository customerRepository,
            CustomerResourceAssembler cra) {
        this.customerRepository = customerRepository;
        this.customerResourceAssembler = cra;
    }

    @GetMapping
    ResponseEntity<Resources<Object>> root() {
        Resources<Object> objects = new Resources<>(Collections.emptyList());

        URI uri = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(getClass()).getCollection())
                .build().toUri();

        Link link = new Link(uri.toString(), "customers");
        objects.add(link);

        return ResponseEntity.ok(objects);
    }

    @RequestMapping(value = "/customers", method = RequestMethod.OPTIONS)
    public ResponseEntity<?> options() {
        return ResponseEntity.ok()
                .allow(HttpMethod.GET, HttpMethod.POST, HttpMethod.HEAD, HttpMethod.OPTIONS, HttpMethod.PUT, HttpMethod.DELETE)
                .build();
    }

    @GetMapping("/customers")
    ResponseEntity<Resources<Resource<Customer>>> getCollection() {
        List<Resource<Customer>> collect = customerRepository.findAll().stream()
                .map(customerResourceAssembler::toResource)
                .collect(Collectors.toList());

        Resources<Resource<Customer>> resources = new Resources<>(collect);
        URI self = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        resources.add(new Link(self.toString(), "self"));
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/customers/{id}")
    ResponseEntity<Resource<Customer>> get(@PathVariable("id") Long id) {
        return customerRepository.findById(id)
                .map(c -> ResponseEntity.ok(customerResourceAssembler.toResource(c)))
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @PostMapping
    ResponseEntity<Resource<Customer>> post(@RequestBody Customer c) {
        Customer customer = customerRepository.save(new Customer(c.getFirstName(), c.getLastName()));
        URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/customers/{id}")
                .buildAndExpand(customer.getId()).toUri();
        return ResponseEntity.created(uri)
                .body(customerResourceAssembler.toResource(customer));
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
    ResponseEntity<Resource<Customer>> put(@PathVariable("id") Long id, @RequestBody Customer c) {
        return customerRepository.findById(id)
                .map(existing -> {
                    Customer customer = customerRepository.save(new Customer(existing.getId(), existing.getFirstName(), existing.getLastName()));
                    URI selfUri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfUri).body(customerResourceAssembler.toResource(customer));
                })
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

}
