package com.homeService.controllers.common;

import com.homeService.entity.Log;
import com.homeService.entity.User;
import com.homeService.services.CategoryService;
import com.homeService.services.LogService;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.security.Principal;

/**
 * Контроллер для "шапки" сайта,
 * подключается к другим контроллерам
 */
@Component
public class HeaderController {
    @Autowired UserService userService;
    @Autowired CategoryService categoryService;
    @Autowired LogService logService;
    private User currentUser;

    public void init(Model model, Principal principal) {
        boolean isAdmin = false, auth = false;
        if (principal != null) {
            currentUser = (User) ((Authentication) principal).getPrincipal();
            if (currentUser != null) auth = true;
            try {
                model.addAttribute("countFavorite", userService.getFavoriteProducts(currentUser).size());
                model.addAttribute("countCart", userService.getCartProducts(currentUser).size());
                for (GrantedAuthority GA : currentUser.getAuthorities()) if (GA.getAuthority().equals("ROLE_ADMIN")) isAdmin = true;
            } catch (Exception e) {
                logService.save(new Log("Error in HeaderController (init user). message: " + e.getMessage()));
                e.printStackTrace();
            }
        }
        model.addAttribute("allCategories", categoryService.getAllNotHaveParent());
        model.addAttribute("auth", auth);
        model.addAttribute("isAdmin", isAdmin);
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
