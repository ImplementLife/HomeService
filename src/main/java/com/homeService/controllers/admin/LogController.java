package com.homeService.controllers.admin;

import com.homeService.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogController {
    @Autowired
    LogService logService;

    @GetMapping("/admin/log")
    public String getLog(Model model) {
        model.addAttribute("logs", logService.findAll());
        return "adminLog/index";
    }
}
