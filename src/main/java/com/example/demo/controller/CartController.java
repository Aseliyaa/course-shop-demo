package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class CartController {

    @GetMapping("/bucket")
    public String bucket(){
        return "/bucket";
    }
}
