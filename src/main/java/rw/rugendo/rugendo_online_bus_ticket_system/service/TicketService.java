package rw.rugendo.rugendo_online_bus_ticket_system.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Booking;
import rw.rugendo.rugendo_online_bus_ticket_system.model.BookingStatus;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Ticket;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.BookingRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.TicketRepository;

@Service
public class TicketService {
    private TicketRepository repository;
    private BookingRepository bookingRepository;

    public TicketService(TicketRepository repository, BookingRepository bookingRepository) {
        this.repository = repository;
        this.bookingRepository = bookingRepository;
    }

    // E1
    public String save(UUID bookingID, Ticket ticket) {
        Optional<Booking> optBooking = bookingRepository.findById(bookingID);
        if (optBooking.isEmpty()) {
            return "The booking with that id does not exist";
        }
        Booking booking = optBooking.get();

        if (booking.getStatus() != BookingStatus.PAID) {
            return "Tickets are only for PAID bookings";
        }

        ticket.setBooking(booking);
        ticket.setIssuedDate(LocalDate.now());
        repository.save(ticket);
        return "Ticket saved successfully";
    }

    // get all
    public List<Ticket> getAll() {
        return repository.findAll();
    }

    // E2
    public List<Ticket> getByNationalID(String nationalID) {
        return repository.findByPassengerNationalID(nationalID);
    }

    // E3
    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Booking deleted successfully";
        }
        return "The ticket with that id does not exist";
    }
}
