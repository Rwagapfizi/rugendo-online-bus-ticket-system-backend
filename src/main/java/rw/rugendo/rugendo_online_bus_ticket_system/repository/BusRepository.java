package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Bus;
import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID>{

    
} 
