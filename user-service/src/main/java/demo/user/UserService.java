package demo.user;

import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public User getUserByPrincipal(Principal principal) {
        if (principal != null && principal.getName().startsWith("user"))
            return new User(1L, principal.getName(), "first", "last");
        else
            return null;
    }
}
