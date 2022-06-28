package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.SearchDTO;
import com.perscholas.astrodia.dto.UpdateFlightDTO;
import com.perscholas.astrodia.models.*;
import com.perscholas.astrodia.services.*;
import com.perscholas.astrodia.util.StringTimestampConverter;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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

    @GetMapping("/flights")
    public String adminViewAllFlights(
            @ModelAttribute("updateFlightDTO") UpdateFlightDTO updateFlightDTO,
            Model model) {
        model.addAttribute("flights", flightService.findByOrderByDeparting());
        return "admin-flights";
    }
    @GetMapping("/flights/{id}")
    public String deleteFlight(@PathVariable("id") String strId, RedirectAttributes redirectAttributes) {
        Integer id = Integer.parseInt(strId);
        log.info("Deleting flight with ID: "+id);
        try {
            flightService.deleteById(id);
            String flashMsg = String.format("Flight with ID: %d deleted successfully!", id);
            redirectAttributes.addFlashAttribute("success", flashMsg);
        } catch (Exception e) {
            String flashMsg = String.format("Failed to update Flight with ID: %d.", id);
            redirectAttributes.addFlashAttribute("error", flashMsg);
        }
        return "redirect:/astrodia/admin/flights";
    }

    @PostMapping("/flights")
    public String updateFlight(
            @ModelAttribute UpdateFlightDTO updateFlight,
            RedirectAttributes redirectAttributes
    ) {
        log.info("***************POST REQUEST TRIGGERED*************");
        Pad departurePad = padService.findById(updateFlight.getDeparturePadID()).get();
        Pad arrivalPad = padService.findById(updateFlight.getArrivalPadID()).get();
        Shuttle shuttle = shuttleService.findById(updateFlight.getShuttleID()).get();

        StringTimestampConverter converter = new StringTimestampConverter();

        Timestamp departureTimestamp = converter.getTimestamp(
                updateFlight.getDepartureDate(),
                updateFlight.getDepartureTime().split(" ")[0],
                updateFlight.getDepartureTime().split(" ")[1]);

        Timestamp arrivalTimestamp = converter.getTimestamp(
                updateFlight.getArrivalDate(),
                updateFlight.getDepartureTime().split(" ")[0],
                updateFlight.getDepartureTime().split(" ")[1]);

        Flight flight = new Flight();
        flight.setId(updateFlight.getId());
        flight.setFlightCode(updateFlight.getFlightCode());
        flight.setSeatsAvailable(updateFlight.getSeatsAvailable());
        flight.setPricePerSeat(updateFlight.getPricePerSeat());
        flight.setShuttle(shuttle);
        flight.setLaunchPad(departurePad);
        flight.setArrivalPad(arrivalPad);
        flight.setDeparting(departureTimestamp);
        flight.setArriving(arrivalTimestamp);

        log.info("Updating flight:");
        log.info(flight.toString());
        try {
            flightService.saveOrUpdate(flight);
            String flashMsg = String.format("Flight %s updated successfully!", flight.getFlightCode());
            redirectAttributes.addFlashAttribute("success", flashMsg);
        } catch (Exception e) {
            String flashMsg = String.format("Failed to update Flight %s.", flight.getFlightCode());
            redirectAttributes.addFlashAttribute("error", flashMsg);
        }

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
