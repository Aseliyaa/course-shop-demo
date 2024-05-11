package com.example.demo.service.impl;

import com.example.demo.exception.CategoryServiceException;
import com.example.demo.exception.CommonServiceException;
import com.example.demo.model.Category;
import com.example.demo.model.Course;
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
        return categoryRepository.save(category);
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

    @Override
    public void updateCategory(Category category) throws CategoryServiceException {
        try {
            update(category);
        } catch (CommonServiceException e) {
            throw new CategoryServiceException(e);
        }
    }

    @Override
    public List<Category> searchCategories(String query) throws CategoryServiceException {
        return categoryRepository.searchCategories(query);
    }

    @Override
    public void saveCategory(Category category) throws CategoryServiceException {
        try {
            save(category);
        } catch (CommonServiceException e) {
            throw new CategoryServiceException(e);
        }
    }

    @Override
    public void addCourseToCategory(Category category, Course course) throws CategoryServiceException {
        category.getCourses().add(course);
//        course.getCategories().add(category);
        categoryRepository.save(category);

    }

    @Override
    public void deleteCategory(Category category) throws CategoryServiceException {
        try{
            delete(category);
        } catch (CommonServiceException e) {
            throw new CategoryServiceException(e);
        }
    }
}
