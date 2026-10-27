package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Driver;

import java.util.List;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID>{

    //C1
    @Query ("""
        SELECT d FROM Driver d
        JOIN d.routes r
        WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :route, '%'))
    """)
    List<Driver> findByRoute(@Param("route") String route);
} 
