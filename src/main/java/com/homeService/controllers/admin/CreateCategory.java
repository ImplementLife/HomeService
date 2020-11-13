package com.homeService.controllers.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateCategory {
    @GetMapping("/admin/createCategory")
    public String createProduct() {
        return "createCategory";
    }
}
