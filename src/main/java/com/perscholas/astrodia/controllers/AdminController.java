package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.models.Port;
import com.perscholas.astrodia.models.Region;
import com.perscholas.astrodia.models.Shuttle;
import com.perscholas.astrodia.models.Spaceliner;
import com.perscholas.astrodia.services.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("astrodia/admin")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {
    FlightService flightService;
    RegionService regionService;
    PortService portService;
    SpacelinerService spacelinerService;
    ShuttleService shuttleService;
    PadService padService;
    @Autowired
    public AdminController(FlightService flightService,
                           RegionService regionService,
                           PortService portService,
                           SpacelinerService spacelinerService,
                           ShuttleService shuttleService,
                           PadService padService) {
        this.flightService = flightService;
        this.regionService = regionService;
        this.portService = portService;
        this.spacelinerService = spacelinerService;
        this.shuttleService = shuttleService;
        this.padService = padService;
    }

    @ModelAttribute("regions")
    public List<Region> regions() {
        return regionService.findAll();
    }
    @ModelAttribute("ports")
    public List<Port> ports() {
        return portService.findAll();
    }
    @ModelAttribute("spaceliners")
    public List<Spaceliner> spaceliners() {
        return spacelinerService.findAll();
    }
    @ModelAttribute("shuttles")
    public List<Shuttle> shuttles() {
        return shuttleService.findAll();
    }
    @RequestMapping("regions")

    public String adminRegionPortal() {
        return "admin-regions";
    }

}
