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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping("/admin/categories/{categoryId}/{courseId}/edit")
    public String editCoursePage(@PathVariable("categoryId") long categoryId, @PathVariable("courseId") long courseId, Model model) throws CourseServiceException, CategoryServiceException {
        Course course = courseService.findCourseById(courseId);
        Category category = categoryService.findCategoryById(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("course", course);
        return "course-edit";
    }

    @PostMapping("/admin/categories/{categoryId}/{courseId}/edit")
    public String editCourse(@ModelAttribute("course") Course course, @PathVariable("categoryId") long categoryId, @PathVariable("courseId") long courseId, Model model) throws CourseServiceException {
        Course updatedCourse = courseService.findCourseById(courseId);
        course.setCategories(updatedCourse.getCategories());
        course.setId(courseId);
        courseService.updateCourse(course);
        return "redirect:/categories/" + categoryId;
    }

    @PostMapping("/admin/{categoryId}/{courseId}/deleteFromCategory")
    public String deleteFromCategoryPage(Model model, @PathVariable("categoryId") long categoryId, @PathVariable("courseId") long courseId) throws CourseServiceException, CategoryServiceException {
        courseService.deleteCourseFromCategory(courseId, categoryId);
        return "redirect:/categories/" + categoryId;
    }

    @PostMapping("/admin/{categoryId}/{courseId}/delete")
    public String deleteCourse(Model model, @PathVariable("categoryId") long categoryId, @PathVariable("courseId") long courseId) throws CourseServiceException, CategoryServiceException {
        courseService.deleteCourse(courseId);
        return "redirect:/categories/" + categoryId;
    }

    @GetMapping("/{categoryId}/courses/search")
    public String searchCategory(@RequestParam(value = "query") String query, Model model, @PathVariable long categoryId) throws CategoryServiceException, CourseServiceException {
        Category category = categoryService.findCategoryById(categoryId);
        List<Course> courses = courseService.searchCoursesForCategory(query, category);
        model.addAttribute("category", category);
        model.addAttribute("courses", courses);
        return "category-details";
    }

}
