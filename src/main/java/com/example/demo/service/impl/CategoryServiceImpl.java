package com.example.demo.service.impl;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.exception.CommonServiceException;
import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.service.CategoryService;
import com.example.demo.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl extends CommonService<Category> implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category save(Category category) throws CommonServiceException {
        categoryRepository.save(category);
        return category;
    }

    @Override
    public boolean delete(Category category) throws CommonServiceException {
        categoryRepository.delete(category);
        return false;
    }

    @Override
    public List<Category> findAll() throws CommonServiceException {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category category) throws CommonServiceException {
        return null;
    }

    @Override
    public List<Category> findAllCategories() throws CategoryServiceException {
        try {
            return findAll();
        } catch (CommonServiceException e) {
            throw new CategoryServiceException(e);
        }
    }

    @Override
    public Category findCategoryById(long id) throws CategoryServiceException {
        return categoryRepository.findById(id).get();
    }
}
