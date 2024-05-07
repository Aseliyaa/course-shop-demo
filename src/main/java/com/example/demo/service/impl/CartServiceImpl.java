package com.example.demo.service.impl;

import com.example.demo.exception.CartServiceException;
import com.example.demo.model.Cart;
import com.example.demo.model.Course;
import com.example.demo.model.User;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.CourseRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;


@Service
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    @Autowired
    public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
    }


    @Override
    public Cart createCart(User user) throws CartServiceException {
        Cart cart = new Cart();
        if (user != null) {
            cart.setUser(user);
        } else throw new CartServiceException();

        return cartRepository.save(cart);
    }

    @Override
    public void addCourse(Course course, Cart cart) throws CartServiceException {
        Cart updatedCart = cartRepository.findById(cart.getId()).get();
        List<Course> courses = updatedCart.getCourses();
        if(!courses.contains(course)) {
            updatedCart.getCourses().add(course);
            cartRepository.save(updatedCart);
        }
    }

    @Override
    public void removeCourse(Course course) throws CartServiceException {

    }

    @Override
    public Cart getCartByLogin(String login) throws CartServiceException {
        User user = userRepository.findByLogin(login);
        if(user == null || user.getCart() == null){
            return new Cart();
        }
        return user.getCart();
    }

    @Override
    public Double getTotal(Cart cart) throws CartServiceException {
        double total = 0;
        List<Course> courseList = cart.getCourses();
        try {
            for (Course course : courseList) {
                total += Double.parseDouble(course.getPrice());
            }
        } catch (NumberFormatException e){
            throw new CartServiceException(e);
        }
        return total;
    }
}
