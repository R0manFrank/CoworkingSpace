package ch.axa.rest.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        if (user.getName() == null || user.getLastname() == null || user.getPassword() == null || user.getEmail() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
        }
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
        return ResponseEntity.status(HttpStatus.OK).body(changes);
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

    @PutMapping("/members/{userId}/phone")
    public ResponseEntity<User> updatePhoneNumber(@PathVariable Long userId, @RequestBody String phoneNumber) {
        User updatedMember = userService.updatePhoneNumber(userId, phoneNumber);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}