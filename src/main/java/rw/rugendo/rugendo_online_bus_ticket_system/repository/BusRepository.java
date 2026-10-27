package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Bus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID> {

    // C2
    @Query("""
            SELECT b
            FROM Bus b
            WHERE NOT EXISTS(
                SELECT d FROM Driver d WHERE d.bus = b
            )
            ORDER BY b.busNumber ASC
            """)
    List<Bus> findFreeBuses();

    // D4
    Optional<Bus> findByBusNumber(int busNumber);
}
