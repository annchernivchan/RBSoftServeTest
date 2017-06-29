package application.controllers;

import application.mapper.MapperManager;
import application.dto.RegistrationUserDto;
import application.model.User;
import application.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String register(@ModelAttribute RegistrationUserDto registrationUserDto) {
        User user = MapperManager.REGISTRATION_USER.fromDto(registrationUserDto);
        if (userService.isExist(user)) return "redirect:/registration?error";

        userService.create(user);
        return "redirect:/login";
    }

}