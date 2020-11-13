package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.User;
import com.homeService.services.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    ProductService productService;

    @Autowired
    HeaderController headerController;

    @GetMapping("/")
    public String m(Model model, Principal principal) throws Exception {
        User currentUser;
        if (principal != null) {
            currentUser = (User) ((Authentication) principal).getPrincipal();
            System.err.println(currentUser.getUsername());
            headerController.init(model, currentUser);
        }

        model.addAttribute("products", productService.allProducts());
        return "main/index";
    }
}
