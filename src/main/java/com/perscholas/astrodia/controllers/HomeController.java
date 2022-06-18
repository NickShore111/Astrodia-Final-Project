package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.RoundtripDTO;
import com.perscholas.astrodia.models.Flight;
import com.perscholas.astrodia.services.*;
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
    ShuttleService shuttleService;

    @Autowired
    public HomeController(
            FlightService flightService,
            RegionService regionService,
            PortService portService,
            SpacelinerService spacelinerService,
            ShuttleService shuttleService) {
        this.flightService = flightService;
        this.regionService = regionService;
        this.portService = portService;
        this.spacelinerService = spacelinerService;
        this.shuttleService = shuttleService;

    }
    @GetMapping("test")
    public String index() {return "index";}

    @GetMapping("/admin/flights")
    public ModelAndView adminViewAllFlights() {
        //        model.addAttribute("flights", flightService.findAll());

        ModelAndView view = new ModelAndView("admin-flights");
        view.addObject("flights", flightService.findByOrderBy());
        view.addObject("regions", regionService.findAll());
        view.addObject("ports", portService.findAll());
        view.addObject("spaceliners", spacelinerService.findAll());
        view.addObject("shuttles", shuttleService.findAll());
        return view;
    }

    @GetMapping("")
    public String mainPage(@ModelAttribute("roundtripDTO") RoundtripDTO roundtripDTO, BindingResult result, Model model){
        model.addAttribute("ports", portService.findAll());
        model.addAttribute("roundtripSearch", roundtripDTO);
        return "home";
    }

    @GetMapping("/roundtrip")
    public String roundtripFlightSearch(Model model, @ModelAttribute("roundtripDTO") BindingResult result, RoundtripDTO roundtripDTO) {
        model.addAttribute("result", roundtripDTO);
        DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
        String departurePort = roundtripDTO.getDeparturePort();
        String arrivalPort = roundtripDTO.getArrivalPort();
        Date departureDate = roundtripDTO.getDepartureDate();
        Date arrivalDate = roundtripDTO.getArrivalDate();

        String formatDeparture = dFormat.format(departureDate);
        String formatArrival = dFormat.format(arrivalDate);
        model.addAttribute("departureFlights", flightService.findFlightsByPortsAndDepartureDate(departurePort, arrivalPort, formatDeparture));
        model.addAttribute("arrivalFlights", flightService.findFLightsByPortsAndArrivalDate(departurePort, arrivalPort, formatArrival));

        return "roundtripFlights";
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
