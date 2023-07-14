package ch.axa.rest.model;

import org.springframework.stereotype.Service;

import java.util.Optional;

import static ch.axa.rest.security.HashCode.generateHashCode;

@Service
public class UserService {

    private final UserRepository userRepository;

    public static String changes;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        changes = "";
    }

    public User registerUser(User user) {
        user.setPassword(generateHashCode(user.getPassword()));
        if (userRepository.findAll().size() <= 1){
            user.setRole("admin");
        }
        else{
            user.setRole("member");
        }
        userRepository.save(user);
        changes = changes + ("Registered User " + user.getName() + " " + user.getLastname() + "\n");
        return user;
    }

    public boolean authenticateUser(User user) {
        changes = changes + ("User " + user.getName() + " " + user.getLastname() + " logged in \n");
        return true;
    }

    public String generateAuthToken(String email) {
        return email;
    }

    public String getChanges() {
        return changes;
    }

    public User createMember(User user) {
        user.setPassword(generateHashCode("123456"));
        userRepository.save(user);
        changes = changes + ("Admin added User " + user.getName() + " " + user.getLastname() + "\n");
        return user;
    }

    public User updateMember(Long userId, User updatedUser) {
        Optional<User> optionalUser =  userRepository.findById(userId);
        User responseUser = new User();
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            User dbUser = optionalUser.get();
            if (updatedUser.getName() != null){
                user.setName(updatedUser.getName());
            }
            if (updatedUser.getLastname() != null){
                user.setLastname(updatedUser.getLastname());
            }
            if (updatedUser.getEmail() != null){
                user.setEmail(updatedUser.getEmail());
            }
            if (updatedUser.getPhonenumber() != null){
                user.setPhonenumber(updatedUser.getPhonenumber());
            }
            responseUser = user;
        }
        changes = changes + ("Updated User with ID " + userId + "\n");
        return responseUser;
    }

    public boolean deleteMember(Long userId) {
        userRepository.deleteById(userId);
        changes = changes + ("Removed User with ID " + userId + "\n");
        return true;
    }

    public User updatePhoneNumber(Long userId, String phoneNumber) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            user.setPhonenumber(phoneNumber);
            changes = changes + ("Updated phone number of User " + user.getName() + " " + user.getLastname() + "\n");
            return user;
        }
        else {
            return null;
        }
    }

}
