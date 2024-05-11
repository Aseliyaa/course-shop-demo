package com.example.demo.service.impl;

import com.example.demo.exception.CartServiceException;
import com.example.demo.exception.CategoryServiceException;
import com.example.demo.exception.CommonServiceException;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.model.*;
import com.example.demo.repository.ConsultationFormRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class CourseServiceImpl extends CommonService<Course> implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ConsultationFormRepository formRepository;
    private final CategoryService categoryService;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, CartService cartService, ConsultationFormRepository formRepository, CategoryService categoryService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;

        this.formRepository = formRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Course save(Course course) throws CommonServiceException {
        return courseRepository.save(course);
    }

    @Override
    public boolean delete(Course course) throws CommonServiceException {
        courseRepository.delete(course);
        return false;
    }

    @Override
    public List<Course> findAll() throws CommonServiceException {
        return courseRepository.findAll();
    }

    @Override
    public Course update(Course course) throws CommonServiceException {
        return courseRepository.save(course);
    }

    @Override
    public Course findCourseById(long courseId) throws CourseServiceException {
        return courseRepository.findById(courseId).get();
    }

    @Override
    public void addCourseToUserCart(long courseId, String login) throws CourseServiceException {
        try {
            User user = userRepository.findByLogin(login);
            if (user == null) {
                throw new UsernameNotFoundException("User " + login + " not found");
            }
            Cart cart = user.getCart();
            Course course = courseRepository.findById(courseId).get();

            if (cart == null) {
                Cart newCart = cartService.createCart(user);
                cartService.addCourse(course, newCart);
                user.setCart(newCart);
                userRepository.save(user);
            } else {
                cartService.addCourse(course, cart);
            }
        } catch (CartServiceException e) {
            throw new CourseServiceException(e);
        }
    }

    @Override
    public void addFormToUser(String login, long courseId, ConsultationForm form) throws CourseServiceException {
        User user = userRepository.findByLogin(login);
        Course course = courseRepository.findById(courseId).get();

        if (user != null) {
            form.setUser(user);
            form.setCourse(course);
            formRepository.save(form);
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Override
    public void updateCourse(Course course) throws CourseServiceException {
        try {
            update(course);
        } catch (CommonServiceException e) {
            throw new CourseServiceException(e);
        }
    }

    @Override
    public void deleteCourseFromCategory(long courseId, long categoryId) throws CourseServiceException {
        try {
            Category category = categoryService.findCategoryById(categoryId);
            Course course = courseRepository.findById(courseId).get();
            category.getCourses().remove(course);
            categoryService.saveCategory(category);
        } catch (CategoryServiceException e) {
            throw new CourseServiceException(e);
        }
    }

    @Override
    public void deleteCourse(long courseId) throws CourseServiceException {
        Course course = courseRepository.findById(courseId).get();
        try {
            delete(course);
        } catch (CommonServiceException e) {
            throw new CourseServiceException(e);
        }
    }

    @Override
    public List<Course> findAllCourses() throws CourseServiceException {
        try {
            return findAll();
        } catch (CommonServiceException e) {
            throw new CourseServiceException(e);
        }
    }

    @Override
    public void saveCourse(Course course) throws CourseServiceException {
        try{
            save(course);
        } catch (CommonServiceException e) {
            throw new CourseServiceException(e);
        }
    }

    @Override
    public List<Course> searchCoursesForCategory(String query, Category category) throws CourseServiceException {
        List<Course> searchCourses = new ArrayList<>();
        for (Course course: category.getCourses()){
            if (course.getCourseName().toLowerCase().contains(query.toLowerCase())){
                searchCourses.add(course);
            }
        }

        return searchCourses;
    }

}
