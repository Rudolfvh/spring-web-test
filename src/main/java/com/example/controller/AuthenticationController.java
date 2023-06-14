package com.example.controller;

import com.example.dto.LoginDto;
import com.example.dto.UserCreateEditDto;
import com.example.entity.Role;
import com.example.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute UserCreateEditDto userReg) {
        model.addAttribute("userReg", userReg);
        model.addAttribute("roles", Role.values());
        return "user/registration";
    }

    @GetMapping("/login")
    public String login(Model model,
                        @ModelAttribute LoginDto userLog) {
        model.addAttribute("userLog", userLog);
        return "user/login";
    }

    @PostMapping("/registration")
    public String createUser( @ModelAttribute UserCreateEditDto userReg,
                              RedirectAttributes redirectAttributes) {
        if (userReg.getPassword().isEmpty()) {
            redirectAttributes.addAttribute("username", userReg.getUsername());
            redirectAttributes.addAttribute("phoneNumber", userReg.getPhoneNumber());
            redirectAttributes.addAttribute("role", userReg.getRole());
            return "redirect:/registration";
        }
        var sessionUser = userService.create(userReg);

        return "redirect:/users/" + sessionUser.getId();
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginDto userLog,
                            RedirectAttributes redirectAttributes) {
        var maybeSessionUser = userService.login(userLog);

        if (userLog.getPassword().isEmpty() || maybeSessionUser.isEmpty()) {
            redirectAttributes.addAttribute("username", userLog.getUsername());
            return "redirect:/login";
        }
        var sessionUser = maybeSessionUser.get();

        if (sessionUser.getRole().equals(Role.ADMIN)) {
            return "redirect:/users";
        }
        return "redirect:/users/" + sessionUser.getId();
    }
}
