package rw.rugendo.rugendo_online_bus_ticket_system.service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import rw.rugendo.rugendo_online_bus_ticket_system.model.BookingStatus;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.BookingRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.BusRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.DriverRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.PassengerRepository;
import rw.rugendo.rugendo_online_bus_ticket_system.repository.TicketRepository;

@Service
public class DashboardService {
    private final PassengerRepository passengerRepository;
    private final DriverRepository driverRepository;
    private final BusRepository busRepository;
    private final BookingRepository bookingRepository;
    private final TicketRepository ticketRepository;

    public DashboardService(
            PassengerRepository passengerRepository,
            DriverRepository driverRepository,
            BusRepository busRepository,
            BookingRepository bookingRepository,
            TicketRepository ticketRepository) {
        this.passengerRepository = passengerRepository;
        this.driverRepository = driverRepository;
        this.busRepository = busRepository;
        this.bookingRepository = bookingRepository;
        this.ticketRepository = ticketRepository;
    }

    public Map<String, Long> summary() {

        LocalDate today = LocalDate.now();
        long freeBuses = busRepository.findFreeBuses().size();
        long bookingsToday = bookingRepository.countByTravelDate(today);
        long requestedBookings = bookingRepository.countByStatus(BookingStatus.REQUESTED);

        Map<String, Long> summary = new LinkedHashMap<>();

        summary.put("passengers", passengerRepository.count());
        summary.put("drivers", driverRepository.count());
        summary.put("freeBuses", freeBuses);
        summary.put("bookingsToday", bookingsToday);
        summary.put("requestedBookings", requestedBookings);
        summary.put("tickets", ticketRepository.count());

        return summary;
    }

}
