package ch.axa.rest;

import ch.axa.rest.modelOld.TaskUser;
import ch.axa.rest.modelOld.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    // @Autowired
    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/signIn")
    @CrossOrigin("http://localhost:3000")
    public ResponseEntity<TokenResponse> signIn(@RequestBody TaskUser authenticationUser) {


        Optional<TaskUser> userOptional = userRepository.findUserByUsername(authenticationUser.getUsername());
        log.info(("user:"+authenticationUser.toString()));
        if (userOptional.isPresent()) {
            TaskUser user = userOptional.get();
            // logged in token is generated
            if (user.getPassword().equals(HashCode.generateHashCode(authenticationUser.getPassword()))) {

                String uuid = UUID.randomUUID().toString();
                user.setToken(uuid);
                log.info("User logged in: " + user);

                TokenResponse response = new TokenResponse(uuid);
//                return new ResponseEntity<>(response, HttpStatus.OK);

                return ResponseEntity
                        .status(HttpStatus.OK)
                        .header("Authorization", "Baerer "+uuid)
                        .body(response);

            }
        }
        return ResponseEntity.notFound().build();
    }


    // POJO to represent the response body
    @Data
    @AllArgsConstructor
    private static class TokenResponse {
        private String token;
    }
}
