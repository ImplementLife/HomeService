package com.homeService.controllers.admin;

import com.homeService.controllers.common.HeaderController;
import com.homeService.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class LogController {
    @Autowired private LogService logService;
    @Autowired private HeaderController headerController;

    @GetMapping("/admin/log")
    public String getLog(Model model, Principal principal) {
        headerController.init(model, principal);
        model.addAttribute("logs", logService.findAll());
        return "admin/adminLog/index";
    }
}
