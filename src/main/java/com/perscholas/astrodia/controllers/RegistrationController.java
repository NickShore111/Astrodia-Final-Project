package com.perscholas.astrodia.controllers;

import com.perscholas.astrodia.dto.UserDto;
import com.perscholas.astrodia.models.User;
import com.perscholas.astrodia.services.UserService;
import com.perscholas.astrodia.validations.UserAlreadyExistException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller @Slf4j
@RequestMapping("astrodia")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RegistrationController {
    UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signin")
    public String signInUserForm() { return "signin"; }

    @GetMapping("/signup")
    public String createNewUserForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("user", userDto);
        return "signup";
    }
    @PostMapping("/signup")
    public String registerNewUser(
            @ModelAttribute("user") @Valid UserDto userDto,
            BindingResult results,
            Errors errors,
            RedirectAttributes redirectAttributes) {

        try {
            User registered = userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            log.info("exception in registerNewUser: "+uaeEx);
            redirectAttributes.addFlashAttribute("fail", "An account with that email already exists.");
            return "redirect:signup";
        }
        redirectAttributes.addFlashAttribute("success", "New account created successfully!");
        return "redirect:signup";
    }
}
