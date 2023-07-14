package ch.axa.rest.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

    @Autowired
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/admin/bookings")
    public ResponseEntity<List<Booking>> getBookingRequests() {
        List<Booking> bookingRequests = bookingService.getBookingRequests();
        return ResponseEntity.ok(bookingRequests);
    }

    @PutMapping("/admin/bookings/{bookingId}/accept")
    public ResponseEntity<String> acceptBookingRequest(@PathVariable Long bookingId) {
        String acceptedBooking = bookingService.acceptBookingRequest(bookingId);
        if (acceptedBooking != null) {
            return ResponseEntity.ok(acceptedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/admin/bookings/{bookingId}/reject")
    public ResponseEntity<String> rejectBookingRequest(@PathVariable Long bookingId) {
        String rejectedBooking = bookingService.rejectBookingRequest(bookingId);
        if (rejectedBooking != null) {
            return ResponseEntity.ok(rejectedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/members/{userId}/bookings/count")
    public ResponseEntity<Integer> getBookingCount(@PathVariable Long userId) {
        Integer bookingCount = bookingService.getBookingCount(userId);
        if (bookingCount != null){
            return ResponseEntity.ok(bookingCount);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/members/{userId}/bookings")
    public ResponseEntity<List<Booking>> getBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookings(userId);
        if (bookings != null){
            return ResponseEntity.status(HttpStatus.OK).body(bookings);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @DeleteMapping("/members/{userId}/bookings/{bookingId}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long userId, @PathVariable Long bookingId) {
        boolean canceled = bookingService.cancelBooking(userId, bookingId);
        if (canceled) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/members/{userId}/bookings/create")
    public ResponseEntity<Booking> requestBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.requestBooking(booking);
        if (createdBooking != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/admin/bookings")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking createdBooking = bookingService.createBooking(booking);
        if (createdBooking != null){
            return ResponseEntity.status(HttpStatus.CREATED).body(createdBooking);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PutMapping("/admin/bookings/{bookingId}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long bookingId, @RequestBody Booking booking) {
        Booking updatedBooking = bookingService.updateBooking(bookingId, booking);
        if (updatedBooking != null) {
            return ResponseEntity.ok(updatedBooking);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/admin/bookings/{bookingId}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long bookingId) {
        boolean deleted = bookingService.deleteBooking(bookingId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
