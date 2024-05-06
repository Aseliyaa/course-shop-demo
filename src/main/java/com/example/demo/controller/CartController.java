package com.example.demo.controller;

import com.example.demo.exception.CartServiceException;
import com.example.demo.model.Cart;
import com.example.demo.security.SecurityUtil;
import com.example.demo.service.CartService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {
    private final CartService cartService;
    Logger logger = LogManager.getLogger();

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String bucket(Model model) throws CartServiceException {
        String authUser = SecurityUtil.getSessionUser();
        logger.log(Level.INFO, "-------------> Username: " + authUser);
        if(authUser == null){
            model.addAttribute("fails", "You must be logged in!");
            model.addAttribute("cart", null);
        } else {
            Cart cart = cartService.getCartByLogin(authUser);
            model.addAttribute("cart", cart);
        }
        return "/cart";
    }
}
