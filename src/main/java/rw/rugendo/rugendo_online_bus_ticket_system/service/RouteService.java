package rw.rugendo.rugendo_online_bus_ticket_system.service;

import org.springframework.stereotype.Service;
import rw.rugendo.rugendo_online_bus_ticket_system.model.Route;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.RouteRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RouteService {

    private final RouteRepository repository;

    public RouteService(RouteRepository repository) {
        this.repository = repository;
    }

    public String save(Route route) {
        repository.save(route);
        return "Route saved successfully";
    }

    public List<Route> getAll() {
        return repository.findAll();
    }

    public Optional<Route> getById(UUID id) {
        return repository.findById(id);
    }

    //C3
    public List<Route> getUnusedRoutes() {
        return repository.findUnusedRoutes();
    }

    public Optional<Route> update(UUID id, Route route) {
        return repository.findById(id).map(existing -> {
            route.setId(id);
            return repository.save(route);
        });
    }

    public String delete(UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return "Route deleted successfully";
        }
        return "The route with that id does not exist";
    }
}
