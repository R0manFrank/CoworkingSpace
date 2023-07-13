package ch.axa.rest.model;

import java.util.List;
import java.util.Optional;

import static ch.axa.rest.model.UserService.changes;

public class BookingService {

    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    public BookingService(UserRepository userRepository, BookingRepository bookingRepository) {
        this.userRepository = userRepository;
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getBookingRequests() {
        return bookingRepository.findByStatus("pending");
    }

    public String acceptBookingRequest(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            booking.setStatus("accepted");
            changes = changes + ("Accepted Booking with ID " + bookingId + "\n");
            return "accepted";
        }
        return null;
    }

    public String rejectBookingRequest(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            booking.setStatus("rejected");
            changes = changes + ("Rejected Booking with ID " + bookingId + "\n");
            return "accepted";
        }
        return null;

    }

    public int getBookingCount(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            int count = user.getBookings().size();
            return count;
        }
        return 0;
    }

    public List<Booking> getBookings(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            return user.getBookings();
        }
        return null;
    }

    public boolean cancelBooking(Long userId, Long bookingId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalUser.isPresent()){
            if (optionalBooking.isPresent()){
                User user = optionalUser.get();
                Booking booking = optionalBooking.get();
                user.getBookings().remove(booking);
                bookingRepository.delete(booking);
                changes = changes + ("Booking with ID " + bookingId + " was canceled by User " + user.getName() + " " + user.getLastname() + "\n");
                return true;
            }
        }
        return false;
    }
}
