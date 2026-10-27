package rw.rugendo.rugendo_online_bus_ticket_system.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Driver;
import rw.rugendo.rugendo_online_bus_ticket_system.service.DriverService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService service;

    public DriverController(DriverService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Driver driver) {
        String message = service.save(driver);

        if (message.equals("Driver saved successfully")) {
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Driver>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        var driver = service.getById(id);

        if (driver.isPresent()) {
            return ResponseEntity.ok(driver.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The driver with that id does not exist");
        }
    }

    // C1
    @GetMapping("/by-route")
    public ResponseEntity<List<Driver>> getByRoute(
            @RequestParam String name) {
        return ResponseEntity.ok(service.getByRoute(name));
    }

    // D3
    @PutMapping("/{driverID}/routes")
    public ResponseEntity<String> addRoute(
            @PathVariable UUID driverID,
            @RequestParam UUID routeID) {
        String result = service.addRoute(driverID, routeID);

        if (result.equals("The driver with that id does not exist")
                || result.equals("That route does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("The driver already has that route")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    // D4
    @PatchMapping("/{driverID}/bus")
    public ResponseEntity<String> moveBus(
            @PathVariable UUID driverID,
            @RequestParam int busNumber) {
        String result = service.moveBus(driverID, busNumber);

        if (result.equals("The driver with that id does not exist")
                || result.equals("The bus with that busNumber does not exist")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        } else if (result.equals("That bus is taken by another driver")
                || result.equals("The driver already has this bus")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable UUID id,
            @RequestBody Driver driver) {

        var updated = service.update(id, driver);

        if (updated.isPresent()) {
            return ResponseEntity.ok(updated.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("The driver with that id does not exist");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable UUID id) {
        String message = service.delete(id);

        if (message.equals("Driver deleted successfully")) {
            return ResponseEntity.ok(message);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
        }
    }
}