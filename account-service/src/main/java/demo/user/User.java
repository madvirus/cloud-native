package demo.user;

public class User {
    private Long id;
    private String username;
    private String firstName;
    private String lastName;

    public User() {
    }

    public User(Long id, String username, String firstName, String lastName) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
