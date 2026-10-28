package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.transaction.Transactional;
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

    // F1
    @Query("""
            SELECT b.status, count(b)
            FROM Booking b
            GROUP BY b.status
            """)
    List<Object[]> countPerStatus();

    @Modifying
    @Transactional
    @Query("""
                UPDATE Booking b SET b.status = :cancelled
                WHERE b.driver.id = :driverID
                AND b.travelDate = :date
                AND b.status <> :paid
                AND b.status <> :cancelled
            """)
    int cancelBookingsByDriverID(
            @Param("driverID") UUID driverID,
            @Param("date") LocalDate date,
            @Param("cancelled") BookingStatus cancelledStatus,
            @Param("paid") BookingStatus paidStatus);

    // Bonus 1
    int countByStatus(BookingStatus status);
    int countByTravelDate(LocalDate travelDate);
}
