package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.services.FlightService;
import com.perscholas.astrodia.services.PortService;
import com.perscholas.astrodia.services.RegionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @Slf4j
@RequestMapping("astrodia")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class HomeController {
    FlightService flightService;
    RegionService regionService;
    PortService portService;

    @Autowired
    public HomeController(FlightService flightService, RegionService regionService, PortService portService) {
        this.flightService = flightService;
        this.regionService = regionService;
        this.portService = portService;

    }
    @GetMapping("test")
    public String index() {return "index";}

    @GetMapping("")
    public String mainPage(Model model) {
//        model.addAttribute("ports", portService.findAll());
        return "home";
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
