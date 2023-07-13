package ch.axa.rest.modelOld;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TaskUser, Long> {
    Optional<TaskUser> findUserByUsername(String username);
    Optional<TaskUser> findUserByToken(String token);
}
