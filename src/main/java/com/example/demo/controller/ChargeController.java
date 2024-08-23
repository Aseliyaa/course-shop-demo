package com.example.demo.controller;

import com.example.demo.EmailSender;
import com.example.demo.exception.CourseServiceException;
import com.example.demo.exception.StripeServiceException;
import com.example.demo.exception.UserServiceException;
import com.example.demo.model.ChargeRequest;
import com.example.demo.model.Course;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.CourseService;
import com.example.demo.service.StripeService;
import com.example.demo.service.UserService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ChargeController {
    private final StripeService paymentsService;
    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public ChargeController(StripeService paymentsService, CourseService courseService, UserService userService) {
        this.paymentsService = paymentsService;
        this.courseService = courseService;
        this.userService = userService;
    }
    @GetMapping("/{courseId}/charge")
    public String chargePage(){
        return "result";
    }
    @PostMapping("/{courseId}/charge")
    public String charge(ChargeRequest chargeRequest, Model model, @PathVariable long courseId)
            throws StripeException, CourseServiceException, UserServiceException, StripeServiceException {
        if (SecurityUtil.getSessionUser() == null){
            long categoryId = courseService.findCourseById(courseId).getCategories().get(0).getId();
            return "redirect:/categories/" + categoryId + "/" + courseId + "?fails";
        }
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.USD);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());

        Order order = new Order();
        Course course = courseService.findCourseById(courseId);
        User user = userService.findByLogin(SecurityUtil.getSessionUser());
        order.setCourse(course);
        order.setUser(user);
        order.setStatus(charge.getStatus());
        order.setEmail(charge.getReceiptEmail());
        paymentsService.saveOrder(order);
//        отправлять сообщение

//        if(paymentsService.saveOrder(order) && order.getStatus().equalsIgnoreCase("succeeded")){
//            EmailSender emailSender = new EmailSender();
//            emailSender.sendEmail(charge.getCustomer());
//        }

        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}