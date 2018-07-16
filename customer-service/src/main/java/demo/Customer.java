package demo;

public class Customer {
    private Long id;
    private String firatName;
    private String lastName;

    public Customer(Long id, String firatName, String lastName) {
        this.id = id;
        this.firatName = firatName;
        this.lastName = lastName;
    }

    public Customer(String firatName, String lastName) {
        this.firatName = firatName;
        this.lastName = lastName;
    }

    void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getFiratName() {
        return firatName;
    }

    public String getLastName() {
        return lastName;
    }
}
