package com.perscholas.astrodia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("astrodia")
public class HomeController {

    @GetMapping("")
    public String mainPage() {
        return "home";
    }

    @GetMapping("/signup")
    public String createUserForm() {
        return "signup";
    }

    @GetMapping("/stays")
    public String staysPage() { return "stays"; }
}
