package com.homeService.controllers.user;

import com.homeService.controllers.common.HeaderController;
import com.homeService.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class PrivateProfile {

    @Autowired
    private HeaderController headerController;

    @GetMapping("/privateProfile")
    public String getPrivateProfile(Model model, Principal principal) {
        if (principal != null) {
            User currentUser = (User) ((Authentication) principal).getPrincipal();
            headerController.init(model, currentUser);

            return "privateProfile/index";
        }
        return "redirect:/login";
    }

}
