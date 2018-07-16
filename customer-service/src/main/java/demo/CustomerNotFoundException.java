package demo;

public class CustomerNotFoundException extends RuntimeException {
    private Long id;

    public CustomerNotFoundException(Long id) {
        super(String.format("Customer[%s] not found", id));
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
