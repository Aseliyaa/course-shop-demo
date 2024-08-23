package com.example.demo.controller;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.ChargeRequest;
import com.example.demo.model.ConsultationForm;
import com.example.demo.model.Course;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CourseService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    Logger logger = LogManager.getLogger();
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;
    @Autowired
    public CourseController(CourseService courseService, CategoryService categoryService){
        this.courseService = courseService;
        this.categoryService = categoryService;
    }
    @GetMapping("/courses")
    public String coursesPage(Model model) throws CourseServiceException {
        String newLevel = "All levels";

        List<Course> courses = courseService.findAllCourses();
        model.addAttribute("courses", courses);

        List<String> newLevels = new ArrayList<>();
        newLevels.add(newLevel);
        newLevels.addAll(courseService.findAllTargetLevels());
        model.addAttribute("levels", newLevels);

        return "courses";
    }
    @GetMapping("/courses/byLevel")
    public String filterByLevel(@RequestParam(name = "level", required = false) String level, Model model) throws CourseServiceException {
        List<Course> filteredCourses;
        String newLevel = "All levels";
        List<String> levels = courseService.findAllTargetLevels();

        if (levels.contains(level)) {
            filteredCourses = courseService.findByLevel(level);
            levels.remove(level);
        } else {
            filteredCourses = courseService.findAllCourses();
        }
        List<String> newLevels = filter(level, newLevel, levels);
        model.addAttribute("courses", filteredCourses);
        model.addAttribute("levels", newLevels);

        return "courses";
    }

    private List<String> filter(String level, String newLevel, List<String> levels) {
        List<String> newLevels = new ArrayList<>();
        if (!level.equals(newLevel)) {
            newLevels.add(level);
            newLevels.addAll(levels);
            newLevels.add(newLevel);
        } else {
            newLevels.add(newLevel);
            newLevels.addAll(levels);
        }
        return newLevels;
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

        model.addAttribute("amount", course.getPrice() * 100); // in cents
        model.addAttribute("stripePublicKey", stripePublicKey);
        model.addAttribute("currency", ChargeRequest.Currency.USD);
        return "/course-details";
    }

    @PostMapping("/{courseId}/cart")
    public String addCourseToCart(@PathVariable("courseId") long courseId) throws CourseServiceException {
        String authUsername = SecurityUtil.getSessionUser();
        long categoryId = courseService.findCourseById(courseId).getCategories().get(0).getId();
        if(authUsername == null){
            return "redirect:/categories/" + categoryId + "/" + courseId + "?add_fails";
        }
        courseService.addCourseToUserCart(courseId, authUsername);
        return"redirect:/categories/" + categoryId + "/" + courseId + "?add_success";
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
    public String deleteFromCategoryPage(@PathVariable("categoryId") long categoryId, @PathVariable("courseId") long courseId) throws CourseServiceException, CategoryServiceException {
        courseService.deleteCourseFromCategory(courseId, categoryId);
        return "redirect:/categories/" + categoryId;
    }

    @PostMapping("/admin/{categoryId}/{courseId}/delete")
    public String deleteCourse(Model model, @PathVariable("categoryId") long categoryId, @PathVariable("courseId") long courseId) throws CourseServiceException, CategoryServiceException {
        courseService.deleteCourse(courseId);
        return "redirect:/categories/" + categoryId;
    }

    @GetMapping("/{categoryId}/courses/search")
    public String searchCategoryCourses(@RequestParam(value = "query") String query, Model model, @PathVariable long categoryId) throws CategoryServiceException, CourseServiceException {
        Category category = categoryService.findCategoryById(categoryId);
        List<Course> courses = courseService.searchCoursesForCategory(query, category);
        model.addAttribute("category", category);
        model.addAttribute("courses", courses);
        return "category-details";
    }

    @GetMapping("/courses/search")
    public String searchCourses(@RequestParam(value = "query") String query, Model model) throws CourseServiceException {
        List<Course> courses = courseService.searchCourses(query);
        model.addAttribute("courses", courses);
        return "courses";
    }
}
