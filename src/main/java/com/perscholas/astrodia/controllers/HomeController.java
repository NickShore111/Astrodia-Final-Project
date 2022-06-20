package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.FlightDTO;
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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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
    @GetMapping("test")
    public String index() {return "index"; }
    @GetMapping("/signup")
    public String createUserForm() {return "signup"; }
    @GetMapping("/stations")
    public String staysPage() { return "stations"; }
    @GetMapping("/signin")
    public String signInUserForm() { return "signin"; }
    @PostMapping("/signup")
    public String signInUser() { return "redirect:astrodia"; }

    @GetMapping("")
    public String mainPage(FlightDTO flightDTO){
        return "main";
    }

    @GetMapping("/admin/flights")
    public String adminViewAllFlights(Model model) {
        model.addAttribute("flights", flightService.findByOrderByDeparting());
        return "admin-flights";
    }

    @GetMapping("/region-roundtrip")
    public String regionRoundtrip(@Valid FlightDTO flightDTO, Errors errors, Model model, BindingResult result) {
        String departing = flightDTO.getLeaving();
        String arriving = flightDTO.getDestination();
        String departureDateStr = flightDTO.getDepartureDate();
        String arrivalDateStr = flightDTO.getArrivalDate();
        if (result.hasErrors()) {
            return "main";
        }
        log.info("Search Params:" );
        log.info("departing: "+departing+" arriving: "+arriving+" departureDate: "+departureDateStr+" arrivalDate: "+arrivalDateStr);
        model.addAttribute("departureFlights", flightService.findFlightsByRegionsAndDepartureDate(departing, arriving, departureDateStr));
        model.addAttribute("arrivalFlights", flightService.findFlightsByRegionsAndArrivalDate(arriving, departing, arrivalDateStr));

        return "roundtripFlights";
    }

//    findFlightsBySelectionCriteria(
//            String[] spacelinerList,
//            String[] shuttleList,
//            String departing,
//            String[] departureRegionList,
//            String[] departurePortList,
//            String arriving,
//            String[] arrivalRegionList,
//            String[] arrivalPortList
//    )
}
