package com.homeService.controllers.common;

import com.homeService.entity.User;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Контроллер для "шапки" сайта,
 * подключается к другим контроллерам
 */
@Component
public class HeaderController {
    @Autowired
    UserService userService;
    public void init(Model model, User currentUser) throws Exception {
        model.addAttribute("countFavorite", userService.getFavoriteProducts(currentUser).size());
        model.addAttribute("countCart", userService.getCartProducts(currentUser).size());
    }
}
