package com.homeService.controllers;

import com.homeService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String m(Model model) throws Exception {
        model.addAttribute("products", productService.allProducts());
        return "main/index";
    }
}
