package com.perscholas.astrodia.services;

import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> findAll() {
        return flightRepository.findAll();
    }

    public void saveOrUpdate(Flight flight) {
        flightRepository.save(flight);
    }

    public List<Flight> findFlightsByDeparture(String date) {
        return flightRepository.findFlightsByDeparture(date);
    }

    public List<Flight> findByOrderBy() { return flightRepository.findByOrderByDeparting(); }

    public List<Flight> findFlightsBySelection(String spaceliner, String region, String port, String departing, String arriving) {
        List<Flight> flights = new ArrayList<Flight>();
        return flights;
    }

    public List<Flight> findFlightsByPortsAndDepartureDate(String departurePort, String arrivalPort, String departureDate) {
        return flightRepository.findFlightsByPortsAndDepartureDate(departurePort, arrivalPort, departureDate);
    }

    public List<Flight> findFLightsByPortsAndArrivalDate(String departurePort, String arrivalPort, String arrivalDate) {
        return flightRepository.findFlightsByPortsAndArrivalDate(departurePort, arrivalPort, arrivalDate);
    }

}
