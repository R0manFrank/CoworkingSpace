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
    public ResponseEntity<CoUser> registerUser(@RequestBody CoUser coUser) {
        if (coUser.getName() == null || coUser.getLastname() == null || coUser.getPassword() == null || coUser.getEmail() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(coUser);
        }
        CoUser createdCoUser = userService.registerUser(coUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCoUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody CoUser coUser) {
        if (userService.authenticateUser(coUser)) {
            String authToken = userService.generateAuthToken(coUser.getEmail());
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
    public ResponseEntity<CoUser> createMember(@RequestBody CoUser coUser) {
        CoUser createdMember = userService.createMember(coUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }

    @PutMapping("/admin/users/{userId}")
    public ResponseEntity<CoUser> updateMember(@PathVariable Long userId, @RequestBody CoUser coUser) {
        CoUser updatedMember = userService.updateMember(userId, coUser);
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
    public ResponseEntity<CoUser> updatePhoneNumber(@PathVariable Long userId, @RequestBody String phoneNumber) {
        CoUser updatedMember = userService.updatePhoneNumber(userId, phoneNumber);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}