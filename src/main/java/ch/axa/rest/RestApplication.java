package ch.axa.rest;

import ch.axa.rest.model.Booking;
import ch.axa.rest.model.BookingRepository;
import ch.axa.rest.model.CoUser;
import ch.axa.rest.model.UserRepository;
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
            CoUser coUser1 = new CoUser();
            coUser1.setName("John");
            coUser1.setLastname("Doe");
            coUser1.setEmail("john.doe@example.com");
            coUser1.setPhonenumber("1234567890");
            coUser1.setPassword("password");
            coUser1.setRole("member");

            CoUser coUser2 = new CoUser();
            coUser2.setName("Jane");
            coUser2.setLastname("Smith");
            coUser2.setEmail("jane.smith@example.com");
            coUser2.setPhonenumber("0987654321");
            coUser2.setPassword("password");
            coUser2.setRole("member");

            userRepository.save(coUser1);
            userRepository.save(coUser2);

            Booking booking1 = new Booking();
            booking1.setDate(LocalDate.now());
            booking1.setPartOfDay("day");
            booking1.setStatus("pending");
            booking1.setCoUser(coUser1);

            Booking booking2 = new Booking();
            booking2.setDate(LocalDate.now());
            booking2.setPartOfDay("morning");
            booking2.setStatus("accepted");
            booking2.setCoUser(coUser2);

            bookingRepository.save(booking1);
            bookingRepository.save(booking2);

        };
    }


}
