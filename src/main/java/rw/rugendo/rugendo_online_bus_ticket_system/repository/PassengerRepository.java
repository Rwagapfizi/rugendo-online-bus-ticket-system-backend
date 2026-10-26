package rw.rugendo.rugendo_online_bus_ticket_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Passenger;

import java.util.List;
import java.util.UUID;

public interface PassengerRepository extends JpaRepository<Passenger, UUID>{

    //B1
    List<Passenger> findByLastNameIgnoreCaseOrderByFirstNameAsc(String lastName);
} 
