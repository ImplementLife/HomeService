package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Product;
import com.homeService.entity.User;
import com.homeService.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@Controller
public class CardProductController {

    @Autowired
    ProductService productService;
    @Autowired
    private HeaderController headerController;

    @GetMapping("/product/{id}")
    public String get(
            Model model,
            Principal principal,
            @PathVariable("id") String id
    ) throws Exception {
        User currentUser;
        if (principal != null) {
            currentUser = (User) ((Authentication) principal).getPrincipal();
            headerController.init(model, currentUser);
        }
        Product product = productService.findProductById(Long.parseLong(id));

        model.addAttribute("product", product);
        return "cardProduct/index";
    }


}
