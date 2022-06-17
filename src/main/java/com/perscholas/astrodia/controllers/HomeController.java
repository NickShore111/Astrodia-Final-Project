package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.RoundtripDTO;
import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.services.FlightService;
import com.perscholas.astrodia.services.PortService;
import com.perscholas.astrodia.services.RegionService;
import com.perscholas.astrodia.services.SpacelinerService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller @Slf4j
@RequestMapping("astrodia")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {
    FlightService flightService;
    RegionService regionService;
    PortService portService;
    SpacelinerService spacelinerService;

    @Autowired
    public HomeController(FlightService flightService, RegionService regionService, PortService portService, SpacelinerService spacelinerService) {
        this.flightService = flightService;
        this.regionService = regionService;
        this.portService = portService;
        this.spacelinerService = spacelinerService;

    }
    @GetMapping("test")
    public String index() {return "index";}

    @GetMapping("/admin/flights")
    public ModelAndView adminViewAllFlights() {
        //        model.addAttribute("flights", flightService.findAll());

        ModelAndView view = new ModelAndView("admin-flights");
        view.addObject("flights", flightService.allFlightsOrderedByDeparting());
        view.addObject("regions", regionService.findAll());
        view.addObject("ports", portService.findAll());
        view.addObject("spaceliners", spacelinerService.findAll());
        return view;
    }

    @GetMapping("")
    public String mainPage(@ModelAttribute("roundtripDTO") RoundtripDTO roundtripDTO, BindingResult result, Model model){
        model.addAttribute("ports", portService.findAll());
        model.addAttribute("roundtripSearch", roundtripDTO);
        return "home";
    }

    @GetMapping("/roundtrip")
    public String roundtripFlightSearch(Model model, @ModelAttribute("roundtripDTO") RoundtripDTO roundtripDTO) {
        model.addAttribute("result", roundtripDTO);
        Date departureDate = roundtripDTO.getDepartureDate();
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
        String formatDateStr = dFormat.format(departureDate);
        List<Flight> flights = flightService.findFlightsByDeparture(formatDateStr);
//        log.info("DateFormat: " + formatDateStr);
//        log.info("FLIGHTS: " + flights.toString());
        model.addAttribute("flights", flights);
        log.info(flights.toString());
        return "flights";
    }

    @GetMapping("/results")
    public String flightSearchResults() {
        return "flights";
    }
    @GetMapping("/signup")
    public String createUserForm() {
        return "signup";
    }

    @GetMapping("/stations")
    public String staysPage() { return "stations"; }

    @GetMapping("/signin")
    public String signInUserForm() { return "signin"; }

    @PostMapping("/signup")
    public String signInUser() {

        return "redirect:astrodia";
    }
}
