package rw.rugendo.rugendo_online_bus_ticket_system.service;

import org.springframework.stereotype.Service;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Passenger;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.PassengerRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PassengerService {

    private final PassengerRepository repository;

    public PassengerService(PassengerRepository repository) {
        this.repository = repository;
    }

    public String save(Passenger passenger) {
        repository.save(passenger);
        return "Passenger saved successfully";
    }

    public List<Passenger> getAll() {
        return repository.findAll();
    }

    public Optional<Passenger> getById(UUID id) {
        return repository.findById(id);
    }

    public Optional<Passenger> update(UUID id, Passenger passenger) {
        return repository.findById(id).map(existing -> {
            passenger.setId(id);
            return repository.save(passenger);
        });
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Passenger deleted successfully";
        }
        return "The passenger with that id does not exist";
    }
}