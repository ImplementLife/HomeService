package com.homeService.controllers.common;

import com.homeService.controllers.Lib;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {
    private static final Pattern PATTERN = Pattern.compile(".+@.+\\..+");

    @Autowired UserService userService;
    @Autowired LogService logService;
    @Autowired private HeaderController headerController;

    @GetMapping("/registration")
    public String registration(HttpServletRequest request, Model model, Principal principal) {
        headerController.init(model, principal);
        model.addAttribute("referer", new Lib().ref(request));
        return "auth/registration/index";
    }

    /**
     * В поле login передаётся email
     */
    @PostMapping("/registration")
    public String addUser(
            @RequestParam(defaultValue = "" ) String login,
            @RequestParam(defaultValue = "" ) String password,
            @RequestParam(defaultValue = "" ) String passwordConfirm,
            @RequestParam(defaultValue = "" ) String referer,
            @RequestParam(defaultValue = "" ) String phone,
            @RequestParam(defaultValue = "" ) String firstName,
            @RequestParam(defaultValue = "" ) String lastName,
            Model model, Principal principal
    ) {
        headerController.init(model, principal);
        User user = new User();
        user.setUsername(login);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setPassword(password);
        user.setPasswordConfirm(passwordConfirm);
        user.setEnabled(true);
        user.setAccountNonLocked(true);

        model.addAttribute("user", user);
        model.addAttribute("referer", referer);
        {
            if (!PATTERN.matcher(login).matches()) {
                model.addAttribute("massageError", "Email содержит ошибку!");
                return "auth/registration/index";
            }
            if (!password.equals(passwordConfirm)){
                model.addAttribute("passwordError", "Пароли не совпадают");
                return "auth/registration/index";
            }
        }
        {
            if (!userService.saveUser(user)){
                model.addAttribute("emailError", "Пользователь с таким email уже существует");
                return "auth/registration/index";
            }
            logService.save(new Log("new User: " + login));
        }
        return "redirect:" + referer;
    }

}