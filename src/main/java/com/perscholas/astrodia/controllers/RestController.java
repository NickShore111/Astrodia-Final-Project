package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.services.FlightService;
import com.perscholas.astrodia.services.PortService;
import com.perscholas.astrodia.services.RegionService;
import com.perscholas.astrodia.services.SpacelinerService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@org.springframework.web.bind.annotation.RestController
@Slf4j
@RequestMapping("astrodia/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RestController {
    FlightService flightService;
    RegionService regionService;
    PortService portService;
    SpacelinerService spacelinerService;

    public RestController(FlightService flightService, RegionService regionService, PortService portService, SpacelinerService spacelinerService) {
        this.flightService = flightService;
        this.regionService = regionService;
        this.portService = portService;
        this.spacelinerService = spacelinerService;
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable("id") int id) {
        return flightService.findById(id);
    }
    @GetMapping("/flights")
    public List<Flight> getFlightSearchResults
            (
            @RequestParam(value = "spaceliner", required = false) String[] spaceliner,
            @RequestParam(value = "shuttle", required = false) String[] shuttle,
            @RequestParam(value = "departureRegion", required = false) String[] departureRegion,
            @RequestParam(value = "arrivalRegion", required = false) String[] arrivalRegion,
            @RequestParam(value = "departurePort", required = false) String[] departurePort,
            @RequestParam(value = "arrivalPort", required = false) String[] arrivalPort,
            @RequestParam(value = "departing", required = false) String departing,
            @RequestParam(value = "arriving", required = false) String arriving
            )
    {
        log.info("************** Selection Params *************");
        log.info("Spaceliners="+Arrays.toString(spaceliner) +" "+ "Shuttles="+Arrays.toString(shuttle));
        log.info("Departing: " + departing +" "+ "Region="+Arrays.toString(departureRegion) +" "+ "Port="+Arrays.toString(departurePort));
        log.info("Arriving: " + arriving +" "+ "Region="+Arrays.toString(arrivalRegion) +" "+ "Port="+Arrays.toString(arrivalPort));
        List<Flight> flights = flightService.findFlightsBySelectionCriteria(spaceliner, shuttle, departing, departureRegion, departurePort, arriving, arrivalRegion, arrivalPort);
//        flights.forEach(flight -> log.info(flight.toString()));
        log.info("Results Count: " + flights.size());
        return flights;
    }

}
