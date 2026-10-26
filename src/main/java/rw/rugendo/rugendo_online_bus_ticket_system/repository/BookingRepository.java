package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Booking;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID>{
    
} 
