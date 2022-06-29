package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.SearchDto;
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
import java.security.Principal;
import java.util.List;

@Controller @Slf4j
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

    @GetMapping("/403")
    public String accessDenied(){
        return "403";
    }

    @GetMapping()
    public String mainPage(
            Principal principal,
            @ModelAttribute("searchDTO") SearchDto searchDTO){
        if(principal != null) log.info(principal.getName());

        return "main";
    }

    @GetMapping("/region-search")
    public String findRoundtripSearchFlights(
            Model model,
            @ModelAttribute("searchDTO")
            @Valid SearchDto searchDTO,
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

        List<Flight> departureFlights = flightService.findFlightsByRegionsAndDepartureDate(departing, arriving, departureDateStr);
        List<Flight> returnFlights = flightService.findFlightsByRegionsAndArrivalDate(arriving, departing, arrivalDateStr);

        Region departureRegion = regionService.findById(departing).get();
        Region arrivalRegion = regionService.findById(arriving).get();

        if (departureFlights.isEmpty()) {
            String resultsMsg = String.format("Sorry, no results found for %s to %s leaving %s.", departureRegion.getName(), arrivalRegion.getName(), departureDateStr);
            model.addAttribute("departureMsg", resultsMsg);
            log.info("no departure results");
        } else {
            model.addAttribute("departureFlights", departureFlights);
        }
        if (returnFlights.isEmpty()) {
            String resultsMsg = String.format("Sorry, no results found for %s to %s returning %s.", arrivalRegion.getName(), departureRegion.getName(), arrivalDateStr);
            model.addAttribute("returnMsg", resultsMsg);
            log.info("no return results");
        } else {
            model.addAttribute("returnFlights", returnFlights);
        }
        log.info("Region Search Params:" );
        log.info("departing: "+departing+" arriving: "+arriving+" departureDate: "+departureDateStr+" arrivalDate: "+arrivalDateStr);
        model.addAttribute("newSearch", searchDTO);
        return "results";
    }
    @GetMapping("/port-search")
    public String roundtripSearchByPort(
            Model model,
            @ModelAttribute("searchDTO")
            @Valid SearchDto searchDTO,
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

        List<Flight> departureFlights = flightService.findFlightsByPortsAndDepartureDate(departing, arriving, departureDateStr);
        List<Flight> returnFlights = flightService.findFlightsByPortsAndArrivalDate(arriving, departing, arrivalDateStr);

        Port departurePort = portService.findById(departing).get();
        Port arrivalPort = portService.findById(arriving).get();

        if (departureFlights.isEmpty()) {
            String resultsMsg = String.format("Sorry, no results found for %s to %s leaving %s.", departurePort.getName(), arrivalPort.getName(), departureDateStr);
            model.addAttribute("departureMsg", resultsMsg);
            log.info("no departure results");
        } else {
            model.addAttribute("departureFlights", departureFlights);
        }
        if (returnFlights.isEmpty()) {
            String resultsMsg = String.format("Sorry, no results found for %s to %s returning %s.", arrivalPort.getName(), departurePort.getName(), arrivalDateStr);
            model.addAttribute("returnMsg", resultsMsg);
            log.info("no return results");
        } else {
            model.addAttribute("returnFlights", returnFlights);
        }

        log.info("Port Search Params:" );
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
