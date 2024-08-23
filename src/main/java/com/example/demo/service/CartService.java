package com.example.demo.service;

import com.example.demo.exception.CartServiceException;
import com.example.demo.model.Cart;
import com.example.demo.model.Course;
import com.example.demo.model.User;

public interface CartService{
    Cart createCart(User user) throws CartServiceException;
    void addCourse(Course course, Cart cart) throws CartServiceException;
    void removeCourse(Course course) throws CartServiceException;
    Cart getCartByLogin(String login) throws CartServiceException;

    void deleteCourseFromUserCart(String login, long courseId) throws CartServiceException;
}
