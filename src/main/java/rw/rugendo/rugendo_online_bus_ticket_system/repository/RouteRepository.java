package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Route;

import java.util.List;
import java.util.UUID;

public interface RouteRepository extends JpaRepository<Route, UUID> {

    //C3
    @Query("""
            SELECT r FROM Route r
            WHERE r.drivers IS EMPTY
            """)
    List<Route> findUnusedRoutes();
}
