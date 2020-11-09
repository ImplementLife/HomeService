package com.homeService.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatSearchController {

    @GetMapping("f")
    public String get(Model model) {

        return "catSearch";
    }


}
