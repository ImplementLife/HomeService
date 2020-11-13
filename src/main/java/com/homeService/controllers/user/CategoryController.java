package com.homeService.controllers.user;

import com.homeService.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/search/categories")
    public String getCategories(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/index";
    }
    @GetMapping("/search/categories/{category}")
    public String get(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/index";
    }




}
