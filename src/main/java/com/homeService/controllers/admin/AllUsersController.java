package com.homeService.controllers.admin;

import com.homeService.controllers.common.HeaderController;
import com.homeService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AllUsersController {
    @Autowired private UserService userService;
    @Autowired private HeaderController headerController;
    @GetMapping("/admin/allUsers")
    public String getAllUsers(Principal principal, Model model) {
        headerController.init(model, principal);
        model.addAttribute("allUsers", userService.allUsers());
        return "admin/tableAllUsers/index";
    }

}
