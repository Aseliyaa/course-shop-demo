package com.example.demo.service.impl;

import com.example.demo.exception.CartServiceException;
import com.example.demo.exception.CommonServiceException;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.model.Cart;
import com.example.demo.model.ConsultationForm;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.ConsultationFormRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl extends CommonService<Course> implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CartService cartService;
    private final ConsultationFormRepository formRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, UserRepository userRepository, CartService cartService, ConsultationFormRepository formRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.cartService = cartService;

        this.formRepository = formRepository;
    }

    @Override
    public Course save(Course course) throws CommonServiceException {
        return null;
    }

    @Override
    public boolean delete(Course course) throws CommonServiceException {
        return false;
    }

    @Override
    public List<Course> findAll() throws CommonServiceException {
        return null;
    }

    @Override
    public Course update(Course course) throws CommonServiceException {
        return null;
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
}
