package com.homeService.controllers;

import com.homeService.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CardProductController {

    @GetMapping("/product/{id}")
    public String get(Model model, @PathVariable("id") String id) {
        Product product = new Product();
        model.addAttribute("product", product);
        return "cardProduct";
    }


}
