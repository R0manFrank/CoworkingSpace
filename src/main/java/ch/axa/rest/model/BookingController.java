package ch.axa.rest.model;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/admin/bookings")
    public ResponseEntity<String> getBookingRequests() {
        String bookingRequests = bookingService.getBookingRequests();
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

    @PutMapping("/members/{userId}/phone")
    public ResponseEntity<User> updatePhoneNumber(@PathVariable Long userId, @RequestBody String phoneNumber) {
        User updatedMember = bookingService.updatePhoneNumber(userId, phoneNumber);
        if (updatedMember != null) {
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/members/{userId}/bookings/count")
    public ResponseEntity<Integer> getBookingCount(@PathVariable Long userId) {
        int bookingCount = bookingService.getBookingCount(userId);
        return ResponseEntity.ok(bookingCount);
    }

    @GetMapping("/members/{userId}/bookings")
    public ResponseEntity<String> getBookings(@PathVariable Long userId) {
        String bookings = bookingService.getBookings(userId);
        return ResponseEntity.ok(bookings);
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
}
