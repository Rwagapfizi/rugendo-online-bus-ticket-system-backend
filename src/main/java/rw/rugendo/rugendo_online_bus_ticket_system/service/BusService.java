package rw.rugendo.rugendo_online_bus_ticket_system.service;

import org.springframework.stereotype.Service;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Bus;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.BusRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BusService {

    private final BusRepository repository;

    public BusService(BusRepository repository) {
        this.repository = repository;
    }

    public String save(Bus bus) {
        repository.save(bus);
        return "Bus saved successfully";
    }

    public List<Bus> getAll() {
        return repository.findAll();
    }

    public Optional<Bus> getById(UUID id) {
        return repository.findById(id);
    }

    public Optional<Bus> update(UUID id, Bus bus) {
        return repository.findById(id).map(existing -> {
            bus.setId(id);
            return repository.save(bus);
        });
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Bus deleted successfully";
        }
        return "The bus with that id does not exist";
    }
}