package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.Category;
import com.homeService.entity.Product;
import com.homeService.services.CategoryService;
import com.homeService.services.ProductService;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class MainController {
    @Autowired ProductService productService;
    @Autowired CategoryService categoryService;
    @Autowired UserService userService;
    @Autowired HeaderController headerController;

    @GetMapping("/")
    public String main(Model model, Principal principal) throws Exception {
        headerController.init(model, principal);
        Collection<Product> products = new ArrayList<>();
        products.addAll(productService.findAllByIsPublic(true));
        //Работа с текущим пользователем
        {
//            if (principal != null) {
//                User currentUser;
//                currentUser = (User) ((Authentication) principal).getPrincipal();
//                headerController.initForCurrentUser(model, currentUser);
//                for (Product product : products) {
//                    for (Product productUser : userService.getFavoriteProducts(currentUser)) {
//                        if (product.getId().equals(productUser.getId())) product.setFavorite(true);
//                    }
//                }
//            }
        }

        List<Category> categories = categoryService.findAll();

        model.addAttribute("newProducts", products);
        model.addAttribute("categories", categories);
        return "viewData/main/index";
    }
}
