package rw.rugendo.rugendo_online_bus_ticket_system.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rw.rugendo.rugendo_online_bus_ticket_system.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public Map<String, Long> summary() {
        return service.summary();
    }
}
