package ch.axa.rest.model;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static ch.axa.rest.model.UserService.changes;

@Service
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

    public Integer getBookingCount(Long userId) {
        Optional<CoUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            CoUser coUser = optionalUser.get();
            int count = coUser.getBookings().size();
            return count;
        }
        return null;
    }

    public List<Booking> getBookings(Long userId) {
        Optional<CoUser> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()){
            CoUser coUser = optionalUser.get();
            return coUser.getBookings();
        }
        return null;
    }

    public boolean cancelBooking(Long userId, Long bookingId) {
        Optional<CoUser> optionalUser = userRepository.findById(userId);
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalUser.isPresent()){
            if (optionalBooking.isPresent()){
                CoUser coUser = optionalUser.get();
                Booking booking = optionalBooking.get();
                coUser.getBookings().remove(booking);
                bookingRepository.delete(booking);
                changes = changes + ("Booking with ID " + bookingId + " was canceled by User " + coUser.getName() + " " + coUser.getLastname() + "\n");
                return true;
            }
        }
        return false;
    }

    public Booking requestBooking(Booking booking) {
        if (booking.getDate() == null || booking.getPartOfDay() == null){
            return null;
        }
        booking.setStatus("pending");
        bookingRepository.save(booking);
        changes = changes + ("Booking with ID " + booking.getId() + " was added for request \n");
        return booking;
    }

    public Booking createBooking(Booking booking) {
        if (booking.getDate() == null || booking.getPartOfDay() == null){
            return null;
        }
        bookingRepository.save(booking);
        changes = changes + ("Admin created Booking with ID " + booking.getId() + "\n");
        return booking;
    }

    public Booking updateBooking(Long bookingId, Booking booking) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()){
            Booking dbBooking = optionalBooking.get();
            if (booking.getDate() != null){
                dbBooking.setDate(booking.getDate());
            }
            if (booking.getPartOfDay() != null){
                dbBooking.setPartOfDay(booking.getPartOfDay());
            }
            if (booking.getCoUser() != null){
                dbBooking.setCoUser(booking.getCoUser());
            }
            changes = changes + ("Admin edited Booking with ID " + bookingId + "\n");
            return dbBooking;
        }
        return null;
    }

    public boolean deleteBooking(Long bookingId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);
        if (optionalBooking.isPresent()){
            Booking booking = optionalBooking.get();
            bookingRepository.delete(booking);
            changes = changes + ("Admin deleted Booking with ID " + bookingId + "\n");
            return true;
        }
        return false;

    }
}
