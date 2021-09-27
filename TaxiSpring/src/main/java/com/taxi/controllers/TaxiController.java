package com.taxi.controllers;

import com.taxi.dto.UserDTO;
import com.taxi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaxiController {

    private final UserService userService;

    @Autowired
    public TaxiController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHome() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findUserByUserName(auth.getName());
        if (user == null)
            return "index";
        else {
            switch (user.getRoles()) {
                case "ROLE_USER":
                    return "redirect:/order";
                case "ROLE_ADMIN":
                    return "redirect:/admin";
                default:
                    return "index";
            }
        }
    }

    @GetMapping("/common/service")
    public String getService(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findUserByUserName(auth.getName());
        model.addAttribute("user", user != null);
        return "common_service";
    }

    @GetMapping("/common/price")
    public String getPrice(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findUserByUserName(auth.getName());
        model.addAttribute("user", user != null);
        return "common_price";
    }

    @GetMapping("/common/about")
    public String getAbout(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDTO user = userService.findUserByUserName(auth.getName());
        model.addAttribute("user", user != null);
        return "common_about";
    }
}
