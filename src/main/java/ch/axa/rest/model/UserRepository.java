package ch.axa.rest.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<CoUser, Long> {

    public Optional<CoUser> findByName(String name);
}
