package org.nicholasshore.astrodia.controllers;

import org.nicholasshore.astrodia.models.Flight;
import org.nicholasshore.astrodia.services.FlightService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("astrodia/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApiController {

    FlightService flightService;

    @Autowired
    public ApiController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/test")
    public String testController() {
        return "Hello, World!";
    }

    @GetMapping("/flights/{id}")
    public Flight getFlight(@PathVariable("id") int id) {
        return flightService.findById(id);
    }
    @GetMapping("/flights")
    public List<Flight> getFlightsBySearchCriteria
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
