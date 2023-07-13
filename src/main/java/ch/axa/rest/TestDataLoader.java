package ch.axa.rest;

import ch.axa.rest.model.Booking;
import ch.axa.rest.model.BookingRepository;
import ch.axa.rest.model.User;
import ch.axa.rest.model.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestDataLoader implements ApplicationRunner {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public TestDataLoader(UserRepository userRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        loadTestData();
    }

    private void loadTestData() {
        // Erstelle User-Objekte mit Testdaten
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
    }
}