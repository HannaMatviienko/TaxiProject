package com.taxi.controllers;

import com.taxi.dto.UserDTO;
import com.taxi.services.UserService;
import com.taxi.tools.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/login")
    public String loginUser(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "logout", required = false) String logout,
                           Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @GetMapping("/user/signup")
    public String signupUser(Model model) {
        model.addAttribute("user", new UserDTO());
        return "signup";
    }

    @GetMapping("/admin/users")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.listAll());
        return "users";
    }

    @GetMapping("/admin/user/new")
    public String newUser(Model model) {
        UserDTO user = new UserDTO();
        user.setRoles("ROLE_USER");
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("mode", 0);
        return "user";
    }

    @PostMapping("/admin/user/save")
    public String saveUser(UserDTO user) {
        user.setUserName(user.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/user/edit/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        try {
            UserDTO user = userService.get(id);
            model.addAttribute("user", user);
            model.addAttribute("mode", 1);
        } catch (UserNotFoundException ignored) {

        }
        return "user";
    }

    @GetMapping("/admin/user/del/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        try {
            userService.delete(id);
        } catch (UserNotFoundException ignored) {

        }
        return "redirect:/admin/users";
    }
}
