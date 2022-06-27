package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.UpdateFlightDTO;
import com.perscholas.astrodia.models.*;
import com.perscholas.astrodia.services.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @ModelAttribute("pads")
    public List<Pad> pads() { return padService.findAll(); }

    @GetMapping("portal")
    public String adminRegionPortal() {
        return "admin-portal";
    }

    @GetMapping("flights/{id}")
    public String deleteFlightById(@PathVariable int id) {
        log.info("Deleting Flight with ID: " + id);
        return "admin-portal";
    }
    @GetMapping("flights")
    public String adminViewAllFlights(Model model) {
        model.addAttribute("flights", flightService.findByOrderByDeparting());
        return "admin-flights";
    }

    @PostMapping("/flights")
    public String updateFlight(
            @ModelAttribute("updateFlightDTO") UpdateFlightDTO updateFlightDTO,
            @RequestParam("flightCode") String flightCode,
            @RequestParam("availableSeats") String availableSeats

        ) {
        log.info("***************POST REQUEST TRIGGERED***********");

        return "redirect:flights";
    }
//    @PostMapping("/admin/flights")
//    public String updateFlightInfo(
//            Model model,
//            @ModelAttribute("updateFlightDTO")
//            @Valid UpdateFlightDTO updateFlightDTO,
//            BindingResult result,
//            Error errors) {
//
//        return "redirect:flights";
//    }
}
