package com.example.demo.controller;


import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(@ModelAttribute("username") String username,
                            @ModelAttribute("password") String password,
                            Model model){
        if (username != null && password != null) {
            model.addAttribute("username", username);
            model.addAttribute("password", password);
        } else {
            model.addAttribute("username", "");
            model.addAttribute("password", "");
        }
        return "login";
    }
}
