package com.example.demo.controller;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.Course;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;
    private final CourseService courseService;

    @Autowired
    public CategoryController(CategoryService categoryService, CourseService courseService) {
        this.categoryService = categoryService;
        this.courseService = courseService;
    }

    @GetMapping("/categories")
    public String categories(Model model) throws CategoryServiceException {
        try {
            List<Category> categories = categoryService.findAllCategories();
            model.addAttribute("categories", categories);
        } catch (CategoryServiceException e) {
            throw new CategoryServiceException(e);
        }
        return "/index";
    }

    @PostMapping("/categories")
    public String logoutSubmit(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @GetMapping("/categories/{categoryId}")
    public String categoryById(@PathVariable("categoryId") long categoryId, Model model) throws CategoryServiceException {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            model.addAttribute("courses", category.getCourses());
            model.addAttribute("category", category);
            return "category-details";
        } catch (Exception e) {
            throw new CategoryServiceException(e);
        }
    }

    @GetMapping("/admin/categories/{categoryId}/edit")
    public String editCategoryPage(@PathVariable("categoryId") long categoryId, Model model) throws CategoryServiceException, CourseServiceException {
        Category category = categoryService.findCategoryById(categoryId);
        List<Course> categoryCourses = category.getCourses();
        List<Course> allCourses = courseService.findAllCourses();
        allCourses.removeAll(categoryCourses);
        model.addAttribute("category", category);
        model.addAttribute("courses", allCourses);
        return "category-edit";
    }

    @PostMapping("/admin/categories/{categoryId}/edit")
    public String editCategory(@ModelAttribute("category") Category category, @PathVariable("categoryId") long categoryId) throws CategoryServiceException {
        Category updatedCategory = categoryService.findCategoryById(categoryId);
        category.setCourses(updatedCategory.getCourses());
        category.setId(categoryId);
        categoryService.updateCategory(category);
        return "redirect:/categories";
    }

    @GetMapping("/categories/search")
    public String searchCategory(@RequestParam(value = "query") String query, Model model) throws CategoryServiceException {
        List<Category> categories = categoryService.searchCategories(query);
        model.addAttribute("categories", categories);
        return "index";
    }

    @PostMapping("/admin/{categoryId}/addCourse/{courseId}")
    public String addCourseToCategory(@PathVariable("categoryId") long categoryId, @PathVariable("courseId") long courseId) throws CategoryServiceException, CourseServiceException {
        Category category = categoryService.findCategoryById(categoryId);
        Course course = courseService.findCourseById(courseId);
        categoryService.addCourseToCategory(category, course);
        return "redirect:/categories/" + categoryId;
    }

    @GetMapping("/admin/{categoryId}/createCourse")
    public String createCoursePage(Model model, @PathVariable("categoryId") long categoryId) throws CategoryServiceException {
        Category category = categoryService.findCategoryById(categoryId);
        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("category", category);
        return "create-course";
    }
    @PostMapping("/admin/{categoryId}/createCourse")
    public String createCourse(@ModelAttribute("course") Course course, @PathVariable("categoryId") long categoryId) throws CategoryServiceException, CourseServiceException {
        Category category = categoryService.findCategoryById(categoryId);
        courseService.saveCourse(course);
        category.getCourses().add(course);
        categoryService.saveCategory(category);
        return "redirect:/categories/" + categoryId;
    }

    @PostMapping("/admin/{categoryId}/delete")
    public String deleteCategory(@PathVariable("categoryId") long categoryId) throws CategoryServiceException {
        Category category = categoryService.findCategoryById(categoryId);
        categoryService.deleteCategory(category);
        return "redirect:/categories";
    }
}
