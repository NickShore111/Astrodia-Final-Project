package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.services.FlightService;
import com.perscholas.astrodia.services.PortService;
import com.perscholas.astrodia.services.RegionService;
import com.perscholas.astrodia.services.SpacelinerService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController @Slf4j
@RequestMapping("astrodia/api")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class APIController {
    FlightService flightService;
    RegionService regionService;
    PortService portService;
    SpacelinerService spacelinerService;

    public APIController(FlightService flightService, RegionService regionService, PortService portService, SpacelinerService spacelinerService) {
        this.flightService = flightService;
        this.regionService = regionService;
        this.portService = portService;
        this.spacelinerService = spacelinerService;
    }
    @GetMapping("/flights")
    public String getFlightSearchResults
            (
            @RequestParam(value = "spaceliner", required = false) String[] spaceliner,
            @RequestParam(value = "region", required = false) String[] region,
            @RequestParam(value = "port", required = false) String[] port,
            @RequestParam(value = "departing", required = false) String departing,
            @RequestParam(value = "arriving", required = false) String arriving
            )
    {
        log.info("************** Spaceliner Params *************");
        log.info("Departing: "+departing +" "+ "Arriving: " + arriving);
        log.info("Spaceliners="+Arrays.toString(spaceliner) +" "+
                "Region="+Arrays.toString(region) +" "+
                "Port="+Arrays.toString(port));
//        List<Flight> flightService.findFlightsBySelection(spaceliner, region, port, departing, arriving);
        return "flight search results";
    }

//    @GetMapping("/flights")
//    public String getFlightSearchResults(
//            @RequestParam("ports") String[] ports,
//            @RequestParam("spaceliners") String[] spaceliners,
//            @RequestParam("regions") String[] regions,
//            @RequestParam("departure") String departure,
//            @RequestParam("arrival") String arrival
//    ) {
//        log.info("************** API Flights Params ***************");
//        log.info("Ports: "+ports);
//        log.info("Regions: "+regions);
//        log.info("Spaceliner: "+spaceliners);
//        log.info("Departure: "+departure);
//        log.info("Arrival: "+arrival);
//        return "flight search results";
//    }
}
