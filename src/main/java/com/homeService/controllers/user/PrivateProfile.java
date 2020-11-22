package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Product;
import com.homeService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class PrivateProfile {

    @Autowired
    private HeaderController headerController;

    @GetMapping("/privateProfile")
    public String getPrivateProfile(Model model, Principal principal) {
        Collection<Product> favoriteProducts = new ArrayList<>();
        Collection<Product> cartProducts = new ArrayList<>();

        if (principal != null) {
            User currentUser = (User) ((Authentication) principal).getPrincipal();
            headerController.init(model, currentUser);
            favoriteProducts = currentUser.getFavoriteProducts();
            cartProducts = currentUser.getCartProducts();
        }

        model.addAttribute("favoriteProducts", favoriteProducts);
        model.addAttribute("cartProducts", cartProducts);

        return "privateProfile/index";
    }

}
