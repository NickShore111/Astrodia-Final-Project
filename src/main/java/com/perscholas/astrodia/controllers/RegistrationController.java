package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.UserDto;
import com.perscholas.astrodia.models.User;
import com.perscholas.astrodia.services.UserService;
import com.perscholas.astrodia.validators.UserAlreadyExistException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller @Slf4j
@RequestMapping("user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {
    UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String displaySignInUserForm(@RequestParam(value = "error", required = false) Boolean error, Model model) {
        model.addAttribute("loginFailed", error);
        return "signin";
    }

    @GetMapping("/signup")
    public String displayCreateNewUserForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }

    @PostMapping("/signup")
    public String registerNewUser(
            @ModelAttribute("user") @Valid @RequestBody UserDto userDto,
            BindingResult result,
            Errors errors,
            RedirectAttributes rda) {

        if (result.hasErrors()) {
            log.warn(result.getAllErrors().toString());
            return "signup";
        }
        try {
            userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException ex) {
            log.warn("exception in registerNewUser: "+ex);
            rda.addFlashAttribute("fail", "An account with that email already exists.");
            return "redirect:signup";
        }
        rda.addFlashAttribute("success", "New account created successfully!");
        return "redirect:signup";
    }

}
