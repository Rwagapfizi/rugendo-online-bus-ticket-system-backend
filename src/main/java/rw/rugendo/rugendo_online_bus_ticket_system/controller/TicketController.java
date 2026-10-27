package rw.rugendo.rugendo_online_bus_ticket_system.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Ticket;
import rw.rugendo.rugendo_online_bus_ticket_system.service.TicketService;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService service;

    public TicketController(TicketService service) {
        this.service = service;
    }

    // E1
    @PostMapping("/save")
    public ResponseEntity<String> saveTicket(
            @RequestParam UUID bookingID,
            @RequestBody Ticket ticket) {
        String result = service.save(bookingID, ticket);

        if (result.equals("The booking with that id does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("Tickets are only for PAID bookings")) {
            return ResponseEntity.badRequest().body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    // get all
    @GetMapping("/all")
    public List<Ticket> getAll() {
        return service.getAll();
    }

    // E2
    @GetMapping("/passenger")
    public List<Ticket> getByNationalID(
            @RequestParam String nationalID) {
        return service.getByNationalID(nationalID);
    }

    // E3
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
