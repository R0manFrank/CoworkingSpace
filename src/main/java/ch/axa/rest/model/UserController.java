package ch.axa.rest.model;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User createdUser = userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        if (userService.authenticateUser(user)) {
            String authToken = userService.generateAuthToken(user.getEmail());
            return ResponseEntity.ok(authToken);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/admin/changes")
    public ResponseEntity<String> getChanges() {
        String changes = userService.getChanges();
        return ResponseEntity.ok(changes);
    }

    @PostMapping("/admin/users")
    public ResponseEntity<User> createMember(@RequestBody User user) {
        User createdMember = userService.createMember(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<User> updateMember(@PathVariable Long userId, @RequestBody User user) {
        User updatedMember = userService.updateMember(userId, user);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/users/{userId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long userId) {
        boolean deleted = userService.deleteMember(userId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}