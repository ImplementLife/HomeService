package com.homeService.controllers;

import com.homeService.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

/**
 * Контроллер для "шапки" сайта,
 * подключается к другим контроллерам
 */
@Component
public class HeaderController {

    public HeaderController() {}

    public void init(Model model, User currentUser) {

    }

}
