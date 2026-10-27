package rw.rugendo.rugendo_online_bus_ticket_system.service;

import org.springframework.stereotype.Service;

import rw.rugendo.rugendo_online_bus_ticket_system.model.Bus;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Driver;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Route;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.DriverRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.RouteRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.BusRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DriverService {

    private final DriverRepository repository;
    private final RouteRepository routeRepository;
    private final BusRepository busRepository;

    public DriverService(
            DriverRepository repository,
            RouteRepository routeRepository,
            BusRepository busRepository) {
        this.repository = repository;
        this.routeRepository = routeRepository;
        this.busRepository = busRepository;
    }

    public String save(Driver driver) {
        repository.save(driver);
        return "Driver saved successfully";
    }

    public List<Driver> getAll() {
        return repository.findAll();
    }

    public Optional<Driver> getById(UUID id) {
        return repository.findById(id);
    }

    // C1
    public List<Driver> getByRoute(String route) {
        return repository.findByRoute(route);
    }

    // D3
    public String addRoute(UUID driverID, UUID routeID) {

        Optional<Driver> optDriver = repository.findById(driverID);
        if (optDriver.isEmpty()) {
            return "The driver with that id does not exist";
        }

        Optional<Route> optRoute = routeRepository.findById(routeID);
        if (optRoute.isEmpty()) {
            return "That route does not exist";
        }

        Driver driver = optDriver.get();
        Route route = optRoute.get();

        if (driver.getRoutes().contains(route)) {
            return "The driver already has that route";
        }

        driver.getRoutes().add(route);
        repository.save(driver);

        return "Route added to the driver";
    }

    // D4
    public String moveBus(UUID driverID, int busNumber) {
        Optional<Driver> optDriver = repository.findById(driverID);
        if (optDriver.isEmpty()) {
            return "The driver with that id does not exist";
        }

        Optional<Bus> optBus = busRepository.findByBusNumber(busNumber);
        if (optBus.isEmpty()) {
            return "The bus with that busNumber does not exist";
        }

        Driver driver = optDriver.get();
        Bus bus = optBus.get();

        if (driver.getBus().equals(bus)) {
            return "The driver already has this bus";
        }

        if (repository.existsByBus(bus)) {
            return "That bus is taken by another driver";
        }

        driver.setBus(bus);
        repository.save(driver);
        return "Bus changed successfully";
    }

    public Optional<Driver> update(UUID id, Driver driver) {
        return repository.findById(id).map(existing -> {
            driver.setId(id);
            return repository.save(driver);
        });
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Driver deleted successfully";
        }
        return "The driver with that id does not exist";
    }
}