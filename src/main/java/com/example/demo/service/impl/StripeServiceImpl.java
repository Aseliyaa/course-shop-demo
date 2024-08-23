package com.example.demo.service.impl;

import com.example.demo.exception.StripeServiceException;
import com.example.demo.model.ChargeRequest;
import com.example.demo.model.Course;
import com.example.demo.model.Order;
import com.example.demo.model.User;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeServiceImpl implements StripeService {
    private final OrderRepository orderRepository;

    @Autowired
    public StripeServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }
    public Charge charge(ChargeRequest chargeRequest)
            throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }

    @Override
    public boolean saveOrder(Order order) throws StripeServiceException {
        try {
            orderRepository.save(order);
            return true;
        } catch (Exception e){
            return false;
        }
    }

}
