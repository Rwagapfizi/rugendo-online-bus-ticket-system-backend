package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Booking;
import rw.rugendo.rugendo_online_bus_ticket_system.model.BookingStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    // B2
    List<Booking> findByStatusOrderByTravelDateAsc(BookingStatus status);

    // B3
    List<Booking> findByTravelDateBetween(LocalDate start, LocalDate end);

    // D1
    boolean existsByDriverIdAndTravelDateAndStatusNot(UUID driverID, LocalDate travelDate, BookingStatus status);
}
