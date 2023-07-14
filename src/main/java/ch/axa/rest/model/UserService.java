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

    public CoUser registerUser(CoUser coUser) {
        coUser.setPassword(generateHashCode(coUser.getPassword()));
        if (userRepository.findAll().size() <= 1){
            coUser.setRole("admin");
        }
        else{
            coUser.setRole("member");
        }
        userRepository.save(coUser);
        changes = changes + ("Registered User " + coUser.getName() + " " + coUser.getLastname() + "\n");
        return coUser;
    }

    public boolean authenticateUser(CoUser coUser) {
        changes = changes + ("User " + coUser.getName() + " " + coUser.getLastname() + " logged in \n");
        return true;
    }

    public String generateAuthToken(String email) {
        return email;
    }

    public String getChanges() {
        return changes;
    }

    public CoUser createMember(CoUser coUser) {
        coUser.setPassword(generateHashCode("123456"));
        userRepository.save(coUser);
        changes = changes + ("Admin added User " + coUser.getName() + " " + coUser.getLastname() + "\n");
        return coUser;
    }

    public CoUser updateMember(Long userId, CoUser updatedCoUser) {
        Optional<CoUser> optionalUser =  userRepository.findById(userId);
        CoUser responseCoUser = new CoUser();
        if (optionalUser.isPresent()){
            CoUser coUser = optionalUser.get();
            CoUser dbCoUser = optionalUser.get();
            if (updatedCoUser.getName() != null){
                coUser.setName(updatedCoUser.getName());
            }
            if (updatedCoUser.getLastname() != null){
                coUser.setLastname(updatedCoUser.getLastname());
            }
            if (updatedCoUser.getEmail() != null){
                coUser.setEmail(updatedCoUser.getEmail());
            }
            if (updatedCoUser.getPhonenumber() != null){
                coUser.setPhonenumber(updatedCoUser.getPhonenumber());
            }
            responseCoUser = coUser;
        }
        changes = changes + ("Updated User with ID " + userId + "\n");
        return responseCoUser;
    }

    public boolean deleteMember(Long userId) {
        userRepository.deleteById(userId);
        changes = changes + ("Removed User with ID " + userId + "\n");
        return true;
    }

    public CoUser updatePhoneNumber(Long userId, String phoneNumber) {
        Optional<CoUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            CoUser coUser = optionalUser.get();
            coUser.setPhonenumber(phoneNumber);
            changes = changes + ("Updated phone number of User " + coUser.getName() + " " + coUser.getLastname() + "\n");
            return coUser;
        }
        else {
            return null;
        }
    }

}
