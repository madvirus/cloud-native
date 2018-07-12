package demo.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/uaa/v1")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/me")
    public ResponseEntity<User> me(Principal principal) {
        return Optional.ofNullable(userService.getUserByPrincipal(principal))
            .map(u -> ResponseEntity.ok(u))
            .orElse(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
}
