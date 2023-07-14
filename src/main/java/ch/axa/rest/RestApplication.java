package ch.axa.rest;

import ch.axa.rest.model.Booking;
import ch.axa.rest.model.BookingRepository;
import ch.axa.rest.model.User;
import ch.axa.rest.model.UserRepository;
import ch.axa.rest.security.HashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class RestApplication {
    private static final Logger log = LoggerFactory.getLogger(RestApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }


    @Bean
    public CommandLineRunner init(UserRepository userRepository, BookingRepository bookingRepository) {
        return (args) -> {
            User user1 = new User();
            user1.setName("John");
            user1.setLastname("Doe");
            user1.setEmail("john.doe@example.com");
            user1.setPhonenumber("1234567890");
            user1.setPassword("password");
            user1.setRole("member");

            User user2 = new User();
            user2.setName("Jane");
            user2.setLastname("Smith");
            user2.setEmail("jane.smith@example.com");
            user2.setPhonenumber("0987654321");
            user2.setPassword("password");
            user2.setRole("member");

            userRepository.save(user1);
            userRepository.save(user2);

            Booking booking1 = new Booking();
            booking1.setDate(LocalDate.now());
            booking1.setPartOfDay("day");
            booking1.setStatus("pending");
            booking1.setUser(user1);

            Booking booking2 = new Booking();
            booking2.setDate(LocalDate.now());
            booking2.setPartOfDay("morning");
            booking2.setStatus("accepted");
            booking2.setUser(user2);

            bookingRepository.save(booking1);
            bookingRepository.save(booking2);

        };
    }


}
