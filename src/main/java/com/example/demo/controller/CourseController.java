package com.example.demo.controller;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.ConsultationForm;
import com.example.demo.model.Course;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CourseService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    Logger logger = LogManager.getLogger();

    @Autowired
    public CourseController(CourseService courseService, CategoryService categoryService){
        this.courseService = courseService;
        this.categoryService = categoryService;
    }
    @GetMapping("/categories/{categoryId}/{courseId}")
    public String coursePage(@PathVariable("courseId") long courseId,
                             Model model, @PathVariable long categoryId) throws CourseServiceException, CategoryServiceException {
        ConsultationForm form = new ConsultationForm();
        Category category = categoryService.findCategoryById(categoryId);
        Course course = courseService.findCourseById(courseId);
        model.addAttribute("course", course);
        model.addAttribute("category", category);
        model.addAttribute("consultationForm", form);
        return "/course-details";
    }

    @PostMapping("/{courseId}/cart")
    public String addCourseToCart(@PathVariable("courseId") long courseId) throws CourseServiceException {
        String authUsername = SecurityUtil.getSessionUser();
        logger.log(Level.INFO, "-------------> Username: " + authUsername);
        if(authUsername == null){
            return "redirect:/categories";
        }
        courseService.addCourseToUserCart(courseId, authUsername);
        return "redirect:/cart";
    }

    @PostMapping("/categories/{categoryId}/{courseId}")
    public String submitConsultationForm(@ModelAttribute("consultationForm") ConsultationForm form,
                                         Model model,
                                         @PathVariable("courseId") long courseId,
                                         @PathVariable("categoryId") long categoryId) throws CourseServiceException {
        String authUsername = SecurityUtil.getSessionUser();

        if (authUsername != null) {
            courseService.addFormToUser(authUsername, courseId, form);
            return "redirect:/categories/" + categoryId + "/" + courseId + "?success";

        }
        return "redirect:/categories/" + categoryId + "/" + courseId + "?fails";
    }
}
