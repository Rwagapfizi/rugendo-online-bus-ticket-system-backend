package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Route;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID>{
    
} 
