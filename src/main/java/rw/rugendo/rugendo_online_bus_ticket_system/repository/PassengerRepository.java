package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Passenger;

import java.util.List;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID> {

    // B1
    List<Passenger> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);

    // C4
    @Query("""
            SELECT DISTINCT p from Passenger p
            JOIN p.bookings b
            WHERE b.driver.id = :driverID
            """)
    List<Passenger> findByDriverID(@Param("driverID") UUID driverID);
}
