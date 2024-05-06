package com.example.demo.service;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories() throws CategoryServiceException;

    Category findCategoryById(long id) throws CategoryServiceException;
}
