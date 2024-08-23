package com.example.demo.controller;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.exception.ConsultationFormServiceException;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.exception.UserServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.ConsultationForm;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ConsultationFormService;
import com.example.demo.service.CourseService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AdminController {
    private final CategoryService categoryService;
    private final CourseService courseService;
    private final ConsultationFormService formService;
    private final UserService userService;

    @Autowired
    public AdminController(CategoryService categoryService, CourseService courseService, ConsultationFormService formService, UserService userService) {
        this.categoryService = categoryService;
        this.courseService = courseService;

        this.formService = formService;
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String adminPage(Model model) throws CategoryServiceException, CourseServiceException, ConsultationFormServiceException, UserServiceException {
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        List<Course> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);
        List<ConsultationForm> forms = formService.findAllForms();
        model.addAttribute("forms", forms);
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PostMapping("/admin/{formId}/deleteForm")
    public String formDelete(Model model, @PathVariable("formId") long formId) throws ConsultationFormServiceException {
        ConsultationForm form = formService.findFormById(formId);
        formService.deleteConsultationForm(form);
        return "redirect:/admin";
    }

    @PostMapping("/admin/{userId}/deleteUser")
    public String userDelete(@PathVariable("userId") long userId) throws UserServiceException {
        User user = userService.findById(userId);
        userService.deleteUser(user);
        return "redirect:/admin";
    }
}
