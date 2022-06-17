package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.services.FlightService;
import com.perscholas.astrodia.services.PortService;
import com.perscholas.astrodia.services.RegionService;
import com.perscholas.astrodia.services.SpacelinerService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public String getFlightSearchResults() {
        return "flight search results";
    }
}
