package rw.rugendo.rugendo_online_bus_ticket_system.service;

import org.springframework.stereotype.Service;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Driver;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.DriverRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {

    private final DriverRepository repository;

    public DriverService(DriverRepository repository) {
        this.repository = repository;
    }

    public String save(Driver driver) {
        repository.save(driver);
        return "Driver saved successfully";
    }

    public List<Driver> getAll() {
        return repository.findAll();
    }

    public Optional<Driver> getById(UUID id) {
        return repository.findById(id);
    }

    //C1
    public List<Driver> getByRoute(String route) {
        return repository.findByRoute(route);
    }

    public Optional<Driver> update(UUID id, Driver driver) {
        return repository.findById(id).map(existing -> {
            driver.setId(id);
            return repository.save(driver);
        });
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Driver deleted successfully";
        }
        return "The driver with that id does not exist";
    }
}