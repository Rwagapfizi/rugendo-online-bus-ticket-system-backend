package rw.rugendo.rugendo_online_bus_ticket_system.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Booking;
import rw.rugendo.rugendo_online_bus_ticket_system.model.BookingStatus;
import rw.rugendo.rugendo_online_bus_ticket_system.service.BookingService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    // D1
    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Booking booking) {
        String message = service.save(booking);

        if (message.equals("Booking date cannot be in the past")) {
            return ResponseEntity.badRequest().body(message);
        } else if (message.equals("Driver already has a trip on that date")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else if (message.equals("Booking saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.badRequest().body(message);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Booking>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var booking = service.getById(id);

        if (booking.isPresent()) {
            return ResponseEntity.ok(booking.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The booking with that id does not exist");
        }
    }

    // B2
    @GetMapping("/by-status")
    public ResponseEntity<List<Booking>> getByStatus(
            @RequestParam BookingStatus status) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    // B3
    @GetMapping("/between")
    public ResponseEntity<List<Booking>> getBetweenDates(
            @RequestParam String start,
            @RequestParam String end) {

        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        return ResponseEntity.ok(service.getBetweenTravelDates(startDate, endDate));
    }

    // D2
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> changeStatus(
            @PathVariable UUID id,
            @RequestParam BookingStatus status) {
        String result = service.updateStatus(id, status);

        if (result.equals("The booking with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.startsWith("Cannot change status from ")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    // F1
    @GetMapping("/stats/by-status")
    public List<Object[]> countByStatus() {
        return service.countByStatus();
    }

    // F2
    @PutMapping("/cancel-day")
    public String cancelBookingsByDoctorID(
            @RequestParam UUID driverID,
            @RequestParam String date) {

        LocalDate travelDate = LocalDate.parse(date);
        return service.cancelBookingsByDriverID(driverID, travelDate);
    }

    // Bonus 2
    @GetMapping("/page")
    public Page<Booking> getBookingsPaged(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sort) {

        String[] sortParts = sort.split(",");

        String property = sortParts[0];
        Sort.Direction direction = Sort.Direction.fromString(
                sortParts.length > 1 ? sortParts[1] : "asc");

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(direction, property));

        return service.getBookingsPaged(pageable);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Booking booking) {

        var updated = service.update(id, booking);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The booking with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);

        if (message.equals("Booking deleted successfully")) {
            return ResponseEntity.ok(message);
        } else if (message.equals("That booking has tickets, delete them first")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}