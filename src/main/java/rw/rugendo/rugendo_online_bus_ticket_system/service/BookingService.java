package rw.rugendo.rugendo_online_bus_ticket_system.service;

import org.springframework.stereotype.Service;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Booking;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.BookingRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public String save(Booking booking) {
        repository.save(booking);
        return "Booking saved successfully";
    }

    public List<Booking> getAll() {
        return repository.findAll();
    }

    public Optional<Booking> getById(UUID id) {
        return repository.findById(id);
    }

    public Optional<Booking> update(UUID id, Booking booking) {
        return repository.findById(id).map(existing -> {
            booking.setId(id);
            return repository.save(booking);
        });
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Booking deleted successfully";
        }
        return "The booking with that id does not exist";
    }
}