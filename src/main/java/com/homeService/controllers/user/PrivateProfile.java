package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Product;
import com.homeService.entity.User;
import com.homeService.services.ProductService;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class PrivateProfile {
    @Autowired ProductService productService;
    @Autowired UserService userService;
    @Autowired private HeaderController headerController;

    @GetMapping("/privateProfile")
    public String getPrivateProfile(
        @RequestParam String tab,
        @RequestParam String productsCart,
        @RequestParam String productsFavorite,
        Principal principal,
        Model model
    ) throws Exception {
        headerController.init(model, principal);
        Collection<Product> favoriteProducts = new ArrayList<>();
        Collection<Product> cartProducts = new ArrayList<>();

        if (productsFavorite != null && !productsFavorite.isEmpty()) {
            ArrayList<Long> favorites = new ArrayList<>();
            for (String str : productsFavorite.split("\\s")) favorites.add(Long.parseLong(str.trim()));
            favoriteProducts.addAll(productService.findAllById(favorites));
        }
        int finalPrice = 0;
        if (productsCart != null && !productsCart.isEmpty()) {
            ArrayList<Long> cart = new ArrayList<>();
            for (String str : productsCart.split("\\s")) cart.add(Long.parseLong(str.trim()));
            cartProducts.addAll(productService.findAllById(cart));
            for (Product p : cartProducts) finalPrice += Long.parseLong(p.getDefaultPrice());
        }

        for (Product p : favoriteProducts) p.setFavorite(true);
        model.addAttribute("finalPrice", finalPrice);
        model.addAttribute("tab", tab);
        model.addAttribute("favoriteProducts", favoriteProducts);
        model.addAttribute("cartProducts", cartProducts);

        return "viewData/privateProfile/index";
    }

}
