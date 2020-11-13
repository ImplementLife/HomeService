package com.homeService.controllers.common;

import com.homeService.entity.Log;
import com.homeService.entity.User;
import com.homeService.services.LogService;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.regex.Pattern;

@Controller
public class RegistrationController {
    private final Pattern pattern = Pattern.compile(".+@.+\\..+");

    @Autowired
    private UserService userService;

    @Autowired
    LogService logService;

    @GetMapping("/registration")
    public String registration() {
        return "registration/index";
    }

    /**
     * В поле login передаётся email
     *
     *
     * @param login
     * @param password
     * @param passwordConfirm
     * @param model
     * @return
     */
    @PostMapping("/registration")
    public String addUser(
            @RequestParam(required = true, defaultValue = "" ) String login,
            @RequestParam(required = true, defaultValue = "" ) String password,
            @RequestParam(required = true, defaultValue = "" ) String passwordConfirm,
            Model model
    ) {
        model.addAttribute("login", login);
        model.addAttribute("password", password);
        model.addAttribute("passwordConfirm", passwordConfirm);
        if (login.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
            model.addAttribute("massageError", "Заполните обязательные поля");
            return "registration/index";
        }
        if (!pattern.matcher(login).matches()) {
            model.addAttribute("massageError", "Email не правильный!");
            return "registration/index";
        }
        if (!password.equals(passwordConfirm)){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration/index";
        }

        User user = new User();
        user.setUsername(login);
        user.setPassword(password);
        user.setPasswordConfirm(passwordConfirm);
        user.setEnabled(true);
        user.setAccountNonLocked(true);

        if (!userService.saveUser(user)){
            model.addAttribute("emailError", "Пользователь с таким email уже существует");
            return "registration/index";
        }
        logService.save(new Log("new User: " + login));
        return "redirect:/";
    }

}