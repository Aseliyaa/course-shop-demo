package com.example.demo.controller;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
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
}
