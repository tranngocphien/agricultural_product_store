package com.example.agricultural_product_store.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/order")
public class OrderController {
    @GetMapping(name = "/list")
    public String list() {
        return "a";
    }
}
