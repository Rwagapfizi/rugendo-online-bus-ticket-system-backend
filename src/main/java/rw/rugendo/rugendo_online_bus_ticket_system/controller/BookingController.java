package rw.rugendo.rugendo_online_bus_ticket_system.controller;

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

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Booking booking) {
        String message = service.save(booking);

        if (message.equals("Booking saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
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

    //B2
    @GetMapping("/by-status")
    public ResponseEntity<List<Booking>> getByStatus(
        @RequestParam BookingStatus status
    ) {
        return ResponseEntity.ok(service.getByStatus(status));
    }

    //B3
    @GetMapping("/between")
    public ResponseEntity<List<Booking>> getBetweenDates(
        @RequestParam LocalDate start,
        @RequestParam LocalDate end
    ) {
        return ResponseEntity.ok(service.getBetweenTravelDates(start, end));
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
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}