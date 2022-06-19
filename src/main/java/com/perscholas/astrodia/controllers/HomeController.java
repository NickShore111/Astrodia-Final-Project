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
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

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
        ModelAndView view = new ModelAndView("admin-flights");
        view.addObject("flights", flightService.findByOrderBy());
        view.addObject("regions", regionService.findAll());
        view.addObject("ports", portService.findAll());
        view.addObject("spaceliners", spacelinerService.findAll());
        view.addObject("shuttles", shuttleService.findAll());
        return view;
    }

    @GetMapping("")
    public String mainPage(Model model, RoundtripDTO roundtripDTO){
        model.addAttribute("ports", portService.findAll());
        return "home";
    }

    @GetMapping("/roundtrip")
    public String roundtripFlightSearch(@Valid RoundtripDTO roundtripDTO, Errors errors, Model model, BindingResult result) {
        String departurePort = roundtripDTO.getDeparturePort();
        String arrivalPort = roundtripDTO.getArrivalPort();
        Date departureDate = roundtripDTO.getDepartureDate();
        Date arrivalDate = roundtripDTO.getArrivalDate();
        if (result.hasErrors()) {
            model.addAttribute("ports", portService.findAll());
            return "home";
        }


        if (departureDate != null && arrivalDate != null) {
            try {
                DateFormat dFormat = new SimpleDateFormat("MM/dd/yyyy");
//                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy"); // for String to Date
                String formatDeparture = dFormat.format(departureDate);
                String formatArrival = dFormat.format(arrivalDate);
                model.addAttribute("departureFlights", flightService.findFlightsByPortsAndDepartureDate(departurePort, arrivalPort, formatDeparture));
                model.addAttribute("arrivalFlights", flightService.findFLightsByPortsAndArrivalDate(departurePort, arrivalPort, formatArrival));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        model.addAttribute("result", roundtripDTO);

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
