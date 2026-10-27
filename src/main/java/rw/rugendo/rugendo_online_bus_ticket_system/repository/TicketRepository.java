package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    // E2
    @Query("""
            SELECT t FROM Ticket t
            JOIN booking b
            WHERE b.passenger.nationalId = :nationalID
                """)
    List<Ticket> findByPassengerNationalID(@Param("nationalID") String nationalID);

    // E3
    boolean existsByBookingId(UUID bookingId);
}
