package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.exception.ServiceException;
import com.example.demo.service.UserService;
import com.example.demo.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class RegisterController {
    private final UserService userService;
    @Autowired
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView regForm(){
        return new ModelAndView("register", "command", new User());
    }
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String regSubmit(HttpServletRequest request,
                            ModelMap model) throws ServiceException {

        String userName = request.getParameter("userName");
        String userEmail = request.getParameter("userEmail");
        String userPass = request.getParameter("userPass");
        String repeatPass = request.getParameter("repeatPass");
        String checkboxValue = request.getParameter("checkbox");

        User user = User.builder()
                .userName(userName)
                .login(userEmail)
                .password(userPass)
                .repeatPassword(repeatPass)
                .checkboxValue(checkboxValue)
                .build();

        try {
            userService.register(user);
            return "index"; // Перенаправляем пользователя на другую страницу после успешной регистрации
        } catch (Exception e) {
            model.addAttribute("register_msg", UserServiceImpl.getErrorMessage()); // Если возникла ошибка, передаем сообщение об ошибке на страницу
            return "index"; // Возвращаем пользователя на страницу регистрации
        }
    }
}
