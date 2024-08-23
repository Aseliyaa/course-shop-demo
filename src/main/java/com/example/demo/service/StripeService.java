package com.example.demo.service;

import com.example.demo.exception.StripeServiceException;
import com.example.demo.model.ChargeRequest;
import com.example.demo.model.Course;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

public interface StripeService {
    Charge charge(ChargeRequest chargeRequest) throws StripeException;

    boolean saveOrder(Order order) throws StripeServiceException;
}
