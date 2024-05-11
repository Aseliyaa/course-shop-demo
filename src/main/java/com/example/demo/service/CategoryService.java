package com.example.demo.service;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.Course;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories() throws CategoryServiceException;

    Category findCategoryById(long id) throws CategoryServiceException;

    void updateCategory(Category category) throws CategoryServiceException;
    List<Category> searchCategories(String query) throws CategoryServiceException;
    void saveCategory(Category category) throws CategoryServiceException;

    void addCourseToCategory(Category category, Course course) throws CategoryServiceException;

    void deleteCategory(Category category) throws CategoryServiceException;
}
