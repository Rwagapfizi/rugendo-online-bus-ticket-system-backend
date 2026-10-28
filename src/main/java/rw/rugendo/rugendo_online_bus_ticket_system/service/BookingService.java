package rw.rugendo.rugendo_online_bus_ticket_system.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Booking;
import rw.rugendo.rugendo_online_bus_ticket_system.model.BookingStatus;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.BookingRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.TicketRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    private final BookingRepository repository;
    private final TicketRepository ticketRepository;

    public BookingService(BookingRepository repository, TicketRepository ticketRepository) {
        this.repository = repository;
        this.ticketRepository = ticketRepository;
    }

    // D1
    public String save(Booking booking) {
        if (booking.getTravelDate().isBefore(LocalDate.now())) {
            return "Booking date cannot be in the past";
        }

        if (repository
                .existsByDriverIdAndTravelDateAndStatusNot(
                        booking.getDriver().getId(),
                        booking.getTravelDate(),
                        BookingStatus.CANCELLED)) {
            return "Driver already has a trip on that date";
        }

        booking.setStatus(BookingStatus.REQUESTED);
        repository.save(booking);
        return "Booking saved successfully";
    }

    // D2
    public String updateStatus(
            UUID id,
            BookingStatus newStatus) {
        Optional<Booking> optBooking = repository.findById(id);
        if (optBooking.isEmpty()) {
            return "The booking with that id does not exist";
        }

        Booking booking = optBooking.get();
        BookingStatus currentStatus = booking.getStatus();
        boolean allowed = false;

        if (currentStatus == BookingStatus.REQUESTED) {
            if (newStatus == BookingStatus.CANCELLED || newStatus == BookingStatus.RESERVED) {
                allowed = true;
            }
        } else if (currentStatus == BookingStatus.RESERVED) {
            if (newStatus == BookingStatus.PAID || newStatus == BookingStatus.CANCELLED) {
                allowed = true;
            }
        } else {
            allowed = false;
        }

        if (!allowed) {
            return "Cannot change status from " + currentStatus + " to " + newStatus;
        }

        booking.setStatus(newStatus);
        repository.save(booking);
        return "Booking status updated successfully";
    }

    public List<Booking> getAll() {
        return repository.findAll();
    }

    public Optional<Booking> getById(UUID id) {
        return repository.findById(id);
    }

    // B2
    public List<Booking> getByStatus(BookingStatus status) {
        return repository.findByStatusOrderByTravelDateAsc(status);
    }

    // B3
    public List<Booking> getBetweenTravelDates(LocalDate start, LocalDate end) {
        return repository.findByTravelDateBetween(start, end);
    }

    // F1
    public List<Object[]> countByStatus() {
        return repository.countPerStatus();
    }

    // F2
    public String cancelBookingsByDriverID(UUID driverID, LocalDate travelDate) {
        int cancelledBookings = repository.cancelBookingsByDriverID(
                driverID,
                travelDate,
                BookingStatus.CANCELLED,
                BookingStatus.PAID);

        return cancelledBookings + " bookings cancelled";
    }

    // Bonus 2
    public Page<Booking> getBookingsPaged(Pageable pageable){
        return repository.findAll(pageable);
    }

    public Optional<Booking> update(UUID id, Booking booking) {
        return repository.findById(id).map(existing -> {
            booking.setId(id);

            existing.setTravelDate(booking.getTravelDate());
            existing.setDestination(booking.getDestination());
            existing.setSeatNumber(booking.getSeatNumber());
            existing.setPassenger(booking.getPassenger());
            existing.setDriver(booking.getDriver());

            return repository.save(booking);
        });
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            if (ticketRepository.existsByBookingId(id)) {
                return "That booking has tickets, delete them first";
            }

            repository.deleteById(id);
            return "Booking deleted successfully";
        }
        return "The booking with that id does not exist";
    }
}