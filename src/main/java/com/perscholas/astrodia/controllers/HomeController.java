package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.RoundtripSearchDTO;
import com.perscholas.astrodia.models.*;
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

import javax.validation.Valid;
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
    @GetMapping("/signin")
    public String signInUserForm() { return "signin"; }
    @PostMapping("/signup")
    public String signInUser() { return "redirect:astrodia"; }

    @GetMapping("")
    public String mainPage(@ModelAttribute("searchDTO") RoundtripSearchDTO searchDTO){
        return "main";
    }
    @GetMapping("/roundtrip-region")
    public String roundtripSearchByRegion(
            Model model,
            @ModelAttribute("searchDTO")
            @Valid RoundtripSearchDTO searchDTO,
            BindingResult result,
            Errors errors) {
        if (result.hasErrors()) {
            log.warn(result.getAllErrors().toString());
            return "main";
        }
        String departing = searchDTO.getLeaving();
        String arriving = searchDTO.getDestination();
        String departureDateStr = searchDTO.getDepartureDate();
        String arrivalDateStr = searchDTO.getArrivalDate();

        model.addAttribute("departureFlights", flightService.findFlightsByRegionsAndDepartureDate(departing, arriving, departureDateStr));
        model.addAttribute("returnFlights", flightService.findFlightsByRegionsAndArrivalDate(arriving, departing, arrivalDateStr));

        log.info("Search Params:" );
        log.info("departing: "+departing+" arriving: "+arriving+" departureDate: "+departureDateStr+" arrivalDate: "+arrivalDateStr);
        model.addAttribute("newSearch", searchDTO);
        return "results";
    }
    @GetMapping("/roundtrip-port")
    public String roundtripSearchByPort(
            Model model,
            @ModelAttribute("searchDTO")
            @Valid RoundtripSearchDTO searchDTO,
            BindingResult result,
            Errors errors) {
        if (result.hasErrors()) {
            log.warn(result.getAllErrors().toString());
            return "main";
        }
        String departing = searchDTO.getLeaving();
        String arriving = searchDTO.getDestination();
        String departureDateStr = searchDTO.getDepartureDate();
        String arrivalDateStr = searchDTO.getArrivalDate();

        model.addAttribute("departureFlights", flightService.findFlightsByPortsAndDepartureDate(departing, arriving, departureDateStr));
        model.addAttribute("returnFlights", flightService.findFlightsByPortsAndArrivalDate(arriving, departing, arrivalDateStr));

        log.info("Search Params:" );
        log.info("departing: "+departing+" arriving: "+arriving+" departureDate: "+departureDateStr+" arrivalDate: "+arrivalDateStr);
        model.addAttribute("newSearch", searchDTO);
        return "results";
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
