package rw.rugendo.rugendo_online_bus_ticket_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Passenger;
import rw.rugendo.rugendo_online_bus_ticket_system.service.PassengerService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService service;

    public PassengerController(PassengerService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Passenger passenger) {
        String message = service.save(passenger);

        if (message.equals("Passenger saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Passenger>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var passenger = service.getById(id);

        if (passenger.isPresent()) {
            return ResponseEntity.ok(passenger.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The passenger with that id does not exist");
        }
    }

    //B1
    @GetMapping("/by-last-name")
    public ResponseEntity<List<Passenger>> getByLastName(
        @RequestParam String lastName
    ) {
        return ResponseEntity.ok(service.getByLastName(lastName));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Passenger passenger) {

        var updated = service.update(id, passenger);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The passenger with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);

        if (message.equals("Passenger deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}