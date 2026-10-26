package rw.rugendo.rugendo_online_bus_ticket_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Bus;
import rw.rugendo.rugendo_online_bus_ticket_system.service.BusService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    private final BusService service;

    public BusController(BusService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Bus bus) {
        String message = service.save(bus);

        if (message.equals("Bus saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Bus>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var bus = service.getById(id);

        if (bus.isPresent()) {
            return ResponseEntity.ok(bus.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The bus with that id does not exist");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Bus bus) {

        var updated = service.update(id, bus);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The bus with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);

        if (message.equals("Bus deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}