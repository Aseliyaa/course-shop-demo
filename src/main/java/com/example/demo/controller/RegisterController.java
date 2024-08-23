package com.example.demo.controller;


import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class RegisterController {
    private final UserService userService;
    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/register")
    public String regForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("register_msg", UserServiceImpl.getErrorMessage());
        return "/register";
    }


    @PostMapping(value = "/register")
    public String regSubmit(@ModelAttribute("user") User user,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        try {
            String password = user.getPassword();
            if(userService.register(user)){
                redirectAttributes.addFlashAttribute("username", user.getUsername());
                redirectAttributes.addAttribute("password", password);
                return "redirect:/login";
            } else {
                model.addAttribute("register_msg", UserServiceImpl.getErrorMessage());
                return "/register";
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
